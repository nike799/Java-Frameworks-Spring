package org.softuni.cardealer.web.controllers;

import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.softuni.cardealer.domain.entities.Part;
import org.softuni.cardealer.domain.entities.Supplier;
import org.softuni.cardealer.repository.PartRepository;
import org.softuni.cardealer.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.nio.charset.Charset;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class PartsControllerTest {
    private static final MediaType APPLICATION_JSON_UTF8 = new MediaType(
            MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8")
    );
    @Autowired
    private MockMvc mvc;
    @Autowired
    private PartRepository partRepository;
    @Autowired
    private SupplierRepository supplierRepository;
    @Autowired
    private Gson gson;

    @Before
    public void emptyDB() {
        this.partRepository.deleteAll();
        this.supplierRepository.deleteAll();
    }

    @Test
    @WithMockUser
    public void allParts_returnCorrectView() throws Exception {
        this.mvc.perform(get("/parts/all"))
                .andExpect(view().name("all-parts"));
    }

    @Test
    @WithMockUser
    public void fetchParts_returnCorrectData() throws Exception {
        Supplier supplier = this.createSupplier();
        supplier = this.supplierRepository.save(supplier);

        Part part1 = createPart(supplier);
        this.partRepository.saveAndFlush(part1);

        Part part2 = createPart(supplier);
        this.partRepository.saveAndFlush(part2);

        String result = this.mvc
                .perform(get("/parts/fetch"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andReturn().getResponse().getContentAsString();

        Part[] parts = this.gson.fromJson(result, Part[].class);

        Part actualFirst = this.partRepository.findAll().get(0);

        Assert.assertEquals(parts[0].getId(), actualFirst.getId());
        Assert.assertEquals(parts[0].getName(), actualFirst.getName());
        Assert.assertEquals(parts.length, this.partRepository.count());
    }

    @Test
    @WithMockUser
    public void addPart_confirmAddingPart() throws Exception {
        Supplier supplier = createSupplier();

        this.mvc
                .perform(post("/parts/add")
                        .param("name", "Part")
                        .param("price", "100")
                        .param("supplier", supplier.getName()));

        Part actual = this.partRepository.findAll().get(0);
        Assert.assertEquals(1, this.partRepository.count());
        Assert.assertEquals("Part", actual.getName());
    }

    @Test
    @WithMockUser
    public void addPart_returnCorrectView() throws Exception {
        Supplier supplier = createSupplier();

        this.mvc
                .perform(post("/parts/add")
                        .param("name", "Part")
                        .param("price", "100")
                        .param("supplier", supplier.getName()))
                .andExpect(redirectedUrl("all"));

    }

    @Test
    @WithMockUser
    public void edit_confirmEditPart() throws Exception {

        Supplier supplier = this.createSupplier();
        Part part = this.createPart(supplier);
        this.partRepository.save(part);

        this.mvc
                .perform(post("/parts/edit/" + part.getId())
                        .param("name", "motor")
                        .param("price", "20.00"));

        Part actualPart = this.partRepository.findById(part.getId()).get();

        Assert.assertEquals("motor", actualPart.getName());
        Assert.assertEquals("20.00", String.valueOf(actualPart.getPrice()));

    }

    @Test
    @WithMockUser
    public void delete_confirmPartIsDeleted() throws Exception {
        Supplier supplier = this.createSupplier();
        Part part = this.createPart(supplier);
        this.partRepository.save(part);

        long countPartRepositoryBefore = this.partRepository.count();

        this.mvc.perform(post("/parts/delete/" + part.getId())
        );

        long countPartRepositoryAfter = this.partRepository.count();

        Assert.assertEquals(countPartRepositoryBefore, 1);
        Assert.assertEquals(countPartRepositoryAfter, 0);
    }

    private Part createPart(Supplier supplier) {
        Part part = new Part();
        part.setName("engine");
        part.setPrice(BigDecimal.TEN);
        part.setSupplier(supplier);
        return part;
    }

    private Supplier createSupplier() {
        Supplier supplier = new Supplier();
        supplier.setName("originalParts");
        supplier.setIsImporter(true);
        return this.supplierRepository.save(supplier);
    }
}

package org.softuni.cardealer.web.controllers;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.softuni.cardealer.domain.entities.Supplier;
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

import java.nio.charset.Charset;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class SuppliersControllerTest {
    private static final MediaType APPLICATION_JSON_UTF8 = new MediaType(
            MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8")
    );
    @Autowired
    private MockMvc mvc;
    @Autowired
    private SupplierRepository supplierRepository;

    @Before
    public void emptyDB() {
        this.supplierRepository.deleteAll();
    }

    @Test
    @WithMockUser
    public void allSuppliers_returnCorrectView() throws Exception {
        this.mvc
                .perform(get("/suppliers/all"))
                .andExpect(view().name("all-suppliers"));
    }

    @Test
    @WithMockUser
    public void addSupplier_confirmAddSupplier() throws Exception {
        this.mvc
                .perform(post("/suppliers/add")
                        .param("name", "originalParts")
                        .param("isImporter", "true"));
        Assert.assertEquals(1, this.supplierRepository.count());
    }

    @Test
    @WithMockUser
    public void editSupplier_confirmEditSupplier() throws Exception {
        Supplier first = new Supplier();
        first.setName("softuni");
        first.setIsImporter(true);

        Supplier second = new Supplier();
        second.setName("unisoft");
        second.setIsImporter(true);
        this.supplierRepository.save(first);
        this.supplierRepository.save(second);

        this.mvc
                .perform(post("/suppliers/edit/" + first.getId())
                        .param("name", "originalParts")
                        .param("isImporter", "false"));
        this.mvc
                .perform(post("/suppliers/edit/" + second.getId())
                        .param("name", "partsOriginal")
                        .param("isImporter", "false"));

        Supplier actual1 = this.supplierRepository.findById(first.getId()).get();
        Supplier actual2 = this.supplierRepository.findById(second.getId()).get();

        Assert.assertEquals("originalParts", actual1.getName());
        Assert.assertFalse(actual1.getIsImporter());
        Assert.assertEquals("partsOriginal", actual2.getName());
        Assert.assertFalse(actual2.getIsImporter());

    }

    @Test
    @WithMockUser
    public void delete_confirmSupplierIsDeleted() throws Exception {
        Supplier supplier = new Supplier();
        supplier.setName("originalParts");
        supplier.setIsImporter(true);
        supplier = this.supplierRepository.save(supplier);
        long countSupplierRepositoryBefore = this.supplierRepository.count();
        this.mvc.perform(post("/suppliers/delete/" + supplier.getId())
        );
        long countSupplierRepositoryAfter = this.supplierRepository.count();
        Assert.assertEquals(countSupplierRepositoryBefore, 1);
        Assert.assertEquals(countSupplierRepositoryAfter, 0);
    }

    @Test
    @WithMockUser
    public void fetchSuppliers_returnCorrectData() throws Exception {
        Supplier supplier = new Supplier();
        supplier.setName("originalParts");
        supplier.setIsImporter(true);
        this.supplierRepository.save(supplier);

        this.mvc.perform(get("/suppliers/fetch"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(content().json("[{\"name\":originalParts,\"isImporter\":true}]"));
    }
}

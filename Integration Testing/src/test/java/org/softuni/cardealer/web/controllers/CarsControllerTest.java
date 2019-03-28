package org.softuni.cardealer.web.controllers;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.softuni.cardealer.domain.entities.Car;
import org.softuni.cardealer.domain.entities.Part;
import org.softuni.cardealer.domain.entities.Supplier;
import org.softuni.cardealer.repository.CarRepository;
import org.softuni.cardealer.repository.PartRepository;
import org.softuni.cardealer.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class CarsControllerTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private CarRepository carRepository;
    @Autowired
    private PartRepository partRepository;
    @Autowired
    private SupplierRepository supplierRepository;

    @Before
    public void emptyDB() {
        this.carRepository.deleteAll();
    }
    @Test
    @WithMockUser
    public void allCars_returnCorrectView() throws Exception {
        this.mvc.perform(get("/cars/all"))
                .andExpect(view().name("all-cars"));
    }

    @Test
    @WithMockUser
    public void addCar_confirmAddingCar() throws Exception {
        Supplier supplier = createSupplier();
        Part part = this.createPart(supplier);
        this.partRepository.save(part);
        this.mvc
                .perform(post("/cars/add")
                        .param("make", "Peugeot")
                        .param("model", "307")
                        .param("travelledDistance", "200000")
                        .param("parts", part.getName())
                );

        Car actual = this.carRepository.findAll().get(0);
        Assert.assertEquals(1, this.carRepository.count());
        Assert.assertEquals("Peugeot", actual.getMake());
    }
    @Test
    @WithMockUser
    public void addCar_returnCorrectView() throws Exception {
        Supplier supplier = createSupplier();
        Part part = this.createPart(supplier);
        this.partRepository.save(part);
        this.mvc
                .perform(post("/cars/add")
                        .param("make", "Peugeot")
                        .param("model", "307")
                        .param("travelledDistance", "200000")
                        .param("parts", part.getName()))
                .andExpect(redirectedUrl("all"));
    }

    @Test
    @WithMockUser
    public void delete_confirmCarIsDeleted() throws Exception {
        Car car = this.createCar();
        Supplier supplier = this.createSupplier();
        Part part = this.createPart(supplier);

        part = this.partRepository.save(part);

        List<Part> parts = new ArrayList<>();
        parts.add(part);
        car.setParts(parts);

        long countPartRepositoryBefore = this.partRepository.count();
        this.mvc.perform(post("/parts/delete/" + part.getId()));
        long countPartRepositoryAfter = this.partRepository.count();

        Assert.assertEquals(countPartRepositoryBefore, 1);
        Assert.assertEquals(countPartRepositoryAfter, 0);
    }

    @Test
    @WithMockUser
    public void edit_confirmEditCar() throws Exception {

        Car car = this.createCar();
        Supplier supplier = this.createSupplier();
        Part part = this.createPart(supplier);

        part = this.partRepository.save(part);

        List<Part> parts = new ArrayList<>();
        parts.add(part);
        car.setParts(parts);
        car = this.carRepository.save(car);

        this.mvc
                .perform(post("/cars/edit/" + car.getId())
                        .param("make", "Renault")
                        .param("model", "Laguna")
                        .param("travelledDistance", "10000"));

        Car actualCar = this.carRepository.findById(car.getId()).get();

        Assert.assertEquals("Renault", actualCar.getMake());
        Assert.assertEquals("Laguna", actualCar.getModel());
        Assert.assertEquals("10000", String.valueOf(actualCar.getTravelledDistance()));

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

    private Car createCar() {
        Car car = new Car();
        car.setMake("Peugeot");
        car.setModel("307");
        car.setTravelledDistance((long) 150000);
        return car;
    }
}

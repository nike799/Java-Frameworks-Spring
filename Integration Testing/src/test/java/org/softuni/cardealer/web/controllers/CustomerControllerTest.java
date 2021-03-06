package org.softuni.cardealer.web.controllers;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.softuni.cardealer.repository.CustomerRepository;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class CustomerControllerTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private CustomerRepository customerRepository;

    @Test
    @WithMockUser
    public void allCustomers_returnCorrectMvc() throws Exception {
        this.mvc.perform(get("/customers/all"))
                .andExpect(view().name("all-customers"));
    }

    @Test
    @WithMockUser
    public void addCustomer_confirmAddCustomer() throws Exception {
        this.mvc
                .perform(post("/customers/add")
                        .param("name", "originalParts")
                        .param("birthDate", "1994-08-31")
                        .param("isYoungDriver", "true"));
        Assert.assertEquals(1, this.customerRepository.count());
    }
}

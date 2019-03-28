package org.softuni.cardealer.web.controllers;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.softuni.cardealer.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class UserControllerTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private UserRepository userRepository;

    @Before
    public void emptyDB() {
        this.userRepository.deleteAll();
    }

    @Test
    public void login_returnCorrectView() throws Exception {
        this.mvc
                .perform(get("/users/login"))
                .andExpect(view().name("login"));
    }

    @Test
    public void register_returnCorrectView() throws Exception {
        this.mvc
                .perform(get("/users/register"))
                .andExpect(view().name("register"));
    }

    @Test
    public void register_confirmRegister() throws Exception {
        this.mvc
                .perform(post("/users/register")
                        .param("username", "nike")
                        .param("password", "123")
                        .param("confirmPassword", "123")
                        .param("email", "nike@mail.bg"));

        Assert.assertEquals(1, this.userRepository.count());
    }

    @Test
    public void register_returnRedirectCorrect() throws Exception {
        this.mvc
                .perform(post("/users/register")
                        .param("username", "nike")
                        .param("password", "123")
                        .param("confirmPassword", "123")
                        .param("email", "nike@mail.bg"))
                .andExpect(view().name("redirect:login"));
    }
}

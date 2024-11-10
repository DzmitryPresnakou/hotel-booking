package com.presnakov.hotelbooking.integration.http.controller;

import com.presnakov.hotelbooking.integration.IntegrationTestBase;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static com.presnakov.hotelbooking.dto.UserCreateEditDto.Fields.*;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@AutoConfigureMockMvc
@RequiredArgsConstructor
class UserControllerTest extends IntegrationTestBase {

    private final MockMvc mockMvc;

    @Test
    void findAll() throws Exception {
        mockMvc.perform(get("/users"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("user/users"))
                .andExpect(model().attributeExists("users"))
                .andExpect(model().attribute("users", hasSize(3)));
    }

    @Test
    void create() throws Exception {
        mockMvc.perform(post("/users")
                        .param(firstName, "Vasya")
                        .param(lastName, "Vasin")
                        .param(email, "vasya@gmail.com")
                        .param(password, "12345")
                        .param(role, "ADMIN")
                        .param(phone, "+375441236547")
                        .param(photo, "vasyaphoto.jpg")
                        .param(money, "4500")
                        .param(birthDate, "2000-01-01"))
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrlPattern("/users/{\\d+}"));
    }
}

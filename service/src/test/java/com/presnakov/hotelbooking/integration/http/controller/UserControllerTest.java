package com.presnakov.hotelbooking.integration.http.controller;

import com.presnakov.hotelbooking.integration.IntegrationTestBase;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static com.presnakov.hotelbooking.dto.UserCreateEditDto.Fields.birthDate;
import static com.presnakov.hotelbooking.dto.UserCreateEditDto.Fields.email;
import static com.presnakov.hotelbooking.dto.UserCreateEditDto.Fields.firstName;
import static com.presnakov.hotelbooking.dto.UserCreateEditDto.Fields.lastName;
import static com.presnakov.hotelbooking.dto.UserCreateEditDto.Fields.money;
import static com.presnakov.hotelbooking.dto.UserCreateEditDto.Fields.password;
import static com.presnakov.hotelbooking.dto.UserCreateEditDto.Fields.phone;
import static com.presnakov.hotelbooking.dto.UserCreateEditDto.Fields.photo;
import static com.presnakov.hotelbooking.dto.UserCreateEditDto.Fields.role;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;


@AutoConfigureMockMvc
@RequiredArgsConstructor
@Sql({
        "classpath:sql/data.sql"
})
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
                        .param(firstName, "Misha")
                        .param(lastName, "Misutkin")
                        .param(email, "misha@gmail.com")
                        .param(password, "12345")
                        .param(role, "ADMIN")
                        .param(phone, "+375441236547")
                        .param(photo, "mishaphoto.jpg")
                        .param(money, "5500")
                        .param(birthDate, "2001-01-01"))
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrlPattern("/users/{\\d+}"));
    }
}

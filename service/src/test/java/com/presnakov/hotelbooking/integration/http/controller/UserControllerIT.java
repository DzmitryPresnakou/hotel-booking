package com.presnakov.hotelbooking.integration.http.controller;

import com.presnakov.hotelbooking.database.entity.RoleEnum;
import com.presnakov.hotelbooking.dto.UserReadDto;
import com.presnakov.hotelbooking.integration.IntegrationTestBase;
import com.presnakov.hotelbooking.service.UserService;
import com.presnakov.hotelbooking.util.CreateDataUtil;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static com.presnakov.hotelbooking.dto.UserCreateEditDto.Fields.birthDate;
import static com.presnakov.hotelbooking.dto.UserCreateEditDto.Fields.firstname;
import static com.presnakov.hotelbooking.dto.UserCreateEditDto.Fields.lastname;
import static com.presnakov.hotelbooking.dto.UserCreateEditDto.Fields.money;
import static com.presnakov.hotelbooking.dto.UserCreateEditDto.Fields.phone;
import static com.presnakov.hotelbooking.dto.UserCreateEditDto.Fields.photo;
import static com.presnakov.hotelbooking.dto.UserCreateEditDto.Fields.role;
import static com.presnakov.hotelbooking.dto.UserCreateEditDto.Fields.username;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@AutoConfigureMockMvc(printOnlyOnFailure = false)
@RequiredArgsConstructor
class UserControllerIT extends IntegrationTestBase {

    private final UserService userService;
    private final MockMvc mockMvc;


    @Test
    void findAll() throws Exception {
        userService.create(CreateDataUtil.getUserCreateEditDto("Vasya", "Vasilyev", "vasya@gmail.com",
                "+375291478523", LocalDate.of(1995, 2, 5), 2500, "12345", RoleEnum.USER, new MockMultipartFile("test", new byte[0])));
        userService.create(CreateDataUtil.getUserCreateEditDto("Vanya", "Ivanov", "vanya@gmail.com",
                "+375446698523", LocalDate.of(1997, 6, 11), 3000, "56987", RoleEnum.USER, new MockMultipartFile("test", new byte[0])));
        userService.create(CreateDataUtil.getUserCreateEditDto("Petya", "Petrov", "petya@gmail.com",
                "+375251478523", LocalDate.of(2000, 11, 9), 5000, "4563258", RoleEnum.USER, new MockMultipartFile("test", new byte[0])));

        mockMvc.perform(get("/users"))
                .andExpectAll(
                        status().is2xxSuccessful(),
                        view().name("user/users"),
                        model().attributeExists("users"));
        assertThat(userService.findAll().size()).isEqualTo(3);
    }

    @Test
    void findById() throws Exception {
        UserReadDto userReadDto = userService.create(CreateDataUtil.getUserCreateEditDto("Vasya", "Vasilyev", "vasya@gmail.com",
                "+375291478523", LocalDate.of(1995, 2, 5), 2500, "12345", RoleEnum.USER, new MockMultipartFile("test", new byte[0])));

        mockMvc.perform(get("/users/" + userService.findByUsername(userReadDto.getUsername()).get().getId()))
                .andExpectAll(
                        status().is2xxSuccessful(),
                        view().name("user/user"),
                        model().attributeExists("user")
                );
    }

    @Test
    void registration() throws Exception {
        mockMvc.perform(get("/users/registration"))
                .andExpectAll(
                        status().is2xxSuccessful(),
                        view().name("user/registration"),
                        model().attributeExists("user"),
                        model().attributeExists("roles")
                );
    }

    @Test
    void create() throws Exception {
        UserReadDto userReadDto = userService.create(CreateDataUtil.getUserCreateEditDto("Vasya", "Vasilyev", "vasya@gmail.com",
                "+375291478523", LocalDate.of(1995, 2, 5), 2500, "12345", RoleEnum.USER, new MockMultipartFile("test", new byte[0])));

        mockMvc.perform(post("/users")
                        .param(firstname, userReadDto.getFirstname())
                        .param(lastname, userReadDto.getLastname())
                        .param(username, userReadDto.getUsername())
                        .param(role, userReadDto.getRole().name())
                        .param(phone, userReadDto.getPhone())
                        .param(photo, "test")
                        .param(money, userReadDto.getMoney().toString())
                        .param(birthDate, userReadDto.getBirthDate().toString()))
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrl("/login"));
        assertThat(userService.findByUsername("misha@gmail.com").isPresent());
    }

    @Test
    void update() throws Exception {
        UserReadDto userReadDto = userService.create(CreateDataUtil.getUserCreateEditDto("Vasya", "Vasilyev", "vasya@gmail.com",
                "+375291478523", LocalDate.of(1995, 2, 5), 2500, "12345", RoleEnum.USER, new MockMultipartFile("test", new byte[0])));

        String newFirstname = "innokentiy@gmail.com";
        String newLastname = "innokentiy@gmail.com";
        String newUsername = "innokentiy@gmail.com";

        mockMvc.perform(post("/users/" + 1 + "/update")
                        .param(firstname, newFirstname)
                        .param(lastname, newLastname)
                        .param(username, newUsername)
                        .param(role, userReadDto.getRole().name())
                        .param(phone, userReadDto.getPhone())
                        .param(photo, (new MockMultipartFile("test", new byte[0])).getOriginalFilename())
                        .param(money, userReadDto.getMoney().toString())
                        .param(birthDate, userReadDto.getBirthDate().toString()))
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrl("/users/" + userReadDto.getId()));

        assertThat(userService.findByUsername(newUsername)).isPresent();
    }

    @Test
    void delete() throws Exception {
        UserReadDto userReadDto = userService.create(CreateDataUtil.getUserCreateEditDto("Vasya", "Vasilyev", "vasya@gmail.com",
                "+375291478523", LocalDate.of(1995, 2, 5), 2500, "12345", RoleEnum.USER, new MockMultipartFile("test", new byte[0])));

        mockMvc.perform(post("/users/" + 1 + "/delete"))
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrl("/users")
                );

        assertThat(userService.findById(1)).isEmpty();
    }
}

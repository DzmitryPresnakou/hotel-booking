package com.presnakov.hotelbooking.integration.http.controller;

import com.presnakov.hotelbooking.entity.RoleEnum;
import com.presnakov.hotelbooking.entity.User;
import com.presnakov.hotelbooking.integration.IntegrationTestBase;
import com.presnakov.hotelbooking.repository.UserRepository;
import com.presnakov.hotelbooking.util.CreateDataUtil;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static com.presnakov.hotelbooking.dto.UserCreateEditDto.Fields.birthDate;
import static com.presnakov.hotelbooking.dto.UserCreateEditDto.Fields.email;
import static com.presnakov.hotelbooking.dto.UserCreateEditDto.Fields.firstName;
import static com.presnakov.hotelbooking.dto.UserCreateEditDto.Fields.lastName;
import static com.presnakov.hotelbooking.dto.UserCreateEditDto.Fields.money;
import static com.presnakov.hotelbooking.dto.UserCreateEditDto.Fields.password;
import static com.presnakov.hotelbooking.dto.UserCreateEditDto.Fields.phone;
import static com.presnakov.hotelbooking.dto.UserCreateEditDto.Fields.photo;
import static com.presnakov.hotelbooking.dto.UserCreateEditDto.Fields.role;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@AutoConfigureMockMvc
@RequiredArgsConstructor
class UserControllerIT extends IntegrationTestBase {

    private final UserRepository userRepository;
    private final MockMvc mockMvc;

    @Test
    void findAll() throws Exception {
        userRepository.saveAndFlush(CreateDataUtil.createUser("Vasya", "Vasilyev", "vasya@gmail.com",
                "+375291478523", "userphoto001.jpg", LocalDate.of(1995, 2, 5),
                2500, "12345", RoleEnum.USER));
        userRepository.saveAndFlush(CreateDataUtil.createUser("Vanya", "Ivanov", "vanya@gmail.com",
                "+375446698523", "userphoto001.jpg", LocalDate.of(1997, 6, 11),
                3000, "56987", RoleEnum.USER));
        userRepository.saveAndFlush(CreateDataUtil.createUser("Petya", "Petrov", "petya@gmail.com",
                "+375251478523", "userphoto001.jpg", LocalDate.of(2000, 11, 9),
                5000, "4563258", RoleEnum.USER));

        mockMvc.perform(get("/users"))
                .andExpectAll(
                        status().is2xxSuccessful(),
                        view().name("user/users"),
                        model().attributeExists("users"));
        assertThat(userRepository.findAll().size()).isEqualTo(3);
    }

    @Test
    void findById() throws Exception {
        User user = userRepository.saveAndFlush(CreateDataUtil.createUser("Vasya", "Vasilyev", "vasya@gmail.com",
                "+375291478523", "userphoto001.jpg", LocalDate.of(1995, 2, 5),
                2500, "12345", RoleEnum.USER));

        mockMvc.perform(get("/users/" + user.getId()))
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
        User user = CreateDataUtil.createUser("Misha", "Misutkin", "misha@gmail.com",
                "+375441236547", null, LocalDate.of(2001, 1, 1),
                5500, "12345", RoleEnum.USER);

        mockMvc.perform(post("/users")
                        .param(firstName, user.getFirstName())
                        .param(lastName, user.getLastName())
                        .param(email, user.getEmail())
                        .param(password, user.getPassword())
                        .param(role, user.getRole().name())
                        .param(phone, user.getPhone())
                        .param(photo, user.getPhoto())
                        .param(money, user.getMoney().toString())
                        .param(birthDate, user.getBirthDate().toString()))
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrl("/users/" + userRepository.findByEmail(user.getEmail()).get().getId()));

        assertThat(userRepository.findByEmail(user.getEmail())).isPresent();
    }

    @Test
    void update() throws Exception {
        User user = userRepository.saveAndFlush(CreateDataUtil.createUser("Vasya", "Vasilyev", "vasya@gmail.com",
                "+375291478523", null, LocalDate.of(1995, 2, 5),
                2500, "12345", RoleEnum.USER));
        String newFirstName = "innokentiy@gmail.com";
        String newLastName = "innokentiy@gmail.com";
        String newEmail = "innokentiy@gmail.com";

        mockMvc.perform(post("/users/" + user.getId() + "/update")
                        .param(firstName, newFirstName)
                        .param(lastName, newLastName)
                        .param(email, newEmail)
                        .param(password, user.getPassword())
                        .param(role, user.getRole().name())
                        .param(phone, user.getPhone())
                        .param(photo, user.getPhoto())
                        .param(money, user.getMoney().toString())
                        .param(birthDate, user.getBirthDate().toString()))
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrl("/users/" + user.getId()));

        assertThat(userRepository.findByEmail(newEmail)).isPresent();
    }

    @Test
    void delete() throws Exception {
        User user = userRepository.saveAndFlush(CreateDataUtil.createUser("Vasya", "Vasilyev", "vasya@gmail.com",
                "+375291478523", "userphoto001.jpg", LocalDate.of(1995, 2, 5),
                2500, "12345", RoleEnum.USER));

        mockMvc.perform(post("/users/" + user.getId() + "/delete"))
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrl("/users")
                );

        assertThat(userRepository.findById(user.getId())).isEmpty();
    }
}

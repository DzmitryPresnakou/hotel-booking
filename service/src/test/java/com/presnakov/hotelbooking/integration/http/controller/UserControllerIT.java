package com.presnakov.hotelbooking.integration.http.controller;

import com.presnakov.hotelbooking.entity.RoleEnum;
import com.presnakov.hotelbooking.entity.User;
import com.presnakov.hotelbooking.integration.IntegrationTestBase;
import com.presnakov.hotelbooking.repository.UserRepository;
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
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@AutoConfigureMockMvc
@RequiredArgsConstructor
class UserControllerIT extends IntegrationTestBase {

    private final UserRepository userRepository;
    private final MockMvc mockMvc;

    @Test
    void findAll() throws Exception {
        userRepository.saveAndFlush(createUser("Vasya", "Vasilyev", "vasya@gmail.com",
                "+375291478523", "userphoto001.jpg", LocalDate.of(1995, 2, 5),
                2500, "12345", RoleEnum.USER));
        userRepository.saveAndFlush(createUser("Vanya", "Ivanov", "vanya@gmail.com",
                "+375446698523", "userphoto001.jpg", LocalDate.of(1997, 6, 11),
                3000, "56987", RoleEnum.USER));
        userRepository.saveAndFlush(createUser("Petya", "Petrov", "petya@gmail.com",
                "+375251478523", "userphoto001.jpg", LocalDate.of(2000, 11, 9),
                5000, "4563258", RoleEnum.USER));

        mockMvc.perform(get("/users"))
                .andExpectAll(
                        status().is2xxSuccessful(),
                        view().name("user/users"),
                        model().attributeExists("users"),
                        model().attribute("users", hasSize(3)));
    }

    @Test
    void findById() throws Exception {
        User user = userRepository.saveAndFlush(createUser("Vasya", "Vasilyev", "vasya@gmail.com",
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

    @Test
    void delete() throws Exception {
        User user = userRepository.saveAndFlush(createUser("Vasya", "Vasilyev", "vasya@gmail.com",
                "+375291478523", "userphoto001.jpg", LocalDate.of(1995, 2, 5),
                2500, "12345", RoleEnum.USER));

        mockMvc.perform(post("/users/" + user.getId() + "/delete"))
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrl("/users")
                );

        assertThat(userRepository.findById(user.getId())).isEmpty();
    }

    private static User createUser(String firstName,
                                   String lastName,
                                   String email,
                                   String phone,
                                   String photo,
                                   LocalDate birthDate,
                                   Integer money,
                                   String password,
                                   RoleEnum roleEnum) {
        return User.builder()
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .phone(phone)
                .photo(photo)
                .birthDate(birthDate)
                .money(money)
                .password(password)
                .role(roleEnum)
                .build();
    }
}

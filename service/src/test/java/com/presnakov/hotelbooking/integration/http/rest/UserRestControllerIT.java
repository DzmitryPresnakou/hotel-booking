package com.presnakov.hotelbooking.integration.http.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.presnakov.hotelbooking.dto.UserCreateEditDto;
import com.presnakov.hotelbooking.dto.UserReadDto;
import com.presnakov.hotelbooking.entity.RoleEnum;
import com.presnakov.hotelbooking.integration.IntegrationTestBase;
import com.presnakov.hotelbooking.service.UserService;
import com.presnakov.hotelbooking.util.CreateDataUtil;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc(printOnlyOnFailure = false)
@RequiredArgsConstructor
class UserRestControllerIT extends IntegrationTestBase {

    private final UserService userService;
    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;

    @Test
    void shouldFindAll() throws Exception {
        userService.create(CreateDataUtil.getUserCreateEditDto("Vasya", "Vasilyev", "vasya@gmail.com",
                "+375291478523", LocalDate.of(1995, 2, 5), 2500, "12345", RoleEnum.USER));
        userService.create(CreateDataUtil.getUserCreateEditDto("Vanya", "Ivanov", "vanya@gmail.com",
                "+375446698523", LocalDate.of(1997, 6, 11), 3000, "56987", RoleEnum.USER));
        userService.create(CreateDataUtil.getUserCreateEditDto("Petya", "Petrov", "petya@gmail.com",
                "+375251478523", LocalDate.of(2000, 11, 9), 5000, "4563258", RoleEnum.USER));

        mockMvc.perform(get("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpectAll(
                        status().is2xxSuccessful(),
                        content().contentType(MediaType.APPLICATION_JSON_VALUE));
        assertThat(userService.findAll().size()).isEqualTo(3);
    }

    @Test
    public void shouldFindById() throws Exception {
        UserReadDto userReadDto = userService.create(CreateDataUtil.getUserCreateEditDto("Vasya", "Vasilyev", "vasya@gmail.com",
                "+375291478523", LocalDate.of(1995, 2, 5), 2500, "12345", RoleEnum.USER));

        mockMvc.perform(get("/api/v1/users/" + userReadDto.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(
                        status().is2xxSuccessful(),
                        content().contentType(MediaType.APPLICATION_JSON_VALUE),
                        content().json(objectMapper.writeValueAsString(userReadDto)));
    }

    @Test
    void shouldCreate() throws Exception {
        UserCreateEditDto user = CreateDataUtil.getUserCreateEditDto("Misha", "Misutkin", "misha@gmail.com",
                "+375441236547", LocalDate.of(2001, 1, 1), 5500, "12345", RoleEnum.USER);

        mockMvc.perform(post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpectAll(
                        status().isCreated(),
                        content().contentType(MediaType.APPLICATION_JSON_VALUE),
                        content().json(objectMapper.writeValueAsString(user)));
        assertThat(userService.findByEmail(user.getEmail())).isPresent();
    }

    @Test
    void shouldUpdate() throws Exception {
        UserReadDto userReadDto = userService.create(CreateDataUtil.getUserCreateEditDto("Vasya", "Vasilyev", "vasya@gmail.com",
                "+375291478523", LocalDate.of(1995, 2, 5), 2500, "12345", RoleEnum.USER));
        UserReadDto foundUser = userService.findByEmail(userReadDto.getEmail()).get();
        UserCreateEditDto updatedUser = CreateDataUtil.getUserCreateEditDto("Misha", "Misutkin", "misha@gmail.com",
                foundUser.getPhone(), foundUser.getBirthDate(), foundUser.getMoney(), foundUser.getPassword(), foundUser.getRole());

        mockMvc.perform(put("/api/v1/users/" + foundUser.getId())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(updatedUser)))
                .andExpectAll(
                        status().is2xxSuccessful(),
                        content().contentType(MediaType.APPLICATION_JSON_VALUE),
                        content().json(objectMapper.writeValueAsString(updatedUser)));
        assertThat(userService.findByEmail(updatedUser.getEmail())).isPresent();
    }

    @Test
    void shouldDelete() throws Exception {
        UserReadDto userReadDto = userService.create(CreateDataUtil.getUserCreateEditDto("Vasya", "Vasilyev", "vasya@gmail.com",
                "+375291478523", LocalDate.of(1995, 2, 5), 2500, "12345", RoleEnum.USER));
        UserReadDto foundUser = userService.findByEmail(userReadDto.getEmail()).get();

        mockMvc.perform(delete("/api/v1/users/" + foundUser.getId())
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpectAll(
                        status().is2xxSuccessful());
        assertThat(userService.findByEmail(foundUser.getEmail())).isEmpty();
    }
}
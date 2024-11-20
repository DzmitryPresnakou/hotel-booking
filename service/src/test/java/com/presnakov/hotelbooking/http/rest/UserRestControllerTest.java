package com.presnakov.hotelbooking.http.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.presnakov.hotelbooking.dto.UserCreateEditDto;
import com.presnakov.hotelbooking.entity.RoleEnum;
import com.presnakov.hotelbooking.entity.User;
import com.presnakov.hotelbooking.integration.IntegrationTestBase;
import com.presnakov.hotelbooking.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@RequiredArgsConstructor
class UserRestControllerTest extends IntegrationTestBase {

    private final UserRepository userRepository;
    private final MockMvc mockMvc;
    private final ObjectMapper mapper;

    @Test
    void findAll() throws Exception {
        userRepository.saveAndFlush(createUser(1, "Vasya", "Vasilyev", "vasya@gmail.com",
                "+375291478523", "userphoto001.jpg", LocalDate.of(1995, 2, 5),
                2500, "12345", RoleEnum.USER));
        userRepository.saveAndFlush(createUser(2, "Vanya", "Ivanov", "vanya@gmail.com",
                "+375446698523", "userphoto001.jpg", LocalDate.of(1997, 6, 11),
                3000, "56987", RoleEnum.USER));
        userRepository.saveAndFlush(createUser(3, "Petya", "Petrov", "petya@gmail.com",
                "+375251478523", "userphoto001.jpg", LocalDate.of(2000, 11, 9),
                5000, "4563258", RoleEnum.USER));

        mockMvc.perform(get("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());
        assertThat(userRepository.findAll().size()).isEqualTo(3);
    }

    @Test
    public void findById() throws Exception {
        User user = userRepository.saveAndFlush(createUser(1, "Vasya", "Vasilyev", "vasya@gmail.com",
                "+375291478523", "userphoto001.jpg", LocalDate.of(1995, 2, 5),
                2500, "12345", RoleEnum.USER));

        mockMvc.perform(get("/api/v1/users/" + user.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(user)))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void create() throws Exception {
        UserCreateEditDto user = createUserCreateEditDto("Misha", "Misutkin", "misha@gmail.com",
                "+375441236547", LocalDate.of(2001, 1, 1),
                5500, "12345", RoleEnum.USER);

        mockMvc.perform(post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(mapper.writeValueAsString(user)))
                .andExpect(status().is2xxSuccessful());
        assertThat(userRepository.findByEmail(user.getEmail())).isPresent();
    }

    private static User createUser(Integer id,
                                   String firstName,
                                   String lastName,
                                   String email,
                                   String phone,
                                   String photo,
                                   LocalDate birthDate,
                                   Integer money,
                                   String password,
                                   RoleEnum role) {
        return User.builder()
                .id(id)
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .phone(phone)
                .photo(photo)
                .birthDate(birthDate)
                .money(money)
                .password(password)
                .role(role)
                .build();
    }

    private UserCreateEditDto createUserCreateEditDto(String firstName,
                                                      String lastName,
                                                      String email,
                                                      String phone,
                                                      LocalDate birthDate,
                                                      Integer money,
                                                      String password,
                                                      RoleEnum role) {


        return UserCreateEditDto.builder()
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .phone(phone)
                .birthDate(birthDate)
                .money(money)
                .password(password)
                .role(role)
                .build();
    }
}
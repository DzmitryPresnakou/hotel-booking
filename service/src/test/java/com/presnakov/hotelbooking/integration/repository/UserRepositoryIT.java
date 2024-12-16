package com.presnakov.hotelbooking.integration.repository;

import com.presnakov.hotelbooking.database.entity.RoleEnum;
import com.presnakov.hotelbooking.database.entity.User;
import com.presnakov.hotelbooking.integration.IntegrationTestBase;
import com.presnakov.hotelbooking.database.repository.UserRepository;
import com.presnakov.hotelbooking.util.CreateDataUtil;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@RequiredArgsConstructor
class UserRepositoryIT extends IntegrationTestBase {

    private final UserRepository userRepository;

    @Test
    void save() {
        User user = CreateDataUtil.createUser("Vasya", "Vasilyev", "vasya@gmail.com",
                "+375291478523", "userphoto001.jpg", LocalDate.of(1995, 2, 5),
                2500, "12345", RoleEnum.USER);

        User actualResult = userRepository.save(user);

        assertNotNull(actualResult.getId());
    }

    @Test
    void update() {
        User user = userRepository.save(CreateDataUtil.createUser("Vasya", "Vasilyev", "vasya@gmail.com",
                "+375291478523", "userphoto001.jpg", LocalDate.of(1995, 2, 5),
                2500, "12345", RoleEnum.USER));
        user.setFirstname("Petr");
        user.setLastname("Petrov");
        user.setUsername("petya@gmail.com");
        user.setPhone("+375446698523");
        user.setPhoto("photo10.jpg");
        user.setBirthDate(LocalDate.of(1998, 12, 18));
        user.setMoney(5000);
        user.setPassword("3698223654");
        user.setRole(RoleEnum.ADMIN);

        userRepository.save(user);

        User updatedUser = userRepository.findById(user.getId()).get();
        assertThat(updatedUser).isEqualTo(user);
    }

    @Test
    void delete() {
        User user = userRepository.save(CreateDataUtil.createUser("Vasya", "Vasilyev", "vasya@gmail.com",
                "+375291478523", "userphoto001.jpg", LocalDate.of(1995, 2, 5),
                2500, "12345", RoleEnum.USER));

        userRepository.softDelete(user);
        Optional<User> actualResult = userRepository.findById(user.getId());
        assertThat(actualResult.get().getIsActive()).isEqualTo(false);
    }

    @Test
    void findById() {
        User user = userRepository.save(CreateDataUtil.createUser("Vasya", "Vasilyev", "vasya@gmail.com",
                "+375291478523", "userphoto001.jpg", LocalDate.of(1995, 2, 5),
                2500, "12345", RoleEnum.USER));

        Optional<User> actualResult = userRepository.findById(user.getId());

        assertThat(actualResult).isPresent();
        assertThat(actualResult.get()).isEqualTo(user);
    }

    @Test
    void findAll() {
        User user1 = userRepository.save(CreateDataUtil.createUser("Vasya", "Vasilyev", "vasya@gmail.com",
                "+375291478523", "userphoto001.jpg", LocalDate.of(1995, 2, 5),
                2500, "12345", RoleEnum.USER));
        User user2 = userRepository.save(CreateDataUtil.createUser("Vanya", "Ivanov", "vanya@gmail.com",
                "++375446698523", "userphoto001.jpg", LocalDate.of(1997, 6, 11),
                3000, "56987", RoleEnum.USER));
        User user3 = userRepository.save(CreateDataUtil.createUser("Petya", "Petrov", "petya@gmail.com",
                "+375251478523", "userphoto001.jpg", LocalDate.of(2000, 11, 9),
                5000, "4563258", RoleEnum.USER));

        List<User> actualResult = (List<User>) userRepository.findAll();
        List<Integer> userIds = actualResult.stream()
                .map(User::getId)
                .toList();

        assertThat(actualResult).hasSize(3);
        assertThat(userIds).contains(user1.getId(), user2.getId(), user3.getId());
    }

    @Test
    void findByUsername() {
        User user = userRepository.save(CreateDataUtil.createUser("Vasya", "Vasilyev", "vasya@gmail.com",
                "+375291478523", "userphoto001.jpg", LocalDate.of(1995, 2, 5),
                2500, "12345", RoleEnum.USER));

        Optional<User> actualResult = userRepository.findByUsername(user.getUsername());

        assertThat(actualResult).isPresent();
        assertThat(actualResult.get()).isEqualTo(user);
    }
}
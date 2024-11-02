package com.presnakov.hotelbooking.integration.repository;

import com.presnakov.hotelbooking.entity.RoleEnum;
import com.presnakov.hotelbooking.entity.User;
import com.presnakov.hotelbooking.integration.annotation.IT;
import com.presnakov.hotelbooking.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@IT
@RequiredArgsConstructor
class UserRepositoryIT {

    private final UserRepository userRepository;

    @Test
    void save() {
        User user = createUser("Vasya", "Vasilyev", "vasya@gmail.com",
                "+375291478523", "userphoto001.jpg", LocalDate.of(1995, 2, 5),
                2500, "12345", RoleEnum.USER);

        User actualResult = userRepository.save(user);

        assertNotNull(actualResult.getId());
    }

    @Test
    void delete() {
        User user = userRepository.save(createUser("Vasya", "Vasilyev", "vasya@gmail.com",
                "+375291478523", "userphoto001.jpg", LocalDate.of(1995, 2, 5),
                2500, "12345", RoleEnum.USER));

        userRepository.delete(user);

        assertThat(userRepository.findById(user.getId())).isEmpty();
    }

    @Test
    void update() {
        User user = userRepository.save(createUser("Vasya", "Vasilyev", "vasya@gmail.com",
                "+375291478523", "userphoto001.jpg", LocalDate.of(1995, 2, 5),
                2500, "12345", RoleEnum.USER));
        user.setFirstName("Petr");
        user.setLastName("Petrov");
        user.setEmail("petya@gmail.com");
        user.setPhone("+375446698523");
        user.setPhoto("photo10.jpg");
        user.setBirthDate(LocalDate.of(1998, 12, 18));
        user.setMoney(5000);
        user.setPassword("3698223654");
        user.setRole(RoleEnum.ADMIN);

        userRepository.update(user);

        User updatedUser = userRepository.findById(user.getId()).get();
        assertThat(updatedUser).isEqualTo(user);
    }

    @Test
    void findById() {
        User user = userRepository.save(createUser("Vasya", "Vasilyev", "vasya@gmail.com",
                "+375291478523", "userphoto001.jpg", LocalDate.of(1995, 2, 5),
                2500, "12345", RoleEnum.USER));

        Optional<User> actualResult = userRepository.findById(user.getId());

        assertThat(actualResult).isPresent();
        assertThat(actualResult.get()).isEqualTo(user);
    }

    @Test
    void findAll() {
        User user1 = userRepository.save(createUser("Vasya", "Vasilyev", "vasya@gmail.com",
                "+375291478523", "userphoto001.jpg", LocalDate.of(1995, 2, 5),
                2500, "12345", RoleEnum.USER));
        User user2 = userRepository.save(createUser("Vanya", "Ivanov", "vanya@gmail.com",
                "++375446698523", "userphoto001.jpg", LocalDate.of(1997, 6, 11),
                3000, "56987", RoleEnum.USER));
        User user3 = userRepository.save(createUser("Petya", "Petrov", "petya@gmail.com",
                "+375251478523", "userphoto001.jpg", LocalDate.of(2000, 11, 9),
                5000, "4563258", RoleEnum.USER));

        List<User> actualResult = userRepository.findAll();
        List<Integer> userIds = actualResult.stream()
                .map(User::getId)
                .toList();

        assertThat(actualResult).hasSize(3);
        assertThat(userIds).contains(user1.getId(), user2.getId(), user3.getId());
    }

    @Test
    void findByEmail() {
        User user = userRepository.save(createUser("Vasya", "Vasilyev", "vasya@gmail.com",
                "+375291478523", "userphoto001.jpg", LocalDate.of(1995, 2, 5),
                2500, "12345", RoleEnum.USER));

        Optional<User> actualResult = userRepository.findByEmail(user.getEmail());

        assertThat(actualResult).isPresent();
        assertThat(actualResult.get()).isEqualTo(user);
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
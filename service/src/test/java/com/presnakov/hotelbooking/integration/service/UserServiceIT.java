package com.presnakov.hotelbooking.integration.service;

import com.presnakov.hotelbooking.dto.UserCreateEditDto;
import com.presnakov.hotelbooking.dto.UserReadDto;
import com.presnakov.hotelbooking.entity.RoleEnum;
import com.presnakov.hotelbooking.integration.IntegrationTestBase;
import com.presnakov.hotelbooking.service.UserService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@RequiredArgsConstructor
@Sql({
        "classpath:sql/data.sql"
})
class UserServiceIT extends IntegrationTestBase {

    private static final Integer USER_1 = 1;

    private final UserService userService;

    @Test
    void findAll() {
        List<UserReadDto> result = userService.findAll();
        assertThat(result).hasSize(3);
    }

    @Test
    void findById() {
        Optional<UserReadDto> maybeUser = userService.findById(USER_1);
        assertTrue(maybeUser.isPresent());
        maybeUser.ifPresent(user -> assertEquals("vasya@gmail.com", user.getEmail()));
    }

    @Test
    void create() {
        UserCreateEditDto userDto = new UserCreateEditDto(
                "Misha",
                "Mishutkin",
                "Misha@gmail.com",
                "12345",
                RoleEnum.ADMIN,
                "+375255453265",
                "photomisha.jpg",
                5500,
                LocalDate.of(1991, 4, 14)
        );

        UserReadDto actualResult = userService.create(userDto);

        assertEquals(userDto.getFirstName(), actualResult.getFirstName());
        assertEquals(userDto.getLastName(), actualResult.getLastName());
        assertEquals(userDto.getEmail(), actualResult.getEmail());
        assertEquals(userDto.getPassword(), actualResult.getPassword());
        assertSame(userDto.getRole(), actualResult.getRole());
        assertEquals(userDto.getPhone(), actualResult.getPhone());
        assertEquals(userDto.getPhoto(), actualResult.getPhoto());
        assertEquals(userDto.getMoney(), actualResult.getMoney());
        assertEquals(userDto.getBirthDate(), actualResult.getBirthDate());
    }

    @Test
    void update() {
        UserCreateEditDto userDto = new UserCreateEditDto(
                "Misha",
                "Mishutkin",
                "Misha@gmail.com",
                "12345",
                RoleEnum.ADMIN,
                "+375255453265",
                "photomisha.jpg",
                5500,
                LocalDate.of(1991, 4, 14)
        );

        Optional<UserReadDto> actualResult = userService.update(USER_1, userDto);

        assertTrue(actualResult.isPresent());
        actualResult.ifPresent(user -> {
            assertEquals(userDto.getFirstName(), actualResult.get().getFirstName());
            assertEquals(userDto.getLastName(), actualResult.get().getLastName());
            assertEquals(userDto.getEmail(), actualResult.get().getEmail());
            assertEquals(userDto.getPassword(), actualResult.get().getPassword());
            assertSame(userDto.getRole(), actualResult.get().getRole());
            assertEquals(userDto.getPhone(), actualResult.get().getPhone());
            assertEquals(userDto.getPhoto(), actualResult.get().getPhoto());
            assertEquals(userDto.getMoney(), actualResult.get().getMoney());
            assertEquals(userDto.getBirthDate(), actualResult.get().getBirthDate());
        });
    }

    @Test
    void delete() {
        assertFalse(userService.delete(-124));
        assertTrue(userService.delete(USER_1));
    }
}

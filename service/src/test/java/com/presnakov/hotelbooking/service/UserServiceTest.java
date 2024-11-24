package com.presnakov.hotelbooking.service;

import com.presnakov.hotelbooking.dto.UserCreateEditDto;
import com.presnakov.hotelbooking.dto.UserReadDto;
import com.presnakov.hotelbooking.database.entity.RoleEnum;
import com.presnakov.hotelbooking.database.entity.User;
import com.presnakov.hotelbooking.mapper.UserCreateEditMapper;
import com.presnakov.hotelbooking.mapper.UserReadMapper;
import com.presnakov.hotelbooking.database.repository.UserRepository;
import com.presnakov.hotelbooking.util.CreateDataUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserReadMapper userReadMapper;

    @Mock
    private UserCreateEditMapper userCreateEditMapper;

    @InjectMocks
    private UserService userService;

    @Test
    void shouldFindAll() {
        User user1 = CreateDataUtil.createUser("Vasya", "Vasilyev", "vasya@gmail.com",
                "+375291478523", null, LocalDate.of(1995, 2, 5),
                2500, "12345", RoleEnum.USER);
        User user2 = CreateDataUtil.createUser("Vanya", "Ivanov", "vanya@gmail.com",
                "+375446698523", null, LocalDate.of(1997, 6, 11),
                3000, "56987", RoleEnum.USER);
        UserReadDto userReadDto1 = CreateDataUtil.getUserReadDto(1, "Vasya", "Vasilyev", "vasya@gmail.com",
                "+375291478523", LocalDate.of(1995, 2, 5),
                2500, RoleEnum.USER);
        UserReadDto userReadDto2 = CreateDataUtil.getUserReadDto(2, "Vanya", "Ivanov", "vanya@gmail.com",
                "+375446698523", LocalDate.of(1997, 6, 11),
                3000, RoleEnum.USER);
        List<User> users = List.of(user1, user2);

        doReturn(users).when(userRepository).findAll();
        doReturn(userReadDto1).when(userReadMapper).map(user1);
        doReturn(userReadDto2).when(userReadMapper).map(user2);
        List<UserReadDto> actualResult = userService.findAll();

        assertEquals(2, actualResult.size());
        assertEquals(userReadDto1, actualResult.get(0));
        assertEquals(userReadDto2, actualResult.get(1));
        verify(userRepository).findAll();
        verify(userReadMapper).map(user1);
        verify(userReadMapper).map(user2);
    }

    @Test
    void shouldFindById() {
        User user = CreateDataUtil.createUser("Vasya", "Vasilyev", "vasya@gmail.com",
                "+375291478523", null, LocalDate.of(1995, 2, 5),
                2500, "12345", RoleEnum.USER);
        Optional<User> maybeUser = Optional.of(user);
        UserReadDto userReadDto = CreateDataUtil.getUserReadDto(1, "Vasya", "Vasilyev", "vasya@gmail.com",
                "+375291478523", LocalDate.of(1995, 2, 5),
                2500, RoleEnum.USER);

        doReturn(maybeUser).when(userRepository).findById(user.getId());
        doReturn(userReadDto).when(userReadMapper).map(user);
        Optional<UserReadDto> actualResult = userService.findById(user.getId());

        assertThat(actualResult)
                .isPresent()
                .contains(userReadDto);
        verify(userRepository).findById(user.getId());
        verify(userReadMapper).map(user);
    }

    @Test
    void shouldNotFindById() {
        Integer dummyId = -123;

        doReturn(Optional.empty()).when(userRepository).findById(dummyId);
        Optional<UserReadDto> actualResult = userService.findById(dummyId);

        assertThat(actualResult).isEmpty();
        verify(userRepository).findById(dummyId);
        verifyNoInteractions(userCreateEditMapper);
    }

    @Test
    void create() {
        User user = CreateDataUtil.createUser("Vasya", "Vasilyev", "vasya@gmail.com",
                "+375291478523", null, LocalDate.of(1995, 2, 5),
                2500, "12345", RoleEnum.USER);
        UserCreateEditDto userCreateEditDto = CreateDataUtil.getUserCreateEditDto("Vasya", "Vasilyev", "vasya@gmail.com",
                "+375291478523", LocalDate.of(1995, 2, 5),
                2500, "12345", RoleEnum.USER);
        UserReadDto userReadDto = CreateDataUtil.getUserReadDto(1, "Vasya", "Vasilyev", "vasya@gmail.com",
                "+375291478523", LocalDate.of(1995, 2, 5),
                2500, RoleEnum.USER);

        doReturn(user).when(userCreateEditMapper).map(userCreateEditDto);
        doReturn(user).when(userRepository).save(user);
        doReturn(userReadDto).when(userReadMapper).map(user);
        UserReadDto actualResult = userService.create(userCreateEditDto);

        assertEquals(userReadDto, actualResult);
        verify(userCreateEditMapper).map(userCreateEditDto);
        verify(userRepository).save(user);
        verify(userReadMapper).map(user);
    }

    @Test
    void update() {
        User user = CreateDataUtil.createUser("Vasya", "Vasilyev", "vasya@gmail.com",
                "+375291478523", null, LocalDate.of(1995, 2, 5),
                2500, "12345", RoleEnum.USER);
        UserCreateEditDto userCreateEditDto = CreateDataUtil.getUserCreateEditDto("Vasya", "Vasilyev", "vasya@gmail.com",
                "+375291478523", LocalDate.of(1995, 2, 5),
                2500, "12345", RoleEnum.USER);
        UserReadDto userReadDto = CreateDataUtil.getUserReadDto(1, "Vasya", "Vasilyev", "vasya@gmail.com",
                "+375291478523", LocalDate.of(1995, 2, 5),
                2500, RoleEnum.USER);

        doReturn(Optional.of(user)).when(userRepository).findById(user.getId());
        doReturn(user).when(userCreateEditMapper).map(userCreateEditDto, user);
        doReturn(user).when(userRepository).saveAndFlush(user);
        doReturn(userReadDto).when(userReadMapper).map(user);
        Optional<UserReadDto> actualResult = userService.update(user.getId(), userCreateEditDto);

        assertThat(actualResult).isPresent().contains(userReadDto);
        verify(userRepository).findById(user.getId());
        verify(userRepository).saveAndFlush(user);
        verify(userReadMapper).map(user);
    }

    @Test
    void delete() {
        User user = CreateDataUtil.createUser("Vasya", "Vasilyev", "vasya@gmail.com",
                "+375291478523", null, LocalDate.of(1995, 2, 5),
                2500, "12345", RoleEnum.USER);

        doReturn(Optional.of(user)).when(userRepository).findById(user.getId());
        boolean actualResult = userService.delete(user.getId());

        assertTrue(actualResult);
        verify(userRepository).findById(user.getId());
        verify(userRepository).delete(user);
    }

    @Test
    void shouldNotDelete() {
        User user = CreateDataUtil.createUser("Vasya", "Vasilyev", "vasya@gmail.com",
                "+375291478523", null, LocalDate.of(1995, 2, 5),
                2500, "12345", RoleEnum.USER);

        doReturn(Optional.empty()).when(userRepository).findById(user.getId());
        boolean actualResult = userService.delete(user.getId());

        assertFalse(actualResult);
        verify(userRepository).findById(user.getId());
        verify(userRepository, never()).delete(user);
    }
}

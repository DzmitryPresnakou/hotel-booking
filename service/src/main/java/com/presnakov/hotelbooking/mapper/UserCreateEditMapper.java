package com.presnakov.hotelbooking.mapper;

import com.presnakov.hotelbooking.dto.UserCreateEditDto;
import com.presnakov.hotelbooking.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

import static java.util.function.Predicate.not;

@Component
@RequiredArgsConstructor
public class UserCreateEditMapper implements Mapper<UserCreateEditDto, User> {

    @Override
    public User map(UserCreateEditDto fromObject, User toObject) {
        copy(fromObject, toObject);
        return toObject;
    }

    @Override
    public User map(UserCreateEditDto object) {
        User user = new User();
        copy(object, user);
        return user;
    }

    private void copy(UserCreateEditDto object, User user) {
        user.setFirstName(object.getFirstName());
        user.setLastName(object.getLastName());
        user.setEmail(object.getEmail());
        if (user.getPassword() == null) {
            user.setPassword(object.getPassword());
        }
        user.setRole(object.getRole());
        user.setPhone(object.getPhone());
        user.setMoney(object.getMoney());
        user.setBirthDate(object.getBirthDate());

        Optional.ofNullable(object.getPhoto())
                .filter(not(MultipartFile::isEmpty))
                .ifPresent(image -> user.setPhoto(image.getOriginalFilename()));
    }
}

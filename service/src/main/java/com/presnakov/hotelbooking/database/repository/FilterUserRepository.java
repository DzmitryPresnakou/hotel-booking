package com.presnakov.hotelbooking.database.repository;

import com.presnakov.hotelbooking.dto.UserFilter;
import com.presnakov.hotelbooking.database.entity.User;

import java.util.List;

public interface FilterUserRepository {

    List<User> findAllByFilter(UserFilter filter);
}

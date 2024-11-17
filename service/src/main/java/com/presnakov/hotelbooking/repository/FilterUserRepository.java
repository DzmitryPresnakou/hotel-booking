package com.presnakov.hotelbooking.repository;

import com.presnakov.hotelbooking.dto.UserFilter;
import com.presnakov.hotelbooking.entity.User;

import java.util.List;

public interface FilterUserRepository {

    List<User> findAllByFilter(UserFilter filter);
}

package com.presnakov.hotelbooking.database.repository;

import com.presnakov.hotelbooking.database.entity.User;
import com.presnakov.hotelbooking.dto.UserFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FilterUserRepository {

    Page<User> findAllByFilter(UserFilter filter, Pageable pageable);

    public void softDelete(User user);
}

package com.presnakov.hotelbooking.repository;

import com.presnakov.hotelbooking.entity.User;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends Repository<User, Integer> {

    User save(User entity);

    void delete(User entity);

    Optional<User> findById(Integer id);

    List<User> findAll();

    Optional<User> findByEmail(String email);
}

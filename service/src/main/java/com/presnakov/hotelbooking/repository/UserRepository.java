package com.presnakov.hotelbooking.repository;

import com.presnakov.hotelbooking.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Integer>, RepositoryBase {

    Optional<User> findByEmail(String email);
}

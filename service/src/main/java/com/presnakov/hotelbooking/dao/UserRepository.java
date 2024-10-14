package com.presnakov.hotelbooking.dao;

import com.presnakov.hotelbooking.entity.User;
import jakarta.persistence.EntityManager;

public class UserRepository extends RepositoryBase<Integer, User> {

    public UserRepository(EntityManager entityManager) {
        super(User.class, entityManager);
    }
}

package com.presnakov.hotelbooking.dao;

import com.presnakov.hotelbooking.entity.User;
import com.querydsl.jpa.impl.JPAQuery;
import jakarta.persistence.EntityManager;

import java.util.Optional;

import static com.presnakov.hotelbooking.entity.QUser.user;

public class UserRepository extends RepositoryBase<Integer, User> {

    public UserRepository(EntityManager entityManager) {
        super(User.class, entityManager);
    }

    public Optional<User> findByEmail(String email) {
        return Optional.ofNullable(new JPAQuery<User>(getEntityManager())
                .select(user)
                .from(user)
                .where(user.email.eq(email))
                .fetchOne());
    }
}

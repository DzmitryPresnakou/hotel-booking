package com.presnakov.hotelbooking.repository;

import com.presnakov.hotelbooking.dto.UserFilter;
import com.presnakov.hotelbooking.entity.User;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.presnakov.hotelbooking.entity.QUser.user;

@RequiredArgsConstructor
public class FilterUserRepositoryImpl implements FilterUserRepository {

    private final EntityManager entityManager;

    @Override
    public List<User> findAllByFilter(UserFilter filter) {
        return new JPAQuery<User>(entityManager)
                .select(user)
                .from(user)
                .where(getByCompleteInfo(filter))
                .fetch();
    }

    private static Predicate getByCompleteInfo(UserFilter filter) {
        return QPredicate.builder()
                .add(filter.getFirstName(), user.firstName::containsIgnoreCase)
                .add(filter.getLastName(), user.lastName::containsIgnoreCase)
                .add(filter.getEmail(), user.email::containsIgnoreCase)
                .add(filter.getRole(), user.role::eq)
                .add(filter.getMoney(), user.money::gt)
                .add(filter.getBirthDate(), user.birthDate::before)
                .buildAnd();
    }
}

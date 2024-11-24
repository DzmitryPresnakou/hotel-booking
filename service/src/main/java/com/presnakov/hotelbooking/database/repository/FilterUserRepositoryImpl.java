package com.presnakov.hotelbooking.database.repository;

import com.presnakov.hotelbooking.database.querydsl.QPredicate;
import com.presnakov.hotelbooking.dto.UserFilter;
import com.presnakov.hotelbooking.database.entity.User;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.presnakov.hotelbooking.database.entity.QUser.user;

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
                .add(filter.getFirstname(), user.firstname::containsIgnoreCase)
                .add(filter.getLastname(), user.lastname::containsIgnoreCase)
                .add(filter.getUsername(), user.username::containsIgnoreCase)
                .add(filter.getRole(), user.role::eq)
                .add(filter.getMoney(), user.money::gt)
                .add(filter.getBirthDate(), user.birthDate::before)
                .buildAnd();
    }
}

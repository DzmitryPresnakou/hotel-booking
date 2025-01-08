package com.presnakov.hotelbooking.database.repository;

import com.presnakov.hotelbooking.database.entity.User;
import com.presnakov.hotelbooking.database.querydsl.QPredicate;
import com.presnakov.hotelbooking.dto.UserFilter;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.presnakov.hotelbooking.database.entity.QUser.user;

@RequiredArgsConstructor
public class FilterUserRepositoryImpl implements FilterUserRepository {

    private final EntityManager entityManager;

    @Override
    public Page<User> findAllByFilter(UserFilter filter, Pageable pageable) {
        JPAQuery<User> query = new JPAQuery<User>(entityManager)
                .select(user)
                .from(user)
                .where(getPredicate(filter));
        long total = query.fetch().size();
        List<User> users = query.offset(pageable.getOffset()).limit(pageable.getPageSize()).fetch();
        return new PageImpl<>(users, pageable, total);
    }

    private static Predicate getPredicate(UserFilter filter) {
        return QPredicate.builder()
                .add(filter.getFirstname(), user.firstname::containsIgnoreCase)
                .add(filter.getLastname(), user.lastname::containsIgnoreCase)
                .add(filter.getUsername(), user.username::containsIgnoreCase)
                .add(filter.getRole(), user.role::eq)
                .add(filter.getMoney(), user.money::gt)
                .add(filter.getBirthDate(), user.birthDate::before)
                .buildAnd();
    }

    public void softDelete(User user) {
        user.setIsActive(false);
    }
}

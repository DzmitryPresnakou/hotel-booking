package com.presnakov.hotelbooking.repository;

import com.presnakov.hotelbooking.entity.Hotel;
import com.querydsl.jpa.impl.JPAQuery;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.presnakov.hotelbooking.entity.QHotel.hotel;

@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Transactional
@Repository
public class HotelRepository extends RepositoryBase<Integer, Hotel> {

    public HotelRepository(EntityManager entityManager) {
        super(Hotel.class, entityManager);
    }

    public Optional<Hotel> findByName(String name) {
        return Optional.ofNullable(new JPAQuery<Hotel>(getEntityManager())
                .select(hotel)
                .from(hotel)
                .where(hotel.name.eq(name))
                .fetchOne());
    }
}

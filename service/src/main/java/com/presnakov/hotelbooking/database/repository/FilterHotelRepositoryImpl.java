package com.presnakov.hotelbooking.database.repository;

import com.presnakov.hotelbooking.database.entity.Hotel;
import com.presnakov.hotelbooking.database.entity.Room;
import com.querydsl.jpa.impl.JPAQuery;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.presnakov.hotelbooking.database.entity.QHotel.hotel;
import static com.presnakov.hotelbooking.database.entity.QRoom.room;

@RequiredArgsConstructor
public class FilterHotelRepositoryImpl implements FilterHotelRepository {

    private final EntityManager entityManager;

    @Override
    public Page<Hotel> findAll(Pageable pageable) {
        JPAQuery<Hotel> query = new JPAQuery<Hotel>(entityManager)
                .select(hotel)
                .from(hotel)
                .where(hotel.isActive.isTrue());
        long total = query.fetch().size();
        List<Hotel> hotels = query.offset(pageable.getOffset()).limit(pageable.getPageSize()).fetch();
        return new PageImpl<>(hotels, pageable, total);
    }

    @Override
    public List<Hotel> findAll() {
        return new JPAQuery<>(entityManager)
                .select(hotel)
                .from(hotel)
                .where(hotel.isActive.isTrue())
                .fetch();
    }

    public void softDelete(Hotel hotel) {
        hotel.setIsActive(false);
    }
}

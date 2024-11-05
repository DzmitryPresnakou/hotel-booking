package com.presnakov.hotelbooking.repository;

import com.presnakov.hotelbooking.entity.BaseEntity;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RepositoryBaseImpl implements RepositoryBase {

    private final EntityManager entityManager;

    @Override
    public void update(BaseEntity entity) {
        entityManager.merge(entity);
    }
}

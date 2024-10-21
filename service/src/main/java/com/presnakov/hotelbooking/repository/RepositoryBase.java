package com.presnakov.hotelbooking.repository;

import com.presnakov.hotelbooking.entity.BaseEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public abstract class RepositoryBase<K extends Serializable, E extends BaseEntity<K>> implements Repository<K, E> {

    private final Class<E> clazz;
    @Getter
    @PersistenceContext
    private final EntityManager entityManager;

    @Override
    @Transactional
    public E save(E entity) {
        entityManager.persist(entity);
        return entity;
    }

    @Override
    @Transactional
    public void delete(E entity) {
        entityManager.remove(entityManager.find(clazz, entity.getId()));
        entityManager.flush();
    }

    @Override
    @Transactional
    public void update(E entity) {
        entityManager.merge(entity);
    }

    @Override
    @Transactional
    public Optional<E> findById(K id) {
        return Optional.ofNullable(entityManager.find(clazz, id));
    }

    @Override
    @Transactional
    public List<E> findAll() {
        var criteria = entityManager.getCriteriaBuilder().createQuery(clazz);
        criteria.from(clazz);
        return entityManager.createQuery(criteria)
                .getResultList();
    }
}

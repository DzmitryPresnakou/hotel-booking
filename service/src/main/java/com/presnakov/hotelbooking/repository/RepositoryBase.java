package com.presnakov.hotelbooking.repository;

import com.presnakov.hotelbooking.entity.BaseEntity;

import java.io.Serializable;

public interface RepositoryBase <K extends Serializable, E extends BaseEntity<K>>{

    void update(E entity);
}

package com.omni.minecraft.database.repository;

import com.omni.minecraft.database.entity.Entity;
import com.omni.minecraft.database.query.Query;

public interface RepositoryCreate<I, E extends Entity<I>> {

    void save(E entity);

    void save(Query query, E entity);

}
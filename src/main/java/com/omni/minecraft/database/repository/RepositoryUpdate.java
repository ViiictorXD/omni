package com.omni.minecraft.database.repository;

import com.omni.minecraft.database.query.Query;

public interface RepositoryUpdate<I, E> {

    void update(I id, E entity);

    void update(Query query, E entity);

}
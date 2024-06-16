package com.omni.minecraft.database.repository;

import com.omni.minecraft.database.entity.Entity;
import com.omni.minecraft.database.query.Query;

import java.util.Optional;
import java.util.Set;

public interface RepositoryRead<I, E extends Entity<I>> {

    Optional<E> findOne(I id);

    Optional<E> findOne(Query query);

    Set<E> findMany();

    Set<E> findMany(Query query);

}

package com.omni.minecraft.database.repository;

import com.omni.minecraft.database.entity.Entity;

public interface Repository<
        I,
        E extends Entity<I>,
        RC extends RepositoryCreate<I, E>,
        RE extends RepositoryRead<I, E>,
        RU extends RepositoryUpdate<I, E>,
        RD extends RepositoryDelete<I, E>> {

}
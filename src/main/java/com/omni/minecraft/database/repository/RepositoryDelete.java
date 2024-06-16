package com.omni.minecraft.database.repository;

import com.omni.minecraft.database.query.Query;

public interface RepositoryDelete<I, E> {

    void delete(I id);

    void delete(Query query);

}

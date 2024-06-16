package com.omni.minecraft.database.storage.model.base;

import java.sql.SQLException;

public interface Repository<E extends BaseEntity> {

    E findOne(Integer id) throws SQLException;

    void save(E entity) throws SQLException;

    void delete(E entity) throws SQLException;
}

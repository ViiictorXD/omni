package com.omni.minecraft.database.storage.model.named;

import com.omni.minecraft.database.SqlConnection;
import com.omni.minecraft.database.storage.model.base.BaseRepository;

import java.sql.SQLException;

public abstract class NamedRepository<E extends NamedEntity,
        O extends NamedDTO,
        D extends NamedDAO<O>,
        C extends NamedCache<E, ?>,
        L extends NamedLoader<E, O>>
        extends BaseRepository<E, O, D, C, L> {

    public NamedRepository(SqlConnection connection) throws SQLException {
        super(connection);
    }

    public E findByName(String name) throws SQLException {
        name = name.toLowerCase();

        E entity = cache.getIfMatch(name);

        if (entity != null) return entity;

        O dto = dao.findByName(name);

        return fromDTO(dto);
    }
}

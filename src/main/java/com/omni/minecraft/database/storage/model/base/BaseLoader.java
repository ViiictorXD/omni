package com.omni.minecraft.database.storage.model.base;

import java.sql.SQLException;

public abstract class BaseLoader<E extends BaseEntity, O extends DTO> implements Loader<E, O> {


    public abstract E newEntity();

    public abstract O newDTO();

    public E fromDTO(O dto) throws SQLException {
        E entity = newEntity();

        entity.setId(dto.id);

        return entity;
    }

    public O toDTO(E entity) throws SQLException {
        O dto = newDTO();

        dto.id = entity.getId();

        return dto;
    }
}

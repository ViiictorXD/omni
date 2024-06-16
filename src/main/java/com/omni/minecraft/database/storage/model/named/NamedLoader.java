package com.omni.minecraft.database.storage.model.named;


import com.omni.minecraft.database.storage.model.base.BaseLoader;

import java.sql.SQLException;

public abstract class NamedLoader<E extends NamedEntity, O extends NamedDTO> extends BaseLoader<E, O> {

    @Override
    public E fromDTO(O dto) throws SQLException {
        E entity = super.fromDTO(dto);

        entity.setName(dto.name);

        return entity;
    }

    @Override
    public O toDTO(E entity) throws SQLException {
        O dto = super.toDTO(entity);

        dto.name = entity.getName();
        dto.fastName = dto.name == null ? null : dto.name.toLowerCase();

        return dto;
    }
}

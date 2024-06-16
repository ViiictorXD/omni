package com.vitu.omni.database.storage.model.base;

import java.sql.SQLException;

public interface Loader<E extends BaseEntity, O extends DTO> {

    E fromDTO(O dto) throws SQLException;

    O toDTO(E entity) throws SQLException;
}

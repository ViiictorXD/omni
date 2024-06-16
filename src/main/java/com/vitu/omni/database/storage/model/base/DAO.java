package com.vitu.omni.database.storage.model.base;

import java.sql.SQLException;
import java.util.List;

public interface DAO<O extends DTO> {

    void save(O dto) throws SQLException;

    void save(List<O> dtos) throws SQLException;

    O findOne(int id) throws SQLException;

    List<O> findByIdIn(List<Integer> ids) throws SQLException;

    O create(O o) throws SQLException;

    void delete(int id) throws SQLException;

    List<Integer> findAll() throws SQLException;
}
package com.omni.minecraft.database.storage.model.named;

import com.omni.minecraft.database.SqlConnection;
import com.omni.minecraft.database.storage.model.base.BaseDAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class NamedDAO<O extends NamedDTO> extends BaseDAO<O> {

    private final String findByNameQuery;

    protected NamedDAO(SqlConnection connection, String name, Class<O> oClass) throws SQLException {
        super(connection, name, oClass);
        findByNameQuery = "SELECT * FROM " + name + " WHERE fastName=?";
    }

    public O findByName(String name) throws SQLException {
        PreparedStatement statement = prepareStatement(findByNameQuery);

        statement.setString(1, name);

        ResultSet result = statement.executeQuery();

        O o = result.next() ? fromResult(result) : null;

        statement.close();

        return o;
    }
}

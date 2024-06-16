package com.omni.minecraft.database.storage.model.base;

import com.omni.minecraft.database.SqlConnection;
import com.omni.minecraft.database.storage.table.Table;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseDAO<O extends DTO> implements DAO<O> {

    protected final SqlConnection connection;
    protected final String name;
    private final String findAllQuery;
    private final String findOneQuery;
    private final String deleteOneQuery;
    private final String findByIdInQuery;
    protected Table table;

    protected BaseDAO(SqlConnection connection, String name, Class<O> oClass) throws SQLException {
        this.connection = connection;
        this.name = name;
        this.findAllQuery = "SELECT id FROM " + name;
        this.findOneQuery = "SELECT * FROM " + name + " WHERE id=?";
        this.deleteOneQuery = "DELETE FROM " + name + " WHERE id=?";
        this.findByIdInQuery = "SELECT * FROM " + name + " WHERE id in (";

        init(oClass);
    }

    protected void init(Class<O> oClass) throws SQLException {
        table = new Table(name, oClass);

        Statement statement = createStatement();

        statement.executeUpdate(table.createTableQuery());

        statement.close();
    }

    protected abstract O newInstance();

    public O fromResult(ResultSet result) {
        O o = newInstance();

        table.setValues(o, result);

        return o;
    }

    public List<O> fromResultList(ResultSet result) throws SQLException {
        List<O> list = new ArrayList<>();

        while (result.next()) list.add(fromResult(result));

        return list;
    }

    @Override
    public void save(O dto) throws SQLException {
        PreparedStatement statement = prepareStatement(table.getUpdateQuery());

        save(dto, statement);

        statement.close();
    }

    @Override
    public void save(List<O> dtos) throws SQLException {
        PreparedStatement statement = prepareStatement(table.getUpdateQuery());

        for (O dto : dtos) save(dto, statement);

        statement.close();
    }

    protected void save(O dto, PreparedStatement statement) throws SQLException {
        table.update(dto, statement);
        statement.executeUpdate();
    }

    @Override
    public O findOne(int id) throws SQLException {
        PreparedStatement statement = prepareStatement(findOneQuery);

        statement.setInt(1, id);

        ResultSet result = statement.executeQuery();

        O dto = result.next() ? fromResult(result) : null;

        statement.close();

        return dto;
    }

    protected List<Integer> prepare(PreparedStatement statement) throws SQLException {
        ResultSet result = statement.executeQuery();

        List<Integer> ids = new ArrayList<>();

        while (result.next()) ids.add(result.getInt(1));

        statement.close();

        return ids;
    }

    protected PreparedStatement prepareStatement(String query) throws SQLException {
        return connection.getConnection().prepareStatement(query);
    }

    protected Statement createStatement() throws SQLException {
        return connection.getConnection().createStatement();
    }

    @Override
    public List<O> findByIdIn(List<Integer> ids) throws SQLException {
        int iMax = ids.size() - 1;

        StringBuilder b = new StringBuilder(findByIdInQuery);
        for (int i = 0; ; i++) {
            b.append(ids.get(i));
            if (i == iMax) {
                b.append(")");
                break;
            }
            b.append(", ");
        }

        Statement statement = createStatement();

        ResultSet result = statement.executeQuery(b.toString());

        List<O> dtos = fromResultList(result);

        statement.close();

        return dtos;
    }

    @Override
    public List<Integer> findAll() throws SQLException {
        Statement statement = createStatement();

        ResultSet result = statement.executeQuery(findAllQuery);

        List<Integer> dtos = new ArrayList<>();

        while (result.next()) dtos.add(result.getInt(1));

        statement.close();

        return dtos;
    }

    @Override
    public O create(O dto) throws SQLException {
        PreparedStatement statement = connection.getConnection().prepareStatement(table.getInsertQuery(), Statement.RETURN_GENERATED_KEYS);

        if (dto == null) dto = newInstance();

        table.insert(dto, statement);

        statement.executeUpdate();

        ResultSet result = statement.getGeneratedKeys();

        if (result.next()) {
            dto.id = result.getInt(1);
        } else throw new SQLException();

        statement.close();

        return dto;
    }

    @Override
    public void delete(int id) throws SQLException {
        PreparedStatement statement = prepareStatement(deleteOneQuery);

        statement.setInt(1, id);

        statement.executeUpdate();

        statement.close();
    }
}

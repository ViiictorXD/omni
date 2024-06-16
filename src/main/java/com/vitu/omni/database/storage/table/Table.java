package com.vitu.omni.database.storage.table;

import com.vitu.omni.database.storage.model.base.DTO;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class Table {

    private String name;
    private List<Column> columns;
    private String allColumns;
    private String insertQuery;
    private String updateQuery;
    private Column primary;

    public <O extends DTO> Table(String name, Class<O> oClass) {
        this.name = name;
        this.columns = new ArrayList<>();

        addColumns(oClass);
        build(oClass);
    }

    private void build(Class<? extends DTO> clazz) {
        insertQuery = insertQuery();
        updateQuery = updateQuery();

        List<Field> fields = getAllFields(new ArrayList<>(), clazz);

        for (Field field : fields) {
            Column column = getColumn(field.getName());
            column.setField(field);
            field.setAccessible(true);
        }

        primary.setIndex(columns.size());
        allColumns = allColumns();
    }

    private void addColumns(Class<? extends DTO> clazz) {
        List<Field> fields = getAllFields(new ArrayList<>(), clazz);

        for (Field field : fields) {
            addColumn(new Column(field.getName(), field.getType(), field.getName().equals("id")));
        }
    }

    private void addColumn(Column column) {
        column.setIndex(columns.size());
        this.columns.add(column);
        if (column.isPrimary()) primary = column;
    }

    private Column getColumn(String name) {
        for (Column column : columns) {
            if (column.getName().equals(name)) return column;
        }

        return null;
    }

    private List<Field> getAllFields(List<Field> fields, Class<?> type) {
        List<Field> add = new ArrayList<>();

        for (Field field : type.getDeclaredFields()) {
            if (Modifier.isFinal(field.getModifiers())) continue;
            add.add(field);
        }

        fields.addAll(0, add);

        if (type.getSuperclass() != null) {
            getAllFields(fields, type.getSuperclass());
        }

        return fields;
    }

    public String createTableQuery() {
        StringBuilder builder = new StringBuilder();

        builder.append("CREATE TABLE IF NOT EXISTS ").append(name)
                .append(" (");

        for (int i = 0; i < columns.size(); i++) {
            Column column = columns.get(i);
            column.append(builder);
            if (i != columns.size() - 1) builder.append(",");
        }

        builder.append(");");

        return builder.toString();
    }

    private String insertQuery() {
        StringBuilder builder = new StringBuilder();

        builder.append("INSERT INTO ").append(name).append(" (");

        for (int i = 0; i < columns.size(); i++) {
            Column column = columns.get(i);

            //0 = 1 (me entendes '-')
            if (column.isPrimary()) continue;

            builder.append(column.getName());
            if (i != columns.size() - 1) builder.append(",");
        }

        builder.append(") VALUES (");

        for (int i = 0; i < columns.size(); i++) {
            Column column = columns.get(i);

            //0 = 1 (me entendes '-')
            if (column.isPrimary()) continue;

            builder.append("?");
            if (i != columns.size() - 1) builder.append(",");
        }

        builder.append(");");

        return builder.toString();
    }

    private String updateQuery() {
        StringBuilder builder = new StringBuilder();

        builder.append("UPDATE ").append(name).append(" SET ");

        int index = 0;
        for (int i = 0; i < columns.size(); i++) {
            Column column = columns.get(i);

            //0 = 1 (me entendes '-')
            if (column.isPrimary()) continue;

            builder.append(column.getName()).append("=").append("?");
            if (i != columns.size() - 1) builder.append(",");
        }

        builder.append(" WHERE id=?");

        return builder.toString();
    }

    private String allColumns() {
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < columns.size(); i++) {
            Column column = columns.get(i);

            builder.append(column.getName());

            if (i != columns.size() - 1) builder.append(",");
        }

        return builder.toString();
    }

    public String getAllColumns() {
        return allColumns;
    }

    public String getInsertQuery() {
        return insertQuery;
    }

    public String getUpdateQuery() {
        return updateQuery;
    }

    public void insert(DTO dto, PreparedStatement statement) {
        try {
            for (Column column : columns) {
                if (column.isPrimary()) continue;

                statement.setObject(column.getIndex(), column.getField().get(dto));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update(DTO dto, PreparedStatement statement) {
        try {
            for (Column column : columns) {
                if (column.isPrimary()) continue;

                statement.setObject(column.getIndex(), column.getField().get(dto));
            }
            statement.setObject(primary.getIndex(), primary.getField().get(dto));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setValues(DTO dto, ResultSet result) {
        try {
            for (Column column : columns) {
                column.setValue(dto, result.getObject(column.getName()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

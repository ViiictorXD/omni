package com.vitu.omni.database.storage.table;

import com.vitu.omni.database.storage.model.base.DTO;

import java.lang.reflect.Field;
import java.math.BigDecimal;

public class Column<E extends Class> {

    private String name;
    private String type;
    private boolean primary;
    private Field field;
    private int index;

    public Column(String name, E type, boolean primary) {
        this.name = name;
        this.type = typeOf(type);
        this.primary = primary;
    }

    private static String typeOf(Class o) {
        if (o.isAssignableFrom(Integer.TYPE) || o == Integer.class) return "INTEGER";
        else if (o.isAssignableFrom(Double.TYPE) || o == Double.class) return "DOUBLE";
        else if (o.isAssignableFrom(Boolean.TYPE) || o == Boolean.class) return "BIT";
        else if (o.isAssignableFrom(String.class) || o == String.class) return "VARCHAR";
        else if (o.isAssignableFrom(BigDecimal.class) || o == BigDecimal.class) return "DECIMAL";
        else if (o.isAssignableFrom(Long.TYPE) || o == Long.class) return "BIGINT";
        else if (o.isAssignableFrom(Float.TYPE) || o == Float.class) return "FLOAT";
        return null;
    }

    private static Size sizeOf(String size) {
        switch (size) {
            case "INTEGER":
                return new Size(11);
            case "DOUBLE":
                return new Size(12, 2);
            case "BIT":
                return new Size(1);
            case "VARCHAR":
                return new Size(256);
            case "BIGINT":
                return new Size(64);
            case "DECIMAL":
                return new Size(12, 2);
            case "FLOAT":
                return new Size(32);
            default:
                return null;
        }
    }

    public void append(StringBuilder builder) {
        if (primary) builder.append("id INTEGER PRIMARY KEY AUTOINCREMENT");
        else {
            builder.append(name).append(" ").append(type).append(" ");
            builder.append(sizeOf(type));
        }
    }

    public String getName() {
        return name;
    }

    public boolean isPrimary() {
        return primary;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public void setValue(DTO dto, Object object) throws IllegalAccessException {
        if (object == null) return;
        switch (type) {
            case "BIT":
                field.setBoolean(dto, ((Integer) object) == 1);
                break;
            default:
                field.set(dto, object);
        }
    }
}

package com.omni.minecraft.database;

public interface DatabaseType {

    enum Relational implements DatabaseType {
        SQLITE,
        MYSQL
    }

    enum Document implements DatabaseType {
        MONGO
    }

    enum Memory implements DatabaseType {
        NATIVE,
        REDIS
    }
}
package com.omni.minecraft.database;

import java.sql.Connection;

public interface Database {

    Connection getConnection();

    DatabaseType getType();

}
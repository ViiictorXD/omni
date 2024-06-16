package com.vitu.omni.database;

import lombok.SneakyThrows;

import java.sql.Connection;

public interface SqlConnection {

  Connection getConnection();

  void connect();

  void disconnect();

  @SneakyThrows
  default boolean hasConnection() {
    Connection connection = getConnection();
    return connection != null && !connection.isClosed();
  }
}

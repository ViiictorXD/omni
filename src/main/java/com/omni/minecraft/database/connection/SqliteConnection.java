package com.omni.minecraft.database.connection;

import com.omni.minecraft.database.SqlConnection;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@RequiredArgsConstructor
public class SqliteConnection implements SqlConnection {

  private @NotNull File file;
  private @NotNull String name;

  private Connection connection;

  @SneakyThrows
  @Override
  public Connection getConnection() {
    if (connection == null || connection.isClosed()) {
      connect();
    }

    return connection;
  }

  @Override
  public void connect() {
    try {
      Class.forName("org.sqlite.JDBC");

      connection = DriverManager.getConnection(String.format(
        "jdbc:sqlite:%s/%s.db", file, name
      ));
    } catch (SQLException | ClassNotFoundException exception) {
      exception.printStackTrace();
    }

  }

  @SneakyThrows
  @Override
  public void disconnect() {
    if (connection == null || connection.isClosed()) {
      return;
    }
    connection.close();
  }
}

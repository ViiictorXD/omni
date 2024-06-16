package com.omni.minecraft.database.connection;

import com.omni.minecraft.database.SqlConnection;
import com.omni.minecraft.database.SqlProperty;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@RequiredArgsConstructor
public class MysqlConnection implements SqlConnection {

  @NotNull
  private SqlProperty property;

  private Connection connection;

  @SneakyThrows
  @Override
  public Connection getConnection() {
    if (connection == null || connection.isClosed()) {
      connect();
    }

    return connection;
  }

  @SneakyThrows
  @Override
  public void connect() {
    try {
      Class.forName("com.mysql.jdbc.Driver");

      connection = DriverManager.getConnection(String.format(
        "jdbc:mysql://%s/%s", property.getProperty("host"), property.getProperty("database")
      ), property.getProperty("username"), property.getProperty("password"));
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

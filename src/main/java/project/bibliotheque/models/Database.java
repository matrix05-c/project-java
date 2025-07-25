package project.bibliotheque.models;

import project.bibliotheque.config.Env;

import java.sql.*;
import java.util.*;


public class Database {
  private static Connection conn = null;

  public static Connection getConnection() {
    if (Database.conn == null) {
      Properties connectionProps = new Properties();
      connectionProps.setProperty("user", Env.DB.getProperty("username"));
      connectionProps.setProperty("password", Env.DB.getProperty("password"));

      try {
        Database.conn = DriverManager.getConnection(Env.DB.getProperty("url"), connectionProps);
      } catch (Exception e) {
        System.err.println("ERROR: Failed to connect to db: " + e);
        Database.conn = null;
      }
    }

    return Database.conn;
  }
}

package project.bibliotheque.models;

import java.sql.*;
import project.bibliotheque.config.Env;


public class Account {
  private static String table = Env.TABLE.getProperty("account");
  private static String findBycredentialSttm = "SELECT id FROM "+table+" WHERE nom = ? AND mdp = ?";
  private static String selectSttm = "SELECT * FROM "+table;
  private static Connection conn = Database.getConnection();

  private Integer id;
  private String username;
  private String password;

  public static Integer verify(String username, String password) {
    Integer accountId = -1;

    try {
      PreparedStatement findSttm = conn.prepareStatement(findBycredentialSttm);
      findSttm.setString(1, username);
      findSttm.setString(2, password);
      ResultSet res = findSttm.executeQuery();

      if (res.next())
        accountId = res.getInt("id");

      res.close();
    } catch (Exception e) {
      System.err.println("ERROR: failed to verify credential: " + e);
    }

    return accountId;
  }
}

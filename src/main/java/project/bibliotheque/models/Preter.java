package project.bibliotheque.models;

import project.bibliotheque.config.Env;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Preter {
  private static String table = Env.TABLE.getProperty("preter");
  private static String deleteSttmt = "DELET * FROM WHERE id = ?";
  private static String updateSttmt = "UPDATE " + table
      + " SET membre = ?, livre = ?, datepret = ?, dateretour = ? WHERE id = ?";
  private static String insertSttmt = "INSERT INTO " + table
      + " (membre, livre, datepret, dateretour) VALUES (?, ?, ?, ?)";
  private static String findsttmt = "SELECT * FROM " + table;
  private static String findEntreDate = "SELECT * FROM " + table + " WHERE DATE(datepret) BETWEEN ? AND ?";
  private static String haveBorrowedSttm = "SELECT (COUNT(*) > 0) AS aEmpreinter FROM " + table
      + " WHERE membre = ? AND dateretour IS NULL";
  private static String borrowedBookSttm = "SELECT p.livre FROM " + table
      + " p WHERE p.membre = ? AND p.dateretour IS NULL;";
  private static String findNotReturned = "SELECT * FROM " + table
      + " WHERE livre = ? AND membre = ? AND dateretour IS NULL";
  private static String findBorrowedBySttm = "SELECT livre, datepret FROM " + table
      + " WHERE membre = ? AND dateretour IS NULL";

  private static Connection conn = Database.getConnection();

  private Integer id;
  private Integer membre;
  private Integer livre;
  private Timestamp dateRetour;
  private Timestamp datePret;

  public static List<Preter> getPreters() {
    List<Preter> list = new ArrayList<>();

    try {
      PreparedStatement sttmt = conn.prepareStatement(findsttmt);
      ResultSet res = sttmt.executeQuery();

      while (res.next()) {
        Preter preter = new Preter();
        preter.id = res.getInt("id");
        preter.membre = res.getInt("membre");
        preter.livre = res.getInt("livre");
        preter.datePret = res.getTimestamp("datepret");
        preter.dateRetour = res.getTimestamp("dateretour");

        list.add(preter);
      }

    } catch (Exception e) {
      System.err.println("Error: failed to get Preter " + e.getMessage());
    }
    return list;
  }
  // membre, livre, datepret, dateretour

  public static void insertPreter(Integer membre, Integer livre, Timestamp datepret, Timestamp dateretour) {
    try {
      PreparedStatement statement = conn.prepareStatement(insertSttmt);
      statement.setInt(1, membre);
      statement.setInt(2, livre);
      statement.setTimestamp(3, datepret);
      statement.setTimestamp(4, dateretour);
      statement.execute();

    } catch (Exception e) {
      System.err.println("error: failed to insert preter " + e.getMessage());
    }
  }

  public static Preter findNotReturned(Integer livre, Integer member) {
    Preter preter = new Preter();

    try {
      PreparedStatement sttmt = conn.prepareStatement(findNotReturned);
      sttmt.setInt(1, livre);
      sttmt.setInt(2, member);
      ResultSet res = sttmt.executeQuery();

      while (res.next()) {
        preter.id = res.getInt("id");
        preter.membre = res.getInt("membre");
        preter.livre = res.getInt("livre");
        preter.datePret = res.getTimestamp("datepret");
        preter.dateRetour = res.getTimestamp("dateretour");
      }

    } catch (Exception e) {
      System.err.println("Error: failed to find Preter " + e.getMessage());
    }
    return preter;
  }

  public static Preter findNotReturnedBy(Integer member) {
    Preter preter = new Preter();

    try {
      PreparedStatement sttmt = conn.prepareStatement(findBorrowedBySttm);
      sttmt.setInt(1, member);
      ResultSet res = sttmt.executeQuery();

      while (res.next()) {
        preter.livre = res.getInt("livre");
        preter.datePret = res.getTimestamp("datepret");
      }

    } catch (Exception e) {
      System.err.println("Error: failed to find Preter " + e.getMessage());
    }
    return preter;
  }

  public static void deletepreter(int id) {
    try {
      PreparedStatement statement = conn.prepareStatement(deleteSttmt);
      statement.setInt(0, id);
      statement.executeUpdate();
    } catch (Exception e) {
      System.err.println("Error: failed to delete preter " + e.getMessage());
    }
  }

  public static void updatePreter(Integer id, Integer membre, Integer livre, Timestamp datepret,
      Timestamp dateretour) {
    try {
      PreparedStatement statement = conn.prepareStatement(updateSttmt);
      statement.setInt(1, membre);
      statement.setInt(2, livre);
      statement.setTimestamp(3, datepret);
      statement.setTimestamp(4, dateretour);
      statement.setInt(5, id);
      statement.executeUpdate();

    } catch (Exception e) {
      System.err.println("Error: failed to update preter " + e.getMessage());
    }
  }

  public static List<Preter> findBetweenDate(Date date1, Date date2) {
    List<Preter> list = new ArrayList<>();

    try {
      PreparedStatement sttmt = conn.prepareStatement(findEntreDate);
      sttmt.setDate(1, date1);
      sttmt.setDate(2, date2);
      ResultSet res = sttmt.executeQuery();

      while (res.next()) {
        Preter preter = new Preter();
        preter.id = res.getInt("id");
        preter.membre = res.getInt("membre");
        preter.livre = res.getInt("livre");
        preter.datePret = res.getTimestamp("datepret");
        preter.dateRetour = res.getTimestamp("dateretour");

        list.add(preter);
      }

    } catch (Exception e) {
      System.err.println("Error: failed to get Preter " + e.getMessage());
    }
    return list;
  }

  public static Boolean aEmpreinter(Integer membre) {
    boolean result = false;
    try {
      PreparedStatement statement = conn.prepareStatement(haveBorrowedSttm);
      statement.setInt(1, membre);
      ResultSet res = statement.executeQuery();
      while (res.next()) {
        result = res.getBoolean("aEmpreinter");
      }
    } catch (Exception e) {
      System.err.println("Error: failed to chech state " + e.getMessage());
    }
    return result;
  }

  public static Integer getBorrowedBook(Integer member) {
    Integer result = -1;
    try {
      PreparedStatement statement = conn.prepareStatement(borrowedBookSttm);
      statement.setInt(1, member);
      ResultSet res = statement.executeQuery();
      while (res.next()) {
        result = res.getInt("livre");
      }
    } catch (Exception e) {
      System.err.println("Error: failed to find borrowed book " + e.getMessage());
    }
    return result;
  }

  public int getId() {

    return id;
  }

  public int getmembre() {
    return membre;
  }

  public int getLivre() {
    return livre;
  }

  public Timestamp getDatepret() {
    return datePret;
  }

  public Timestamp getDateretour() {
    return dateRetour;
  }

  // ..............................
  public void setId(Integer id) {
    this.id = id;
  }

  public void setmembre(Integer membre) {
    this.membre = membre;
  }

  public void setgetLivre(Integer livre) {
    this.livre = livre;
  }

  public void setDatepret(Timestamp datePret) {
    this.datePret = datePret;
  }

  public void setDateretour(Timestamp dateretour) {
    this.dateRetour = dateretour;

  }

}

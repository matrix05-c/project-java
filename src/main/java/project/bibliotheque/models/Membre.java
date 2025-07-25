package project.bibliotheque.models;

import project.bibliotheque.config.Env;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Membre {
  private static String table = Env.TABLE.getProperty("member");
  private static String preterTable = Env.TABLE.getProperty("preter");
  private static String findBycredentialisme = "SELECT * FROM " + table + " WHERE nom LIKE ?";
  private static String findsttmt = "SELECT * FROM " + table;
  private static String deleteSttmt = "DELETE FROM " + table + " WHERE id = ?";
  private static String insertSttmt = "INSERT INTO " + table + " (nom, sexe, age, contact) VALUES (?, ?, ?, ?)";
  private static String lateStmt = "SELECT m.*, ((DATE(NOW()) - DATE(p.datepret)) - 30) AS dayAfterLate FROM " + table
      + " m JOIN " + preterTable
      + " p ON m.id = p.membre WHERE (DATE(NOW()) - DATE(p.datepret)) >= 30 AND p.dateretour IS NULL";
  private static String eligibleStmt = "SELECT m.* FROM " + table + " m WHERE m.id NOT IN (SELECT p.membre FROM "
      + preterTable + " p WHERE p.dateretour IS NULL)";

  private static String updateSttmt = "UPDATE " + table
      + " SET nom = ? , sexe = ?, age = ?, contact = ?  WHERE id = ?";

  private static Connection conn = Database.getConnection();

  private Integer id;
  private String nom;
  private Gender sexe;
  private Integer age;
  private String contact;
  private Integer dayAfterLate;

  public static List<Membre> getMembres() {

    List<Membre> list = new ArrayList<>();

    try {
      PreparedStatement sttmt = conn.prepareStatement(findsttmt);
      ResultSet res = sttmt.executeQuery();

      while (res.next()) {
        Membre membre = new Membre();
        membre.id = res.getInt("id");
        membre.nom = res.getString("nom");
        membre.sexe = res.getString("sexe").equals("h") ? Gender.man : Gender.woman;
        membre.age = res.getInt("age");
        membre.contact = res.getString("contact");
        list.add(membre);
      }

    } catch (Exception e) {
      System.err.println("Error: failer to get member  list: " + e.getMessage());
      list.clear();
    }
    return list;
  }

  public static List<Membre> getMembreLike(String name) {
    List<Membre> list = new ArrayList<>();
    try {
      PreparedStatement sttmt = conn.prepareStatement(findBycredentialisme);
      sttmt.setString(1, name);
      ResultSet res = sttmt.executeQuery();

      while (res.next()) {
        Membre membre = new Membre();
        membre.id = res.getInt("id");
        membre.nom = res.getString("nom");
        membre.sexe = res.getString("sexe").equals("h") ? Gender.man : Gender.woman;
        membre.age = res.getInt("age");
        membre.contact = res.getString("contact");
        list.add(membre);
      }

    } catch (Exception e) {
      System.err.println("Error: failer to get member  list: " + e.getMessage());
      list.clear();
    }

    return list;
  }

  public static List<Membre> getMembreLate() {
    List<Membre> list = new ArrayList<>();
    try {
      PreparedStatement sttmt = conn.prepareStatement(lateStmt);
      ResultSet res = sttmt.executeQuery();

      while (res.next()) {
        Membre membre = new Membre();
        membre.id = res.getInt("id");
        membre.nom = res.getString("nom");
        membre.sexe = res.getString("sexe").equals("h") ? Gender.man : Gender.woman;
        membre.age = res.getInt("age");
        membre.contact = res.getString("contact");
        membre.dayAfterLate = res.getInt("dayAfterLate");
        list.add(membre);
      }

    } catch (Exception e) {
      System.err.println("Error: failer to get member  list: " + e.getMessage());
      list.clear();
    }

    return list;
  }

  public static List<Membre> getMembreEligible() {
    List<Membre> list = new ArrayList<>();
    try {
      PreparedStatement sttmt = conn.prepareStatement(eligibleStmt);
      ResultSet res = sttmt.executeQuery();

      while (res.next()) {
        Membre membre = new Membre();
        membre.id = res.getInt("id");
        membre.nom = res.getString("nom");
        membre.sexe = res.getString("sexe").equals("h") ? Gender.man : Gender.woman;
        membre.age = res.getInt("age");
        membre.contact = res.getString("contact");
        list.add(membre);
      }

    } catch (Exception e) {
      System.err.println("Error: failer to get member  list: " + e.getMessage());
      list.clear();
    }

    return list;
  }

  public static void deleteMembre(int id) {
    try {
      PreparedStatement statement = conn.prepareStatement(deleteSttmt);
      statement.setInt(1, id);
      statement.execute();
    } catch (Exception e) {
      System.err.println("Error: failed to delete member : " + e.getMessage());
    }
  }

  public static void updateMembre(int id, String nom, Gender sexe, Integer age, String contact) {

    try {
      PreparedStatement sttmt = conn.prepareStatement(updateSttmt);
      sttmt.setString(1, nom);
      sttmt.setString(2, sexe == Gender.man ? "h" : "f");
      sttmt.setInt(3, age);
      sttmt.setString(4, contact);
      sttmt.setInt(5, id);

      sttmt.executeUpdate();

    } catch (Exception e) {
      System.err.println("Error: failed to update membre : " + e.getMessage());
    }

  }

  public static void insertMember(String nom, Gender sexe, Integer age, String contact) {
    try {
      PreparedStatement statement = conn.prepareStatement(insertSttmt);
      statement.setString(1, nom);
      statement.setString(2, sexe == Gender.man ? "h" : "f");
      statement.setInt(3, age);
      statement.setString(4, contact);

      statement.execute();
    } catch (Exception e) {
      System.err.println("error: failed to Insert member " + e.getMessage());
    }
  }

  public int getId() {
    return id;
  }

  public String getNom() {
    return nom;
  }

  public Gender getSexe() {
    return sexe;
  }

  public Integer getAge() {
    return age;
  }

  public String getContact() {
    return contact;
  }

  public Integer getDayAfterLate() {
    return this.dayAfterLate;
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setNom(String nom) {
    this.nom = nom;
  }

  public void setSexe(Gender sexe) {
    this.sexe = sexe;
  }

  public void setAge(int age) {
    this.age = age;
  }

  public void setContact(String contact) {
    this.contact = contact;
  }
}

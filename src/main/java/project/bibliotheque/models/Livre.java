package project.bibliotheque.models;

import project.bibliotheque.config.Env;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.*;

public class Livre {
  private static String table = Env.TABLE.getProperty("livre");
  private static String deleteSttmt = "DELETE FROM " + table + " WHERE id = ?";
  private static String insertSttmt = "INSERT INTO " + table + " (titre, auteur, exemplaire) VALUES (?, ?, ?)";
  private static String updateSttmt = "UPDATE " + table + " SET titre = ?, auteur = ?, exemplaire = ? WHERE id = ?";
  private static String stmtAll = "SELECT * FROM " + table;
  private static String findByIdSttm = "SELECT * FROM " + table + " WHERE id = ?";
  private static String stmtTitreFind = "SELECT * FROM " + table + " WHERE titre LIKE ?";
  private static String isBorrowedSttm = "SELECT (COUNT(*) > 0) AS estEmpreinter FROM preter WHERE livre = ? AND dateretour IS NULL";

  private static Connection conn = Database.getConnection();

  private IntegerProperty id = new SimpleIntegerProperty();
  private StringProperty titre = new SimpleStringProperty();
  private StringProperty auteur = new SimpleStringProperty();
  private IntegerProperty exemplaire = new SimpleIntegerProperty();

  public static List<Livre> getLivre() {
    List<Livre> list = new ArrayList<>();

    try {
      PreparedStatement sttmt = conn.prepareStatement(stmtAll);
      ResultSet res = sttmt.executeQuery();

      while (res.next()) {
        Livre livre = new Livre();
        livre.id.set(res.getInt("id"));
        livre.titre.set(res.getString("titre"));
        livre.auteur.set(res.getString("auteur"));
        livre.exemplaire.set(res.getInt("exemplaire"));

        list.add(livre);
      }
    } catch (Exception e) {
      System.err.println("error: failed to load all livre " + e.getMessage());
    }
    return list;
  }

  public static Livre findById(Integer id) {
    Livre book = new Livre();

    try {
      PreparedStatement sttmt = conn.prepareStatement(findByIdSttm);
      sttmt.setInt(1, id);
      ResultSet res = sttmt.executeQuery();

      while (res.next()) {
        book.id.set(res.getInt("id"));
        book.titre.set(res.getString("titre"));
        book.auteur.set(res.getString("auteur"));
        book.exemplaire.set(res.getInt("exemplaire"));
      }
    } catch (Exception e) {
      System.err.println("error: failed to find livre " + e.getMessage());
    }
    return book;
  }

  public static void insertLivre(String titre, String auteur, Integer exemplaire) {
    try {
      PreparedStatement statement = conn.prepareStatement(insertSttmt);
      statement.setString(1, titre);
      statement.setString(2, auteur);
      statement.setInt(3, exemplaire);

      statement.execute();

    } catch (Exception e) {
      System.err.println("failed to Insert Livre: " + e.getMessage());
    }
  }

  public static void updateLivre(String titre, String auteur, Integer exemplaire, Integer id) {
    try {
      PreparedStatement statement = conn.prepareStatement(updateSttmt);
      statement.setString(1, titre);
      statement.setString(2, auteur);
      statement.setInt(3, exemplaire);
      statement.setInt(4, id);
      statement.executeUpdate();

    } catch (Exception e) {
      System.err.println("Error: failled to update Livre, " + e.getMessage());
    }
  }

  public static void deleteLivre(Integer id) {
    try {
      PreparedStatement statement = conn.prepareStatement(deleteSttmt);
      statement.setInt(1, id);
      statement.executeQuery();
    } catch (Exception e) {
      System.err.println("Error: failed to delete Livre, " + e.getMessage());
    }
  }

  public static List<Livre> findLivre(String nomLivre) {
    List<Livre> list = new ArrayList<>();

    try {
      PreparedStatement sttmt = conn.prepareStatement(stmtTitreFind);
      sttmt.setString(1, nomLivre);
      ResultSet res = sttmt.executeQuery();

      while (res.next()) {
        Livre livre = new Livre();
        livre.id.set(res.getInt("id"));
        livre.titre.set(res.getString("titre"));
        livre.auteur.set(res.getString("auteur"));
        livre.exemplaire.set(res.getInt("exemplaire"));

        list.add(livre);
      }
    } catch (Exception e) {
      System.err.println("error: failed to search livre " + e.getMessage());
    }
    return list;
  }

  public static boolean isBorrowed(Integer id) {
    Boolean result = false;
    try {
      PreparedStatement statement = conn.prepareStatement(isBorrowedSttm);
      statement.setInt(1, id);
      ResultSet res = statement.executeQuery();

      while (res.next()) {
        result = res.getBoolean("estEmpreinter");
      }
    } catch (Exception e) {
      System.err.println("Error: failed to delete Livre, " + e.getMessage());
    }
    return result;
  }

  public Integer getId() {
    return id.get();
  }

  public String getTitre() {
    return titre.get();
  }

  public String getAuteur() {
    return auteur.get();
  }

  public Integer getExemplaire() {
    return exemplaire.get();
  }

  public void setId(int id) {
    this.id.set(id);
  }

  public void setTitre(String titre) {
    this.titre.set(titre);
  }

  public void setAuteur(String auteur) {
    this.auteur.set(auteur);
  }

  public void setExemplaire(int exemplaire) {
    this.exemplaire.set(exemplaire);
  }

  public IntegerProperty getIdProperty() {
    return id;
  }

  public StringProperty getTitreProperty() {
    return titre;
  }

  public StringProperty getAuteurProperty() {
    return auteur;
  }

  public IntegerProperty getExemplaireProperty() {
    return exemplaire;
  }

}

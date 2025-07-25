package project.bibliotheque.models;

import project.bibliotheque.config.Env;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.postgresql.translation.messages_nl;



public class Rendre {
     private static String table = Env.TABLE.getProperty("rendre");
    private static String deleteSttmt = "DELET * FROM " + table + " WHERE id = ?";
    private static String updateSttmt = "UPDATE " + table
            + " SET membre = ?, livre = ?, daterendu = ? WHERE id = ?";
    private static String insertSttmt = "INSERT INTO " + table
            + " (membre, livre, daterendu) VALUES (?, ?, ?)";
    private static String findsttmt = "SELECT * FROM " + table;

    private static Connection conn = Database.getConnection();

    private Integer id;
    private Integer membre;
    private Integer livre;
    private Timestamp daterendu;


    public static List<Rendre> getRendres() {
        List<Rendre> list = new ArrayList<>();

        try {
            PreparedStatement sttmt = conn.prepareStatement(findsttmt);
            ResultSet res = sttmt.executeQuery();

            while (res.next()) {
                Rendre rendre = new Rendre();
                rendre.id = res.getInt("id");
                rendre.membre = res.getInt("membre");
                rendre.livre = res.getInt("livre");
                rendre.daterendu = res.getTimestamp("daterendu");

                list.add(rendre);
            }
        } catch (Exception e) {
            System.err.println("error: failed to load all rendre" + e.getMessage());
        }
        return list;
    }


     public static void deleteRendre(Integer id) {
        try {
            PreparedStatement statement = conn.prepareStatement(deleteSttmt);
            statement.setInt(0, id);
            statement.executeQuery();
        } catch (Exception e) {
            System.err.println("Error: failed to delete Rendre " + e.getMessage());
        }
    }


     public static void updateRendre(Integer membre, Integer livre, Timestamp daterendu, Integer id) {
        try {
            PreparedStatement statement = conn.prepareStatement(updateSttmt);
            statement.setInt(0, membre);
            statement.setInt(1, livre);
            statement.setTimestamp(2, daterendu);
            statement.setInt(3, id);
            statement.executeUpdate();

        } catch (Exception e) {
            System.err.println("Error: failled to update Rendre, " + e.getMessage());
        }
    }

    public static void inserRendre(Integer membre, Integer livre, Timestamp daterendu) {
        try {
            PreparedStatement statement = conn.prepareStatement(insertSttmt);
            statement.setInt(1, membre);
            statement.setInt(2, livre);
            statement.setTimestamp(3, daterendu);

            statement.execute();

        } catch (Exception e) {
            System.err.println("failed to Insert rendu: " + e.getMessage());
        }
    }

    public Integer getId(){
        return id;
    }

    public Integer getMembre(){
        return membre;
    }

    public Integer getLivre(){
        return livre;
    }

    public Timestamp getDaterendu(){
        return daterendu;
    }

    //lllkjlllllllllllll

    public void settId(Integer id){
        this.id = id;
    }

    public void setMembre(Integer membre){
        this.membre = membre;
    }

    public void setLivre(Integer livre){
        this.livre = livre;
    }

    public void setDaterendu(Timestamp daterendu){
        this.daterendu = daterendu;
    }

}

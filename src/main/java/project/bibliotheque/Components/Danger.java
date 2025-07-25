package project.bibliotheque.Components;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * Danger
 */
public class Danger {
  private static Alert alert = new Alert(AlertType.WARNING);
  public static void show(String title, String message) {
    alert.setTitle(title);
    alert.setContentText(message);
    alert.showAndWait();
  }
}

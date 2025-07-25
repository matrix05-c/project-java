package project.bibliotheque.Components;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class Information {
  private static Alert alert = new Alert(AlertType.INFORMATION);

  public static void show(String title, String message) {
    alert.setTitle(title);
    alert.setContentText(message);
    alert.showAndWait();
  }
}

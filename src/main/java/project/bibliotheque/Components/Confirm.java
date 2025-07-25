package project.bibliotheque.Components;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;


public class Confirm {
  private static Alert alert = new Alert(AlertType.CONFIRMATION);
  public static boolean show(String title, String message) {
    alert.setTitle(title);
    alert.setContentText(message);
    return alert.showAndWait().get() == ButtonType.OK;
  }
}

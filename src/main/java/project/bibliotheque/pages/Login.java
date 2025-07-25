package project.bibliotheque.pages;

import io.github.palexdev.materialfx.controls.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.geometry.*;
import javafx.scene.input.*;
import javafx.event.*;

public class Login extends GridPane {
  private Label title;
  private MFXButton loginBtn;
  private MFXTextField usernameInput;
  private MFXPasswordField passwordInput;

  private Label validationFeedback;

  public Login() {
    title = new Label();
    loginBtn = new MFXButton();
    usernameInput = new MFXTextField();
    passwordInput = new MFXPasswordField();
    validationFeedback = new Label();

    title.setText("Login");
    loginBtn.setText("Login");
    usernameInput.setPromptText("Username");
    passwordInput.setPromptText("Password");
    validationFeedback.setText("Invalid credential!");

    title.setPadding(new Insets(8));
    loginBtn.setPadding(new Insets(4, 8, 4, 8));
    usernameInput.setMinSize(128, 32);
    passwordInput.setMinSize(128, 32);
    usernameInput.setMaxSize(Double.MAX_VALUE, 32);
    passwordInput.setMaxSize(Double.MAX_VALUE, 32);

    this.setPadding(new Insets(32));
    this.setHgap(4);
    this.setVgap(4);

    this.addColumn(0, title, usernameInput, passwordInput, validationFeedback, loginBtn);

    GridPane.setVgrow(title, Priority.ALWAYS);
    GridPane.setVgrow(loginBtn, Priority.ALWAYS);
    GridPane.setHgrow(usernameInput, Priority.ALWAYS);
    GridPane.setHgrow(passwordInput, Priority.ALWAYS);

    GridPane.setHalignment(title, HPos.CENTER);
    GridPane.setValignment(title, VPos.CENTER);
    GridPane.setHalignment(loginBtn, HPos.RIGHT);
    GridPane.setValignment(loginBtn, VPos.CENTER);
    GridPane.setHalignment(usernameInput, HPos.CENTER);
    GridPane.setHalignment(passwordInput, HPos.CENTER);

    title.getStyleClass().add("display1");
    loginBtn.getStyleClass().addAll("mfx-button", "primary");
    validationFeedback.getStyleClass().add("validation-feedback");

    resetValidation();
  }

  public String getUsername() {
    return usernameInput.getText();
  }

  public String getPassword() {
    return passwordInput.getText();
  }

  public void notifyInvalidCredential() {
    validationFeedback.setVisible(true);
  }

  public void resetValidation() {
    validationFeedback.setVisible(false);
  }

  public void clearInput() {
    usernameInput.clear();
    passwordInput.clear();
  }

  public void setLoginBtnHandler(EventHandler<ActionEvent> handler) {
    loginBtn.setOnAction(handler);
  }
}

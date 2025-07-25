package project.bibliotheque.controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.*;
import project.bibliotheque.models.Account;
import project.bibliotheque.pages.*;


public class LoginController {
  private Login view;

  public LoginController() {
    view = new Login();
    view.setMaxSize(300, 300);
    view.setVisible(false);
  }

  public void setLoginBtnHandler(EventHandler<ActionEvent> handler) {
    view.setLoginBtnHandler(handler);
  }

  public boolean isCredentialValid() {
    String username = view.getUsername();
    String password = view.getPassword();

    return Account.verify(username, password) != -1;
  }

  public void notifyInvalidCredential() {
    view.notifyInvalidCredential();
  }

  public Pane getView() {
    return view;
  }
}

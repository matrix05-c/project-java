package project.bibliotheque.Components;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.*;
import javafx.scene.layout.*;

public class TableToolBtn extends HBox {
  private EmailBtn emailBtn;
  private EditBtn editBtn;
  private DeleteBtn deleteBtn;
  private TableActionBtn actionBtn;

  public TableToolBtn(String btnIcon, String btnLabel) {
    this.emailBtn = new EmailBtn();
    this.editBtn = new EditBtn();
    this.deleteBtn = new DeleteBtn();
    this.actionBtn = new TableActionBtn(btnIcon, btnLabel, false);

    this.editBtn.getStyleClass().addAll("mfx-button", "tool-button", "flat-button");
    this.deleteBtn.getStyleClass().addAll("mfx-button", "tool-button", "flat-button");

    this.setPadding(new Insets(16));
    this.setSpacing(8);
    this.getChildren()
        .addAll(
            this.emailBtn,
            this.editBtn,
            this.deleteBtn,
            this.actionBtn);
    this.setAlignment(Pos.CENTER_RIGHT);
  }

  public void setEmailVisibility(boolean visible) {
    this.emailBtn.setVisible(visible);
  }

  public void setEditActionState(boolean enabled) {
    this.editBtn.setDisable(!enabled);
  }

  public void setDeleteActionState(boolean enabled) {
    this.deleteBtn.setDisable(!enabled);
  }

  public void setActionBtnState(boolean enabled) {
    this.actionBtn.setDisable(!enabled);
  }

  public void setEmailHandler(EventHandler<ActionEvent> handler) {
    this.emailBtn.setOnAction(handler);
  }

  public void setEditHandler(EventHandler<ActionEvent> handler) {
    this.editBtn.setOnAction(handler);
  }

  public void setDeleteHandler(EventHandler<ActionEvent> handler) {
    this.deleteBtn.setOnAction(handler);
  }

  public void setActionHandler(EventHandler<ActionEvent> handler) {
    this.actionBtn.setOnAction(handler);
  }
}

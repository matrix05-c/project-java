package project.bibliotheque.pages;

import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.event.*;
import javafx.geometry.*;
import io.github.palexdev.materialfx.controls.*;

import project.bibliotheque.models.*;

public class BookEditPage extends BorderPane {
  private MFXTextField titleInput;
  private MFXTextField authorInput;
  private MFXTextField copyInput;
  private MFXButton saveBtn;

  public BookEditPage() {
    GridPane card = new GridPane();
    Label title = new Label();
    this.titleInput = new MFXTextField();
    this.authorInput = new MFXTextField();
    this.copyInput = new MFXTextField();
    this.saveBtn = new MFXButton();

    title.setText("Book");
    this.titleInput.setPromptText("Title");
    this.authorInput.setPromptText("Author");
    this.copyInput.setPromptText("Copy");
    this.saveBtn.setText("Save");

    title.getStyleClass()
        .add("display1");

    this.titleInput.getStyleClass()
        .addAll("mfx-text-field", "lg");
    this.authorInput.getStyleClass()
        .addAll("mfx-text-field", "lg");
    this.copyInput.getStyleClass()
        .addAll("mfx-text-field", "lg");
    this.saveBtn.getStyleClass()
        .addAll("mfx-button", "primary");

    card.addColumn(
        0,
        title,
        this.titleInput,
        this.authorInput,
        this.copyInput,
        this.saveBtn);

    card.setAlignment(Pos.CENTER);
    card.setVgap(8);

    GridPane.setHalignment(this.saveBtn, HPos.RIGHT);

    this.setCenter(card);
  }

  public void clearValue() {
    this.titleInput.clear();
    this.authorInput.clear();
    this.copyInput.clear();
  }

  public void setValue(Livre livre) {
    this.titleInput.setText(livre.getTitre());
    this.authorInput.setText(livre.getAuteur());
    this.copyInput.setText(livre.getExemplaire().toString());
  }

  public Livre getValue() {
    Livre livre = new Livre();
    livre.setTitre(this.titleInput.getText());
    livre.setAuteur(this.authorInput.getText());
    livre.setExemplaire(Integer.valueOf(this.copyInput.getText()));
    return livre;
  }

  public void setSaveHandler(EventHandler<ActionEvent> handler) {
    this.saveBtn.setOnAction(handler);
  }
}

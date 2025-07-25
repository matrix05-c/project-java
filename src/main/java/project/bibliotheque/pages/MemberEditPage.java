package project.bibliotheque.pages;

import javafx.event.*;
import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.collections.*;

import io.github.palexdev.materialfx.controls.*;

import project.bibliotheque.models.*;


public class MemberEditPage extends BorderPane {
  private MFXTextField nameInput;
  private MFXComboBox<Gender> genderSelector;
  private MFXTextField ageInput;
  private MFXTextField emailInput;
  private MFXButton saveBtn;

  public MemberEditPage() {
    GridPane card = new GridPane();
    Label title = new Label();
    this.nameInput = new MFXTextField();
    this.genderSelector = new MFXComboBox<>();
    this.ageInput = new MFXTextField();
    this.emailInput = new MFXTextField();
    this.saveBtn = new MFXButton();

    title.setText("Member");
    this.nameInput.setPromptText("Name");
    this.genderSelector.setPromptText("Sexe");
    this.ageInput.setPromptText("Age");
    this.emailInput.setPromptText("Email");
    this.saveBtn.setText("Save");

    title.getStyleClass()
        .add("display1");
    this.genderSelector.setItems(
        FXCollections.observableArrayList(
            Gender.man,
            Gender.woman));
    this.ageInput.setEditable(true);

    this.nameInput.getStyleClass()
        .addAll("mfx-text-field", "lg");
    this.genderSelector.getStyleClass()
        .addAll("mfx-combo-box", "lg");
    this.ageInput.getStyleClass()
        .addAll("mfx-text-field", "lg");
    this.emailInput.getStyleClass()
        .addAll("mfx-text-field", "lg");
    this.saveBtn.getStyleClass()
        .addAll("mfx-button", "primary");

    card.addColumn(
        0,
        title,
        this.nameInput,
        this.genderSelector,
        this.ageInput,
        this.emailInput,
        this.saveBtn);

    card.setAlignment(Pos.CENTER);
    card.setVgap(8);

    GridPane.setHalignment(this.saveBtn, HPos.RIGHT);

    this.setCenter(card);
  }

  public void clearValue() {
    this.nameInput.clear();
    this.genderSelector.clear();
    this.ageInput.clear();
    this.emailInput.clear();
  }

  public void setValue(Membre member) {
    this.nameInput.setText(member.getNom());
    this.genderSelector.setValue(member.getSexe());
    this.ageInput.setText(member.getAge().toString());
    this.emailInput.setText(member.getContact());
  }

  public Membre getValue() {
    Membre member = new Membre();
    member.setNom(this.nameInput.getText());
    member.setSexe(this.genderSelector.getValue());
    member.setAge(Integer.valueOf(this.ageInput.getText()));
    member.setContact(this.emailInput.getText());
    return member;
  }

  public void setSaveHandler(EventHandler<ActionEvent> handler) {
    this.saveBtn.setOnAction(handler);
  }
}

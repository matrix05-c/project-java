package project.bibliotheque.Components;

import java.time.*;

import io.github.palexdev.materialfx.controls.*;
import io.github.palexdev.mfxcore.controls.Label;
import javafx.event.*;
import javafx.scene.layout.*;


public class DateBetweenSearch extends HBox {
  private MFXDatePicker dateBeginInput;
  private MFXDatePicker dateEndInput;
  private SearchBtn searchBtn;
  private MFXButton clearBtn;

  public DateBetweenSearch() {
    this.clearBtn = new MFXButton();
    this.dateBeginInput = new MFXDatePicker();
    this.dateEndInput = new MFXDatePicker();
    this.searchBtn = new SearchBtn();

    this.clearBtn.setText("");
    this.clearBtn.getStyleClass()
        .addAll("mfx-button", "flat-button");
    this.dateBeginInput.getStyleClass()
        .addAll("mfx-text-field");
    this.dateEndInput.getStyleClass()
        .addAll("mfx-text-field");
    this.searchBtn.getStyleClass()
        .addAll("mfx-button", "tool-button", "flat-button");

    this.setupeHandler();

    this.setSpacing(4);
    this.getChildren()
        .addAll(
            clearBtn,
            dateBeginInput,
            dateEndInput,
            searchBtn);
  }

  private void setupeHandler() {
    this.clearBtn.setOnAction(event -> {
      this.dateEndInput.setValue(null);
      this.dateBeginInput.setValue(null);
      this.dateBeginInput.clear();
      this.dateEndInput.clear();
    });
  }

  public LocalDate getBeginDate() {
    return this.dateBeginInput.getValue();
  } 

  public LocalDate getEndDate() {
    return this.dateEndInput.getValue();
  } 

  public void setSearchBtnHandler(EventHandler<ActionEvent> handler) {
    this.searchBtn.setOnAction(handler);
  }
}

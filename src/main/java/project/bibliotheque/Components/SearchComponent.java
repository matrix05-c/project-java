package project.bibliotheque.Components;

import java.util.List;

import io.github.palexdev.materialfx.controls.*;
import javafx.collections.*;
import javafx.event.*;
import javafx.scene.layout.*;

public class SearchComponent extends HBox {
  private MFXComboBox<String> statusSelector;
  private MFXTextField searchInput;
  private SearchBtn searchBtn;

  public SearchComponent(List<String> status) {
    this.statusSelector = new MFXComboBox<>();
    this.searchInput = new MFXTextField();
    this.searchBtn = new SearchBtn();

    this.setupStatusSelector(status);

    this.searchInput.getStyleClass()
        .addAll("mfx-text-field", "lg");
    this.searchBtn.getStyleClass()
        .addAll("mfx-button", "tool-button", "flat-button");

    this.setSpacing(4);
    this.getChildren()
        .addAll(
            statusSelector,
            searchInput,
            searchBtn);
  }

  private void setupStatusSelector(List<String> status) {
    if (status != null) {
      ObservableList<String> items = FXCollections.observableList(status);
      this.statusSelector.setItems(items);
    }
  }

  public void disableSelector() {
    this.statusSelector.setVisible(false);
  }

  public String getSelectedStatus() {
    return this.statusSelector.getText();
  }

  public String getSearchInputText() {
    return this.searchInput.getText();
  }

  public void setSearchBtnHandler(EventHandler<ActionEvent> handler) {
    this.searchBtn.setOnAction(handler);
  }
}

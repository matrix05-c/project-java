package project.bibliotheque.Components;

import java.util.List;

import javafx.event.*;
import javafx.geometry.Insets;
import javafx.scene.layout.*;

public class TableHeader extends HBox {
  private TableActionBtn actionBtn;
  private SearchComponent searchComp;

  public TableHeader(String btnLabel, List<String> status) {
    this.actionBtn = new TableActionBtn("", btnLabel, true);
    this.searchComp = new SearchComponent(status);

    if (status == null)
      searchComp.disableSelector();

    Pane empty = new Pane();

    this.setPadding(new Insets(16));
    this.getChildren()
        .addAll(this.actionBtn, empty, this.searchComp);
    HBox.setHgrow(empty, Priority.ALWAYS);
  }

  public void disableSearch() {
    this.searchComp.setVisible(false);
  }

  public void disableAction() {
    this.actionBtn.setDisable(true);
  }

  public void enableAction() {
    this.actionBtn.setDisable(false);
  }

  public void setActionHandler(EventHandler<ActionEvent> handler) {
    this.actionBtn.setOnAction(handler);
  }

  public String getSelectedStatus() {
    return this.searchComp.getSelectedStatus();
  }

  public String getSearchInputText() {
    return this.searchComp.getSearchInputText();
  }

  public void setSearchHandler(EventHandler<ActionEvent> handler) {
    if (this.searchComp != null)
      this.searchComp.setSearchBtnHandler(handler);
  }
}

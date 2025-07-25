package project.bibliotheque.pages;

import java.util.*;

import javafx.collections.*;
import javafx.beans.value.*;
import javafx.scene.layout.*;
import javafx.event.*;
import javafx.geometry.*;

import io.github.palexdev.materialfx.controls.*;
import io.github.palexdev.materialfx.controls.cell.*;

import project.bibliotheque.models.*;
import project.bibliotheque.Components.*;


public class BookListPage extends BorderPane {
  private TableHeader header;
  private TableToolBtn toolBtn;
  private MFXTableView<Livre> bookListView;

  private ObservableList<Livre> items;


  public BookListPage() {
    this.header = new TableHeader("New book", null);
    this.toolBtn = new TableToolBtn("", "Borrow");
    this.bookListView = new MFXTableView<>();

    VBox tableContainer = new VBox();

    this.toolBtn.setEmailVisibility(false);

    tableContainer.getChildren()
        .addAll(this.toolBtn, this.bookListView);
    VBox.setVgrow(this.bookListView, Priority.ALWAYS);

    this.setupTable();

    this.setPadding(new Insets(16));
    this.setTop(this.header);
    this.setCenter(tableContainer);
  }

  public void setData(List<Livre> items) {
    this.items = FXCollections.observableList(items);
    this.bookListView.setItems(this.items);
  }

  private void setupTable() {
    this.bookListView.setFooterVisible(false);
    this.bookListView.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

    MFXTableColumn<Livre> idCol = new MFXTableColumn<>("Id");
    MFXTableColumn<Livre> titleCol = new MFXTableColumn<>("Title");
    MFXTableColumn<Livre> authorCol = new MFXTableColumn<>("Author");
    MFXTableColumn<Livre> copyCol = new MFXTableColumn<>("Copy");

    idCol.setRowCellFactory(livre -> new MFXTableRowCell<>(Livre::getId));
    titleCol.setRowCellFactory(livre -> new MFXTableRowCell<>(Livre::getTitre));
    authorCol.setRowCellFactory(livre -> new MFXTableRowCell<>(Livre::getAuteur));
    copyCol.setRowCellFactory(livre -> new MFXTableRowCell<>(Livre::getExemplaire));

    titleCol.setMinWidth(256);
    authorCol.setMinWidth(256);

    for (var col : List.of(idCol, titleCol, authorCol, copyCol)) {
      this.bookListView.getTableColumns().add(col);
      col.setColumnResizable(true);
    }
  }

  public Livre getSelectedValue() {
    return this.bookListView.getSelectionModel().getSelectedValue();
  }

  public void clearTableSelection() {
    this.bookListView.getSelectionModel().deselectItems(this.getSelectedValue());
    this.bookListView.getSelectionModel().clearSelection();
  }


  public void enableTool() {
    this.toolBtn.setDisable(false);
  }

  public void disableTool() {
    this.toolBtn.setDisable(true);
  }

  public void setDeleteState(boolean enabled) {
    this.toolBtn.setDeleteActionState(enabled);
  }

  public void setActionState(boolean enabled) {
    this.toolBtn.setActionBtnState(enabled);
  }

  public void setTableListener(ChangeListener<ObservableMap<Integer, Livre>> listener) {
    this.bookListView.getSelectionModel()
        .selectionProperty()
        .addListener(listener);
  }

  public void setEditHandler(EventHandler<ActionEvent> handler) {
    this.toolBtn.setEditHandler(handler);
  }

  public void setDeleteHandler(EventHandler<ActionEvent> handler) {
    this.toolBtn.setDeleteHandler(handler);
  }

  public void setActionHandler(EventHandler<ActionEvent> handler) {
    this.toolBtn.setActionHandler(handler);
  }
  
  public void setAddHandler(EventHandler<ActionEvent> handler) {
    this.header.setActionHandler(handler);
  }

  public void setSearchHandler(EventHandler<ActionEvent> handler) {
    this.header.setSearchHandler(handler);
  }

  public String getSelectedStatus() {
    return this.header.getSelectedStatus();
  }

  public String getSearchInputText() {
    return this.header.getSearchInputText();
  }
}

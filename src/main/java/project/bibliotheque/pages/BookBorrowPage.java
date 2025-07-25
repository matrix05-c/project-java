package project.bibliotheque.pages;

import java.util.*;

import javafx.collections.*;
import javafx.beans.value.*;
import javafx.scene.layout.*;
import javafx.event.*;
import javafx.geometry.Insets;
import io.github.palexdev.materialfx.controls.*;
import io.github.palexdev.materialfx.controls.cell.*;

import project.bibliotheque.models.*;
import project.bibliotheque.Components.*;


public class BookBorrowPage extends BorderPane {
  private TableHeader header;
  private MFXTableView<Membre> memberListView;

  private ObservableList<Membre> items;


  public BookBorrowPage() {
    this.header = new TableHeader("Done", null);
    this.memberListView = new MFXTableView<>();

    VBox tableContainer = new VBox();

    tableContainer.getChildren()
        .addAll(this.memberListView);
    this.header.disableSearch();
    VBox.setVgrow(this.memberListView, Priority.ALWAYS);

    this.setupTable();

    this.setPadding(new Insets(16));
    this.setTop(this.header);
    this.setCenter(tableContainer);
  }

  public void setData(List<Membre> items) {
    this.items = FXCollections.observableList(items);
    this.memberListView.setItems(this.items);
  }

  private void setupTable() {
    this.memberListView.setFooterVisible(false);
    this.memberListView.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

    MFXTableColumn<Membre> idCol = new MFXTableColumn<>("Id");
    MFXTableColumn<Membre> nameCol = new MFXTableColumn<>("Name");
    MFXTableColumn<Membre> genderCol = new MFXTableColumn<>("Gander");
    MFXTableColumn<Membre> ageCol = new MFXTableColumn<>("Age");
    MFXTableColumn<Membre> emailCol = new MFXTableColumn<>("Email");

    idCol.setRowCellFactory(membre -> new MFXTableRowCell<>(Membre::getId));
    nameCol.setRowCellFactory(membre -> new MFXTableRowCell<>(Membre::getNom));
    genderCol.setRowCellFactory(membre -> new MFXTableRowCell<>(Membre::getSexe));
    ageCol.setRowCellFactory(membre -> new MFXTableRowCell<>(Membre::getAge));
    emailCol.setRowCellFactory(membre -> new MFXTableRowCell<>(Membre::getContact));

    nameCol.setMinWidth(256);
    emailCol.setMinWidth(256);

    for (var col : List.of(idCol, nameCol, genderCol, ageCol, emailCol)) {
      this.memberListView.getTableColumns().add(col);
      col.setColumnResizable(true);
    }
  }

  public void disableAction() {
    this.header.disableAction();
  }
  
  public void enableAction() {
    this.header.enableAction();
  }

  public Membre getSelectedValue() {
    return this.memberListView.getSelectionModel().getSelectedValue();
  }

  public void clearTableSelection() {
    this.memberListView.getSelectionModel().deselectItems(this.getSelectedValue());
    this.memberListView.getSelectionModel().clearSelection();
  }

  public void setTableListener(ChangeListener<ObservableMap<Integer, Membre>> listener) {
    this.memberListView.getSelectionModel()
        .selectionProperty()
        .addListener(listener);
  }

  public void setDoneHandler(EventHandler<ActionEvent> handler) {
    this.header.setActionHandler(handler);
  }
}

package project.bibliotheque.pages;

import java.util.*;

import javafx.collections.*;
import javafx.beans.value.*;
import javafx.event.*;
import javafx.geometry.Insets;
import javafx.scene.layout.*;

import io.github.palexdev.materialfx.controls.*;
import io.github.palexdev.materialfx.controls.cell.*;

import project.bibliotheque.models.*;
import project.bibliotheque.Components.*;

public class MemberListPage extends BorderPane {
  private TableHeader header;
  private TableToolBtn toolBtn;
  private MFXTableView<Membre> memberListView;

  private ObservableList<Membre> items;

  MFXTableColumn<Membre> lateCol;

  private static List<String> status = List.of(
      "",
      "late");

  public MemberListPage() {
    this.header = new TableHeader("New member", MemberListPage.status);
    this.toolBtn = new TableToolBtn("", "Return");
    this.memberListView = new MFXTableView<>();

    VBox tableContainer = new VBox();

    tableContainer.getChildren()
        .addAll(this.toolBtn, this.memberListView);
    VBox.setVgrow(this.memberListView, Priority.ALWAYS);

    this.setupTable();

    this.setPadding(new Insets(16));
    this.setTop(this.header);
    this.setCenter(tableContainer);
  }

  public void setData(List<Membre> items) {
    this.items = FXCollections.observableList(items);
    this.memberListView.setItems(this.items);
    this.memberListView.autosize();
  }

  private void setupTable() {
    this.memberListView.setFooterVisible(false);
    this.memberListView.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

    MFXTableColumn<Membre> idCol = new MFXTableColumn<>("Id");
    MFXTableColumn<Membre> nameCol = new MFXTableColumn<>("Name");
    MFXTableColumn<Membre> genderCol = new MFXTableColumn<>("Gender");
    MFXTableColumn<Membre> ageCol = new MFXTableColumn<>("Age");
    MFXTableColumn<Membre> emailCol = new MFXTableColumn<>("Email");
    lateCol = new MFXTableColumn<>("Day after date limit");

    idCol.setRowCellFactory(membre -> new MFXTableRowCell<>(Membre::getId));
    nameCol.setRowCellFactory(membre -> new MFXTableRowCell<>(Membre::getNom));
    genderCol.setRowCellFactory(membre -> new MFXTableRowCell<>(Membre::getSexe));
    ageCol.setRowCellFactory(membre -> new MFXTableRowCell<>(Membre::getAge));
    emailCol.setRowCellFactory(membre -> new MFXTableRowCell<>(Membre::getContact));
    lateCol.setRowCellFactory(membre -> new MFXTableRowCell<>(Membre::getDayAfterLate));

    nameCol.setMinWidth(256);
    emailCol.setMinWidth(192);

    for (var col : List.of(idCol, nameCol, genderCol, ageCol, emailCol)) {
      this.memberListView.getTableColumns().add(col);
      col.setColumnResizable(true);
    }
  }

  public Membre getSelectedValue() {
    return this.memberListView.getSelectionModel().getSelectedValue();
  }

  public void clearTableSelection() {
    this.memberListView.getSelectionModel().deselectItems(this.getSelectedValue());
    this.memberListView.getSelectionModel().clearSelection();
  }

  public void setLateColVisibility(Boolean visible) {
    if (visible && !this.memberListView.getTableColumns().contains(this.lateCol))
      this.memberListView.getTableColumns().add(this.lateCol);
    else if (this.memberListView.getTableColumns().contains(this.lateCol))
      this.memberListView.getTableColumns().remove(this.lateCol);
  }

  public void enableTool() {
    this.toolBtn.setDisable(false);
  }

  public void disableTool() {
    this.toolBtn.setDisable(true);
  }

  public void enableDelete() {
    this.toolBtn.setDeleteActionState(true);
  }

  public void disableDelete() {
    this.toolBtn.setDeleteActionState(false);
  }

  public void setDeleteState(boolean enabled) {
    this.toolBtn.setDeleteActionState(enabled);
  }

  public void setActionBtnState(boolean enabled) {
    this.toolBtn.setActionBtnState(enabled);
  }

  public void setEmailVisibility(boolean visible) {
    this.toolBtn.setEmailVisibility(visible);
  }

  public void setTableListener(ChangeListener<ObservableMap<Integer, Membre>> listener) {
    this.memberListView.getSelectionModel()
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

  public void setReturnHandler(EventHandler<ActionEvent> handler) {
    this.toolBtn.setActionHandler(handler);
  }

  public void setEmailandler(EventHandler<ActionEvent> handler) {
    this.toolBtn.setEmailHandler(handler);
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

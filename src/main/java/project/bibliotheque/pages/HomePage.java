package project.bibliotheque.pages;

import java.time.LocalDate;
import java.util.*;

import io.github.palexdev.materialfx.controls.*;
import io.github.palexdev.materialfx.controls.cell.*;
import io.github.palexdev.mfxcore.controls.*;

import javafx.scene.layout.*;
import javafx.collections.*;
import javafx.geometry.*;
import javafx.event.*;

import project.bibliotheque.Components.*;
import project.bibliotheque.models.*;


import java.util.List;

import io.github.palexdev.materialfx.controls.*;
import io.github.palexdev.materialfx.controls.cell.*;

import project.bibliotheque.models.*;
import project.bibliotheque.Components.*;

public class HomePage extends BorderPane {
  private MFXTableView<Rendre> returnListView;
  private MFXTableView<Preter> borrowListView;
  private DateBetweenSearch searchComp;

  private ObservableList<Rendre> returnList;
  private ObservableList<Preter> borrowList;

  public HomePage() {
    this.returnListView = new MFXTableView<>();
    this.borrowListView = new MFXTableView<>();
    this.searchComp = new DateBetweenSearch();

    Label returnTitle = new Label("Return list");
    Label borrowTitle = new Label("Borrow list");
    GridPane tableContainer = new GridPane();

    this.setupBorrowTable();
    this.setupReturnTable();

    tableContainer.setVgap(8);

    returnTitle.getStyleClass().add("display2");
    borrowTitle.getStyleClass().add("display2");

    tableContainer.add(returnTitle, 0, 0);
    tableContainer.add(borrowTitle, 0, 3);
    tableContainer.add(this.searchComp, 1, 3);

    tableContainer.add(this.returnListView, 0, 1);
    tableContainer.add(this.borrowListView, 0, 4);

    GridPane.setHgrow(borrowTitle, Priority.ALWAYS);
    GridPane.setVgrow(this.borrowListView, Priority.ALWAYS);
    GridPane.setColumnSpan(this.returnListView, 2);
    GridPane.setColumnSpan(this.borrowListView, 2);

    this.setPadding(new Insets(16));
    this.setCenter(tableContainer);
  }

  public void setReturnData(List<Rendre> items) {
    this.returnList = FXCollections.observableList(items);
    this.returnListView.setItems(this.returnList);
  }

  public void setBorrowData(List<Preter> items) {
    this.borrowList = FXCollections.observableList(items);
    this.borrowListView.setItems(this.borrowList);
  }

  private void setupReturnTable() {
    this.returnListView.setFooterVisible(false);
    this.returnListView.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

    MFXTableColumn<Rendre> idCol = new MFXTableColumn<>("Id");
    MFXTableColumn<Rendre> bookCol = new MFXTableColumn<>("Book");
    MFXTableColumn<Rendre> memberCol = new MFXTableColumn<>("Member");
    MFXTableColumn<Rendre> dateCol = new MFXTableColumn<>("Return date");

    idCol.setRowCellFactory(bookReturn -> new MFXTableRowCell<>(Rendre::getId));
    bookCol.setRowCellFactory(bookReturn -> new MFXTableRowCell<>(Rendre::getLivre));
    memberCol.setRowCellFactory(bookReturn -> new MFXTableRowCell<>(Rendre::getMembre));
    dateCol.setRowCellFactory(bookReturn -> new MFXTableRowCell<>(Rendre::getDaterendu));

    dateCol.setMinWidth(256);

    for (var col : List.of(idCol, bookCol, memberCol, dateCol)) {
      this.returnListView.getTableColumns().add(col);
      col.setColumnResizable(true);
    }
  }

  private void setupBorrowTable() {
    this.borrowListView.setFooterVisible(false);
    this.borrowListView.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

    MFXTableColumn<Preter> idCol = new MFXTableColumn<>("Id");
    MFXTableColumn<Preter> bookCol = new MFXTableColumn<>("Book");
    MFXTableColumn<Preter> memberCol = new MFXTableColumn<>("Member");
    MFXTableColumn<Preter> dateBorrowCol = new MFXTableColumn<>("Borrow date");
    MFXTableColumn<Preter> dateReturnCol = new MFXTableColumn<>("Return date");

    idCol.setRowCellFactory(borrow -> new MFXTableRowCell<>(Preter::getId));
    bookCol.setRowCellFactory(borrow -> new MFXTableRowCell<>(Preter::getLivre));
    memberCol.setRowCellFactory(borrow -> new MFXTableRowCell<>(Preter::getmembre));
    dateBorrowCol.setRowCellFactory(borrow -> new MFXTableRowCell<>(Preter::getDatepret));
    dateReturnCol.setRowCellFactory(borrow -> new MFXTableRowCell<>(Preter::getDateretour));

    dateBorrowCol.setMinWidth(256);
    dateReturnCol.setMinWidth(256);

    for (var col : List.of(idCol, bookCol, memberCol, dateBorrowCol, dateReturnCol)) {
      this.borrowListView.getTableColumns().add(col);
      col.setColumnResizable(true);
    }
  }

  public LocalDate getBeginDateFilter() {
    return this.searchComp.getBeginDate();
  }

  public LocalDate getEndDateFilter() {
    return this.searchComp.getEndDate();
  }

  public void setSearchHandler(EventHandler<ActionEvent> handler) {
    this.searchComp.setSearchBtnHandler(handler);
  }
}

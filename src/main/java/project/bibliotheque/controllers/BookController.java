package project.bibliotheque.controllers;

import project.bibliotheque.Components.Confirm;
import project.bibliotheque.Components.Information;
import project.bibliotheque.models.*;
import project.bibliotheque.pages.*;
import project.bibliotheque.services.*;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

import javafx.scene.layout.*;

public class BookController {
  private BookListPage bookListPage;
  private BookEditPage bookEditPage;
  private BookBorrowPage bookBorrowPage;

  private PageController pager;
  private StackPane mainPane;

  private Livre toUpdateBook;
  private Livre toBorrowBook;

  public BookController() {
    this.pager = new PageController();
    this.mainPane = new StackPane();

    initListPage();
    initEditPage();
    initBorrowPage();
    setupHandler();

    gotoListPage();
  }

  public Pane getView() {
    return this.mainPane;
  }

  private void initListPage() {
    this.bookListPage = new BookListPage();
    pager.registerPage("list", this.bookListPage);
    mainPane.getChildren().add(this.bookListPage);
  }

  public void reload() {
    this.bookListPage.setData(Livre.getLivre());
  }

  private void initEditPage() {
    this.bookEditPage = new BookEditPage();
    pager.registerPage("edit", this.bookEditPage);
    mainPane.getChildren().add(this.bookEditPage);
  }

  private void initBorrowPage() {
    this.bookBorrowPage = new BookBorrowPage();
    pager.registerPage("borrow", this.bookBorrowPage);
    mainPane.getChildren().add(this.bookBorrowPage);
  }

  private void setupHandler() {
    this.bookListPage.setAddHandler(event -> {
      gotoEditPage();
    });

    // Listener for row click
    this.bookListPage.setTableListener((obs, oldVal, newVal) -> {
      Livre book = bookListPage.getSelectedValue();
      if (book == null) {
        bookListPage.disableTool();
      } else {
        bookListPage.enableTool();
        Boolean hasCopy = book.getExemplaire() > 0;
        Boolean isBorrowed = Livre.isBorrowed(book.getId());
        bookListPage.setDeleteState(!isBorrowed);
        bookListPage.setActionState(hasCopy);
      }
    });

    // Handler for deletion
    this.bookListPage.setDeleteHandler(event -> {
      Livre book = bookListPage.getSelectedValue();
      if (book == null)
        return;
      if (Confirm.show("Delete", "Delete book " + book.getTitre() + "?")) {
        Livre.deleteLivre(book.getId());
      }
      bookListPage.clearTableSelection();
      bookListPage.disableTool();
      reload();
    });

    // Handler for new member
    this.bookListPage.setAddHandler(event -> {
      bookEditPage.clearValue();
      gotoEditPage();
    });

    // handler for creation and update
    this.bookEditPage.setSaveHandler(event -> {
      Livre book = bookEditPage.getValue();
      if (toUpdateBook == null) {
        Livre.insertLivre(
            book.getTitre(),
            book.getAuteur(),
            book.getExemplaire());
        toUpdateBook = null;
        Information.show("Creationg", "Book " + book.getTitre() + " created!");
      } else {
        Livre.updateLivre(
            book.getTitre(),
            book.getAuteur(),
            book.getExemplaire(),
            toUpdateBook.getId());
        Information.show("Creationg", "Book " + book.getTitre() + " updated!");
      }
      gotoListPage();
      reload();
    });

    // Handler for borrow
    this.bookListPage.setActionHandler(event -> {
      toBorrowBook = bookListPage.getSelectedValue();
      if (toBorrowBook == null) {
        bookListPage.disableTool();
        return;
      }

      gotoBorrowPage();
    });

    // Handler for member edit
    this.bookListPage.setEditHandler(event -> {
      toUpdateBook = bookListPage.getSelectedValue();
      if (toUpdateBook == null) {
        bookListPage.disableTool();
        return;
      }

      bookEditPage.setValue(toUpdateBook);
      gotoEditPage();
    });

    // Handler for search
    this.bookListPage.setSearchHandler(event -> {
      String textSerach = bookListPage.getSearchInputText();
      List<Livre> listLivre = Livre.findLivre("%" + textSerach + "%");
      this.bookListPage.setData(listLivre);
    });

    // Listener for book borrow row click
    this.bookBorrowPage.setTableListener((obs, oldVal, newVal) -> {
      Membre member = this.bookBorrowPage.getSelectedValue();
      if (member == null)
        this.bookBorrowPage.disableAction();
      else
        this.bookBorrowPage.enableAction();
    });

    // Handler for borrowing action
    this.bookBorrowPage.setDoneHandler(event -> {
      Membre member = this.bookBorrowPage.getSelectedValue();
      if (member == null)
        this.bookBorrowPage.disableAction();
      else if (toBorrowBook == null)
        this.gotoListPage();
      else if (Confirm.show("Borrow", "Borrow book " + toBorrowBook.getTitre() + "?")) {
        Timestamp now = Timestamp.from(Instant.now());
        Preter.insertPreter(
            member.getId(),
            toBorrowBook.getId(),
            now, null);
        Livre.updateLivre(
            toBorrowBook.getTitre(),
            toBorrowBook.getAuteur(),
            toBorrowBook.getExemplaire() - 1,
            toBorrowBook.getId());
        PDFGenerator.main(member, toBorrowBook, now.toLocalDateTime());
        Information.show("Borrow", "Book borrowed successfully!\nPDF generated!");
      }
      this.bookBorrowPage.clearTableSelection();
      this.bookBorrowPage.disableAction();
      this.gotoListPage();
    });
  }

  public void gotoListPage() {
    this.pager.gotoPage("list");
    this.bookListPage.disableTool();
    this.bookListPage.clearTableSelection();
    this.bookListPage.setData(Livre.getLivre());
  }

  public void gotoEditPage() {
    this.pager.gotoPage("edit");
  }

  public void gotoBorrowPage() {
    this.pager.gotoPage("borrow");
    this.bookBorrowPage.disableAction();
    this.bookBorrowPage.clearTableSelection();
    this.bookBorrowPage.setData(Membre.getMembreEligible());
  }
}

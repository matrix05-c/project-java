package project.bibliotheque.controllers;

import project.bibliotheque.Components.Confirm;
import project.bibliotheque.Components.Information;
import project.bibliotheque.models.*;
import project.bibliotheque.pages.*;
import project.bibliotheque.services.Mailer;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.layout.*;

public class MemberController {
  private MemberListPage memberListPage;
  private MemberEditPage memberEditPage;

  private PageController pager;
  private StackPane mainPane;

  private Membre toUpdateMember;

  public MemberController() {
    this.pager = new PageController();
    this.mainPane = new StackPane();

    initListPage();
    initEditPage();
    setupHandler();

    gotoListPage();
  }

  public Pane getView() {
    return this.mainPane;
  }

  private void initListPage() {
    this.memberListPage = new MemberListPage();
    this.mainPane.getChildren().add(this.memberListPage);
    this.pager.registerPage("list", this.memberListPage);
  }

  private void initEditPage() {
    this.memberEditPage = new MemberEditPage();
    this.mainPane.getChildren().add(this.memberEditPage);
    this.pager.registerPage("edit", this.memberEditPage);
  }

  private void setupHandler() {
    


    // Listener for row click
    this.memberListPage.setTableListener((obs, oldVal, newVal) -> {
      Membre member = memberListPage.getSelectedValue();
      if (member == null) {
        memberListPage.disableTool();
      } else {
        Boolean haveBorrowed = Preter.aEmpreinter(member.getId());
        this.memberListPage.setActionBtnState(haveBorrowed);
        this.memberListPage.setDeleteState(haveBorrowed);
        this.memberListPage.enableTool();
      }
    });

    // Handler for deletion
    this.memberListPage.setDeleteHandler(event -> {
      Membre member = memberListPage.getSelectedValue();
      if (member == null)
        return;

      if (Confirm.show("Delete", "Delete member " + member.getNom() + "?"))
        Membre.deleteMembre(member.getId());
      memberListPage.clearTableSelection();
      memberListPage.disableTool();
      reload();
    });

    // Handler for new member
    this.memberListPage.setAddHandler(event -> {
      memberEditPage.clearValue();
      toUpdateMember = null;
      gotoEditPage();
    });

    // Handler for member edit
    this.memberListPage.setEditHandler(event -> {
      toUpdateMember = memberListPage.getSelectedValue();
      if (toUpdateMember == null) {
        memberListPage.disableTool();
        return;
      }

      memberEditPage.setValue(toUpdateMember);
      gotoEditPage();
    });

    // handler for creation and update
    this.memberEditPage.setSaveHandler(event -> {
      Membre member = memberEditPage.getValue();
      if (toUpdateMember == null) {
        Membre.insertMember(
            member.getNom(),
            member.getSexe(),
            member.getAge(),
            member.getContact());
        toUpdateMember = null;
        Information.show("Creationg", "Member " + member.getNom() + " registered!");
      } else {
        Membre.updateMembre(
            toUpdateMember.getId(),
            member.getNom(),
            member.getSexe(),
            member.getAge(),
            member.getContact());
        Information.show("Creationg", "Member " + member.getNom() + " Information updated!");
      }
      gotoListPage();
      reload();
    });

    // Handler for search
    this.memberListPage.setSearchHandler(event -> {
      String textSerach = memberListPage.getSearchInputText();
      Boolean isLate = this.memberListPage.getSelectedStatus().equals("late");
      List<Membre> listLivre = new ArrayList<>();

      if (isLate)
        listLivre = Membre.getMembreLate();
      else
        listLivre = Membre.getMembreLike("%" + textSerach + "%");
      this.memberListPage.setLateColVisibility(isLate);

      this.memberListPage.setData(listLivre);
    });

    // Handler for returning a book
    this.memberListPage.setReturnHandler(event -> {
      Membre member = this.memberListPage.getSelectedValue();
      Integer bookId = Preter.getBorrowedBook(member.getId());
      Livre book = Livre.findById(bookId);
      Preter borrowing = Preter.findNotReturned(bookId, member.getId());
      Timestamp now = Timestamp.from(Instant.now());

      if (Confirm.show("Borrow", "Return book " + book.getTitre() + "?")) {
        Rendre.inserRendre(member.getId(), book.getId(), now);
        Preter.updatePreter(borrowing.getId(), member.getId(), bookId, borrowing.getDatepret(), now);
        Livre.updateLivre(book.getTitre(), book.getAuteur(), book.getExemplaire() + 1, book.getId());
      }

      this.gotoListPage();
    });

    //  Handler for email
    this.memberListPage.setEmailandler(event -> {
      Membre member = this.memberListPage.getSelectedValue();
      if (member.getContact() != null && member.getContact() != "") {
        Preter preter = Preter.findNotReturnedBy(member.getId());
        Livre book = Livre.findById(preter.getLivre());
        Mailer.main(member, book, preter.getDatepret());
        Information.show("Email", "Email sent to "+member.getContact()+"!");
      }
    });
  }

  public void reload() {
    this.memberListPage.setData(Membre.getMembres());
  }

  public void gotoListPage() {
    this.pager.gotoPage("list");
    this.memberListPage.disableTool();
    this.memberListPage.clearTableSelection();
    this.memberListPage.setLateColVisibility(false);
  }

  public void gotoEditPage() {
    this.pager.gotoPage("edit");
  }
}

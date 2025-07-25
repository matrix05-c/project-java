package project.bibliotheque.controllers;

import project.bibliotheque.models.Membre;
import project.bibliotheque.models.Preter;
import project.bibliotheque.pages.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.layout.*;

import project.bibliotheque.models.*;


public class HomeController {
  private HomePage view;

  public HomeController() {
    view = new HomePage();
    view.setVisible(false);

    setupHandler();
  }

  private void setupHandler() {
    this.view.setSearchHandler(event -> {
      LocalDate begin = this.view.getBeginDateFilter();
      LocalDate end = this.view.getEndDateFilter();
      List<Preter> list = new ArrayList<>();
      if (begin == null || end == null) list = Preter.getPreters();
      else list = Preter.findBetweenDate(Date.valueOf(begin), Date.valueOf(end));
      view.setBorrowData(list);
    });
  }

  public void reload() {
    view.setBorrowData(Preter.getPreters());
    view.setReturnData(Rendre.getRendres());
  }

  public Pane getView() {
    return view;
  }
}

package project.bibliotheque.controllers;

import java.util.*;
import javafx.scene.layout.*;

public class PageController {
  private String currentPage = null;
  private Map<String, Pane> pageList;

  public PageController() {
    pageList = new HashMap<>();
  }

  public void registerPage(String pageName, Pane page) {
    pageList.put(pageName, page);
    page.setVisible(false);
  }

  public void gotoPage(String pageName) {
    if (currentPage != null)
      pageList.get(currentPage).setVisible(false);
    currentPage = pageName;
    if (currentPage != null)
      pageList.get(currentPage).setVisible(true);
  }

  public String getCurrentPage() {
    return currentPage;
  }
}

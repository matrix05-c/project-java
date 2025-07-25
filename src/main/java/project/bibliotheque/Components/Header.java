package project.bibliotheque.Components;

import java.util.*;

import io.github.palexdev.materialfx.controls.*;
import javafx.event.EventHandler;
import javafx.geometry.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;


public class Header extends GridPane {
  private MFXButton brand;
  private MFXButton homeBtn;
  private MFXButton bookBtn;
  private MFXButton memberBtn;
  private MFXButton profilBtn;
  private MFXButton accentedBtn;
  private Map<String, MFXButton> navItemList = new HashMap<>();

  public Header() {
    brand = new MFXButton("  Library");
    homeBtn = new MFXButton("HOME");
    bookBtn = new MFXButton("BOOK");
    memberBtn = new MFXButton("MEMBER");
    profilBtn = new MFXButton("⚙");

    navItemList.put("home", homeBtn);
    navItemList.put("book", bookBtn);
    navItemList.put("member", memberBtn);

    homeBtn.setMinWidth(128);
    bookBtn.setMinWidth(128);
    memberBtn.setMinWidth(128);

    HBox centerBox = new HBox(0, homeBtn, bookBtn, memberBtn);
    centerBox.setAlignment(Pos.CENTER);

    ColumnConstraints left = new ColumnConstraints();
    ColumnConstraints center = new ColumnConstraints();
    ColumnConstraints right = new ColumnConstraints();

    center.setHalignment(HPos.CENTER);
    center.setHgrow(Priority.ALWAYS);
    right.setHgrow(Priority.ALWAYS);
    right.setHalignment(HPos.RIGHT);

    this.getColumnConstraints().addAll(left, center, right);

    this.add(brand, 0, 0);
    this.add(centerBox, 1, 0);
    this.add(profilBtn, 2, 0);

    this.setPadding(new Insets(4));

    this.getStyleClass().add("header");
    brand.getStyleClass().add("brand");
    homeBtn.getStyleClass().add("tertiary");
    bookBtn.getStyleClass().add("tertiary");
    memberBtn.getStyleClass().add("tertiary");
    profilBtn.getStyleClass().add("tool-button");
  }

  public void setAccentTo(String btnName) {
    if (accentedBtn != null) accentedBtn.getStyleClass().remove("accent");
    accentedBtn = navItemList.get(btnName);
    if (accentedBtn != null) accentedBtn.getStyleClass().add("accent");
  }

  public void removeAccent() {
    if (accentedBtn != null)
      accentedBtn.getStyleClass().add("tertiary");
  }

  public void setBrandBtnHandler(EventHandler<MouseEvent> handler) {
    brand.setOnMouseClicked(handler);
  }

  public void setHomeBtnHandler(EventHandler<MouseEvent> handler) {
    homeBtn.setOnMouseClicked(handler);
  }

  public void setBookBtnHandler(EventHandler<MouseEvent> handler) {
    bookBtn.setOnMouseClicked(handler);
  }

  public void setMemberBtnHandler(EventHandler<MouseEvent> handler) {
    memberBtn.setOnMouseClicked(handler);
  }
}

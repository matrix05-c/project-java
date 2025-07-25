package project.bibliotheque.Components;

import io.github.palexdev.materialfx.controls.*;
import javafx.scene.control.Label;

public class SearchBtn extends MFXButton {
  public SearchBtn() {
    this.setGraphic(new Label(""));
    this.setText("");
    this.getStyleClass()
        .addAll(
            "mfx-button",
            "tool-button");
  }
}

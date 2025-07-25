package project.bibliotheque.Components;

import io.github.palexdev.materialfx.controls.*;


public class EmailBtn extends MFXButton {
  public EmailBtn() {
    this.setText("");
    this.getStyleClass()
        .addAll(
            "mfx-button",
            "tool-button",
            "success");
  }
}

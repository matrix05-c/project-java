package project.bibliotheque.Components;

import io.github.palexdev.materialfx.controls.*;


public class EditBtn extends MFXButton {
  public EditBtn() {
    this.setText("");
    this.getStyleClass()
        .addAll(
            "mfx-button",
            "tool-button",
            "tertiary");
  }
}

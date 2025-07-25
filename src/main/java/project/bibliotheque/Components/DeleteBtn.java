package project.bibliotheque.Components;

import io.github.palexdev.materialfx.controls.*;


public class DeleteBtn extends MFXButton {
  public DeleteBtn() {
    this.setText("");
    this.getStyleClass()
        .addAll(
            "mfx-button",
            "tool-button",
            "danger");
  }
}

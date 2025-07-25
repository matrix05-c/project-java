package project.bibliotheque.Components;

import io.github.palexdev.materialfx.controls.*;
import io.github.palexdev.mfxcore.controls.*;

public class TableActionBtn extends MFXButton {
  public TableActionBtn(String icon, String label, boolean isPrimary) {
    this.setGraphic(new Label(icon));
    this.setText(label);
    this.getStyleClass()
        .add("mfx-button");
    if (isPrimary)
      this.getStyleClass().add("primary");
  }
}

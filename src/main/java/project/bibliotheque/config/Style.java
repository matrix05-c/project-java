package project.bibliotheque.config;

import javafx.scene.Scene;

public class Style {
  public static void load(Scene scene) {
    scene.getStylesheets()
        .addAll(
            Resource.getUrl("/project/bibliotheque/css/materialfx-design.css"),
            Resource.getUrl("/project/bibliotheque/css/style.css"),
            Resource.getUrl("/project/bibliotheque/css/Fonts.css"),
            Resource.getUrl("/project/bibliotheque/fonts/Fonts.css"));
  }
}

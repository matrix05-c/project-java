package project.bibliotheque.Components;
import java.util.*;

import io.github.palexdev.materialfx.controls.*;
import io.github.palexdev.materialfx.controls.base.MFXLabeled;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class Heder2 extends GridPane {
    private Button btn;
    

    public Heder2(){
        btn = new Button();
        btn.setText("Liste des Prets");
        System.out.println("header2 appeler");
        getheder2(); 
    }
    public Button getheder2(){
       return btn;
    }
}

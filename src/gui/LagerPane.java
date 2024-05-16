package gui;

import application.controller.Controller;
import application.model.Fad;
import application.model.Hylde;
import application.model.Lager;
import application.model.Reol;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;

public class LagerPane extends GridPane {

    ListView<Lager> lvwLager = new ListView<>();
    ListView<Reol> lvwReol = new ListView<>();
    ListView<Hylde> lvwHylde = new ListView<>();
    ListView<Fad> lvwFad = new ListView<>();


    public LagerPane() {
        this.setPadding(new Insets(20));
        this.setHgap(20);
        this.setVgap(10);
        this.setGridLinesVisible(false);

        Label lblAlleLagre = new Label("Alle lagre");
        this.add(lblAlleLagre, 0,0);
        lvwLager.getItems().setAll();

    }

}

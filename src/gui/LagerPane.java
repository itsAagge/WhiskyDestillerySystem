package gui;

import application.controller.Controller;
import application.model.Fad;
import application.model.Hylde;
import application.model.Lager;
import application.model.Reol;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

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
        this.add(lvwLager,0,1);
        lvwLager.getItems().setAll(Controller.getLagre());

        Label lblReol = new Label("Reoler på lageret");
        this.add(lblReol, 1,0);
        this.add(lvwReol,1,1);

        Label lblHylde = new Label("Hylder på reolen");
        this.add(lblHylde,2,0);
        this.add(lvwHylde,2,1);

        Label lblFad = new Label("Fade på hylden");
        this.add(lblFad, 3,0);
        this.add(lvwFad, 3,1);

        Button btnOpret = new Button("Opret");
        this.add(btnOpret, 0,2);
        btnOpret.setOnAction(actionEvent -> this.opretAction());

        Button btnFlytFad = new Button("Flyt fad");
        this.add(btnFlytFad,3,2);
        this.setHalignment(btnFlytFad, HPos.RIGHT);


    }

    private void opretAction() {
        LagerDialog lagerDialog = new LagerDialog();
        lagerDialog.showAndWait();
        lvwLager.getItems().setAll(Controller.getLagre());

    }

}

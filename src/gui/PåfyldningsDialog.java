package gui;

import application.controller.Controller;
import application.model.Destillat;
import application.model.Fad;
import application.model.Påfyldning;
import javafx.beans.value.ChangeListener;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PåfyldningsDialog extends Stage {

    ComboBox<Destillat> cBoxDestillater = new ComboBox<>();
    ListView<Fad> lvwFad = new ListView<>();
    ListView<String> lvwTilføjetDestillater = new ListView<>();
    DatePicker dpPåfyldningsdato = new DatePicker();
    DatePicker dpFærdigDato = new DatePicker();
    TextField txfMængde = new TextField();
    int totalFadMængdeValgt = 0;
    Label lblFadMængdeValgt = new Label("Fad mængde valgt:  0 L");
    double totalDestillatMængdeValgt = 0.0;
    Label lblDestillatMængdeValgt = new Label("Destillat mængde valgt:  0.0 L");

    public PåfyldningsDialog() {
        this.initStyle(StageStyle.UTILITY);
        this.initModality(Modality.APPLICATION_MODAL);
        this.setResizable(false);
        this.setMinWidth(640);
        this.setTitle("Opret påfyldning");

        GridPane pane = new GridPane();
        Scene scene = new Scene(pane);
        this.initContent(pane);
        this.setScene(scene);
    }

    private void initContent(GridPane pane) {
        pane.setPadding(new Insets(20));
        pane.setHgap(20);
        pane.setVgap(10);
        pane.setGridLinesVisible(false);

        Label lblLedigeFad = new Label("Ledige fad");
        pane.add(lblLedigeFad, 0, 0);
        pane.add(lvwFad, 0, 1, 1, 5);
        lvwFad.setPrefWidth(250);
        lvwFad.setPrefHeight(200);
        lvwFad.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        lvwFad.getItems().setAll(Controller.getLedigeFad());
        ChangeListener<Fad> changeListenerFad = (observableValue, fad, t1) -> this.changeFad();
        lvwFad.getSelectionModel().selectedItemProperty().addListener(changeListenerFad);

        Label lblUgensDestillater = new Label("Ugens destillater");
        pane.add(lblUgensDestillater, 2, 0);
        pane.add(cBoxDestillater, 2, 1);
        cBoxDestillater.getItems().setAll(Controller.getUgensDestillater());

        Label lblpåfyldningsDato = new Label("Påfyldnings dato");
        pane.add(lblpåfyldningsDato, 1, 0);
        pane.add(dpPåfyldningsdato, 1, 1);

        Label lblFærdigDato = new Label("Færdig dato");
        pane.add(lblFærdigDato, 1, 2);
        pane.add(dpFærdigDato, 1, 3);

        Label lblMængde = new Label("Mængde i liter");
        pane.add(lblMængde, 2, 2);
        pane.add(txfMængde, 2, 3);
        txfMængde.setPrefWidth(100);
        Label lblLiter = new Label("L");
        pane.add(lblLiter, 3, 3);

        Button btnOpret = new Button("Opret");
        pane.add(btnOpret, 0, 7);
        btnOpret.setOnAction(actionEvent -> this.opretAction());
        pane.setHalignment(btnOpret, HPos.LEFT);

        Button btnTilføjDestillat = new Button("Tilføj Destillat");
        pane.add(btnTilføjDestillat, 2, 7);
        pane.setHalignment(btnTilføjDestillat, HPos.RIGHT);
        btnTilføjDestillat.setOnAction(actionEvent -> this.tilføjDestillat());

        Label lblTilføjetDestillater = new Label("Tilføjet detstillater");
        pane.add(lblTilføjetDestillater, 1, 4);
        pane.add(lvwTilføjetDestillater, 1, 5, 2, 1);
        lvwTilføjetDestillater.setPrefHeight(150);
        lvwTilføjetDestillater.setPrefWidth(250);

        pane.add(lblFadMængdeValgt, 0, 6);
        pane.add(lblDestillatMængdeValgt, 2, 6);
        pane.setHalignment(lblDestillatMængdeValgt, HPos.RIGHT);
    }

    ArrayList<Double> mængder = new ArrayList<>();
    ArrayList<Destillat> destillats = new ArrayList<>();

    private void tilføjDestillat() {
        try {
            double mængde = Double.parseDouble(txfMængde.getText().trim());
            Destillat destillat = cBoxDestillater.getSelectionModel().getSelectedItem();
            if (destillat == null) {
                Controller.opretAlert(Alert.AlertType.ERROR, "Fejl", "Intet destillat valgt til at fylde på fad");
            } else if (mængde <= 0) {
                Controller.opretAlert(Alert.AlertType.ERROR, "Fejl", "Mængde skal være over 0");
            } else if (destillat.mængdeTilbage() < mængde) {
                Controller.opretAlert(Alert.AlertType.ERROR, "Fejl", "Der er ikke nok destillat tilbage");
            } else {
                totalDestillatMængdeValgt += mængde;
                lblDestillatMængdeValgt.setText("Destillat mængde valgt:  " + totalDestillatMængdeValgt + " L");
                destillats.add(destillat);
                mængder.add(mængde);
                cBoxDestillater.getSelectionModel().clearSelection(destillats.indexOf(destillat));
                cBoxDestillater.getItems().remove(destillat);
                String s = "Destillat " + destillat.getSpiritBatchNr() + ", " + txfMængde.getText().trim() + " L";
                lvwTilføjetDestillater.getItems().add(s);
            }
        } catch (NumberFormatException e) {
            Controller.opretAlert(Alert.AlertType.ERROR, "Fejl", "mængde er ikke registreret");
        }
    }

    private void opretAction() {
        List<Fad> fade = lvwFad.getSelectionModel().getSelectedItems();
        LocalDate påfyldningsdato = dpPåfyldningsdato.getValue();
        LocalDate færdigDato = dpFærdigDato.getValue();

        if (fade.isEmpty()) {
            Controller.opretAlert(Alert.AlertType.ERROR, "Fejl", "Intet fad er blevet valgt");
        } else if (påfyldningsdato == null) {
            Controller.opretAlert(Alert.AlertType.ERROR, "Fejl", "Ingen påfyldningsdato registreret");
        } else if (destillats.isEmpty()) {
            Controller.opretAlert(Alert.AlertType.ERROR, "Fejl", "Ingen destillater påfyldt");
        } else if (mængder.isEmpty()) {
            Controller.opretAlert(Alert.AlertType.ERROR, "Fejl", "Mængden af de forskellige destillater skal registreres");
        } else if (destillats.size() != mængder.size()) {
            Controller.opretAlert(Alert.AlertType.ERROR, "Fejl", "De registreret destillater og mængder stemmer ikke overens");
        } else if (totalFadMængdeValgt < totalDestillatMængdeValgt) {
            Controller.opretAlert(Alert.AlertType.ERROR, "Fejl", "Der er ikke nok plads i fadene til denne mængde destillater");
        } else {
            Controller.opretPåfyldninger(fade, påfyldningsdato, færdigDato, destillats, mængder);
            this.close();
        }
    }

    private void changeFad() {
        List<Fad> fade = lvwFad.getSelectionModel().getSelectedItems();
        for (Fad fad : fade) {
            totalFadMængdeValgt += fad.getStørrelseL();
        }
        lblFadMængdeValgt.setText("Fad mængde valgt:  " + totalFadMængdeValgt + " L");
    }
}

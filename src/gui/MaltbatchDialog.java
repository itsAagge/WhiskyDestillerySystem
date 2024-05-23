package gui;

import application.controller.Controller;
import application.model.Korn;
import javafx.beans.value.ChangeListener;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.time.LocalDate;

public class MaltbatchDialog extends Stage {
    ListView<Korn> lvwKorn = new ListView<>();
    private TextField txfMængdeL = new TextField();
    private DatePicker dpGærringStart = new DatePicker();
    private DatePicker dpGærringSlut = new DatePicker();
    private TextField txfGærType = new TextField();
    private Label lblMængdeTilbage = new Label("Korn tilbage: " + 0.0 + "Kg");
    private Controller controller;

    public MaltbatchDialog() {
        controller = Controller.getController();
        this.initStyle(StageStyle.UTILITY);
        this.initModality(Modality.APPLICATION_MODAL);
        this.setResizable(false);
        this.setMinWidth(400);
        this.setTitle("Registrer maltbatch");

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

        Label lblAlleKorn = new Label("Alt Korn");
        pane.add(lblAlleKorn, 0, 0);
        pane.add(lvwKorn, 0, 1, 1, 4);
        lvwKorn.setPrefWidth(250);
        lvwKorn.setPrefHeight(300);
        lvwKorn.getItems().setAll(controller.getKorn());

        pane.add(lblMængdeTilbage, 0, 5);
        ChangeListener<Korn> kornChangeListener = (observableValue, oldValue, newValue) -> this.changeKorn();
        lvwKorn.getSelectionModel().selectedItemProperty().addListener(kornChangeListener);


        Label lblMængdeL = new Label("Mængde i liter");
        pane.add(lblMængdeL, 1, 0);
        pane.add(txfMængdeL, 1, 1);

        Label lblGærringsStartDato = new Label("Gærrings startdato");
        pane.add(lblGærringsStartDato, 1, 2);
        pane.add(dpGærringStart, 1, 3);
        dpGærringStart.setEditable(false);

        Label lblGærringsSlutDato = new Label("Gærring slutdato");
        pane.add(lblGærringsSlutDato, 2, 2);
        pane.add(dpGærringSlut, 2, 3);
        dpGærringSlut.setEditable(false);

        Label lblGærType = new Label("Gærtype");
        pane.add(lblGærType, 2, 0);
        pane.add(txfGærType, 2, 1);

        Button btnRegistrer = new Button("Registrer");
        pane.add(btnRegistrer, 2, 4);
        pane.setValignment(btnRegistrer, VPos.BOTTOM);
        pane.setHalignment(btnRegistrer, HPos.RIGHT);
        btnRegistrer.setOnAction(actionEvent -> this.registrerAction());
    }

    private void changeKorn() {
        Korn korn = lvwKorn.getSelectionModel().getSelectedItem();
        lblMængdeTilbage.setText("Mængde tilbage: " + controller.mængdeTilbageKorn(korn) + "Kg");
    }

    private void registrerAction() {
        try {
            if (lvwKorn.getSelectionModel().getSelectedItem() == null) {
                controller.opretAlert(Alert.AlertType.ERROR, "Fejl", "Intet korn valgt");
            } else if (txfMængdeL.getText() == null || Double.parseDouble(txfMængdeL.getText()) <= 0) {
                controller.opretAlert(Alert.AlertType.ERROR, "Fejl", "Mængde skal være indtastet og over 0");
            } else if (dpGærringStart.getValue() == null) {
                controller.opretAlert(Alert.AlertType.ERROR, "Fejl", "Ingen startdato angivet");
            } else if (dpGærringSlut.getValue() == null) {
                controller.opretAlert(Alert.AlertType.ERROR, "Fejl", "Ingen slutdato angivet");
            } else if (txfGærType.getText().isEmpty()) {
                controller.opretAlert(Alert.AlertType.ERROR, "Fejl", "Gærtype skal angives");
            } else if (controller.mængdeTilbageKorn(lvwKorn.getSelectionModel().getSelectedItem()) < Double.parseDouble(txfMængdeL.getText().trim())) {
                controller.opretAlert(Alert.AlertType.ERROR, "Fejl", "Der er ikke nok korn tilbage");
            } else {
                Korn korn = lvwKorn.getSelectionModel().getSelectedItem();
                double mængdeL = Double.parseDouble(txfMængdeL.getText().trim());
                LocalDate startdato = dpGærringStart.getValue();
                LocalDate slutdato = dpGærringSlut.getValue();
                String gærType = txfGærType.getText().trim();
                controller.opretMaltbatch(mængdeL, startdato, slutdato, gærType, korn);
                this.close();
            }
        } catch (Exception e) {
            controller.opretAlert(Alert.AlertType.ERROR, "Fejl", "Indtastningsfejl");
        }
    }
}

package gui;

import application.controller.Controller;
import application.model.Destillat;
import application.model.Påfyldning;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import java.util.List;

public class DestillatPane extends GridPane {
    private ListView<Destillat> lvwDestillater = new ListView<>();
    private TextArea txaDestillatBeskrivelse = new TextArea();
    private ListView<Påfyldning> lvwPåfyldningAfDestillat = new ListView<>();
    private Button btnOpretDestillat = new Button("Opret");
    private Button btnRedigerDestillat = new Button("Rediger");
    private Button btnSletDestillat = new Button("Slet");
    private Button btnPrintDestillat = new Button("Print beskrivelse");
    private Controller controller;

    public DestillatPane() {
        controller = Controller.getController();
        this.setPadding(new Insets(20));
        this.setHgap(20);
        this.setVgap(10);
        this.setGridLinesVisible(false);

        Label lblAlleDestillater = new Label("Alle destillater");
        this.add(lblAlleDestillater, 0, 0);
        this.add(lvwDestillater, 0, 1);
        lvwDestillater.getItems().setAll(controller.getAlleDestillater());
        ChangeListener<Destillat> destillatChangeListener = (observableValue, oldValue, newValue) -> this.changeDestillat();
        lvwDestillater.getSelectionModel().selectedItemProperty().addListener(destillatChangeListener);
        lvwDestillater.setPrefWidth(200);

        Label lblDestillatBeskrivelse = new Label("Destillates historie");
        this.add(lblDestillatBeskrivelse, 1, 0);
        this.add(txaDestillatBeskrivelse, 1, 1, 2, 1);
        txaDestillatBeskrivelse.setPrefWidth(250);
        txaDestillatBeskrivelse.setWrapText(true);
        txaDestillatBeskrivelse.setEditable(false);

        Label lblPåfyldningAfDestillat = new Label("Påfyldninger af destillat");
        this.add(lblPåfyldningAfDestillat, 3, 0);
        this.add(lvwPåfyldningAfDestillat, 3, 1);
        lvwPåfyldningAfDestillat.setPrefWidth(250);

        HBox hBoxButtons = new HBox();
        hBoxButtons.setSpacing(20);
        this.add(hBoxButtons, 0, 2, 4, 1);
        hBoxButtons.getChildren().addAll(List.of(btnOpretDestillat, btnRedigerDestillat, btnSletDestillat, btnPrintDestillat));
        btnOpretDestillat.setOnAction(event -> this.opretDestillatAction());
        btnRedigerDestillat.setOnAction(event -> this.redigerDestillatAction());
        btnSletDestillat.setOnAction(event -> this.sletDestillatAction());
        btnPrintDestillat.setOnAction(event -> this.printDestillatAction());
        setKnapperAktive(false);
    }

    private void sletDestillatAction() {
        Destillat destillat = lvwDestillater.getSelectionModel().getSelectedItem();

        if (!destillat.getPåfyldninger().isEmpty()) {
            controller.opretAlert(Alert.AlertType.ERROR, "Fejl", "Du kan ikke slette et destillat, der er hældt på fade");
        } else {
            controller.fjernDestillat(destillat);
            lvwDestillater.getItems().setAll(controller.getAlleDestillater());
        }
    }

    private void redigerDestillatAction() {
        Destillat destillat = lvwDestillater.getSelectionModel().getSelectedItem();
        DestillatDialog destillatDialog = new DestillatDialog(destillat);
        destillatDialog.showAndWait();
        lvwDestillater.getItems().setAll(controller.getAlleDestillater());
        setKnapperAktive(false);
    }

    private void opretDestillatAction() {
        DestillatDialog destillatDialog = new DestillatDialog(null);
        destillatDialog.showAndWait();
        lvwDestillater.getItems().setAll(controller.getAlleDestillater());
    }

    private void printDestillatAction() {
        Destillat destillat = lvwDestillater.getSelectionModel().getSelectedItem();
        if (!IndstillingPane.erOutputStrategiValgt()) {
            controller.opretAlert(Alert.AlertType.ERROR, "Fejl", "Du mangler at vælge en output type i indstillingerne");
        } else {
            controller.udtrækBeskrivelse(IndstillingPane.getValgtOutputStrategi(), destillat);
            controller.opretAlert(Alert.AlertType.INFORMATION, "Succes", "Beskrivelse succesfuldt udtrukket");
        }
    }

    private void changeDestillat() {
        Destillat destillat = lvwDestillater.getSelectionModel().getSelectedItem();

        if (destillat != null) {
            txaDestillatBeskrivelse.setText(destillat.getBeskrivelseTilListView());
            if (!destillat.getPåfyldninger().isEmpty()) {
                lvwPåfyldningAfDestillat.getItems().setAll(destillat.getPåfyldninger());
            }
        }

        setKnapperAktive(true);
    }

    private void setKnapperAktive(boolean bool) {
        btnRedigerDestillat.setDisable(!bool);
        btnSletDestillat.setDisable(!bool);
        btnPrintDestillat.setDisable(!bool);
    }
}

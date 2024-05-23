package gui;

import application.controller.Controller;
import application.model.Påfyldning;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;


public class PåfyldningPane extends GridPane {
    private ListView<Påfyldning> lvwPåfyldninger = new ListView<>();
    private TextArea txaPåfyldningsBeskrivelse = new TextArea();
    private Button btnOpretPåfyldning = new Button("Opret");
    private Button btnFlytPåfyldning = new Button("Flyt");
    private Button btnPrintPåfyldning = new Button("Print beskrivelse");
    private Controller controller;

    public PåfyldningPane() {
        controller = Controller.getController();
        this.setPadding(new Insets(20));
        this.setHgap(20);
        this.setVgap(10);
        this.setGridLinesVisible(false);

        //Label og listview til at vise alle påfyldninger
        Label lblAllePåfyldninger = new Label("Alle påfyldninger");
        this.add(lblAllePåfyldninger, 0, 0);
        this.add(lvwPåfyldninger, 0, 1);
        lvwPåfyldninger.getItems().setAll(controller.getPåfyldninger());


        //Label og textarea til påfyldninges beskrivelse
        Label lblPåfyldningBeskrivelse = new Label("Beskrivelse af valgte påfyldning");
        this.add(lblPåfyldningBeskrivelse, 1, 0);
        this.add(txaPåfyldningsBeskrivelse, 1, 1);
        txaPåfyldningsBeskrivelse.setEditable(false);
        txaPåfyldningsBeskrivelse.setWrapText(true);
        ChangeListener<Påfyldning> påfyldningChangeListener = (observableValue, oldValue, newValue) -> this.changePåfyldning();
        lvwPåfyldninger.getSelectionModel().selectedItemProperty().addListener(påfyldningChangeListener);


        HBox hBoxButtons = new HBox();
        hBoxButtons.setSpacing(20);
        this.add(hBoxButtons, 0, 2, 4, 1);
        hBoxButtons.getChildren().setAll(btnOpretPåfyldning, btnFlytPåfyldning, btnPrintPåfyldning);
        btnOpretPåfyldning.setOnAction(event -> this.opretPåfyldningAction());
        btnFlytPåfyldning.setOnAction(event -> this.flytPåfyldningAction());
        btnPrintPåfyldning.setOnAction(event -> this.printPåfyldningAction());
        setKnapperAktive(false);
    }

    private void changePåfyldning() {
        Påfyldning påfyldning = lvwPåfyldninger.getSelectionModel().getSelectedItem();
        if (påfyldning != null) {
            txaPåfyldningsBeskrivelse.setText(påfyldning.getBeskrivelse());
            if (!påfyldning.getUdgivelser().isEmpty()) {
                setKnapperAktive(false);
            } else {
                setKnapperAktive(true);
            }
        }
    }

    private void flytPåfyldningAction() {
        Påfyldning påfyldning = lvwPåfyldninger.getSelectionModel().getSelectedItem();
        FlytPåfyldningDialog flytPåfyldningDialog = new FlytPåfyldningDialog(påfyldning);
        flytPåfyldningDialog.showAndWait();
        txaPåfyldningsBeskrivelse.clear();
        lvwPåfyldninger.getItems().setAll(controller.getPåfyldninger());
        setKnapperAktive(false);
    }

    private void opretPåfyldningAction() {
        PåfyldningsDialog påfyldningsDialog = new PåfyldningsDialog();
        påfyldningsDialog.showAndWait();
        lvwPåfyldninger.getItems().setAll(controller.getPåfyldninger());
    }

    private void printPåfyldningAction() {
        Påfyldning påfyldning = lvwPåfyldninger.getSelectionModel().getSelectedItem();
        if (!IndstillingPane.erOutputStrategiValgt()) {
            controller.opretAlert(Alert.AlertType.ERROR, "Fejl", "Du mangler at vælge en output type i indstillingerne");
        } else {
            controller.udtrækBeskrivelse(IndstillingPane.getValgtOutputStrategi(), påfyldning);
            controller.opretAlert(Alert.AlertType.INFORMATION, "Succes", "Beskrivelse succesfuldt udtrukket");
        }
    }

    private void setKnapperAktive(boolean bool) {
        btnFlytPåfyldning.setDisable(!bool);
        btnPrintPåfyldning.setDisable(!bool);
    }
}

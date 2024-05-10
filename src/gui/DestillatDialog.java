package gui;

import application.controller.Controller;
import application.model.Destillat;
import application.model.Maltbatch;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.time.LocalDate;

public class DestillatDialog extends Stage {
    private Destillat destillat;
    private TextField txfSpiritBatchNr = new TextField();
    private TextField txfMængde = new TextField();
    private TextField txfAlkoholProcent = new TextField();
    private TextField txfRygemateriale = new TextField();
    private TextField txfKommentar = new TextField();
    private DatePicker datePicker = new DatePicker();
    private ComboBox<String> comboBoxMedarbejdere = new ComboBox<>();
    ListView<Maltbatch> lvwMaltbatche = new ListView<>();

    public DestillatDialog(Destillat destillat) {
        this.initStyle(StageStyle.UTILITY);
        this.initModality(Modality.APPLICATION_MODAL);
        this.setResizable(false);
        //Todo: Find ud af hvorfor stagen ikke automatisk finder længden på alle elementer
        this.setMinWidth(640);
        this.destillat = destillat;
        if (this.destillat == null) {
            this.setTitle("Opret destillat");
        } else {
            this.setTitle("Rediger destillat");
        }

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

        Label lblSpiritBatchNr = new Label("Spirit Batch nr.:");
        pane.add(lblSpiritBatchNr,0,0);
        pane.add(txfSpiritBatchNr,1,0);

        Label lblMængde = new Label("Størrelse:");
        pane.add(lblMængde,0,1);
        pane.add(txfMængde,1,1);
        Label lblMængdeIndikator = new Label("L");
        pane.add(lblMængdeIndikator,2,1);

        Label lblAlkoholProcent = new Label("Alkoholprocent:");
        pane.add(lblAlkoholProcent,0,2);
        pane.add(txfAlkoholProcent,1,2);
        Label lblAkoholProcentIndikator = new Label("%");
        pane.add(lblAkoholProcentIndikator,2,2);

        Label lblRygematerialer = new Label("Rygemateriale:");
        pane.add(lblRygematerialer,0,3);
        pane.add(txfRygemateriale,1,3);

        Label lblKommentar = new Label("Kommentar:");
        pane.add(lblKommentar,0,4);
        pane.add(txfKommentar,1,4);

        Label lblDato = new Label("Dato:");
        pane.add(lblDato,0,5);
        pane.add(datePicker,1,5);

        Label lblMedarbejder = new Label("Medarbejder:");
        pane.add(lblMedarbejder,0,6);
        pane.add(comboBoxMedarbejdere,1,6);
        comboBoxMedarbejdere.getItems().setAll(Controller.getMedarbejdere());

        Label lblMaltbatche = new Label("Maltbatches");
        pane.add(lblMaltbatche,3,0);
        pane.add(lvwMaltbatche,3,1,1,6);
        lvwMaltbatche.setPrefHeight(180);
        lvwMaltbatche.getItems().setAll(Controller.getAlleMaltbatche());

        if(this.destillat != null) {
            hentDestillatData();
        }

        Button btnGem = new Button("Gem");
        pane.add(btnGem,3,7);
        pane.setHalignment(btnGem, HPos.RIGHT);

        btnGem.setOnAction(event -> gemAction());
    }

    private void gemAction() {
        String spiritBatchNr = null;
        double mængde = 0.0;
        double alkoholprocent = 0.0;
        String rygemateriale = null;
        String kommentar = null;
        LocalDate dato = datePicker.getValue();
        String medarbejder = null;
        Maltbatch maltbatch = null;

        if(txfSpiritBatchNr.getText().isEmpty()) {
            Controller.opretAlert(Alert.AlertType.ERROR, "Fejl", "Du mangler at angive et Spirit Batch Nr.");
        } else if(txfMængde.getText().isEmpty()) {
            Controller.opretAlert(Alert.AlertType.ERROR, "Fejl", "Du mangler at angive en mængde");
        } else if(txfAlkoholProcent.getText().isEmpty()) {
            Controller.opretAlert(Alert.AlertType.ERROR, "Fejl", "Du mangler at angive en alkoholprocent");
        } else if(comboBoxMedarbejdere.getSelectionModel().isEmpty()) {
            Controller.opretAlert(Alert.AlertType.ERROR, "Fejl", "Du mangler at vælge en medarbejder");
        } else if(dato == null) {
            Controller.opretAlert(Alert.AlertType.ERROR, "Fejl", "Du mangler at angive en dato");
        } else if(lvwMaltbatche.getSelectionModel().isEmpty()) {
            Controller.opretAlert(Alert.AlertType.ERROR, "Fejl", "Du mangler at vælge et maltbatch");
        } else {
            if(!txfRygemateriale.getText().isEmpty()) {
                rygemateriale = txfRygemateriale.getText().trim();
            }
            if(!txfKommentar.getText().isEmpty()) {
                kommentar = txfKommentar.getText().trim();
            }
            spiritBatchNr = txfSpiritBatchNr.getText().trim();
            mængde = Double.parseDouble(txfMængde.getText());
            alkoholprocent = Double.parseDouble(txfAlkoholProcent.getText());
            dato = datePicker.getValue();
            medarbejder = comboBoxMedarbejdere.getSelectionModel().getSelectedItem().trim();
            maltbatch = lvwMaltbatche.getSelectionModel().getSelectedItem();
            if (destillat != null) {
                //Todo: Evt. lav dette om til en controllermetode, så der ikke er logik i gui
                this.destillat.setSpiritBatchNr(spiritBatchNr);
                this.destillat.setMængdeL(mængde);
                this.destillat.setAlkoholprocent(alkoholprocent);
                this.destillat.setDestilleringsdato(dato);
                this.destillat.setMedarbejder(medarbejder);
                this.destillat.setRygemateriale(rygemateriale);
                this.destillat.setKommentar(kommentar);
                this.destillat.setMaltbatch(maltbatch);
            } else {
                Controller.opretDestillat(spiritBatchNr, mængde, alkoholprocent, medarbejder, rygemateriale, kommentar, dato, maltbatch);
            }
        }
    }

    private void hentDestillatData() {
        txfSpiritBatchNr.setText(this.destillat.getSpiritBatchNr());
        txfMængde.setText(this.destillat.getMængdeL() + "");
        txfAlkoholProcent.setText(this.destillat.getAlkoholprocent() + "");
        if(this.destillat.getRygemateriale() != null) {
            txfRygemateriale.setText(this.destillat.getRygemateriale());
        }
        if(this.destillat.getKommentar() != null) {
            txfKommentar.setText(this.destillat.getKommentar());
        }
        datePicker.setValue(this.destillat.getDestilleringsdato());
        comboBoxMedarbejdere.getSelectionModel().select(this.destillat.getMedarbejder());
        lvwMaltbatche.getSelectionModel().select(this.destillat.getMaltbatch());
    }
}

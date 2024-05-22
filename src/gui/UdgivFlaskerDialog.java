package gui;

import application.controller.Controller;
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


public class UdgivFlaskerDialog extends Stage {

    private ComboBox<Påfyldning> cBPåfylninger = new ComboBox<>();
    private TextField txfFlaskeStørrelse = new TextField();
    private DatePicker dpUdgivelsesDato = new DatePicker();
    private TextField txfAlkoholProcent = new TextField();
    private TextField txfVandMængde = new TextField();
    private TextField txfVandetsOprindelse = new TextField();
    private ComboBox<String> cBMedarbejder = new ComboBox<>();
    private ListView<String> lvwGemtePåfyldningerMedMængde = new ListView<>();
    private TextField txfMængdeL = new TextField();
    private TextField txfPrisPerFlaske = new TextField();
    private Label lblLiterTilbage = new Label("Liter tilbage: ");
    private TextField txfAngelsShare = new TextField();
    private Controller controller;


    public UdgivFlaskerDialog() {
        controller = Controller.getController();
        this.initStyle(StageStyle.UTILITY);
        this.initModality(Modality.APPLICATION_MODAL);
        this.setResizable(false);
        this.setMinWidth(640);
        this.setTitle("Udgiv flasker");

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

        Label lblFlaskeStørrelse = new Label("Flaske størrelse");
        pane.add(lblFlaskeStørrelse, 0,0);
        pane.add(txfFlaskeStørrelse, 0,1);

        Label lblUdgivelsesDato = new Label("Udgivelses dato");
        pane.add(lblUdgivelsesDato, 1,0);
        pane.add(dpUdgivelsesDato, 1,1);
        dpUdgivelsesDato.setEditable(false);
        dpUdgivelsesDato.setValue(LocalDate.now());
        ChangeListener<LocalDate> changeListenerUdgivelsesdato = (observableValue, localDate, t1) -> this.changeDato();
        dpUdgivelsesDato.valueProperty().addListener(changeListenerUdgivelsesdato);

        Label lblPrisPrFlaske = new Label("Pris pr flaske");
        pane.add(lblPrisPrFlaske, 0,2);
        pane.add(txfPrisPerFlaske, 0,3);

        Label lblAlkoholProcent = new Label("Alkohol procent");
        pane.add(lblAlkoholProcent,1,2);
        pane.add(txfAlkoholProcent,1,3);

        Label lblVandMængde = new Label("Liter vand tilføjet");
        pane.add(lblVandMængde, 0,4);
        pane.add(txfVandMængde, 0,5);

        Label lblMedarbejder = new Label("Medarbejder");
        pane.add(lblMedarbejder, 1,4);
        pane.add(cBMedarbejder, 1,5);
        cBMedarbejder.getItems().setAll(controller.getMedarbejdere());
        if (IndstillingPane.erMedarbejderValgt()) cBMedarbejder.getSelectionModel().select(IndstillingPane.getValgtMedarbejder());

        Label lblVandetsOprindelse = new Label("Vandets oprindelse");
        pane.add(lblVandetsOprindelse,0,6);
        pane.add(txfVandetsOprindelse,0,7);

        Label lblPåfyldninger = new Label("Alle påfyldninger");
        pane.add(lblPåfyldninger, 0,8);
        pane.add(cBPåfylninger, 0,9);
        cBPåfylninger.getItems().setAll(controller.getMindst3ÅrsIkkeTommePåfyldninger(LocalDate.now()));

        Label lblAngelsShare = new Label("Angels Share i procent");
        pane.add(lblAngelsShare, 1,6);
        pane.add(txfAngelsShare, 1,7);

        Label lblMængde = new Label("Mængde i liter");
        pane.add(lblMængde, 1,8);
        pane.add(txfMængdeL, 1,9);

        Label lblTilføjetPåfyldninger = new Label("Tilføjet påfyldninger");
        pane.add(lblTilføjetPåfyldninger, 0,11);
        pane.add(lvwGemtePåfyldningerMedMængde, 0,12,2,1);
        lvwGemtePåfyldningerMedMængde.setPrefWidth(250);
        lvwGemtePåfyldningerMedMængde.setPrefHeight(200);

        Button btnTilføjPåfyldning = new Button("Tilføj Påfyldning");
        pane.add(btnTilføjPåfyldning, 1,11);
        btnTilføjPåfyldning.setOnAction(actionEvent -> this.tilføjAction());
        pane.setHalignment(btnTilføjPåfyldning, HPos.RIGHT);

        Button btnOpret = new Button("Opret");
        pane.add(btnOpret, 0,13);
        btnOpret.setOnAction(actionEvent -> this.opretAction());
        pane.setHalignment(btnOpret, HPos.LEFT);

        pane.add(lblLiterTilbage, 0,10);

        ChangeListener<Påfyldning> changeListenerFad = (observableValue, fad, t1) -> this.changePåfyldning();
        cBPåfylninger.getSelectionModel().selectedItemProperty().addListener(changeListenerFad);


    }

    private void changePåfyldning() {
        Påfyldning påfyldning = cBPåfylninger.getSelectionModel().getSelectedItem();
        String s = "Liter tilbage: ";

        if (påfyldning != null) {
            s += påfyldning.mængdeTilbage() + " L";
        }
        lblLiterTilbage.setText(s);
    }

    ArrayList<Double> mængder = new ArrayList<>();
    ArrayList<Påfyldning> påfyldninger = new ArrayList<>();

    private void opretAction() {
        try {
            double flaskeStørrelse = Double.parseDouble(txfFlaskeStørrelse.getText().trim());
            LocalDate dato = dpUdgivelsesDato.getValue();
            double prisPrFlaske = Double.parseDouble(txfPrisPerFlaske.getText().trim());
            double alkoholProcent = Double.parseDouble(txfAlkoholProcent.getText().trim());
            double vandMængde = Double.parseDouble(txfVandMængde.getText().trim());
            String medarbejder = cBMedarbejder.getSelectionModel().getSelectedItem();
            String vandetsOprindelse = txfVandetsOprindelse.getText().trim();
            double angelsShare = Double.parseDouble(txfAngelsShare.getText().trim());

            if (flaskeStørrelse <= 0) {
                controller.opretAlert(Alert.AlertType.ERROR, "Fejl", "Flaskestørrelse skal være over 0");
            } else if (dato == null) {
                controller.opretAlert(Alert.AlertType.ERROR, "Fejl", "Dato er ikke registreret");
            } else if (prisPrFlaske < 0) {
                controller.opretAlert(Alert.AlertType.ERROR, "Fejl", "Pris per flaske må ikke være negativ");
            } else if (alkoholProcent < 0) {
                controller.opretAlert(Alert.AlertType.ERROR, "Fejl", "Alkohol procent må ikke være negativ");
            } else if (vandMængde < 0) {
                controller.opretAlert(Alert.AlertType.ERROR, "Fejl", "Vandmængde må ikke være negativ");
            } else if (medarbejder == null) {
                controller.opretAlert(Alert.AlertType.ERROR, "Fejl", "Der skal vælges en medarbjeder");
            } else if (txfVandetsOprindelse.getText().isEmpty() && vandMængde > 0) {
                controller.opretAlert(Alert.AlertType.ERROR, "Fejl", "Der skal skrives hvor vandet kommer fra");
            } else if (angelsShare < 0) {
                controller.opretAlert(Alert.AlertType.ERROR, "Fejl", "Angels share skal registreres");
            } else {
                controller.opretUdgivelse(flaskeStørrelse, prisPrFlaske, false, dato, alkoholProcent, vandMængde, medarbejder, vandetsOprindelse, angelsShare, påfyldninger, mængder);
                this.close();
            }
        } catch (Exception e) {
            controller.opretAlert(Alert.AlertType.ERROR, "Fejl", "Indtastfejl");
        }
    }

    private void tilføjAction() {
        try {
            Påfyldning påfyldning = cBPåfylninger.getSelectionModel().getSelectedItem();
            double mængde = Double.parseDouble(txfMængdeL.getText().trim());

            if (påfyldning == null) {
                controller.opretAlert(Alert.AlertType.ERROR, "Fejl", "Ingen påfyldning valgt til at tilføje til udgivelse");
            } else if (mængde <= 0) {
                controller.opretAlert(Alert.AlertType.ERROR, "Fejl", "Mængde skal være over 0");
            } else if (påfyldning.mængdeTilbage() < mængde) {
                controller.opretAlert(Alert.AlertType.ERROR, "Fejl", "Der er ikke nok af påfyldningen tilbage");
            } else {
                påfyldninger.add(påfyldning);
                mængder.add(mængde);

                cBPåfylninger.getSelectionModel().clearSelection(påfyldninger.indexOf(påfyldning));
                txfMængdeL.clear();
                cBPåfylninger.getItems().remove(påfyldning);
                String s = "Påfyldning " + påfyldning.getPåfyldningsNr() + ", " + mængde + " L";
                lvwGemtePåfyldningerMedMængde.getItems().add(s);
            }
        } catch (Exception e) {
            controller.opretAlert(Alert.AlertType.ERROR, "Fejl", "mængde er ikke registreret");
        }
    }

    private void changeDato() {
        LocalDate udgivelsesDato = dpUdgivelsesDato.getValue();

        if (udgivelsesDato != null) {
            cBPåfylninger.getItems().setAll(controller.getMindst3ÅrsIkkeTommePåfyldninger(udgivelsesDato));
            cBPåfylninger.getVisibleRowCount();
        }
    }
}

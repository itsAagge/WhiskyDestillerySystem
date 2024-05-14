package gui;

import application.controller.Controller;
import application.model.Påfyldning;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class UdgivelseDialog extends Stage {
    ComboBox<String> cbFadEllerFlasker = new ComboBox<>();
    DatePicker dpUdgivelsesDato = new DatePicker();
    TextField txfAlkoholProcent = new TextField();
    TextField txfPrisPerUnit = new TextField();
    TextField txfVandMængde = new TextField();
    TextField txfFlaskeStørrelse = new TextField();
    ComboBox<String> cbMedarbejdere = new ComboBox<>();
    ListView<Påfyldning> lvwPåfyldning = new ListView<>();

    public UdgivelseDialog() {
        this.initStyle(StageStyle.UTILITY);
        this.initModality(Modality.APPLICATION_MODAL);
        this.setResizable(false);
        this.setMinWidth(640);
        this.setTitle("Opret udgivelse");

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

        Label lblAllePåfyldning = new Label("Alle påfyldning");
        pane.add(lblAllePåfyldning,0,0);
        pane.add(lvwPåfyldning, 0,1,1,8);
        lvwPåfyldning.setPrefWidth(250);
        lvwPåfyldning.getItems().setAll(Controller.getPåfyldninger());
        lvwPåfyldning.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        Label lblUdgivelsesType = new Label("Udgivelses type");
        pane.add(lblUdgivelsesType, 1,0);
        pane.add(cbFadEllerFlasker,1,1);

        Label lblUdgivelsesDato = new Label("Udgivelses dato");
        pane.add(lblUdgivelsesDato, 2,0);
        pane.add(dpUdgivelsesDato, 2, 1);

        Label lblflaskeStørrelse = new Label("Flaske størrelse");
        pane.add(lblflaskeStørrelse, 1,2);
        pane.add(txfFlaskeStørrelse, 1,3);

        Label lblVandMængde = new Label("Vand mængde");
        pane.add(lblVandMængde, 2,2);
        pane.add(txfVandMængde, 2,3);

        Label lblAlkoholprocent = new Label("Alkohol procent");
        pane.add(lblAlkoholprocent, 1,4);
        pane.add(txfAlkoholProcent, 1,5);

        Label lblPrisPerFlaske = new Label("Pris per flaske");
        pane.add(lblPrisPerFlaske, 2,4);
        pane.add(txfPrisPerUnit, 2,5);

        Label lblMedarbejder = new Label("Medarbejder");
        pane.add(lblMedarbejder, 1,6);
        pane.add(cbMedarbejdere, 1,7);
        cbMedarbejdere.getItems().setAll(Controller.getMedarbejdere());

        String[] s = {"Flaske", "Fad"};
        cbFadEllerFlasker.getItems().setAll(s);
        cbFadEllerFlasker.setOnAction(actionEvent -> selectAction());

        Button btnOpret = new Button("Opret");
        pane.add(btnOpret,1,8);
        pane.setValignment(btnOpret, VPos.BOTTOM);
        btnOpret.setOnAction(actionEvent -> this.opretAction());


    }

    private void opretAction() {
        List<Påfyldning> påfyldninger = lvwPåfyldning.getSelectionModel().getSelectedItems();
        LocalDate udgivelsesDato = dpUdgivelsesDato.getValue();
        double unitStørrelse = Double.parseDouble(txfFlaskeStørrelse.getText().trim());
        double vandMængde = Double.parseDouble(txfVandMængde.getText().trim());
        double alkoholProcent = Double.parseDouble(txfAlkoholProcent.getText().trim());
        double prisPrUnit = Double.parseDouble(txfPrisPerUnit.getText().trim());
        String medarbejder = cbMedarbejdere.getSelectionModel().getSelectedItem();
        String udgivelsesType = cbFadEllerFlasker.getSelectionModel().getSelectedItem();

        try {
            if (udgivelsesType == null) {
                Controller.opretAlert(Alert.AlertType.ERROR, "Fejl", "Ingen udgivelsestype valgt");
            } else if (udgivelsesDato == null) {
                Controller.opretAlert(Alert.AlertType.ERROR, "Fejl", "Ingen dato valgt");
            } else if (unitStørrelse <= 0 && udgivelsesType.equals("Flaske")) {
                Controller.opretAlert(Alert.AlertType.ERROR, "Fejl", "Størrelse på unit skal være over 0");
            } else if (vandMængde <= 0 && udgivelsesType.equals("Flaske")) {
                Controller.opretAlert(Alert.AlertType.ERROR, "Fejl", "Vandmængde skal være over 0");
            } else if (alkoholProcent <= 0) {
                Controller.opretAlert(Alert.AlertType.ERROR, "Fejl", "Alkohol procent skal være over 0");
            } else if (prisPrUnit <= 0 && udgivelsesType.equals("Flaske")) {
                Controller.opretAlert(Alert.AlertType.ERROR, "Fejl", "Pris pr unit skal være over 0");
            } else {
                if (udgivelsesType.equals("Fad")) {
                    Controller.opretUdgivelse(unitStørrelse, prisPrUnit, true, udgivelsesDato, alkoholProcent, 0.0, medarbejder, påfyldninger);
                } else {
                    Controller.opretUdgivelse(unitStørrelse, prisPrUnit, false, udgivelsesDato, alkoholProcent, vandMængde, medarbejder, påfyldninger);
                }
                this.close();
            }
        } catch (NumberFormatException e) {
            Controller.opretAlert(Alert.AlertType.ERROR, "Fejl", "Mangel på indtastning");
        }



    }

    private void selectAction() {
        String s = cbFadEllerFlasker.getSelectionModel().getSelectedItem();

        if (s.equals("Fad")) {
            txfFlaskeStørrelse.setDisable(true);
            txfVandMængde.setDisable(true);
            txfPrisPerUnit.setDisable(true);
        }
        else {
            txfFlaskeStørrelse.setDisable(false);
            txfVandMængde.setDisable(false);
            txfPrisPerUnit.setDisable(false);
        }

    }

    //TODO Antal af valgte påfyldninger skal ændres ift om det er flaske eller fad
    //TODO påfyldning skal fjernes efter den er udgivet og sættet til udgivet



}

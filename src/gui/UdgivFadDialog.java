package gui;

import application.controller.Controller;
import application.model.Påfyldning;
import javafx.beans.value.ChangeListener;
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

public class UdgivFadDialog extends Stage {
    private DatePicker dpUdgivelsesDato = new DatePicker();
    private TextField txfAlkoholProcent = new TextField();
    private TextField txfPrisForFadet = new TextField();
    private ComboBox<String> cbMedarbejdere = new ComboBox<>();
    private ListView<Påfyldning> lvwPåfyldning = new ListView<>();
    private TextField txfAngelsShare = new TextField();
    private Controller controller;

    public UdgivFadDialog() {
        controller = Controller.getController();
        this.initStyle(StageStyle.UTILITY);
        this.initModality(Modality.APPLICATION_MODAL);
        this.setResizable(false);
        this.setMinWidth(640);
        this.setTitle("Udgiv fad");

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
        pane.add(lblAllePåfyldning, 0, 0);
        pane.add(lvwPåfyldning, 0, 1, 1, 8);
        lvwPåfyldning.setPrefWidth(250);
        lvwPåfyldning.getItems().setAll(controller.getMindst3ÅrsIkkeTommePåfyldninger(LocalDate.now()));

        Label lblUdgivelsesDato = new Label("Udgivelses dato");
        pane.add(lblUdgivelsesDato, 1, 0);
        pane.add(dpUdgivelsesDato, 1, 1);
        dpUdgivelsesDato.setEditable(false);
        dpUdgivelsesDato.setValue(LocalDate.now());
        ChangeListener<LocalDate> changeListenerUdgivelsesdato = (observableValue, localDate, t1) -> this.changeDato();
        dpUdgivelsesDato.valueProperty().addListener(changeListenerUdgivelsesdato);

        Label lblAlkoholprocent = new Label("Alkohol procent");
        pane.add(lblAlkoholprocent, 2, 0);
        pane.add(txfAlkoholProcent, 2, 1);

        Label lblPrisPerUnit = new Label("Pris for fad");
        pane.add(lblPrisPerUnit, 1, 2);
        pane.add(txfPrisForFadet, 1, 3);

        Label lblAngelsShare = new Label("Angels share i procent");
        pane.add(lblAngelsShare,2,2);
        pane.add(txfAngelsShare, 2,3);

        Label lblMedarbejder = new Label("Medarbejder");
        pane.add(lblMedarbejder, 1, 4);
        pane.add(cbMedarbejdere, 1, 5);
        cbMedarbejdere.getItems().setAll(controller.getMedarbejdere());
        if (IndstillingPane.erMedarbejderValgt()) cbMedarbejdere.getSelectionModel().select(IndstillingPane.getValgtMedarbejder());

        Button btnOpret = new Button("Opret");
        pane.add(btnOpret, 1, 8);
        pane.setValignment(btnOpret, VPos.BOTTOM);
        btnOpret.setOnAction(actionEvent -> this.opretAction());
    }

    private void opretAction() {
        try {
            List<Påfyldning> påfyldninger = lvwPåfyldning.getSelectionModel().getSelectedItems();
            LocalDate udgivelsesDato = dpUdgivelsesDato.getValue();
            double unitStørrelse = 0.0;
            double vandMængde = 0.0;
            double alkoholProcent = Double.parseDouble(txfAlkoholProcent.getText().trim());
            double prisPrUnit = Double.parseDouble(txfPrisForFadet.getText().trim());
            String medarbejder = cbMedarbejdere.getSelectionModel().getSelectedItem();
            double angelsShare = Double.parseDouble(txfAngelsShare.getText().trim());

            if (påfyldninger.isEmpty()) {
                controller.opretAlert(Alert.AlertType.ERROR, "Fejl", "Ingen påfyldninger valgt");
            } else if (udgivelsesDato == null) {
                controller.opretAlert(Alert.AlertType.ERROR, "Fejl", "Ingen dato valgt");
            } else if (alkoholProcent <= 0) {
                controller.opretAlert(Alert.AlertType.ERROR, "Fejl", "Alkohol procent skal være over 0");
            } else if (prisPrUnit <= 0) {
                controller.opretAlert(Alert.AlertType.ERROR, "Fejl", "Pris pr unit skal være over 0");
            } else if (angelsShare < 0) {
                controller.opretAlert(Alert.AlertType.ERROR, "Fejl", "Angels share skal registreres");
            } else {
                List<Double> mængder = new ArrayList<>(List.of(påfyldninger.getFirst().mængdeTilbage()));
                controller.opretUdgivelse(unitStørrelse, prisPrUnit, true, udgivelsesDato, alkoholProcent, vandMængde, medarbejder, null, angelsShare, påfyldninger, mængder);
                this.close();
            }
        } catch (NumberFormatException e) {
            controller.opretAlert(Alert.AlertType.ERROR, "Fejl", "Indtastnings fejl");
        }
    }

    private void changeDato() {
        LocalDate udgivelsesDato = dpUdgivelsesDato.getValue();

        if (udgivelsesDato != null) {
            lvwPåfyldning.getItems().setAll(controller.getMindst3ÅrsIkkeTommePåfyldninger(udgivelsesDato));
        }
    }
}

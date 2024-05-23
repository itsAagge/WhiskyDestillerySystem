package gui;

import application.controller.Controller;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.time.LocalDate;

public class KornDialog extends Stage {

    private TextField txfMarkNavn = new TextField();
    private TextField txfSort = new TextField();
    private DatePicker dpHøstdato = new DatePicker();
    private TextField txfMængdeKg = new TextField();
    private Controller controller;

    public KornDialog() {
        controller = Controller.getController();
        this.initStyle(StageStyle.UTILITY);
        this.initModality(Modality.APPLICATION_MODAL);
        this.setResizable(false);
        this.setMinWidth(400);
        this.setTitle("Registrer korn");

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

        Label lblMarknavn = new Label("Mark navn");
        pane.add(lblMarknavn, 0,0);
        pane.add(txfMarkNavn, 0,1);

        Label lblSort = new Label("Korn sort");
        pane.add(lblSort, 0,2);
        pane.add(txfSort, 0,3);

        Label lblHøstDato = new Label("Høst dato");
        pane.add(lblHøstDato, 1,0);
        pane.add(dpHøstdato, 1,1);
        dpHøstdato.setEditable(false);

        Label lblMængdeKg = new Label("Mængde i kg");
        pane.add(lblMængdeKg,1,2);
        pane.add(txfMængdeKg, 1,3);

        Button btnRegistrer = new Button("Registrer");
        pane.add(btnRegistrer, 1,4);
        pane.setHalignment(btnRegistrer, HPos.RIGHT);
        btnRegistrer.setOnAction(actionEvent -> this.registrerKorn());

    }

    private void registrerKorn() {
        try {
            if (txfMarkNavn.getText() == null) {
                controller.opretAlert(Alert.AlertType.ERROR, "Fejl", "mark navn skal udfyldes");
            } else if (txfSort.getText() == null) {
                controller.opretAlert(Alert.AlertType.ERROR, "Fejl", "korn sort skal udfyldes");
            } else if (dpHøstdato.getValue() == null) {
                controller.opretAlert(Alert.AlertType.ERROR, "Fejl", "Ingen høst dato");
            } else if (txfMængdeKg.getText() == null || Double.parseDouble(txfMængdeKg.getText()) <= 0) {
                controller.opretAlert(Alert.AlertType.ERROR, "Fejl", "Mængde skal være indtastet og over 0");
            } else {
                String markNavn = txfMarkNavn.getText().trim();
                String sort = txfSort.getText().trim();
                LocalDate høstDato = dpHøstdato.getValue();
                double mængdeKg = Double.parseDouble(txfMængdeKg.getText().trim());
                controller.opretKorn(markNavn, sort, høstDato, mængdeKg);
                this.close();
            }
        } catch (Exception e) {
            controller.opretAlert(Alert.AlertType.ERROR, "Fejl", "indtastningsfejl");
        }
    }
}

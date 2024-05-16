package gui;

import application.controller.Controller;
import application.model.Lager;
import application.model.Reol;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class LagerDialog extends Stage {

    TextField txfAdresse = new TextField();
    TextField txfKvadrat = new TextField();


    public LagerDialog() {
        this.initStyle(StageStyle.UTILITY);
        this.initModality(Modality.APPLICATION_MODAL);
        this.setResizable(false);
        this.setTitle("Opret lager");

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

        Label lblAdresse = new Label("Lager adresse");
        pane.add(lblAdresse, 0,0);
        pane.add(txfAdresse, 0,1);

        Label lblKvadratMeter = new Label("Lagers kvadratmeter areal");
        pane.add(lblKvadratMeter, 1,0);
        pane.add(txfKvadrat, 1,1);

        Button btnOpret = new Button("Opret");
        pane.add(btnOpret,1,5);
        pane.setHalignment(btnOpret, HPos.RIGHT);
        btnOpret.setOnAction(actionEvent -> this.opretAction());


    }

    private void opretAction() {
        String adresse = txfAdresse.getText().trim();
        String kvadratMeter = txfKvadrat.getText().trim();

        if (txfAdresse.getText().isEmpty()) {
            Controller.opretAlert(Alert.AlertType.ERROR, "Fejl", "Ingen adresse indtastet");
        } else if (txfKvadrat.getText().isEmpty()) {
            Controller.opretAlert(Alert.AlertType.ERROR, "Fejl", "Ingen kvadratmeter indtastet");
        } else {
            Controller.opretLager(adresse, kvadratMeter);
            this.close();
        }

    }

}

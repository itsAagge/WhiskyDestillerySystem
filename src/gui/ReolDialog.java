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

public class ReolDialog extends Stage {

    Lager lager;
    TextField txfAntalReoler = new TextField();
    TextField txfHyldeMaxPlads = new TextField();
    TextField txfAntalHylder = new TextField();

    public ReolDialog(Lager lager) {
        this.initStyle(StageStyle.UTILITY);
        this.initModality(Modality.APPLICATION_MODAL);
        this.setResizable(false);
        this.lager = lager;
        this.setTitle("Tilføj reol");

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

        Label lblReol = new Label("Antal reoler");
        pane.add(lblReol, 0, 0);
        pane.add(txfAntalReoler, 0, 1);

        Label lblAntalHylder = new Label("Antal hylder");
        pane.add(lblAntalHylder, 1, 0);
        pane.add(txfAntalHylder, 1, 1);

        Label lblHyldeMaxPlads = new Label("Max antal fade pr. hylde");
        pane.add(lblHyldeMaxPlads, 0, 2);
        pane.add(txfHyldeMaxPlads, 0, 3);


        Button btnTilføj = new Button("Tilføj");
        pane.add(btnTilføj, 1, 3);
        btnTilføj.setOnAction(actionEvent -> this.tilføjAction());
        pane.setHalignment(btnTilføj, HPos.RIGHT);

    }

    private void tilføjAction() {
        try {
            int antalReoler = Integer.parseInt(txfAntalReoler.getText().trim());
            int antalHylder = Integer.parseInt(txfAntalHylder.getText().trim());
            int hyldeMaxPlads = Integer.parseInt(txfHyldeMaxPlads.getText().trim());

            if (antalReoler <= 0) {
                Controller.opretAlert(Alert.AlertType.ERROR, "Fejl", "antal reoler skal være over nul");
            } else if (antalHylder <= 0) {
                Controller.opretAlert(Alert.AlertType.ERROR, "Fejl", "antal hylder skal være over nul");
            } else if (hyldeMaxPlads <= 0) {
                Controller.opretAlert(Alert.AlertType.ERROR, "Fejl", "Hylde max plads skal være over nul");
            } else {
                lager.tilføjReol(antalReoler, antalHylder, hyldeMaxPlads);
                this.close();
            }
        } catch (NumberFormatException e) {
            Controller.opretAlert(Alert.AlertType.ERROR, "Fejl", "Indtastningsfejl");
        }

    }

}

package gui;

import application.controller.Controller;
import application.model.Fad;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class FadDialog extends Stage {
    private TextField txfLand = new TextField();
    private TextField txfFirma = new TextField();
    private TextField txfTidligereIndhold = new TextField();
    private TextField txfTidligereIndholdAlder = new TextField();
    private TextField txfStørrelse = new TextField();
    private TextField txfTræType = new TextField();
    private Fad fad;

    public FadDialog(Fad fad) {
        this.initStyle(StageStyle.UTILITY);
        this.initModality(Modality.APPLICATION_MODAL);
        this.setResizable(false);
        this.fad = fad;
        if (this.fad == null) {
            this.setTitle("Opret fad");
        } else {
            this.setTitle("Rediger fad");
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

        Label lblLand = new Label("Fra land:");
        pane.add(lblLand,0,0);
        pane.add(txfLand,1,0);

        Label lblFirma = new Label("Leverandør:");
        pane.add(lblFirma,0,1);
        pane.add(txfFirma,1,1);

        Label lblTidligereIndhold = new Label("Tidligere indhold:");
        pane.add(lblTidligereIndhold,0,2);
        pane.add(txfTidligereIndhold,1,2);

        Label lblTidligereIndholdAlder = new Label("Tidligere indhold alder:");
        pane.add(lblTidligereIndholdAlder,0,3);
        pane.add(txfTidligereIndholdAlder,1,3);

        Label lblStørrelse = new Label("Størrelse i liter:");
        pane.add(lblStørrelse,0,4);
        pane.add(txfStørrelse,1,4);

        Label lblTræType = new Label("Trætype:");
        pane.add(lblTræType,0,5);
        pane.add(txfTræType,1,5);

        if(this.fad != null) {
            hentFadData();
        }

        Button btnGem = new Button("Gem");
        pane.add(btnGem,1,6);
        pane.setHalignment(btnGem, HPos.RIGHT);

        btnGem.setOnAction(evnet -> gemAction());
    }

    private void gemAction() {
        String fraLand = null;
        String leverandør = null;
        String tidligereIndhold = null;
        double tidligereIndholdAlder = 0.0;
        int størrelseL = 0;
        String træType = null;

        if(txfLand.getText().isEmpty()) {
            Controller.opretAlert(Alert.AlertType.ERROR, "Fejl", "Du mangler at angive et land");
        } else if(txfFirma.getText().isEmpty()) {
            Controller.opretAlert(Alert.AlertType.ERROR, "Fejl", "Du mangler at angive et leverandør");
        } else if(txfStørrelse.getText().isEmpty() || txfStørrelse.getText().equals("0")) {
            Controller.opretAlert(Alert.AlertType.ERROR, "Fejl", "Du mangler at angive antal liter, der er plads til i fadet");
        } else if (txfTræType.getText().isEmpty()) {
            Controller.opretAlert(Alert.AlertType.ERROR, "Fejl", "Du mangler at angive en type af træ");
        } else {
            if (!txfTidligereIndhold.getText().isEmpty()) {
                tidligereIndhold = txfTidligereIndhold.getText().trim();
                tidligereIndholdAlder = Double.parseDouble(txfTidligereIndholdAlder.getText());
            }
            fraLand = txfLand.getText().trim();
            leverandør = txfFirma.getText().trim();
            størrelseL = Integer.parseInt(txfStørrelse.getText());
            træType = txfTræType.getText().trim();
            if (this.fad != null) {
                Controller.opdaterFad(fad, fraLand, tidligereIndhold, størrelseL, træType, tidligereIndholdAlder, leverandør);
            } else {
                Controller.opretFad(fraLand, tidligereIndhold, størrelseL, træType, tidligereIndholdAlder, leverandør);
            }
            this.close();
        }
    }

    private void hentFadData() {
        txfLand.setText(this.fad.getFraLand());
        txfFirma.setText(this.fad.getLeverandør());
        txfStørrelse.setText(this.fad.getStørrelseL() + "");
        txfTræType.setText(this.fad.getTræType());
        String tidligereIndhold = this.fad.getTidligereIndhold();
        double tidligereIndholdAlder = this.fad.getAlderAfTidligereIndhold();
        if(tidligereIndhold != null) {
            txfTidligereIndhold.setText(tidligereIndhold);
            txfTidligereIndholdAlder.setText(tidligereIndholdAlder + "");
        }
    }
}

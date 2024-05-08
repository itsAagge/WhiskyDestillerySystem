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

        Label lblLand = new Label("Fra hvilket land kommer fadet?");
        pane.add(lblLand,0,0);
        pane.add(txfLand,1,0);

        Label lblFirma = new Label("Fra hvilket firma kommer fadet?");
        pane.add(lblFirma,0,1);
        pane.add(txfFirma,1,1);

        Label lblTidligereIndhold = new Label("Hvad har fadet tidligere indeholdt?");
        pane.add(lblTidligereIndhold,0,2);
        pane.add(txfTidligereIndhold,1,2);

        Label lblTidligereIndholdAlder = new Label("Hvor mange år har det tidligere indhold ligget på fadet?");
        pane.add(lblTidligereIndholdAlder,0,3);
        pane.add(txfTidligereIndholdAlder,1,3);

        Label lblStørrelse = new Label("Hvor mange liter kan fadet indeholde?");
        pane.add(lblStørrelse,0,4);
        pane.add(txfStørrelse,1,4);

        Label lblTræType = new Label("Hvilket type træ er fadet lavet af?");
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
        String land = null;
        String firma = null;
        String tidligereIndhold = null;
        double tidligereIndholdAlder = 0.0;
        int størrelse = 0;
        String træType = null;

        if(txfLand.getText().isEmpty()) {
            opretFejl("Du mangler at angive et land");
        } else if(txfFirma.getText().isEmpty()) {
            opretFejl("Du mangler at angive et firma");
        } else if(txfStørrelse.getText().isEmpty()) {
            opretFejl("Du mangler at angive antal liter, der er plads til i fadet");
        } else if (txfTræType.getText().isEmpty()) {
            opretFejl("Du mangler at angive en type af træ");
        } else {
            if (!txfTidligereIndhold.getText().isEmpty()) {
                tidligereIndhold = txfTidligereIndhold.getText().trim();
                tidligereIndholdAlder = Double.parseDouble(txfTidligereIndholdAlder.getText());
            }
            land = txfLand.getText().trim();
            firma = txfFirma.getText().trim();
            størrelse = Integer.parseInt(txfStørrelse.getText());
            træType = txfTræType.getText().trim();
            if (this.fad != null) {
                this.fad.setFraLand(land);
                this.fad.setLeverandør(firma);
                this.fad.setStørrelseL(størrelse);
                this.fad.setTræType(træType);
                this.fad.setTidligereIndhold(tidligereIndhold);
                this.fad.setAlderAfTidligereIndhold(tidligereIndholdAlder);
            } else {
                Controller.opretFad(land, tidligereIndhold, størrelse, træType, tidligereIndholdAlder, firma);
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

    private Alert opretFejl(String contentText) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Fejl");
        alert.setContentText(contentText);
        alert.initModality(Modality.APPLICATION_MODAL);
        return alert;
    }
}

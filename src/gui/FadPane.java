package gui;

import application.controller.Controller;
import application.model.Fad;
import application.model.Påfyldning;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;

public class FadPane extends GridPane {
    private ListView<Fad> lvwFade = new ListView<>();
    private ListView<Påfyldning> lvwTidligerePåfyldninger = new ListView<>();
    private TextArea txtAreaBeskrivelse = new TextArea();
    private TextArea txtAreaNuværendePåfyldning = new TextArea();
    private Button btnOpretFad = new Button("Opret");
    private Button btnRedigerFad = new Button("Rediger");
    private Button btnDeaktiverFad = new Button("Deaktiver");
    private Button btnSletFad = new Button("Slet");

    public FadPane() {
        this.setPadding(new Insets(20));
        this.setHgap(20);
        this.setVgap(10);
        this.setGridLinesVisible(false);

        Label lblAlleFade = new Label("Alle fade");
        this.add(lblAlleFade, 0, 0);
        this.add(lvwFade, 0,1,3,3);
        lvwFade.getItems().setAll(Controller.getAlleFade());
        ChangeListener<Fad> fadChangeListener = (observableValue, oldValue, newValue) -> {
            if(newValue == null) {
                txtAreaBeskrivelse.clear();
                txtAreaNuværendePåfyldning.clear();
                lvwTidligerePåfyldninger.getItems().clear();
            } else {
                this.changeFad();
            }
        };
        lvwFade.getSelectionModel().selectedItemProperty().addListener(fadChangeListener);
        lvwFade.setPrefWidth(250);

        HBox hBoxButtons = new HBox();
        hBoxButtons.setSpacing(20);
        this.add(hBoxButtons, 0, 4, 4, 1);
        this.add(btnOpretFad, 0, 4);
        btnOpretFad.setOnAction(event -> this.opretFadAction());
        hBoxButtons.getChildren().add(btnOpretFad);
        this.add(btnRedigerFad, 1, 4);
        btnRedigerFad.setOnAction(event -> this.redigerFadAction());
        hBoxButtons.getChildren().add(btnRedigerFad);
        this.add(btnSletFad, 2, 4);
        btnSletFad.setOnAction(event -> this.sletFadAction());
        hBoxButtons.getChildren().add(btnSletFad);
        this.add(btnDeaktiverFad, 3, 4);
        btnDeaktiverFad.setOnAction(event -> this.deaktiverFadAction());
        hBoxButtons.getChildren().add(btnDeaktiverFad);
        setKnapperAktive(false);

        Label lblFadBeskrivelse = new Label("Fadets historie");
        this.add(lblFadBeskrivelse, 3, 0);
        this.add(txtAreaBeskrivelse, 3, 1, 2, 1);
        this.txtAreaBeskrivelse.setEditable(false);
        txtAreaBeskrivelse.setPrefWidth(250);

        Label lblNuværendePåfyldning = new Label("Nuværende påfyldning");
        this.add(lblNuværendePåfyldning, 3, 2);
        this.add(txtAreaNuværendePåfyldning, 3, 3, 2 ,1);
        this.txtAreaNuværendePåfyldning.setEditable(false);
        txtAreaNuværendePåfyldning.setPrefWidth(250);

        Label lblTidligerePåfyldninger = new Label("Tidligere påfyldninger");
        this.add(lblTidligerePåfyldninger, 5, 0);
        this.add(lvwTidligerePåfyldninger, 5, 1, 1, 3);
        lvwTidligerePåfyldninger.setPrefWidth(250);

    }

    private void changeFad() {
        Fad fad = lvwFade.getSelectionModel().getSelectedItem();

        //Todo: fix fad beskrivelse, så vi ikke bare kalder en toString
        this.txtAreaBeskrivelse.setText(fad.toString());

        //Todo: Når Påfyldningsklassen er færdig
        /*
        if (fad.erFyldt()) {
            Påfyldning påfyldning = fad.getPåfyldninger.getLast();
            this.txtAreaNuværendePåfyldning.setText(påfyldning.getBeskrivelse());
        } else {
            this.txtAreaNuværendePåfyldning.setText("Fadet er tomt.");
        }
         */

        setKnapperAktive(true);
    }

    private void opretFadAction() {
        FadDialog fadDialog = new FadDialog(null);
        fadDialog.showAndWait();
        lvwFade.getItems().setAll(Controller.getAlleFade());
        setKnapperAktive(false);
    }

    private void redigerFadAction() {
        Fad fad = lvwFade.getSelectionModel().getSelectedItem();
        FadDialog fadDialog = new FadDialog(fad);
        fadDialog.showAndWait();
        lvwFade.getItems().setAll(Controller.getAlleFade());
        setKnapperAktive(false);
    }

    private void deaktiverFadAction() {
        lvwFade.getSelectionModel().getSelectedItem().setAktiv(false);
        lvwFade.getItems().setAll(Controller.getAlleFade());
        setKnapperAktive(false);
    }

    private void sletFadAction() {
        Fad fad = lvwFade.getSelectionModel().getSelectedItem();
        if(fad.erFyldt()) {
            opretAlert(Alert.AlertType.ERROR, "Fejl", "Du kan ikke slette et fad med indhold.");
        } /* else if() {
            //Todo: Efter implementering af Påfyldnignsklassen
            opretAlert(Alert.AlertType.ERROR, "Fejl", "Du kan ikke slette et fad, der har været indhold i tidligere. Disse fade skal deaktiveres");
        } */ else {
            Controller.fjernFad(fad);
        }
        lvwFade.getItems().setAll(Controller.getAlleFade());
        setKnapperAktive(false);
    }

    private Alert opretAlert(Alert.AlertType alertType, String title, String contentText) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(contentText);
        alert.initModality(Modality.APPLICATION_MODAL);
        return alert;
    }

    private void setKnapperAktive(boolean bool) {
        btnRedigerFad.setDisable(!bool);
        btnSletFad.setDisable(!bool);
        btnDeaktiverFad.setDisable(!bool);
    }
}

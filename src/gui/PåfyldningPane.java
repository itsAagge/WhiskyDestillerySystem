package gui;

import application.controller.Controller;
import application.model.Destillat;
import application.model.Fad;
import application.model.Påfyldning;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import java.util.ArrayList;


public class PåfyldningPane extends GridPane {

    private ListView<Påfyldning> lvwPåfyldninger = new ListView<>();
    private TextArea txtPåfyldningsBeskrivelse = new TextArea();
    private Button btnOpretPåfyldning = new Button("Opret");
    private Button btnFlytPåfyldning = new Button("Flyt");
    private Button btnSletPåfyldning = new Button("Slet");

    public PåfyldningPane() {
        this.setPadding(new Insets(20));
        this.setHgap(20);
        this.setVgap(10);
        this.setGridLinesVisible(false);

        //Label og listview til at vise alle påfyldninger
        Label lblAllePåfyldninger = new Label("Alle påfyldninger");
        this.add(lblAllePåfyldninger, 0, 0);
        this.add(lvwPåfyldninger, 0, 1);
        //TODO - tilføj så alle påfyldninger vises
        lvwPåfyldninger.getItems().setAll(getPåfyldninger());


        //Label og textarea til påfyldninges beskrivelse
        Label lblPåfyldningBeskrivelse = new Label("Beskrivelse af valgte påfyldning");
        this.add(lblPåfyldningBeskrivelse, 1, 0);
        this.add(txtPåfyldningsBeskrivelse, 1,1);
        txtPåfyldningsBeskrivelse.setWrapText(true);
        //TODO - gør sådan at den valgte påfyldnings beskrivelse vises
        ChangeListener<Påfyldning> påfyldningChangeListener = (observableValue, oldValue, newValue) -> this.changePåfyldning();
        lvwPåfyldninger.getSelectionModel().selectedItemProperty().addListener(påfyldningChangeListener);


        //Hbox af knapper
        HBox hBoxButtons = new HBox();
        hBoxButtons.setSpacing(20);
        this.add(hBoxButtons, 0, 4, 4, 1);
        //Knap til oprettelse af ny påfyldning
        btnOpretPåfyldning.setOnAction(event -> this.opretPåfyldningAction());
        hBoxButtons.getChildren().add(btnOpretPåfyldning);
        //Knap til flytning af påfyldning
        btnFlytPåfyldning.setOnAction(event -> this.flytPåfyldningAction());
        hBoxButtons.getChildren().add(btnFlytPåfyldning);
        //Knap til sletning af påfyldning
        btnSletPåfyldning.setOnAction(event -> this.sletPåfyldningAction());
        hBoxButtons.getChildren().add(btnSletPåfyldning);
    }

    private void changePåfyldning() {
        Påfyldning påfyldning = lvwPåfyldninger.getSelectionModel().getSelectedItem();

        if (påfyldning != null) {
            txtPåfyldningsBeskrivelse.setText(påfyldning.getBeskrivelse());
        }
    }

    //Todo: Denne metode skal i Controlleren
    private ArrayList<Påfyldning> getPåfyldninger() {
        ArrayList<Påfyldning> påfyldninger = new ArrayList<>();
        for (Fad fad : Controller.getAlleFade()) {
            if(fad.erFyldt()) {
                påfyldninger.add(fad.getPåfyldninger().getLast());
            }
        }
        return påfyldninger;
    }

    private void sletPåfyldningAction() {
        //TODO
    }

    private void flytPåfyldningAction() {
        //TODO
    }

    private void opretPåfyldningAction() {
        //TODO
    }
}

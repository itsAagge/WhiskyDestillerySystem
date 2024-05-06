package gui;

import application.controller.Controller;
import application.model.Fad;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;

public class FadPane extends GridPane {
    private ListView<Fad> lvwFade = new ListView<>();
    private TextArea txtAreaBeskrivelse = new TextArea();
    private TextArea txtAreaNuværendePåfyldning = new TextArea();

    public FadPane() {
        this.setPadding(new Insets(20));
        this.setHgap(20);
        this.setVgap(10);
        this.setGridLinesVisible(false);

        Label lblAlleFade = new Label("Alle fade");
        this.add(lblAlleFade, 0, 0);
        this.add(lvwFade, 0,1,1,3);
        lvwFade.getItems().setAll(Controller.getAlleFade());
        ChangeListener<Fad> fadChangeListener = (observableValue, oldValue, newValue) -> this.changeFad();
        lvwFade.getSelectionModel().selectedItemProperty().addListener(fadChangeListener);

        Label lblFadBeskrivelse = new Label("Fadets historie");
        this.add(lblFadBeskrivelse, 1, 0);
        this.add(txtAreaBeskrivelse, 1, 1);
        this.txtAreaBeskrivelse.setEditable(false);

        Label lblNuværendePåfyldning = new Label("Nuværende påfyldning");
        this.add(lblNuværendePåfyldning, 1, 2);
        this.add(txtAreaNuværendePåfyldning, 1, 3);
        this.txtAreaNuværendePåfyldning.setEditable(false);

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
    }
}

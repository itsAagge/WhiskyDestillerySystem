package gui;

import application.controller.Controller;
import application.model.Destillat;
import application.model.Fad;
import application.model.Påfyldning;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class påfyldningsDialog extends Stage {

    Påfyldning påfyldning;
    //ListView<Destillat> lvwDestillat = new ListView<>();
    ComboBox<Destillat> cBoxDestillater = new ComboBox<>();
    ListView<Fad> lvwFad = new ListView<>();
    DatePicker dpPåfyldningsdato = new DatePicker();
    DatePicker dpFærdigDato = new DatePicker();
    TextField txfMængde = new TextField();

    public påfyldningsDialog(Påfyldning påfyldning) {
        this.initStyle(StageStyle.UTILITY);
        this.initModality(Modality.APPLICATION_MODAL);
        this.setResizable(false);
        this.setMinWidth(640);
        this.påfyldning = påfyldning;
        if (this.påfyldning == null) {
            this.setTitle("Opret påfyldning");
        } else {
            this.setTitle("Rediger påfyldning");
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

        Label lblLedigeFad = new Label("Ledige fad");
        pane.add(lblLedigeFad, 0, 0);
        pane.add(lvwFad, 0, 1, 1,6);
        lvwFad.setPrefWidth(250);
        lvwFad.setPrefHeight(200);
        lvwFad.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        lvwFad.getItems().setAll(Controller.getLedigeFad());

        Label lblUgensDestillater = new Label("Ugens destillater");
        pane.add(lblUgensDestillater, 1, 4);
        pane.add(cBoxDestillater, 1, 5);
        cBoxDestillater.getItems().setAll(Controller.getUgensDestillater());

        Label lblpåfyldningsDato = new Label("Påfyldnings dato");
        pane.add(lblpåfyldningsDato, 1, 0);
        pane.add(dpPåfyldningsdato, 1,1);

        Label lblFærdigDato = new Label("Færdig dato");
        pane.add(lblFærdigDato,1,2);
        pane.add(dpFærdigDato, 1,3);

        Label lblMængde = new Label("Mængde i liter");
        pane.add(lblMængde, 2,4);
        pane.add(txfMængde, 2,5);
        txfMængde.setPrefWidth(10);
        Label lblLiter = new Label("L");
        pane.add(lblLiter, 3,5);

        Button btnOpret = new Button("Opret");
        pane.add(btnOpret, 2,6);
        btnOpret.setOnAction(actionEvent -> this.opretAction());
        pane.setHalignment(btnOpret, HPos.RIGHT);

        Button btnTilføjDestillat = new Button("Tilføj Destillat");
        pane.add(btnTilføjDestillat, 1,6);
        pane.setHalignment(btnTilføjDestillat, HPos.LEFT);
        btnTilføjDestillat.setOnAction(actionEvent -> this.tilføjDestillat());




    }

    double[] mængder;
    Destillat[] destillats;

    private void tilføjDestillat() {
        double mængde = Double.parseDouble(txfMængde.getText().trim());
        Destillat destillat = cBoxDestillater.getSelectionModel().getSelectedItem();

        if (destillat != null && mængde > 0) {
            Arrays.fill(destillats, destillat);
            Arrays.fill(mængder, mængde);
        }

    }

    private void opretAction() {
        List<Fad> fade = lvwFad.getSelectionModel().getSelectedItems();
        LocalDate påfyldningsdato = dpPåfyldningsdato.getValue();
        LocalDate færdigDato = dpFærdigDato.getValue();

        if (!fade.isEmpty() && påfyldningsdato != null && destillats.length > 0 && mængder.length > 0 && destillats.length == mængder.length) {

        }



    }

}

package gui;

import application.controller.Controller;
import application.model.*;
import javafx.beans.value.ChangeListener;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.GridPane;

import java.util.List;

public class LagerPane extends GridPane {

    ListView<Lager> lvwLager = new ListView<>();
    ListView<Reol> lvwReol = new ListView<>();
    ListView<Hylde> lvwHylde = new ListView<>();
    ListView<Fad> lvwFad = new ListView<>();
    Button btnTilføjReol = new Button("Tilføj Reol");
    Button btnFlytFad = new Button("Placer fad");


    public LagerPane() {
        this.setPadding(new Insets(20));
        this.setHgap(20);
        this.setVgap(10);
        this.setGridLinesVisible(false);

        Label lblAlleLagre = new Label("Alle lagre");
        this.add(lblAlleLagre, 0, 0);
        this.add(lvwLager, 0, 1);
        lvwLager.getItems().setAll(Controller.getLagre());
        ChangeListener<Lager> lagerChangeListener = (observableValue, oldValue, newValue) -> this.changeLager();
        lvwLager.getSelectionModel().selectedItemProperty().addListener(lagerChangeListener);

        Label lblReol = new Label("Reoler på lageret");
        this.add(lblReol, 1, 0);
        this.add(lvwReol, 1, 1);
        ChangeListener<Reol> reolChangeListener = (observableValue, oldValue, newValue) -> this.changeReol();
        lvwReol.getSelectionModel().selectedItemProperty().addListener(reolChangeListener);

        Label lblHylde = new Label("Hylder på reolen");
        this.add(lblHylde, 2, 0);
        this.add(lvwHylde, 2, 1);
        ChangeListener<Hylde> hyldeChangeListener = (observableValue, oldValue, newValue) -> this.changeHylde();
        lvwHylde.getSelectionModel().selectedItemProperty().addListener(hyldeChangeListener);

        Label lblFad = new Label("Fade på hylden");
        this.add(lblFad, 3, 0);
        this.add(lvwFad, 3, 1);
        ChangeListener<Fad> fadChangeListener = (observableValue, oldValue, newValue) -> this.changeFad();
        lvwFad.getSelectionModel().selectedItemProperty().addListener(fadChangeListener);
        lvwFad.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        Button btnOpret = new Button("Opret");
        this.add(btnOpret, 0, 2);
        btnOpret.setOnAction(actionEvent -> this.opretAction());

        this.add(btnFlytFad, 3, 2);
        this.setHalignment(btnFlytFad, HPos.RIGHT);
        btnFlytFad.setOnAction(actionEvent -> this.flytFadAction());

        this.add(btnTilføjReol, 1, 2);
        btnTilføjReol.setOnAction(actionEvent -> this.tilføjAction());
        setKnapperAktive(false);


    }

    private void flytFadAction() {
        List<Fad> fade = lvwFad.getSelectionModel().getSelectedItems();

        if (fade != null) {
            FlytFadPåLagerDialog flytFadPåLagerDialog = new FlytFadPåLagerDialog(fade);
            flytFadPåLagerDialog.showAndWait();
        }
        else {
            FlytFadPåLagerDialog flytFadPåLagerDialog = new FlytFadPåLagerDialog(null);
            flytFadPåLagerDialog.showAndWait();
        }

    }

    private void changeFad() {
        Fad fad = lvwFad.getSelectionModel().getSelectedItem();
        if (fad != null) {
            btnFlytFad.setText("Flyt fad");
        }
    }

    private void tilføjAction() {
        Lager lager = lvwLager.getSelectionModel().getSelectedItem();
        if (lager != null) {
            ReolDialog reolDialog = new ReolDialog(lager);
            reolDialog.showAndWait();
            lvwReol.getItems().setAll(lager.getReoler());
        }

    }

    private void changeHylde() {
        Hylde hylde = lvwHylde.getSelectionModel().getSelectedItem();
        if (hylde != null) {
            lvwFad.getItems().setAll(hylde.getFade());
        }
    }

    private void changeReol() {
        Reol reol = lvwReol.getSelectionModel().getSelectedItem();

        if (reol != null) {
            lvwHylde.getItems().setAll(reol.getHylder());
        }
    }

    private void changeLager() {
        Lager lager = lvwLager.getSelectionModel().getSelectedItem();

        if (lager != null) {
            lvwReol.getItems().setAll(lager.getReoler());
            btnTilføjReol.setDisable(false);
        }

    }

    private void opretAction() {
        LagerDialog lagerDialog = new LagerDialog();
        lagerDialog.showAndWait();
        lvwLager.getItems().setAll(Controller.getLagre());

    }

    private void setKnapperAktive(boolean bool) {
        btnTilføjReol.setDisable(!bool);
    }

}

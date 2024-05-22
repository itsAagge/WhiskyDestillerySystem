package gui;

import application.controller.Controller;
import application.model.*;
import javafx.beans.value.ChangeListener;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import java.util.List;

public class LagerPane extends GridPane {

    private ListView<Lager> lvwLager = new ListView<>();
    private ListView<Reol> lvwReol = new ListView<>();
    private ListView<Hylde> lvwHylde = new ListView<>();
    private ListView<Fad> lvwFad = new ListView<>();
    private Button btnTilføjReol = new Button("Tilføj Reol");
    private Button btnFlytFad = new Button("Placer fad");
    private Button btnKlarWhiskey = new Button("Se færdiglagret whisky");
    private Controller controller;


    public LagerPane() {
        controller = Controller.getController();
        this.setPadding(new Insets(20));
        this.setHgap(20);
        this.setVgap(10);
        this.setGridLinesVisible(false);

        Label lblAlleLagre = new Label("Alle lagre");
        this.add(lblAlleLagre, 0, 0);
        this.add(lvwLager, 0, 1);
        lvwLager.getItems().setAll(controller.getLagre());
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

        HBox hBoxButtons = new HBox();
        hBoxButtons.setSpacing(20);
        this.add(hBoxButtons, 2, 2, 2, 1);
        hBoxButtons.alignmentProperty().set(Pos.CENTER_RIGHT);
        hBoxButtons.getChildren().setAll(btnKlarWhiskey, btnFlytFad);
        btnKlarWhiskey.setOnAction(event -> this.klarWhiskyAction());
        btnFlytFad.setOnAction(actionEvent -> this.flytFadAction());

        this.add(btnTilføjReol, 1, 2);
        btnTilføjReol.setOnAction(actionEvent -> this.tilføjReolAction());

        setKnapperAktive(false);
    }

    List<Fad> fade = lvwFad.getSelectionModel().getSelectedItems();

    private void flytFadAction() {
        if (controller.getLagre().isEmpty() || controller.getLagre().getFirst().getReoler().isEmpty()) controller.opretAlert(Alert.AlertType.ERROR, "Fejl", "Du mangler at oprette et lager eller tilføje reoler til lagret");
        else {
            if (fade != null) {
                FlytFadPåLagerDialog flytFadPåLagerDialog = new FlytFadPåLagerDialog(fade);
                flytFadPåLagerDialog.showAndWait();
                lvwHylde.getSelectionModel().clearSelection();
                lvwFad.getItems().clear();
            } else {
                FlytFadPåLagerDialog flytFadPåLagerDialog = new FlytFadPåLagerDialog(null);
                flytFadPåLagerDialog.showAndWait();
            }
        }
    }

    private void changeFad() {
        Fad fad = lvwFad.getSelectionModel().getSelectedItem();
        if (fad != null) {
            btnFlytFad.setText("Flyt fad");
        }
    }

    private void tilføjReolAction() {
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
        lvwLager.getItems().setAll(controller.getLagre());
    }

    private void setKnapperAktive(boolean bool) {
        btnTilføjReol.setDisable(!bool);
    }

    private void klarWhiskyAction() {
        KlarWhiskeyDialog klarWhiskeyDialog = new KlarWhiskeyDialog();
        klarWhiskeyDialog.showAndWait();
    }
}

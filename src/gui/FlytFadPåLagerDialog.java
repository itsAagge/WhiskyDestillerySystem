package gui;

import application.controller.Controller;
import application.model.Fad;
import application.model.Hylde;
import application.model.Lager;
import application.model.Reol;
import javafx.beans.value.ChangeListener;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.List;


public class FlytFadPåLagerDialog extends Stage {

    List<Fad> fade;
    ListView<Fad> lvwFade = new ListView();
    ComboBox<Lager> cBLager = new ComboBox<>();
    ComboBox<Reol> cBreol = new ComboBox<>();
    ComboBox<Hylde> cBhylde = new ComboBox<>();

    public FlytFadPåLagerDialog(List<Fad> fade) {
        this.initStyle(StageStyle.UTILITY);
        this.initModality(Modality.APPLICATION_MODAL);
        this.setResizable(false);
        this.fade = fade;
        this.setTitle("Flyt Fad hen på nyt lager");

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

        Label lblValgteFad = new Label("Valgte fad");
        pane.add(lblValgteFad, 0,0);
        pane.add(lvwFade, 0,1,1,7);
        if (!fade.isEmpty()) {
            lvwFade.getItems().setAll(fade);
        } else {
            lvwFade.getItems().setAll(Controller.getAlleFade());
        }

        Label lblLager = new Label("Vælg lager");
        pane.add(lblLager,1,0);
        pane.add(cBLager, 1,1);
        cBLager.getItems().setAll(Controller.getLagre());
        ChangeListener<Lager> lagerChangeListener = (observableValue, oldValue, newValue) -> this.changeLager();
        cBLager.getSelectionModel().selectedItemProperty().addListener(lagerChangeListener);

        Label lblReol = new Label("Vælg reol nr.");
        pane.add(lblReol, 1,2);
        pane.add(cBreol,1,3);
        ChangeListener<Reol> reolChangeListener = (observableValue, oldValue, newValue) -> this.changeReol();
        cBreol.getSelectionModel().selectedItemProperty().addListener(reolChangeListener);


        Label lblHylde = new Label("Vælg hylde nr.");
        pane.add(lblHylde, 1,4);
        pane.add(cBhylde, 1,5);

        Button btnPlacer = new Button("Placer");
        pane.add(btnPlacer, 1,6);
        pane.setValignment(btnPlacer, VPos.BOTTOM);
        pane.setHalignment(btnPlacer, HPos.RIGHT);
        btnPlacer.setOnAction(actionEvent -> this.placerAction());

    }

    private void placerAction() {
        Hylde hylde = cBhylde.getSelectionModel().getSelectedItem();
        List<Fad> valgtFade = lvwFade.getSelectionModel().getSelectedItems();

        if (hylde != null && !valgtFade.isEmpty()) {
            //Controller.flytFad(valgtFade, hylde);
            this.close();
        }

    }

    private void changeReol() {
        Reol reol = cBreol.getSelectionModel().getSelectedItem();

        if (reol != null) {
            cBhylde.getItems().setAll(Controller.getHyldeNumre(reol));
        }
    }

    private void changeLager() {
        Lager lager = cBLager.getSelectionModel().getSelectedItem();

        if (lager != null) {
            cBreol.getItems().setAll(Controller.getReolNumre(lager));
        }

    }




}

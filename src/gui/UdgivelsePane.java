package gui;

import application.controller.Controller;
import application.model.Udgivelse;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class UdgivelsePane extends GridPane {
    private ListView<Udgivelse> lvwUdgivelser = new ListView<>();
    private TextArea txaBeskrivelse = new TextArea();
    private Button btnPrintUdgivelse = new Button("Print beskrivelse");
    private Controller controller;

    public UdgivelsePane() {
        controller = Controller.getController();
        this.setPadding(new Insets(20));
        this.setHgap(20);
        this.setVgap(10);
        this.setGridLinesVisible(false);

        Label lblUdgivelser = new Label("Udgivelser");
        this.add(lblUdgivelser,0,0);
        this.add(lvwUdgivelser,0,1);
        lvwUdgivelser.getItems().setAll(controller.getUdgivelser());
        ChangeListener<Udgivelse> changeListenerUdgivelser = (observableValue, udgivelse, t1) -> this.changeUdgivelse();
        lvwUdgivelser.getSelectionModel().selectedItemProperty().addListener(changeListenerUdgivelser);
        lvwUdgivelser.setPrefWidth(250);

        Label lblUdgivelsesBeskrivelse = new Label("Udgivelses beskrivelse");
        this.add(lblUdgivelsesBeskrivelse, 1,0);
        txaBeskrivelse.setEditable(false);
        txaBeskrivelse.setWrapText(true);
        this.add(txaBeskrivelse, 1,1);

        HBox hBoxButtons = new HBox();
        hBoxButtons.setSpacing(20);
        Button btnUdgivFad = new Button("Udgiv fad");
        btnUdgivFad.setOnAction(actionEvent -> this.udgivFad());
        btnPrintUdgivelse.setOnAction(actionEvent -> this.printAction());
        Button btnUdgivFlasker = new Button("Udgiv Flasker");
        btnUdgivFlasker.setOnAction(actionEvent -> this.UdskrivFlaskerAction());
        hBoxButtons.getChildren().setAll(btnUdgivFad, btnUdgivFlasker, btnPrintUdgivelse);
        this.add(hBoxButtons,0,2,2,1);
        setKnapperAktive(false);
    }

    private void UdskrivFlaskerAction() {
        UdgivFlaskerDialog udgivFlaskerDialog = new UdgivFlaskerDialog();
        udgivFlaskerDialog.showAndWait();
        lvwUdgivelser.getItems().setAll(controller.getUdgivelser());
        lvwUdgivelser.getSelectionModel().clearSelection();
        setKnapperAktive(false);
    }

    private void changeUdgivelse() {
        Udgivelse udgivelse = lvwUdgivelser.getSelectionModel().getSelectedItem();
        if (udgivelse != null) {
            txaBeskrivelse.setText(udgivelse.getBeskrivelse());
            setKnapperAktive(true);
        }
    }

    private void printAction() {
        Udgivelse udgivelse = lvwUdgivelser.getSelectionModel().getSelectedItem();
        if(!IndstillingPane.erOutputStrategiValgt()) {
            controller.opretAlert(Alert.AlertType.ERROR, "Fejl", "Du mangler at vælge en output type i indstillingerne");
        } else {
            controller.udtrækBeskrivelse(IndstillingPane.getValgtOutputStrategi(), udgivelse);
        }
    }

    private void udgivFad() {
        UdgivFadDialog udgivFadDialog = new UdgivFadDialog();
        udgivFadDialog.showAndWait();
        lvwUdgivelser.getItems().setAll(controller.getUdgivelser());
        lvwUdgivelser.getSelectionModel().clearSelection();
        setKnapperAktive(false);
    }

    private void setKnapperAktive(boolean bool) {
        btnPrintUdgivelse.setDisable(!bool);
    }
}

package gui;

import application.controller.Controller;
import application.model.Korn;
import application.model.Maltbatch;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import java.util.List;

public class KornOgMaltbatchPane extends GridPane {

    private ListView<Korn> lvwKorn = new ListView<>();
    private ListView<Maltbatch> lvwMaltbatch = new ListView<>();
    private TextArea txaKornBeskrivelse = new TextArea();
    private TextArea txaMaltbatchBeskrivelse = new TextArea();
    private Button btnPrintBeskrivelse = new Button("Print beskrivelse af: ");
    private ComboBox<String> comboBoxVælgBeskrivelse = new ComboBox<>();
    private Controller controller;

    public KornOgMaltbatchPane() {
        controller = Controller.getController();
        this.setPadding(new Insets(20));
        this.setHgap(20);
        this.setVgap(10);
        this.setGridLinesVisible(false);

        Label lblAlleKorn = new Label("Registreret korn");
        this.add(lblAlleKorn, 0,0);
        this.add(lvwKorn, 0,1,1,3);
        lvwKorn.getItems().setAll(controller.getKorn());
        lvwKorn.setPrefWidth(400);
        ChangeListener<Korn> kornChangeListener = (observableValue, oldValue, newValue) -> this.changeKorn();
        lvwKorn.getSelectionModel().selectedItemProperty().addListener(kornChangeListener);

        Label lblKornBeskrivelse = new Label("Korn beskrivelse");
        this.add(lblKornBeskrivelse, 1,0);
        this.add(txaKornBeskrivelse, 1,1);
        txaKornBeskrivelse.setWrapText(true);

        Label lblMaltbatchBeskrivelse = new Label("Maltbatch beskrivelse");
        this.add(lblMaltbatchBeskrivelse, 1,2);
        this.add(txaMaltbatchBeskrivelse, 1,3);
        txaMaltbatchBeskrivelse.setWrapText(true);

        Label lblAlleMaltbatch = new Label("Alle Maltbatches");
        this.add(lblAlleMaltbatch, 2,0);
        this.add(lvwMaltbatch, 2,1,1,3);
        lvwMaltbatch.getItems().setAll(controller.getAlleMaltbatche());
        lvwMaltbatch.setPrefWidth(400);
        ChangeListener<Maltbatch> maltbatchChangeListener = (observableValue, oldValue, newValue) -> this.changeMaltbatch();
        lvwMaltbatch.getSelectionModel().selectedItemProperty().addListener(maltbatchChangeListener);

        Button btnRegistrerKorn = new Button("Registrer korn");
        this.add(btnRegistrerKorn, 0,4);
        btnRegistrerKorn.setOnAction(actionEvent -> this.registrerKornAction());

        HBox hBoxBeskrivelse = new HBox();
        hBoxBeskrivelse.setSpacing(10);
        hBoxBeskrivelse.getChildren().setAll(btnPrintBeskrivelse, comboBoxVælgBeskrivelse);
        btnPrintBeskrivelse.setOnAction(event -> this.printBeskrivelseAction());
        comboBoxVælgBeskrivelse.getItems().setAll(List.of("Korn", "Maltbatch"));
        comboBoxVælgBeskrivelse.setPrefWidth(100);
        this.add(hBoxBeskrivelse,1,4);
        setPrintBeskrivelseAktiv(false);

        Button btnRegistrerMaltchbatch = new Button("Registrer maltbatch");
        this.add(btnRegistrerMaltchbatch, 2,4);
        btnRegistrerMaltchbatch.setOnAction(actionEvent -> this.registrerMaltbatchAction());
    }

    private void changeMaltbatch() {
        Maltbatch maltbatch = lvwMaltbatch.getSelectionModel().getSelectedItem();
        if (maltbatch != null) {
            txaMaltbatchBeskrivelse.setText(maltbatch.getBeskrivelse());
        }
        setPrintBeskrivelseAktiv(true);
    }

    private void changeKorn() {
        Korn korn = lvwKorn.getSelectionModel().getSelectedItem();
        if (korn != null) {
            txaKornBeskrivelse.setText(korn.getBeskrivelse());
        }
        setPrintBeskrivelseAktiv(true);
    }

    private void registrerMaltbatchAction() {
        MaltbatchDialog maltbatchDialog = new MaltbatchDialog();
        maltbatchDialog.showAndWait();
        lvwMaltbatch.getItems().setAll(controller.getAlleMaltbatche());
    }

    private void registrerKornAction() {
        KornDialog kornDialog = new KornDialog();
        kornDialog.showAndWait();
        lvwKorn.getItems().setAll(controller.getKorn());
    }

    private void printBeskrivelseAction() {
        if (comboBoxVælgBeskrivelse.getSelectionModel().getSelectedItem() == null) {
            controller.opretAlert(Alert.AlertType.ERROR, "Fejl", "Du skal vælge, hvad du vil printe en beskrivelse af, før du kan printe den");
        } else if (comboBoxVælgBeskrivelse.getSelectionModel().getSelectedItem().equals("Korn") && lvwKorn.getSelectionModel().getSelectedItem() == null) {
            controller.opretAlert(Alert.AlertType.ERROR, "Fejl", "Du mangler at vælge den omgang korn, som du vil printe en beskrivelse af");
        } else if (comboBoxVælgBeskrivelse.getSelectionModel().getSelectedItem().equals("Maltbatch") && lvwMaltbatch.getSelectionModel().getSelectedItem() == null) {
            controller.opretAlert(Alert.AlertType.ERROR, "Fejl", "Du mangler at vælge det maltbatch, som du vil printe en beskrivelse af");
        } else {
            if (comboBoxVælgBeskrivelse.getSelectionModel().getSelectedItem().equals("Korn")) {
                Korn korn = lvwKorn.getSelectionModel().getSelectedItem();
                if(!IndstillingPane.erOutputStrategiValgt()) {
                    controller.opretAlert(Alert.AlertType.ERROR, "Fejl", "Du mangler at vælge en output type i indstillingerne");
                } else {
                    controller.udtrækBeskrivelse(IndstillingPane.getValgtOutputStrategi(), korn);
                    controller.opretAlert(Alert.AlertType.INFORMATION, "Succes", "Beskrivelse succesfuldt udtrukket");
                    lvwKorn.getSelectionModel().clearSelection();
                    txaKornBeskrivelse.clear();
                }
            } else {
                Maltbatch maltbatch = lvwMaltbatch.getSelectionModel().getSelectedItem();
                if(!IndstillingPane.erOutputStrategiValgt()) {
                    controller.opretAlert(Alert.AlertType.ERROR, "Fejl", "Du mangler at vælge en output type i indstillingerne");
                } else {
                    controller.udtrækBeskrivelse(IndstillingPane.getValgtOutputStrategi(), maltbatch);
                    controller.opretAlert(Alert.AlertType.INFORMATION, "Succes", "Beskrivelse succesfuldt udtrukket");
                    lvwMaltbatch.getSelectionModel().clearSelection();
                    txaMaltbatchBeskrivelse.clear();
                }
            }
        }
    }

    private void setPrintBeskrivelseAktiv(boolean bool) {
        btnPrintBeskrivelse.setDisable(!bool);
        comboBoxVælgBeskrivelse.setDisable(!bool);
    }
}

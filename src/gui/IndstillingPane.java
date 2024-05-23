package gui;

import application.controller.Controller;
import application.model.output.Logger;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class IndstillingPane extends GridPane {
    private ComboBox<String> comboBoxOutputStrategi = new ComboBox<>();
    private ComboBox<String> comboBoxMedarbejdere = new ComboBox<>();
    private static Logger valgtOutputStrategi;
    private static String valgtMedarbejder = "";
    private Controller controller;

    public IndstillingPane() {
        controller = Controller.getController();
        this.setPadding(new Insets(20));
        this.setHgap(20);
        this.setVgap(10);
        this.setGridLinesVisible(false);

        Label lblOutputStrategi = new Label("Vælg output af beskrivelser");
        this.add(lblOutputStrategi, 0, 0);
        this.add(comboBoxOutputStrategi, 0, 1);
        comboBoxOutputStrategi.getItems().setAll(controller.getOutputStrategies());
        ChangeListener<String> changeListenerOutputStrategi = (observableValue, s, t1) -> this.changeOutputStrategi();
        comboBoxOutputStrategi.getSelectionModel().selectedItemProperty().addListener(changeListenerOutputStrategi);

        Label lblMedarbejder = new Label("Vælg medarbejder");
        this.add(lblMedarbejder, 0, 3);
        this.add(comboBoxMedarbejdere, 0, 4);
        comboBoxMedarbejdere.getItems().setAll(controller.getMedarbejdere());
        ChangeListener<String> changeListenerMedarbejder = (observableValue, s, t1) -> this.changeMedarbejder();
        comboBoxMedarbejdere.getSelectionModel().selectedItemProperty().addListener(changeListenerMedarbejder);
    }

    private void changeOutputStrategi() {
        Logger logger = null;
        logger = controller.getLoggerStrategy(comboBoxOutputStrategi.getSelectionModel().getSelectedItem());
        valgtOutputStrategi = logger;
    }

    private void changeMedarbejder() {
        valgtMedarbejder = comboBoxMedarbejdere.getSelectionModel().getSelectedItem();
    }

    public static Logger getValgtOutputStrategi() {
        return valgtOutputStrategi;
    }

    public static String getValgtMedarbejder() {
        return valgtMedarbejder;
    }

    public static boolean erOutputStrategiValgt() {
        return valgtOutputStrategi != null;
    }

    public static boolean erMedarbejderValgt() {
        return !valgtMedarbejder.isEmpty();
    }
}

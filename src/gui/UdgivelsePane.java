package gui;

import application.controller.Controller;
import application.model.Udgivelse;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

public class UdgivelsePane extends GridPane {
    ListView<Udgivelse> lvwUdgivelser = new ListView<>();
    TextArea txaBeskrivelse = new TextArea();

    public UdgivelsePane() {
        this.setPadding(new Insets(20));
        this.setHgap(20);
        this.setVgap(10);
        this.setGridLinesVisible(false);

        Label lblUdgivelser = new Label("Udgivelser");
        this.add(lblUdgivelser,0,0);
        this.add(lvwUdgivelser,0,1);
        lvwUdgivelser.getItems().setAll(Controller.getUdgivelser());
        lvwUdgivelser.setPrefWidth(250);

        Label lblUdgivelsesBeskrivelse = new Label("Udgivelses beskrivelse");
        this.add(lblUdgivelsesBeskrivelse, 1,0);
        txaBeskrivelse.setEditable(false);
        txaBeskrivelse.setWrapText(true);
        this.add(txaBeskrivelse, 1,1);

        Button btnOpret = new Button("Opret");
        this.add(btnOpret, 0,2);
        btnOpret.setOnAction(actionEvent -> this.opretAction());

        Button btnPrint = new Button("print");
        this.add(btnPrint, 1,2);
        this.setHalignment(btnPrint, HPos.RIGHT);
        btnPrint.setOnAction(actionEvent -> this.printAction());


    }

    private void printAction() {
        //Todo print file metode
    }

    private void opretAction() {
        UdgivelseDialog udgivelseDialog = new UdgivelseDialog();
        udgivelseDialog.showAndWait();
        lvwUdgivelser.getItems().setAll(Controller.getUdgivelser());
    }
}

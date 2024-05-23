package gui;

import application.controller.Controller;
import application.model.Påfyldning;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.time.LocalDate;

public class KlarWhiskeyDialog extends Stage {
    private Controller controller;

    public KlarWhiskeyDialog() {
        controller = Controller.getController();
        this.initStyle(StageStyle.UTILITY);
        this.initModality(Modality.APPLICATION_MODAL);
        this.setResizable(false);
        this.setMinWidth(400);
        this.setTitle("Whiskey klar til udgivelse");

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

        Label lblWhiskeyKlar = new Label("Whiskey klar til udgiveslse");
        pane.add(lblWhiskeyKlar,0,0);

        ListView<Påfyldning> lvwKlarWhiskey = new ListView<>();
        pane.add(lvwKlarWhiskey,0,1);
        lvwKlarWhiskey.getItems().setAll(controller.getMindst3ÅrsIkkeTommePåfyldninger(LocalDate.now()));
        lvwKlarWhiskey.setPrefWidth(350);

        Button btnClose = new Button("Luk");
        pane.add(btnClose,0,2);
        pane.setHalignment(btnClose, HPos.RIGHT);
        btnClose.setOnAction(event -> this.closeAction());
    }
    private void closeAction() {
        this.close();
    }
}
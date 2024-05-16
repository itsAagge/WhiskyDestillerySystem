package gui;

import application.controller.Controller;
import application.model.Fad;
import application.model.Påfyldning;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;



public class FlytPåfyldningDialog extends Stage {

    Påfyldning påfyldning;

    ListView<Fad> lvwFade = new ListView<>();




    public FlytPåfyldningDialog(Påfyldning påfyldning) {
        this.initStyle(StageStyle.UTILITY);
        this.initModality(Modality.APPLICATION_MODAL);
        this.setResizable(false);
        this.påfyldning = påfyldning;
        this.setTitle("Flyt påfyldning mellem fad");

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

        Label lblLedigeFade = new Label("Ledige fad");
        pane.add(lblLedigeFade,0,0);
        pane.add(lvwFade,0,1);
        lvwFade.setPrefWidth(250);
        lvwFade.getItems().setAll(Controller.getLedigeFad());

        Button btnGem = new Button("Gem");
        pane.add(btnGem,0,2);
        btnGem.setOnAction(actionEvent -> this.gemAction());
    }

    private void gemAction() {
        Fad fad = lvwFade.getSelectionModel().getSelectedItem();
        if (fad != null) {
            fad.addPåfyldning(påfyldning);
        }
        this.close();

    }


}

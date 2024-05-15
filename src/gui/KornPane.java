package gui;

import application.controller.Controller;
import application.model.Korn;
import application.model.Maltbatch;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class KornPane extends GridPane {

    private ListView<Korn> lvwKorn = new ListView<>();
    private ListView<Maltbatch> lvwMaltbatch = new ListView<>();
    private TextArea txaKornBeskrivelse = new TextArea();
    private TextArea txaMaltbatchBeskrivelse = new TextArea();

    public KornPane() {

        this.setPadding(new Insets(20));
        this.setHgap(20);
        this.setVgap(10);
        this.setGridLinesVisible(false);

        Label lblAlleKorn = new Label("Registreret korn");
        this.add(lblAlleKorn, 0,0);
        this.add(lvwKorn, 0,1,1,3);
        lvwKorn.getItems().setAll(Controller.getKorn());
        lvwKorn.setPrefWidth(400);

        Label lblKornBeskrivelse = new Label("Korn beskrivelse");
        this.add(lblKornBeskrivelse, 1,0);
        this.add(txaKornBeskrivelse, 1,1);

        Label lblMaltbatchBeskrivelse = new Label("Maltbatch beskrivelse");
        this.add(lblMaltbatchBeskrivelse, 1,2);
        this.add(txaMaltbatchBeskrivelse, 1,3);

        Label lblAlleMaltbatch = new Label("Alle Maltbatches");
        this.add(lblAlleMaltbatch, 2,0);
        this.add(lvwMaltbatch, 2,1,1,3);
        lvwMaltbatch.getItems().setAll(Controller.getAlleMaltbatche());
        lvwMaltbatch.setPrefWidth(400);

        Button btnRegistrerKorn = new Button("Registrer korn");
        this.add(btnRegistrerKorn, 0,4);
        btnRegistrerKorn.setOnAction(actionEvent -> this.registrerKornAction());

        Button btnRegistrerMaltchbatch = new Button("Registrer maltbatch");
        this.add(btnRegistrerMaltchbatch, 2,4);
        btnRegistrerMaltchbatch.setOnAction(actionEvent -> this.registrerMaltbatchAction());

    }

    private void registrerMaltbatchAction() {
        MaltbatchDialog maltbatchDialog = new MaltbatchDialog();
        maltbatchDialog.showAndWait();
        lvwMaltbatch.getItems().setAll(Controller.getAlleMaltbatche());

    }

    private void registrerKornAction() {
        KornDialog kornDialog = new KornDialog();
        kornDialog.showAndWait();
        lvwKorn.getItems().setAll(Controller.getKorn());
    }


}

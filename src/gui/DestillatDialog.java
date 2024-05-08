package gui;

import application.model.Destillat;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class DestillatDialog extends Stage {
    private Destillat destillat;
    private TextField txfMængde = new TextField();
    private TextField txfAlkoholProcent = new TextField();
    private TextField txfMedarbejder = new TextField();

    public DestillatDialog(Destillat destillat) {
        this.initStyle(StageStyle.UTILITY);
        this.initModality(Modality.APPLICATION_MODAL);
        this.setResizable(false);
        this.destillat = destillat;
        if (this.destillat == null) {
            this.setTitle("Opret destillat");
        } else {
            this.setTitle("Rediger destillat");
        }

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


    }
}

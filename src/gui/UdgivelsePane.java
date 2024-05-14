package gui;

import application.model.Udgivelse;
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
        //lvwUdgivelser.getItems().setAll(Storage.getUdgivelser());
    }
}

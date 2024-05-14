package gui;

import application.model.Udgivelse;
import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class UdgivelsePane extends GridPane {
    ListView<Udgivelse> lvwUdgivelser = new ListView<>();
    ComboBox<String> comboBoxFadEllerFlasker = new ComboBox<>();
    DatePicker datePicker = new DatePicker();
    TextField txfAlkoholProcent = new TextField();
    TextField txfPrisPerFlaske = new TextField();
    TextField txfVandMængde = new TextField();
    TextField txfFlaskeStørrelse = new TextField();
    ComboBox<String> comboBoxMedarbejdere = new ComboBox<>();

    public UdgivelsePane() {
        this.setPadding(new Insets(20));
        this.setHgap(20);
        this.setVgap(10);
        this.setGridLinesVisible(false);
    }
}

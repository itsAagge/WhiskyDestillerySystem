package gui;

import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class UdgivelseDialog extends Stage {
    ComboBox<String> comboBoxFadEllerFlasker = new ComboBox<>();
    DatePicker datePicker = new DatePicker();
    TextField txfAlkoholProcent = new TextField();
    TextField txfPrisPerFlaske = new TextField();
    TextField txfVandMængde = new TextField();
    TextField txfFlaskeStørrelse = new TextField();
    ComboBox<String> comboBoxMedarbejdere = new ComboBox<>();

    public UdgivelseDialog() {
    }
}

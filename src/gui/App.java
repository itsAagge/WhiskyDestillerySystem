package gui;

import application.controller.Controller;
import application.model.Korn;
import application.model.Maltbatch;
import javafx.application.Application;

import java.time.LocalDate;

public class App {
    public static void main(String[] args) {
        initContent();
        Application.launch(MainWindow.class);
    }

    private static void initContent() {
        Controller.opretFad("Spanien", "Cherry", 30, "Eg", 9.5, "El egetræsfadfirma");
        Controller.opretFad("Spanien", "Cherry", 250, "Eg", 1.0, "El egetræsfadfirma");
        Controller.opretFad("Spanien", "Bourbon", 30, "Eg", 9.5, "El fakefadefirma");
        Controller.opretFad("Spanien", "Bourbon", 250, "Eg", 5, "El egetræsfadfirma");
        Controller.opretFad("Spanien", "Bourbon", 100, "Eg", 9.5, "El fakefadefirma");

        Korn hvede = Controller.opretKorn("Mark 1", "Hvede", LocalDate.of(2019,8,20), 200);
        Controller.opretKorn("Mark 2", "Hvede", LocalDate.of(2019,8,23), 200);

        Maltbatch maltbatch1 = Controller.opretMaltbatch(100, LocalDate.of(2019,12,15), LocalDate.of(2019,12,17), "Gær", hvede);
        Maltbatch maltbatch2 = Controller.opretMaltbatch(100, LocalDate.of(2019,12,15), LocalDate.of(2019,12,19), "Special gær", hvede);

        Controller.opretDestillat("NM77P", 50, 70, "Snævar Njáll Albertsson", null, null, LocalDate.of(2020,1,17), maltbatch1);
        Controller.opretDestillat("NM78P",50, 70, "Ingus Brikmanis", null, "Kommentar2", LocalDate.of(2020,1,19), maltbatch1);
        Controller.opretDestillat("NM79P",50, 70, "Snævar Njáll Albertsson", "Tørv", null, LocalDate.of(2020,1,20), maltbatch2);
        Controller.opretDestillat("NM80P",50, 70, "Snævar Njáll Albertsson", null, null, LocalDate.of(2020,1,22), maltbatch2);
        Controller.opretDestillat("NM81P",50, 70, "Ingus Brikmanis", "Tørv", "Kommentar1", LocalDate.of(2020,1,25), maltbatch1);
    }
}

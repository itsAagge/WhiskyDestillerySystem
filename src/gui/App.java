package gui;

import application.controller.Controller;
import application.model.*;
import com.sun.security.auth.NTNumericCredential;
import javafx.application.Application;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class App {
    public static void main(String[] args) {
        initContent();
        Application.launch(MainWindow.class);
    }

    private static void initContent() {
        Fad fad1 = Controller.opretFad("Spanien", "Cherry", 30, "Eg", 9.5, "El egetræsfadfirma");
        Fad fad2 = Controller.opretFad("Spanien", "Cherry", 250, "Eg", 1.0, "El egetræsfadfirma");
        Fad fad3 = Controller.opretFad("Spanien", "Bourbon", 30, "Eg", 9.5, "El fakefadefirma");
        Fad fad4 = Controller.opretFad("Spanien", "Bourbon", 250, "Eg", 5, "El egetræsfadfirma");
        Fad fad5 = Controller.opretFad("Spanien", "Bourbon", 100, "Eg", 9.5, "El fakefadefirma");

        Korn hvede = Controller.opretKorn("Mark 1", "Hvede", LocalDate.of(2019, 8, 20), 200);
        Controller.opretKorn("Mark 2", "Hvede", LocalDate.of(2019, 8, 23), 200);

        Maltbatch maltbatch1 = Controller.opretMaltbatch(100, LocalDate.of(2019, 12, 15), LocalDate.of(2019, 12, 17), "Gær", hvede);
        Maltbatch maltbatch2 = Controller.opretMaltbatch(100, LocalDate.of(2019, 12, 15), LocalDate.of(2019, 12, 19), "Special gær", hvede);

        Destillat destillat1 = Controller.opretDestillat("NM77P", 500, 70, "Snævar Njáll Albertsson", null, null, LocalDate.of(2020, 1, 17), maltbatch1);
        Destillat destillat2 = Controller.opretDestillat("NM78P", 500, 70, "Ingus Brikmanis", null, "Kommentar2", LocalDate.of(2020, 1, 19), maltbatch1);
        Destillat destillat3 = Controller.opretDestillat("NM79P", 500, 70, "Snævar Njáll Albertsson", "Tørv", null, LocalDate.of(2024, 5, 20), maltbatch2);
        Destillat destillat4 = Controller.opretDestillat("NM80P", 500, 70, "Snævar Njáll Albertsson", null, null, LocalDate.of(2024, 5, 19), maltbatch2);
        Destillat destillat5 = Controller.opretDestillat("NM81P", 500, 70, "Ingus Brikmanis", "Tørv", "Kommentar1", LocalDate.of(2024, 5, 18), maltbatch1);

        Destillat[] destillatArray1 = {destillat1, destillat2};
        double[] mængdeArray1 = {10, 15};
        Destillat[] destillatArray2 = {destillat3, destillat4};
        double[] mængdeArray2 = {100, 150};
        Destillat[] destillatArray3 = {destillat5};
        double[] mængdeArray3 = {20};
        Controller.opretPåfyldninger(new ArrayList<>(List.of(fad1)), LocalDate.of(2019, 10, 25), LocalDate.of(2022, 10, 27), destillatArray1, mængdeArray1);
        Controller.opretPåfyldninger(new ArrayList<>(List.of(fad2)), LocalDate.of(2021, 7, 17), LocalDate.of(2024, 7, 17), destillatArray2, mængdeArray2);
        Controller.opretPåfyldninger(new ArrayList<>(List.of(fad3)), LocalDate.of(2022, 7, 17), null, destillatArray3, mængdeArray3);
    }
    }

package application.controller;

import application.model.*;
import javafx.scene.control.Alert;
import javafx.stage.Modality;
import storage.Storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Controller {

    public static Destillat opretDestillat(String spiritBatchNr, double mængdeL, double alkoholprocent, String medarbejder, String rygemateriale, String kommentar, LocalDate destilleringsdato, Maltbatch maltbatch) {
        Destillat destillat = new Destillat(spiritBatchNr, mængdeL, alkoholprocent, medarbejder, rygemateriale, kommentar, destilleringsdato, maltbatch);
        Storage.tilføjDestillat(destillat);
        return destillat;
    }

    public static Fad opretFad(String fraLand, String tidligereIndhold, int størrelseL, String træType, double alderAfTidligereIndhold, String leverandør) {
        Fad fad = new Fad(fraLand, tidligereIndhold, størrelseL, træType, alderAfTidligereIndhold, leverandør);
        Storage.tilføjFad(fad);
        return fad;

    }

    public static Korn opretKorn(String markNavn, String sort, LocalDate høstDato, int mængdeKg) {
        Korn korn = new Korn(markNavn, sort, høstDato, mængdeKg);
        Storage.tilføjKorn(korn);
        return korn;
    }

    public static Maltbatch opretMaltbatch(double mængdeL, LocalDate gæringStart, LocalDate gæringSlut, String gærType, Korn korn) {
        Maltbatch maltbatch = new Maltbatch(mængdeL, gæringStart, gæringSlut, gærType, korn);
        Storage.tilføjMaltBatch(maltbatch);
        return maltbatch;
    }

    private static Påfyldning opretPåfyldning(LocalDate påfyldningsDato, LocalDate færdigDato, Fad førsteFad, ArrayList<Destillat> destillater, ArrayList<Double> mængder) {
        Påfyldning påfyldning = new Påfyldning(påfyldningsDato, færdigDato, førsteFad);
        påfyldning.tilføjDestillatMedMængde(destillater, mængder);
        førsteFad.addPåfyldning(påfyldning);
        førsteFad.setFyldt(true);
        return påfyldning;
    }

    public static List<Påfyldning> opretPåfyldninger(List<Fad> fade, LocalDate påfyldningsDato, LocalDate færdigDato, ArrayList<Destillat> destillater, ArrayList<Double> mængder) {
        ArrayList<Påfyldning> oprettedePåfyldninger = new ArrayList<>();

        //Beregner den totale mængde af destillater og tjekker, om der er nok tilbage af destillaterne til at oprette disse påfyldninger
        double mængdeIAlt = 0;
        boolean derErNokDestillatTilbage = true;
        for (int i = 0; i < destillater.size(); i++) {
            mængdeIAlt += mængder.get(i);
            if (destillater.get(i).mængdeTilbage() < mængder.get(i)) {  //Tjekker om der er nok destillat tilbage til påfyldningerne
                derErNokDestillatTilbage = false;
            }
        }

        //Beregner den totale fadplads og tjekker, om alle fadene er tomme og aktive
        boolean alleFadeErAktive = true;
        boolean alleFadeErTomme = true;
        double fadPladsTotalt = 0.0;
        for (Fad fad : fade) {
            if(!fad.erAktiv()) alleFadeErAktive = false;
            if(fad.erFyldt()) alleFadeErTomme = false;
            fadPladsTotalt += fad.getStørrelseL();
        }

        //Kaster fejlmeldinger, hvis noget er galt, og opretter påfyldningerne, hvis intet er galt
        if(mængdeIAlt > fadPladsTotalt) throw new IllegalArgumentException("Der er ikke nok plads i fadene til denne mængde destillater");
        else if(!derErNokDestillatTilbage) throw new IllegalArgumentException("Der er ikke nok destillat tilbage");
        else if(!alleFadeErAktive) throw new IllegalArgumentException("Et eller flere valgte fade er deaktiveret, og kan derfor ikke bruges");
        else if(!alleFadeErTomme) throw new IllegalArgumentException("Et eller flere fade er allerede fyldte");
        else {
            for (Fad fad : fade) {
                double størrelsesforhold = fad.getStørrelseL() / fadPladsTotalt; //Beregner fadets procentvise størrelse af alle fadene
                ArrayList<Double> mængderIDetteFad = new ArrayList<>();
                for (int i = 0; i < mængder.size(); i++) {
                    mængderIDetteFad.add(mængder.get(i) * størrelsesforhold);
                }
                Påfyldning påfyldning = opretPåfyldning(påfyldningsDato, færdigDato, fad, destillater, mængderIDetteFad);
                oprettedePåfyldninger.add(påfyldning);
            }
        }

        return oprettedePåfyldninger;
    }

    public static Alert opretAlert(Alert.AlertType alertType, String title, String contentText) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(contentText);
        alert.initModality(Modality.APPLICATION_MODAL);
        return alert;
    }

    public static List<Fad> getAlleFade() {
        return Storage.getFade();
    }

    public static ArrayList<Fad> getLedigeFad() {
        ArrayList<Fad> ledigeFade = new ArrayList<>();
        for (Fad fad : Storage.getFade()) {
            if (!fad.erFyldt() && fad.erAktiv()) {
                ledigeFade.add(fad);
            }
        }
        return ledigeFade;
    }

    public static List<Maltbatch> getAlleMaltbatche() { return Storage.getMaltbatche(); }

    public static List<Destillat> getAlleDestillater() {
        return Storage.getDestillater();
    }

    public static List<Destillat> getUgensDestillater() {
        ArrayList<Destillat> ugensDestillater = new ArrayList<>();
        for (Destillat destillat : Storage.getDestillater()) {
            if (destillat.getDestilleringsdato().isAfter(LocalDate.now().minusWeeks(1))) {
                ugensDestillater.add(destillat);
            }
        }
        return ugensDestillater;
    }

    public static void fjernFad(Fad fad) {
        Storage.fjernFad(fad);
    }

    public static void fjernDestillat(Destillat destillat) {
        Storage.fjernDestilat(destillat);
    }

    public static List<String> getMedarbejdere() {
        List<String> medarbejdere = new ArrayList<>();
        File file = new File("resources/medarbejdere.txt");
        try {
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                medarbejdere.add(scanner.nextLine());
            }

        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return medarbejdere;
    }

    //TODO oprette flere påfyldninger til flere fad samtidig

}

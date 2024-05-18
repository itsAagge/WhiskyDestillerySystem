package application.controller;

import application.model.*;
import application.model.output.FilLogger;
import application.model.output.Logger;
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

    //Todo: Finde ud af, om opret og opdater burde være 1 eller 2 metoder
    public static Destillat opdaterDestillat(Destillat destillat, String spiritBatchNr, double mængdeL, double alkoholprocent, String medarbejder, String rygemateriale, String kommentar, LocalDate destilleringsdato, Maltbatch maltbatch) {
        if(destillat == null || spiritBatchNr == null || mængdeL == 0.0 || alkoholprocent == 0.0 || medarbejder == null || destilleringsdato == null || maltbatch == null) throw new IllegalArgumentException("Information mangler eller er null. Kun String rygemateriale og String kommentar må være null.");
        else {
            destillat.setSpiritBatchNr(spiritBatchNr);
            destillat.setMængdeL(mængdeL);
            destillat.setAlkoholprocent(alkoholprocent);
            destillat.setDestilleringsdato(destilleringsdato);
            destillat.setMedarbejder(medarbejder);
            destillat.setRygemateriale(rygemateriale);
            destillat.setKommentar(kommentar);
            destillat.setMaltbatch(maltbatch);
            return destillat;
        }
    }

    public static Fad opretFad(String fraLand, String tidligereIndhold, int størrelseL, String træType, double alderAfTidligereIndhold, String leverandør) {
        Fad fad = new Fad(fraLand, tidligereIndhold, størrelseL, træType, alderAfTidligereIndhold, leverandør);
        Storage.tilføjFad(fad);
        return fad;
    }

    //Todo: Finde ud af, om opret og opdater burde være 1 eller 2 metoder
    public static Fad opdaterFad(Fad fad, String fraLand, String tidligereIndhold, int størrelseL, String træType, double alderAfTidligereIndhold, String leverandør) {
        if(fad == null || fraLand == null || størrelseL == 0 || træType == null || leverandør == null) throw new IllegalArgumentException("Information mangler eller er null. Kun String tidligereIndhold og double alderAfTidligereIndhold må være null/0.0");
        else {
            fad.setFraLand(fraLand);
            fad.setLeverandør(leverandør);
            fad.setStørrelseL(størrelseL);
            fad.setTræType(træType);
            fad.setTidligereIndhold(tidligereIndhold);
            fad.setAlderAfTidligereIndhold(alderAfTidligereIndhold);
            return fad;
        }
    }

    public static Korn opretKorn(String markNavn, String sort, LocalDate høstDato, double mængdeKg) {
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

    public static Udgivelse opretUdgivelse(double unitStørrelse, double prisPerUnit, boolean erFad, LocalDate udgivelsesDato, double alkoholProcent, double vandMængdeL, String medarbejder, List<Påfyldning> påfyldninger, List<Double> mængder) {
        //Beregner den totale mængde af påfyldninger og tjekker, om der er nok tilbage af påfyldningerne til at oprette denne udgivelse
        boolean derErNokPåfyldningTilbage = true;
        for (int i = 0; i < påfyldninger.size(); i++) {
            if (påfyldninger.get(i).mængdeTilbage() < mængder.get(i)) {  //Tjekker om der er nok destillat tilbage til påfyldningerne
                derErNokPåfyldningTilbage = false;
            }
        }
        if(!derErNokPåfyldningTilbage) throw new IllegalArgumentException("Der er ikke nok påfyldning tilbage til dette");
        else {
            Udgivelse udgivelse = new Udgivelse(unitStørrelse, prisPerUnit, erFad, udgivelsesDato, alkoholProcent, vandMængdeL, medarbejder, påfyldninger, mængder);
            Storage.tilføjUdgivelse(udgivelse);
            return udgivelse;
        }
    }

    public static Lager opretLager(String adresse, String kvadratmeter) {
        Lager lager = new Lager(adresse, kvadratmeter);
        Storage.tilføjLager(lager);
        return lager;
    }

    public static Alert opretAlert(Alert.AlertType alertType, String title, String contentText) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(contentText);
        alert.initModality(Modality.APPLICATION_MODAL);
        if (alertType.equals(Alert.AlertType.INFORMATION)) {
            alert.getDialogPane().setPrefWidth(500);
        }
        alert.setResizable(true);
        alert.show();
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
    public static List<Udgivelse> getUdgivelser() {
        return Storage.getUdgivelser();
    }

    public static List<Korn> getKorn() {
        return Storage.getKornList();
    }
    public static List<Lager> getLagre() {
        return Storage.getLagre();
    }

    public static ArrayList<Reol> getReolNumre(Lager lager) {
        ArrayList<Reol> reoler = new ArrayList<>();
        for (int i = 0; i < lager.getReoler().size(); i++) {
            reoler.add(lager.getReoler().get(i));
        }
        return reoler;
    }

    public static ArrayList<Hylde> getHyldeNumre(Reol reol) {
        ArrayList<Hylde> hylder = new ArrayList<>();
        for (int i = 0; i < reol.getHylder().size(); i++) {
            hylder.add(reol.getHylder().get(i));
        }
        return hylder;
    }

    public static void fjernFad(Fad fad) {
        Storage.fjernFad(fad);
    }

    public static void fjernDestillat(Destillat destillat) {
        Storage.fjernDestilat(destillat);
    }

    public static void flytFad(List<Fad> fade, Hylde hylde) {
        if (hylde != null && !hylde.erLedigPlads(fade.size())) throw new IllegalArgumentException("Der er ikke nok plads på denne hylde");
        else {
            for (Fad fad : fade) {
                if (fad.getHylde() != null) {
                    fad.getHylde().removeFad(fad);
                }
                fad.setHylde(hylde);
                if (hylde != null) {
                    hylde.addFad(fad);
                }
            }
        }
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

    public static ArrayList<Påfyldning> getPåfyldninger() {
        ArrayList<Påfyldning> påfyldninger = new ArrayList<>();
        for (Fad fad : Controller.getAlleFade()) {
            if(fad.erFyldt()) {
                påfyldninger.add(fad.getPåfyldninger().getLast());
            }
        }
        return påfyldninger;
    }

    public static ArrayList<Påfyldning> getAlleIkkeTommePåfyldninger() {
        ArrayList<Påfyldning> allePåfyldninger = getPåfyldninger();
        ArrayList<Påfyldning> ikkeUdgivedePåfyldninger = new ArrayList<>();
        for (Påfyldning påfyldning : allePåfyldninger) {
            if (påfyldning.mængdeTilbage() != 0) {
                ikkeUdgivedePåfyldninger.add(påfyldning);
            }
        }
        return ikkeUdgivedePåfyldninger;
    }

    public static FilLogger getFileLoggerStrategy() {
        return new FilLogger();
    }

    public static void udtrækBeskrivelse(Logger logger, Logable object) {
        logger.log(object);
    }

    public static ArrayList<String> getOutputStrategies() {
        ArrayList<String> foundStrategies = new ArrayList<>();
        File file = new File("src/application/model/output/");
        String[] directory = file.list();
        for (String s : directory) {
            if (!s.equals("Logger.java")) {
                foundStrategies.add(s);
            }
        }
        for (int i = 0; i < foundStrategies.size(); i++) {
            foundStrategies.set(i, foundStrategies.get(i).substring(0,foundStrategies.get(i).length()-11));
        }
        return foundStrategies;
    }

    public static Logger getLoggerStrategy(String LoggerType) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        String className = "application.model.output." + LoggerType + "Logger";
        return (Logger) Class.forName(className).newInstance();
    }
}

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

    private Storage storage;
    private static Controller controller;

    private Controller() {
        storage = new Storage();
    }

    public static Controller getController() {
        if (controller == null) {
            controller = new Controller();
        }
        return controller;
    }

    public static Controller getTestController() {
        return new Controller();
    }

    public Destillat opretDestillat(String spiritBatchNr, double mængdeL, double alkoholprocent, String medarbejder, String rygemateriale, String kommentar, LocalDate destilleringsdato, Maltbatch maltbatch) {
        Destillat destillat = new Destillat(spiritBatchNr, mængdeL, alkoholprocent, medarbejder, rygemateriale, kommentar, destilleringsdato, maltbatch);
        storage.tilføjDestillat(destillat);
        return destillat;
    }

    //Todo: Finde ud af, om opret og opdater burde være 1 eller 2 metoder
    public Destillat opdaterDestillat(Destillat destillat, String spiritBatchNr, double mængdeL, double alkoholprocent, String medarbejder, String rygemateriale, String kommentar, LocalDate destilleringsdato, Maltbatch maltbatch) {
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

    public Fad opretFad(String fraLand, String tidligereIndhold, int størrelseL, String træType, double alderAfTidligereIndhold, String leverandør) {
        Fad fad = new Fad(fraLand, tidligereIndhold, størrelseL, træType, alderAfTidligereIndhold, leverandør);
        storage.tilføjFad(fad);
        return fad;
    }

    //Todo: Finde ud af, om opret og opdater burde være 1 eller 2 metoder
    public Fad opdaterFad(Fad fad, String fraLand, String tidligereIndhold, int størrelseL, String træType, double alderAfTidligereIndhold, String leverandør) {
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

    public Korn opretKorn(String markNavn, String sort, LocalDate høstDato, double mængdeKg) {
        Korn korn = new Korn(markNavn, sort, høstDato, mængdeKg);
        storage.tilføjKorn(korn);
        return korn;
    }

    public Maltbatch opretMaltbatch(double mængdeL, LocalDate gæringStart, LocalDate gæringSlut, String gærType, Korn korn) {
        Maltbatch maltbatch = new Maltbatch(mængdeL, gæringStart, gæringSlut, gærType, korn);
        storage.tilføjMaltBatch(maltbatch);
        return maltbatch;
    }

    public Påfyldning opretPåfyldning(LocalDate påfyldningsDato, LocalDate færdigDato, Fad førsteFad, ArrayList<Destillat> destillater, ArrayList<Double> mængder) {
        Påfyldning påfyldning = new Påfyldning(påfyldningsDato, færdigDato, førsteFad);
        påfyldning.tilføjDestillatMedMængde(destillater, mængder);
        førsteFad.addPåfyldning(påfyldning);
        return påfyldning;
    }

    public List<Påfyldning> opretPåfyldninger(List<Fad> fade, LocalDate påfyldningsDato, LocalDate færdigDato, ArrayList<Destillat> destillater, ArrayList<Double> mængder) {
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

    public Udgivelse opretUdgivelse(double unitStørrelse, double prisPerUnit, boolean erFad, LocalDate udgivelsesDato, double alkoholProcent, double vandMængdeL, String medarbejder, List<Påfyldning> påfyldninger, List<Double> mængder) {
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
            storage.tilføjUdgivelse(udgivelse);
            return udgivelse;
        }
    }

    public Lager opretLager(String adresse, String kvadratmeter) {
        Lager lager = new Lager(adresse, kvadratmeter);
        storage.tilføjLager(lager);
        return lager;
    }

    public Alert opretAlert(Alert.AlertType alertType, String title, String contentText) {
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

    public List<Fad> getAlleFade() {
        return storage.getFade();
    }

    public  List<Fad> getFadeUdenHylde() {
       ArrayList<Fad> fadeUdenHylde = new ArrayList<>();
        for (Fad fad : storage.getFade()) {
            if (fad.getHylde() == null) {
                fadeUdenHylde.add(fad);
            }
        }
        return fadeUdenHylde;
    }

    public ArrayList<Fad> getLedigeFad() {
        ArrayList<Fad> ledigeFade = new ArrayList<>();
        for (Fad fad : storage.getFade()) {
            if (!fad.erFyldt() && fad.erAktiv()) {
                ledigeFade.add(fad);
            }
        }
        return ledigeFade;
    }

    public List<Maltbatch> getAlleMaltbatche() { return storage.getMaltbatche(); }

    public List<Destillat> getAlleDestillater() {
        return storage.getDestillater();
    }

    public List<Destillat> getUgensDestillater() {
        ArrayList<Destillat> ugensDestillater = new ArrayList<>();
        for (Destillat destillat : storage.getDestillater()) {
            if (destillat.getDestilleringsdato().isAfter(LocalDate.now().minusWeeks(1))) {
                ugensDestillater.add(destillat);
            }
        }
        return ugensDestillater;
    }
    public List<Udgivelse> getUdgivelser() {
        return storage.getUdgivelser();
    }

    public List<Korn> getKorn() {
        return storage.getKornList();
    }
    public List<Lager> getLagre() {
        return storage.getLagre();
    }

    public ArrayList<Reol> getReolNumre(Lager lager) {
        ArrayList<Reol> reoler = new ArrayList<>();
        for (int i = 0; i < lager.getReoler().size(); i++) {
            reoler.add(lager.getReoler().get(i));
        }
        return reoler;
    }

    public ArrayList<Hylde> getHyldeNumre(Reol reol) {
        ArrayList<Hylde> hylder = new ArrayList<>();
        for (int i = 0; i < reol.getHylder().size(); i++) {
            hylder.add(reol.getHylder().get(i));
        }
        return hylder;
    }

    public void fjernFad(Fad fad) {
        storage.fjernFad(fad);
    }

    public void fjernDestillat(Destillat destillat) {
        storage.fjernDestilat(destillat);
    }

    public void flytFad(List<Fad> fade, Hylde hylde) {
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

    public List<String> getMedarbejdere() {
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

    public ArrayList<Påfyldning> getPåfyldninger() {
        ArrayList<Påfyldning> påfyldninger = new ArrayList<>();
        for (Fad fad : this.getAlleFade()) {
            if(fad.erFyldt()) {
                påfyldninger.add(fad.getPåfyldninger().getLast());
            }
        }
        return påfyldninger;
    }

    public ArrayList<Påfyldning> getAlleIkkeTommePåfyldninger() {
        ArrayList<Påfyldning> allePåfyldninger = getPåfyldninger();
        ArrayList<Påfyldning> ikkeUdgivedePåfyldninger = new ArrayList<>();
        for (Påfyldning påfyldning : allePåfyldninger) {
            if (påfyldning.mængdeTilbage() != 0) {
                ikkeUdgivedePåfyldninger.add(påfyldning);
            }
        }
        return ikkeUdgivedePåfyldninger;
    }

    public FilLogger getFileLoggerStrategy() {
        return new FilLogger();
    }

    public void udtrækBeskrivelse(Logger logger, Logable object) {
        logger.log(object);
    }

    public ArrayList<String> getOutputStrategies() {
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

    public Logger getLoggerStrategy(String LoggerType) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        String className = "application.model.output." + LoggerType + "Logger";
        return (Logger) Class.forName(className).newInstance();
    }

    public void initContent() {
        Fad fad1 = this.opretFad("Spanien", "Cherry", 30, "Eg", 9.5, "El egetræsfadfirma");
        Fad fad2 = this.opretFad("Spanien", "Cherry", 250, "Eg", 1.0, "El egetræsfadfirma");
        Fad fad3 = this.opretFad("Spanien", "Bourbon", 30, "Eg", 9.5, "El fakefadefirma");
        Fad fad4 = this.opretFad("Spanien", "Bourbon", 250, "Eg", 5, "El egetræsfadfirma");
        Fad fad5 = this.opretFad("Spanien", "Bourbon", 100, "Eg", 9.5, "El fakefadefirma");

        Korn byg = this.opretKorn("Mark 1", "Byg", LocalDate.of(2019, 8, 20), 200);
        this.opretKorn("Mark 2", "Byg", LocalDate.of(2019, 8, 23), 200);

        Maltbatch maltbatch1 = this.opretMaltbatch(100, LocalDate.of(2019, 12, 15), LocalDate.of(2019, 12, 17), "Gær", byg);
        Maltbatch maltbatch2 = this.opretMaltbatch(100, LocalDate.of(2019, 12, 15), LocalDate.of(2019, 12, 19), "Special gær", byg);

        Destillat destillat1 = this.opretDestillat("NM77P", 500, 70, "Snævar Njáll Albertsson", null, null, LocalDate.of(2020, 1, 17), maltbatch1);
        Destillat destillat2 = this.opretDestillat("NM78P", 500, 70, "Ingus Brikmanis", null, "Kommentar2", LocalDate.of(2020, 1, 19), maltbatch1);
        Destillat destillat3 = this.opretDestillat("NM79P", 500, 70, "Snævar Njáll Albertsson", "Tørv", null, LocalDate.of(2024, 5, 20), maltbatch2);
        Destillat destillat4 = this.opretDestillat("NM80P", 500, 70, "Snævar Njáll Albertsson", null, null, LocalDate.of(2024, 5, 19), maltbatch2);
        Destillat destillat5 = this.opretDestillat("NM81P", 500, 70, "Ingus Brikmanis", "Tørv", "Kommentar1", LocalDate.of(2024, 5, 18), maltbatch1);

        ArrayList<Destillat> destillatArray1 = new ArrayList<>();
        destillatArray1.add(destillat1);
        destillatArray1.add(destillat2);
        ArrayList<Double> mængdeArray1 = new ArrayList<>();
        mængdeArray1.add(10.0);
        mængdeArray1.add(15.0);
        ArrayList<Destillat> destillatArray2 = new ArrayList<>();
        destillatArray2.add(destillat3);
        destillatArray2.add(destillat4);
        ArrayList<Double> mængdeArray2 = new ArrayList<>();
        mængdeArray2.add(100.0);
        mængdeArray2.add(150.0);
        ArrayList<Destillat> destillatArray3 = new ArrayList<>();
        destillatArray3.add(destillat5);
        ArrayList<Double> mængdeArray3 = new ArrayList<>();
        mængdeArray3.add(20.0);
        this.opretPåfyldninger(new ArrayList<>(List.of(fad1)), LocalDate.of(2019, 10, 25), LocalDate.of(2022, 10, 27), destillatArray1, mængdeArray1);
        this.opretPåfyldninger(new ArrayList<>(List.of(fad2)), LocalDate.of(2021, 7, 17), LocalDate.of(2024, 7, 17), destillatArray2, mængdeArray2);
        this.opretPåfyldninger(new ArrayList<>(List.of(fad3)), LocalDate.of(2022, 7, 17), null, destillatArray3, mængdeArray3);
    }

}

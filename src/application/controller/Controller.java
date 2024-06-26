package application.controller;

import application.model.*;
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
        if (mængdeL > this.mængdeTilbageMaltbatch(maltbatch)) {
            throw new IllegalArgumentException("Der er ikke nok maltbatch tilbage");
        } else {
            Destillat destillat = new Destillat(spiritBatchNr, mængdeL, alkoholprocent, medarbejder, rygemateriale, kommentar, destilleringsdato, maltbatch);
            storage.tilføjDestillat(destillat);
            return destillat;
        }
    }

    public Destillat opdaterDestillat(Destillat destillat, String spiritBatchNr, double mængdeL, double alkoholprocent, String medarbejder, String rygemateriale, String kommentar, LocalDate destilleringsdato, Maltbatch maltbatch) {
        if (destillat == null || spiritBatchNr == null || mængdeL == 0.0 || alkoholprocent == 0.0 || medarbejder == null || destilleringsdato == null || maltbatch == null)
            throw new IllegalArgumentException("Information mangler eller er null. Kun String rygemateriale og String kommentar må være null.");
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

    public Fad opdaterFad(Fad fad, String fraLand, String tidligereIndhold, int størrelseL, String træType, double alderAfTidligereIndhold, String leverandør) {
        if (fad == null || fraLand == null || størrelseL == 0 || træType == null || leverandør == null)
            throw new IllegalArgumentException("Information mangler eller er null. Kun String tidligereIndhold og double alderAfTidligereIndhold må være null/0.0");
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
        if (mængdeL > mængdeTilbageKorn(korn)) {
            throw new IllegalArgumentException("Der er ikke nok korn tilbage");
        } else {
            Maltbatch maltbatch = new Maltbatch(mængdeL, gæringStart, gæringSlut, gærType, korn);
            storage.tilføjMaltBatch(maltbatch);
            return maltbatch;
        }
    }

    private Påfyldning opretPåfyldning(LocalDate påfyldningsDato, LocalDate færdigDato, Fad førsteFad, ArrayList<Destillat> destillater, ArrayList<Double> mængder) {
        Påfyldning påfyldning = new Påfyldning(påfyldningsDato, færdigDato, førsteFad);
        påfyldning.tilføjDestillatMedMængde(destillater, mængder);
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
            if (!fad.erAktiv()) alleFadeErAktive = false;
            if (fad.erFyldt()) alleFadeErTomme = false;
            fadPladsTotalt += fad.getStørrelseL();
        }

        //Kaster fejlmeldinger, hvis noget er galt, og opretter påfyldningerne, hvis intet er galt
        if (mængdeIAlt > fadPladsTotalt)
            throw new IllegalArgumentException("Der er ikke nok plads i fadene til denne mængde destillater");
        else if (!derErNokDestillatTilbage) throw new IllegalArgumentException("Der er ikke nok destillat tilbage");
        else if (mængder.size() != destillater.size())
            throw new IllegalArgumentException("Antal mængder og destillater er ikke det samme");
        else if (mængder.isEmpty())
            throw new IllegalArgumentException("Der er ikke tilføjet en mængde og et destillat");
        else if (!alleFadeErAktive)
            throw new IllegalArgumentException("Et eller flere valgte fade er deaktiveret, og kan derfor ikke bruges");
        else if (!alleFadeErTomme) throw new IllegalArgumentException("Et eller flere fade er allerede fyldte");
        else if (færdigDato != null && færdigDato.isBefore(påfyldningsDato.plusYears(3)))
            throw new IllegalArgumentException("Hvis færdigdato bruges, skal denne være mindst 3 år efter påfyldningsdatoen");
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

    public Udgivelse opretUdgivelse(double unitStørrelse, double prisPerUnit, boolean erFad, LocalDate udgivelsesDato, double alkoholProcent, double vandMængdeL, String medarbejder, String vandetsOprindelse, double angelShare, List<Påfyldning> påfyldninger, List<Double> mængder) {
        //Beregner den totale mængde af påfyldninger og tjekker, om der er nok tilbage af påfyldningerne til at oprette denne udgivelse
        boolean derErNokPåfyldningTilbage = true;
        for (int i = 0; i < påfyldninger.size(); i++) {
            if (påfyldninger.get(i).mængdeTilbage() < mængder.get(i)) {  //Tjekker om der er nok destillat tilbage til påfyldningerne
                derErNokPåfyldningTilbage = false;
            }
        }
        if (!derErNokPåfyldningTilbage)
            throw new IllegalArgumentException("Der er ikke nok påfyldning tilbage til dette");
        else if (mængder.size() != påfyldninger.size())
            throw new IllegalArgumentException("Antal mængder og påfyldninger er ikke det samme");
        else if (mængder.isEmpty())
            throw new IllegalArgumentException("Der er ikke tilføjet en mængde og en påfyldning");
        else {
            Udgivelse udgivelse = new Udgivelse(unitStørrelse, prisPerUnit, erFad, udgivelsesDato, alkoholProcent, vandMængdeL, medarbejder, vandetsOprindelse, angelShare, påfyldninger, mængder);
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

    public List<Fad> getFadeUdenHylde() {
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

    public List<Maltbatch> getAlleMaltbatche() {
        return storage.getMaltbatche();
    }

    public List<Maltbatch> getIkkeTommeMaltbatches() {
        List<Maltbatch> maltbatchList = new ArrayList<>();
        for (Maltbatch maltbatch : storage.getMaltbatche()) {
            if (mængdeTilbageMaltbatch(maltbatch) != 0) {
                maltbatchList.add(maltbatch);
            }
        }
        return maltbatchList;
    }

    public List<Destillat> getAlleDestillater() {
        return storage.getDestillater();
    }

    public List<Destillat> getUgensDestillater(LocalDate dato) {
        ArrayList<Destillat> ugensDestillater = new ArrayList<>();
        for (Destillat destillat : storage.getDestillater()) {
            if (destillat.getDestilleringsdato().isAfter(dato.minusWeeks(1)) && destillat.getDestilleringsdato().isBefore(dato)) {
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

    public List<Korn> getIkkeTommeKorn() {
        List<Korn> kornList = new ArrayList<>();
        for (Korn korn : storage.getKornList()) {
            if (mængdeTilbageKorn(korn) > 0) {
                kornList.add(korn);
            }
        }
        return kornList;
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

    public void flytFadePåLageret(List<Fad> fade, Hylde hylde) {
        if (hylde != null && !hylde.erLedigPlads(fade.size()))
            throw new IllegalArgumentException("Der er ikke nok plads på denne hylde");
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
            if (fad.erFyldt()) {
                påfyldninger.add(fad.getPåfyldninger().getLast());
            }
        }
        return påfyldninger;
    }

    public List<Påfyldning> getMindst3ÅrsIkkeTommePåfyldninger(LocalDate fraDato) {
        List<Påfyldning> fundnePåfyldninger = new ArrayList<>();
        for (Påfyldning påfyldning : getPåfyldninger()) {
            if (påfyldning.getPåfyldningsDato().isBefore(fraDato.minusYears(3)) && påfyldning.mængdeTilbage() != 0) {
                fundnePåfyldninger.add(påfyldning);
            }
        }
        return fundnePåfyldninger;
    }

    public void udtrækBeskrivelse(Logger logger, Logable object) {
        if (logger == null) throw new IllegalArgumentException("En type logger er ikke valgt");
        else if (object == null) throw new IllegalArgumentException("Et objekt er ikke valgt");
        else logger.log(object);
    }

    public ArrayList<String> getAllOutputStrategies() {
        ArrayList<String> foundStrategies = new ArrayList<>();
        File file = new File("src/application/model/output/");
        String[] directory = file.list();
        for (String s : directory) {
            if (!s.equals("Logger.java")) {
                foundStrategies.add(s);
            }
        }
        for (int i = 0; i < foundStrategies.size(); i++) {
            foundStrategies.set(i, foundStrategies.get(i).substring(0, foundStrategies.get(i).length() - 11));
        }
        return foundStrategies;
    }

    public Logger getLoggerStrategy(String LoggerType) {
        Logger logger = null;
        if (LoggerType == null || LoggerType.isEmpty())
            throw new IllegalArgumentException("Der er ingen type valgt");
        else {
            try {
                String className = "application.model.output." + LoggerType + "Logger";
                logger = (Logger) Class.forName(className).newInstance();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return logger;
    }

    public double mængdeTilbageKorn(Korn korn) {
        double mængdeTilbage = korn.getMængdeKg();
        for (Maltbatch maltbatch : storage.getMaltbatche()) {
            Korn brugteKorn = maltbatch.getKorn();
            if (brugteKorn.equals(korn)) {
                mængdeTilbage -= maltbatch.getMængdeL();
            }
        }
        return mængdeTilbage;
    }

    public double mængdeTilbageMaltbatch(Maltbatch maltbatch) {
        double mængdeTilbage = maltbatch.getMængdeL();
        for (Destillat destillat : storage.getDestillater()) {
            Maltbatch brugtMaltbatch = destillat.getMaltbatch();
            if (brugtMaltbatch.equals(maltbatch)) {
                mængdeTilbage -= destillat.getMængdeL();
            }
        }
        return mængdeTilbage;
    }

    public void initContent() {
        Fad fad1 = this.opretFad("Spanien", "Cherry", 30, "Eg", 9.5, "El egetræsfadfirma");
        Fad fad2 = this.opretFad("Spanien", "Cherry", 250, "Eg", 1.0, "El egetræsfadfirma");
        Fad fad3 = this.opretFad("Spanien", "Bourbon", 30, "Eg", 9.5, "El fakefadefirma");
        Fad fad4 = this.opretFad("Spanien", "Bourbon", 250, "Eg", 5, "El egetræsfadfirma");
        Fad fad5 = this.opretFad("Spanien", "Bourbon", 100, "Eg", 9.5, "El fakefadefirma");

        Korn byg = this.opretKorn("Mark 1", "Byg", LocalDate.of(2019, 8, 20), 1200);
        Korn byg2 = this.opretKorn("Mark 2", "Byg", LocalDate.of(2019, 8, 23), 800);
        Korn byg3 = this.opretKorn("Mark 3", "Byg", LocalDate.of(2021, 9, 25), 1200);
        Korn byg4 = this.opretKorn("Mark navn 4", "Byg", LocalDate.of(2022, 12, 26), 900);

        Maltbatch maltbatch1 = this.opretMaltbatch(1000, LocalDate.of(2019, 12, 15), LocalDate.of(2019, 12, 17), "Gær", byg);
        Maltbatch maltbatch2 = this.opretMaltbatch(800, LocalDate.of(2019, 12, 15), LocalDate.of(2019, 12, 19), "Special gær", byg2);
        Maltbatch maltbatch3 = this.opretMaltbatch(700, LocalDate.of(2021, 12, 30), LocalDate.of(2021, 12, 31), "Gær", byg3);
        Maltbatch maltbatch4 = this.opretMaltbatch(600, LocalDate.of(2022, 12, 28), LocalDate.of(2022, 12, 30), "Gær", byg4);

        Destillat destillat1 = this.opretDestillat("NM77P", 500, 70, "Snævar Njáll Albertsson", null, null, LocalDate.of(2020, 1, 17), maltbatch1);
        Destillat destillat2 = this.opretDestillat("NM78P", 500, 70, "Ingus Brikmanis", null, "Kommentar2", LocalDate.of(2020, 1, 19), maltbatch2);
        Destillat destillat3 = this.opretDestillat("NM79P", 500, 70, "Snævar Njáll Albertsson", "Tørv", null, LocalDate.of(2024, 5, 20), maltbatch3);
        Destillat destillat4 = this.opretDestillat("NM80P", 500, 70, "Snævar Njáll Albertsson", null, null, LocalDate.of(2024, 5, 19), maltbatch4);
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

        List<Påfyldning> påfyldninger1 = this.opretPåfyldninger(new ArrayList<>(List.of(fad1)), LocalDate.of(2019, 10, 25), LocalDate.of(2022, 10, 27), destillatArray1, mængdeArray1);
        List<Påfyldning> påfyldninger2 = this.opretPåfyldninger(new ArrayList<>(List.of(fad2)), LocalDate.of(2021, 7, 17), LocalDate.of(2024, 7, 17), destillatArray2, mængdeArray2);
        List<Påfyldning> påfyldninger3 = this.opretPåfyldninger(new ArrayList<>(List.of(fad3)), LocalDate.of(2022, 7, 17), null, destillatArray3, mængdeArray3);

        Lager lager = opretLager("Hammelvej 2", "1000");
        Lager lager2 = opretLager("Sall whisky lager", "1500");

        lager.tilføjReol(10, 5, 3);
        lager.tilføjReol(20, 2, 1);
        lager2.tilføjReol(10, 5, 3);
        lager2.tilføjReol(20, 2, 1);

        ArrayList<Double> fadMængdeList = new ArrayList<>();
        fadMængdeList.add(25.0);
        ArrayList<Double> flaskeMængdeList = new ArrayList<>();
        flaskeMængdeList.add(40.0);

        Udgivelse udgivelse1 = opretUdgivelse(0, 25000, true, LocalDate.of(2024, 05, 23), 80, 0, "Snævar", null, 8, påfyldninger1, fadMængdeList);
        Udgivelse udgivelse2 = opretUdgivelse(0.7, 1000, false, LocalDate.of(2024, 04, 22), 78, 100, "Snævar", "Sall whisky kilde", 10, påfyldninger2, flaskeMængdeList);
    }
}

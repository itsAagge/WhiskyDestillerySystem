package storage;

import application.model.Destillat;
import application.model.Fad;
import application.model.Korn;
import application.model.Maltbatch;

import java.util.ArrayList;

public class Storage {

    private static ArrayList<Destillat> destillater = new ArrayList<>();
    private static ArrayList<Fad> fade = new ArrayList<>();
    private static ArrayList<Korn> kornList = new ArrayList<>();
    private static ArrayList<Maltbatch> maltbatches = new ArrayList<>();

    public static ArrayList<Destillat> getDestillats() {
        return new ArrayList<>(destillater);
    }

    public static ArrayList<Fad> getFads() {
        return new ArrayList<>(fade);
    }

    public static ArrayList<Korn> getKornList() {
        return new ArrayList<>(kornList);
    }

    public static ArrayList<Maltbatch> getMaltbatches() {
        return new ArrayList<>(maltbatches);
    }

    public static void tilføjDestillat(Destillat destillat) {
        if (!destillater.contains(destillat)) {
            destillater.add(destillat);
        }
    }

    public static void  tilføjFad (Fad fad) {
        if (!fade.contains(fad)) {
            fade.add(fad);
        }
    }

    public static void  tilføjKorn (Korn korn) {
        if (!kornList.contains(korn)) {
            kornList.add(korn);
        }
    }

    public static void  tilføjMaltBatch (Maltbatch maltbatch) {
        if (!maltbatches.contains(maltbatch)) {
            maltbatches.add(maltbatch);
        }
    }



    public static void fjernDestilat(Destillat destillat) {
        if (destillater.contains(destillat)) {
            destillater.remove(destillat);
        }
    }

    public static void fjernFad(Fad fad) {
        if (fade.contains(fad)) {
            fade.remove(fad);
        }
    }

    public static void fjernKorn(Korn korn) {
        if (kornList.contains(korn)) {
            kornList.remove(korn);
        }
    }

    public static void fjernMaltbatch(Maltbatch maltbatch) {
        if (maltbatches.contains(maltbatch)) {
            maltbatches.remove(maltbatch);
        }
    }


}

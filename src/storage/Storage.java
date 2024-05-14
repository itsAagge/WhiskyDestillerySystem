package storage;

import application.model.*;

import java.util.ArrayList;

public class Storage {

    private static ArrayList<Destillat> destillater = new ArrayList<>();
    private static ArrayList<Fad> fade = new ArrayList<>();
    private static ArrayList<Korn> kornList = new ArrayList<>();
    private static ArrayList<Maltbatch> maltbatches = new ArrayList<>();
    private static ArrayList<Udgivelse> udgivelser = new ArrayList<>();


    //Getters til listerne
    public static ArrayList<Destillat> getDestillater() {
        return new ArrayList<>(destillater);
    }

    public static ArrayList<Fad> getFade() {
        return new ArrayList<>(fade);
    }

    public static ArrayList<Korn> getKornList() {
        return new ArrayList<>(kornList);
    }

    public static ArrayList<Maltbatch> getMaltbatche() {
        return new ArrayList<>(maltbatches);
    }
    public static ArrayList<Udgivelse> getUdgivelser() {
        return new ArrayList<>(udgivelser);
    }


    //Tilføj til listerne
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

    public static void tilføjUdgivelse (Udgivelse udgivelse) {
        if(!udgivelser.contains(udgivelse)) {
            udgivelser.add(udgivelse);
        }
    }


    //Fjern ting (som kan være relevante)
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

package storage;

import application.model.*;

import java.util.ArrayList;

public class Storage {

    private ArrayList<Destillat> destillater = new ArrayList<>();
    private ArrayList<Fad> fade = new ArrayList<>();
    private ArrayList<Korn> kornList = new ArrayList<>();
    private ArrayList<Maltbatch> maltbatches = new ArrayList<>();
    private ArrayList<Udgivelse> udgivelser = new ArrayList<>();
    private ArrayList<Lager> lagre = new ArrayList<>();

    public Storage() {
        destillater = new ArrayList<>();
        fade = new ArrayList<>();
        kornList = new ArrayList<>();
        maltbatches = new ArrayList<>();
        udgivelser = new ArrayList<>();
        lagre = new ArrayList<>();
    }


    //Getters til listerne
    public ArrayList<Destillat> getDestillater() {
        return new ArrayList<>(destillater);
    }

    public ArrayList<Fad> getFade() {
        return new ArrayList<>(fade);
    }

    public ArrayList<Korn> getKornList() {
        return new ArrayList<>(kornList);
    }

    public ArrayList<Maltbatch> getMaltbatche() {
        return new ArrayList<>(maltbatches);
    }

    public ArrayList<Udgivelse> getUdgivelser() {
        return new ArrayList<>(udgivelser);
    }

    public ArrayList<Lager> getLagre() {
        return new ArrayList<>(lagre);
    }

    //Tilføj til listerne
    public void tilføjDestillat(Destillat destillat) {
        if (!destillater.contains(destillat)) {
            destillater.add(destillat);
        }
    }

    public void  tilføjFad (Fad fad) {
        if (!fade.contains(fad)) {
            fade.add(fad);
        }
    }

    public void  tilføjKorn (Korn korn) {
        if (!kornList.contains(korn)) {
            kornList.add(korn);
        }
    }

    public void  tilføjMaltBatch (Maltbatch maltbatch) {
        if (!maltbatches.contains(maltbatch)) {
            maltbatches.add(maltbatch);
        }
    }

    public void tilføjUdgivelse (Udgivelse udgivelse) {
        if(!udgivelser.contains(udgivelse)) {
            udgivelser.add(udgivelse);
        }
    }

    public void tilføjLager(Lager lager) {
        if(!lagre.contains(lager)) {
            lagre.add(lager);
        }
    }

    //Fjern fra listerne (hvis relevant)
    public void fjernDestilat(Destillat destillat) {
        if (destillater.contains(destillat)) {
            destillater.remove(destillat);
        }
    }

    public void fjernFad(Fad fad) {
        if (fade.contains(fad)) {
            fade.remove(fad);
        }
    }

    public void fjernKorn(Korn korn) {
        if (kornList.contains(korn)) {
            kornList.remove(korn);
        }
    }

    public void fjernMaltbatch(Maltbatch maltbatch) {
        if (maltbatches.contains(maltbatch)) {
            maltbatches.remove(maltbatch);
        }
    }

    public void fjernLager(Lager lager) {
        if(lagre.contains(lager)) {
            lagre.add(lager);
        }
    }
}

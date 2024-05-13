package application.model;

import com.sun.source.tree.IfTree;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class Påfyldning {
    private LocalDate påfyldningsDato;
    private LocalDate færdigDato;
    private boolean flyttetFraFad; //Skal vi bruge denne, når vi gemmer hele historikken af fade i en ArrayList?
    private HashMap<Destillat, Double> destillatMængder;
    //TODO er det meningen at vi ikke bruger fadArrayList som parameter i constructor
    private ArrayList<Fad> fade;
    private ArrayList<Destillat> destillater; //Var hele ideen med at bruge et HashMap ikke, at vi slap for denne?

    public Påfyldning(LocalDate påfyldningsDato, LocalDate færdigDato, boolean flyttetFraFad, Fad førsteFad) {
        this.påfyldningsDato = påfyldningsDato;
        this.færdigDato = færdigDato;
        this.flyttetFraFad = flyttetFraFad;
        this.destillatMængder = new HashMap<>();
        this.fade = new ArrayList<>();
        this.fade.add(førsteFad);
        this.destillater = new ArrayList<>();
    }

    public ArrayList<Fad> getFade() {
        return new ArrayList<>(fade); //Måske skal vi også have en getter, som bare henter det fad, som påfyldningen er i?
    }

    public void addDestillat(Destillat destillat) {
        if (!destillater.contains(destillat)) {
            destillater.add(destillat);
            destillat.addPåfyldning(this);
        }
    }

    public void removeDestillat(Destillat destillat) {
        if (destillater.contains(destillat)) {
            destillater.remove(destillat);
            destillat.removePåfyldning(this);
        }
    }

    public void addFad(Fad fad) {
        if (!fade.contains(fad)) {
            fade.add(fad);
            fad.addPåfyldning(this);
        }
    }

    public void removeFad(Fad fad) {
        if (fade.contains(fad)) {
            fade.remove(fad);
            fad.removePåfyldning(this);
        }
    }

    public void tilføjDestillatMedMængde(Destillat[] destillater, double[] mængde, Fad førstefad) {
        double mængdeIAlt = 0;
        for (int i = 0; i < mængde.length; i++) {
            mængdeIAlt += mængde[i];
        }
        if (mængdeIAlt <= førstefad.getStørrelseL()) {
            for (int i = 0; i < destillater.length; i++) {
                destillatMængder.put(destillater[i], mængde[i]);
            }
        }
    }

    public double tjekDestillatMængde(Destillat destillat) {
        return destillat.getMængdeL(); //Hvorfor har vi en metode til dette i påfyldningsklassen? Man kan da bare kalde den på destillatklassen.
    }

    public double tjekFadPlads() {
        return fade.getLast().getStørrelseL(); //Hvis ideen er at tjekke, om der er plads i fadet inden påfyldningen bliver oprettet, vil fadet endnu ikke være i denne ArrayListe.
    }

    public String getBeskrivelse() {
        String s = "";
        int size = 0;
        s += "Påfyldningen er blevet påfyldt den. " + this.påfyldningsDato + ".";
        if (færdigDato != null) {
            s += " Denne påfyldning er sat til at være færdig den. " + this.færdigDato + ".";
        }
        s += " Påfyldningen indeholder " + destillatMængder.toString() + ", og er fyldt i fad med fadnr: ";
        for (Fad fad : fade) {
            s += fad.getFadNr();
            size++;
            if (size < fade.size()) {
                s += ", ";
            }
        }


        if (flyttetFraFad) {
            s += " Denne påfyldningen stammer fra et andet fad og ikke direkte fra et distillat";
        }
        return s;
    }

    @Override
    public String toString() {
        String s = "";
        int size = 0;
        s += "påfyldning" + " {destillat: ";
        for (Destillat destillat : destillatMængder.keySet()) {
            s += destillat.getSpiritBatchNr();
            size++;
            if (size < destillatMængder.size()) {
                s += ", ";
            }
        }
        s += "}.";
        return s;
    }
}

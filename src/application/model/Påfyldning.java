package application.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class Påfyldning {
    private LocalDate påfyldningsDato;
    private LocalDate færdigDato;
    private HashMap<Destillat, Double> destillatMængder;
    //TODO er det meningen at vi ikke bruger fadArrayList som parameter i constructor
    private ArrayList<Fad> fade;

    public Påfyldning(LocalDate påfyldningsDato, LocalDate færdigDato, Fad førsteFad) {
        this.påfyldningsDato = påfyldningsDato;
        this.færdigDato = færdigDato;
        this.destillatMængder = new HashMap<>();
        this.fade = new ArrayList<>();
        this.fade.add(førsteFad);
    }

    public HashMap<Destillat, Double> getDestillatMængder() {
        return new HashMap<>(destillatMængder);
    }

    public ArrayList<Fad> getFade() {
        return new ArrayList<>(fade);
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

    public void tilføjDestillatMedMængde(Destillat[] destillater, double[] mængde) {
        for (int i = 0; i < destillater.length; i++) {
            destillatMængder.put(destillater[i], mængde[i]);
            destillater[i].addPåfyldning(this);
        }
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

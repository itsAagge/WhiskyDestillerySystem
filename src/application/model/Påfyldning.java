package application.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class Påfyldning {
    private LocalDate påfyldningsDato;
    private LocalDate færdigDato;
    private boolean flyttetFraFad;
    private HashMap<Destillat, Double> destillatMængder = new HashMap<>();
    private ArrayList<Fad> fade;
    private ArrayList<Destillat> destillater;

    public Påfyldning(LocalDate påfyldningsDato, LocalDate færdigDato, boolean flyttetFraFad, Fad førsteFad) {
        this.påfyldningsDato = påfyldningsDato;
        this.færdigDato = færdigDato;
        this.flyttetFraFad = flyttetFraFad;
        this.fade.add(førsteFad);
        førsteFad.setFyldt(true);
    }

    public ArrayList<Fad> getFade() {
        return new ArrayList<>(fade);
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

    public void addFade(Fad fad) {
        if (!fade.contains(fad)) {
            fade.add(fad);
            fad.addPåfyldning(this);
        }
    }

    public void removeFade(Fad fad) {
        if (fade.contains(fad)) {
            fade.remove(fad);
            fad.removePåfyldning(this);
        }
    }

    public void tilføjDestillatMedMængde(Destillat[] destillater, double[] mængde) {
        //TODO - check om der er plads i fadet
        for (int i = 0; i < destillater.length; i++) {
            destillatMængder.put(destillater[i], mængde[i]);
        }
    }

    public double tjekDestillatMængde(Destillat destillat) {
        return destillat.getMængdeL();
    }

    public double tjekFadPlads() {
        return fade.getLast().getStørrelseL();
    }


}

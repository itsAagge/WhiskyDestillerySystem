package application.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class Påfyldning {
    private LocalDate påfyldningsDato;
    private LocalDate færdigDato;
    private HashMap<Destillat, Double> destillatMængder;
    private ArrayList<Fad> fade;
    //TODO: Tilføj ArrayList med LocalDates til Use Case om flytning af påfyldning mellem fade

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
        if(this.destillatMængder.size() > 1) {
            s = "Denne påfyldning består af " + this.destillatMængder.size() + " destillater." +
                "\nDisse er:";
        } else {
            s = "Denne påfyldning består af 1 destillat:";
        }
        for (Destillat destillat : destillatMængder.keySet()) {
            s += "\n- Destillat " + destillat.getSpiritBatchNr();
            s += "\n" + destillat.getBeskrivelse();
        }
        if(this.fade.size() > 1) {
            s += "\nPåfyldningen har ligget på " + this.fade.size() + " fade.";
        } else {
            s += "\nPåfyldningen har ligget på 1 fad:";
        }
        for (Fad fad : fade) {
            s += "\n- Fad nr. " + fad.getFadNr();
            s += "\n" + fad.getBeskrivelse();
        }
        s += "\nDenne påfyldning blev hældt på fad d. " + this.påfyldningsDato;
        return s;
    }

    public String getBeskrivelseKort() {
        String s = "Denne påfyldning har følgende indhold:";
        for (Destillat destillat : destillatMængder.keySet()) {
            s += "\n- Destillat: " + destillat.getSpiritBatchNr() + ". Mængde: " + destillatMængder.get(destillat) + " liter";
        }
        s += "\nPåfyldningen har ligget på følgende fad(e):";
        for (Fad fad : fade) {
            s += "\n- Fad nr. " + fad.getFadNr();
        }
        return s;
    }

    @Override
    public String toString() {
        String s = "";
        int size = 0;
        s += "Påfyldning. Destillat: ";
        for (Destillat destillat : destillatMængder.keySet()) {
            s += destillat.getSpiritBatchNr();
            size++;
            if (size < destillatMængder.size()) {
                s += ", ";
            }
        }
        return s;
    }
}

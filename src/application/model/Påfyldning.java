package application.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class Påfyldning {
    private static int antalPåfyldninger = 0;
    private int påfyldningsNr;
    private LocalDate påfyldningsDato;
    private LocalDate færdigDato;
    private HashMap<Destillat, Double> destillatMængder;
    private ArrayList<Fad> fade;
    private boolean erUdgivet;


    //Constructor
    public Påfyldning(LocalDate påfyldningsDato, LocalDate færdigDato, Fad førsteFad) {
        antalPåfyldninger++;
        this.påfyldningsNr = antalPåfyldninger;
        this.påfyldningsDato = påfyldningsDato;
        this.færdigDato = færdigDato;
        this.destillatMængder = new HashMap<>();
        this.fade = new ArrayList<>();
        this.fade.add(førsteFad);
        this.erUdgivet = false;
    }


    //Getters
    public HashMap<Destillat, Double> getDestillatMængder() {
        return new HashMap<>(destillatMængder);
    }

    public ArrayList<Fad> getFade() {
        return new ArrayList<>(fade);
    }

    public int getPåfyldningsNr() {
        return påfyldningsNr;
    }

    public boolean erUdgivet() {
        return erUdgivet;
    }

    //Setters
    public void setErUdgivet(boolean erUdgivet) {
        this.erUdgivet = erUdgivet;
    }


    //Tilføjer et fad til fade listen, hvilket gør det til det nye fad påfyldningen
    //ligger på og fylder det fad og gør det gamle tomt.
    public void addFad(Fad fad) {
        if (!fade.contains(fad)) {
            fade.getLast().setFyldt(false);
            fade.add(fad);
            fad.setFyldt(true);
            fad.addPåfyldning(this);
        }
    }


    //Fjerner et fad fra påfyldningen
    public void removeFad(Fad fad) {
        if (fade.contains(fad)) {
            fade.remove(fad);
            fad.removePåfyldning(this);
        }
    }


    //Tilføjer et destillat med en bestemt mængde til påfyldningen
    public void tilføjDestillatMedMængde(ArrayList<Destillat> destillater, ArrayList<Double> mængde) {
        for (int i = 0; i < destillater.size(); i++) {
            destillatMængder.put(destillater.get(i), mængde.get(i));
            destillater.get(i).addPåfyldning(this);
        }
    }


    //Beskrivelses metoder og to string metode
    @Override
    public String toString() {
        String s = "";
        int size = 0;
        s += "Påfyldning " + this.påfyldningsNr + ". Fad nr. " + this.fade.getLast().getFadNr() + ". Destillat: ";
        for (Destillat destillat : destillatMængder.keySet()) {
            s += destillat.getSpiritBatchNr();
            size++;
            if (size < destillatMængder.size()) {
                s += ", ";
            }
        }
        return s;
    }

    public String getBeskrivelse() {
        String s;
            s = "Sall Whisky påfyldning nr. " + this.påfyldningsNr;
        if(this.destillatMængder.size() > 1) {
            s += "\nDenne påfyldning består af " + this.destillatMængder.size() + " destillater." +
                "\nDisse er:";
        } else {
            s += "\nDenne påfyldning består af 1 destillat:";
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


}

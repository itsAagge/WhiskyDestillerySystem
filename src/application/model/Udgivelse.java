package application.model;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

public class Udgivelse implements Logable {
    private static int antalUdgivelser = 0;
    private int udgivelsesNr;
    private double unitStørrelse;
    private int antalFlasker;
    private double prisPerUnit;
    private boolean erFad;
    private LocalDate udgivelsesDato;
    private double alkoholProcent;
    private double vandMængdeL;
    private String medarbejder;
    private HashMap<Påfyldning, Double> påfyldningsMængder;
    private String vandetsOprindelse;
    private double angelsShare;


    //Constructor
    public Udgivelse(double unitStørrelse, double prisPerUnit, boolean erFad, LocalDate udgivelsesDato, double alkoholProcent, double vandMængdeL, String medarbejder, String vandetsOprindelse, double angelsShare, List<Påfyldning> påfyldninger, List<Double> mængder) {
        antalUdgivelser++;
        this.udgivelsesNr = antalUdgivelser;
        this.erFad = erFad;
        this.udgivelsesDato = udgivelsesDato;
        this.alkoholProcent = alkoholProcent;
        this.vandMængdeL = vandMængdeL;
        this.medarbejder = medarbejder;
        this.prisPerUnit = prisPerUnit;
        this.unitStørrelse = unitStørrelse;
        this.påfyldningsMængder = new HashMap<>();
        this.vandetsOprindelse = vandetsOprindelse;
        this.angelsShare = angelsShare;
        this.tilføjPåfyldningmedMængde(påfyldninger, mængder);
        if (!this.erFad) {
            this.antalFlasker = beregnAntalFlasker();
        }
    }

    //Getters
    public int getUdgivelsesNr() {
        return udgivelsesNr;
    }

    public boolean erFad() {
        return erFad;
    }

    public LocalDate getUdgivelsesDato() {
        return udgivelsesDato;
    }

    public HashMap<Påfyldning, Double> getPåfyldningsMængder() {
        return new HashMap<>(påfyldningsMængder);
    }

    //Tilføjer påfyldninger med en bestemt mængde til udgivelsen
    public void tilføjPåfyldningmedMængde(List<Påfyldning> påfyldninger, List<Double> mængder) {
        for (int i = 0; i < påfyldninger.size(); i++) {
            if (mængder.get(i) < 0) {
                throw new IllegalArgumentException("Mængder må ikke være under 0");
            }
            else {
                this.påfyldningsMængder.put(påfyldninger.get(i), mængder.get(i));
                påfyldninger.get(i).tilføjUdgivelse(this);
            }
        }
    }

    //To string og beskrivelses metoder
    @Override
    public String toString() {
        int size = 0;
        String s = "Udgivelse " + this.udgivelsesNr;
        if(erFad) {
            Fad fad = null;
            //TODO: Finde ud af, om der er en bedre metode til dette
            for (Påfyldning påfyldning : påfyldningsMængder.keySet()) { //Der er kun 1 påfyldning i HashMappet, når udgivelsen er et fad
                fad = påfyldning.getFade().getLast();
            }
            s += " Fad nr. " + fad.getFadNr() + " " + fad.getStørrelseL() + "L";
        } else {
            s += " Påfyldning: ";
            for (Påfyldning påfyldning : påfyldningsMængder.keySet()) {
                s += påfyldning.getPåfyldningsNr();
                size++;
                if(size < this.påfyldningsMængder.size()) {
                    s += ", ";
                }
            }
            s += ". Flasker: " + this.antalFlasker;
        }
        return s;
    }

    @Override
    public String getBeskrivelse() {
        String s = "Udgivelse nr. " + this.udgivelsesNr + ", udgivet d. " + this.udgivelsesDato + ". Angels share på i denne udgivelse blev: " + angelsShare + "%.";
        if(erFad) {
            Fad fad = null;
            //TODO: Finde ud af, om der er en bedre metode til dette
            for (Påfyldning påfyldning : påfyldningsMængder.keySet()) { //Der er kun 1 påfyldning i HashMappet, når udgivelsen er et fad
                fad = påfyldning.getFade().getLast();
            }
            s += "\nUdgivelsen er fad nr. " + fad.getFadNr();
        } else {
            s += "\nUdgivelsen består af " + this.antalFlasker + " flasker, som hver koster " + this.prisPerUnit + " kr.";
        }
        s += "\nAlkoholprocenten i denne udgivelse er " + this.alkoholProcent;
        s += " Udgivelsen er ";
        if(this.vandMængdeL == 0) {
            s += "cask strength, og der er " + this.getTotalMængdePåfyldning() + " liter i udgivelsen.";
        } else {
            s += "single malt, og der er brugt " + this.vandMængdeL + " liter vand fra " + vandetsOprindelse + ", til at blande de " + this.getTotalMængdePåfyldning() + " liter whisky op med.";
        }
        s += "\nUdgivelsen består af følgende påfyldninger af whisky:\n\n";
        for (Påfyldning påfyldning : påfyldningsMængder.keySet()) {
            s += påfyldning.getBeskrivelse() + "\n\n";
        }
        return s;
    }


    //Finder den totale mængde af påfyldning i udgivelsen
    public double getTotalMængdePåfyldning() {
        double mængdeTotal = 0;
        for (Double mængde : påfyldningsMængder.values()) {
            mængdeTotal += mængde;
        }
        return mængdeTotal;
    }


    //Beregner antallet af flasker ud fra den totale mængde af påfyldning og mængden af vand,
    //divideret med unit størrelsen
    private int beregnAntalFlasker() {
        double totalMængdePåfyldning = getTotalMængdePåfyldning();
        return (int) (((totalMængdePåfyldning * (1 - angelsShare /100)) + vandMængdeL) / unitStørrelse);
    }

    //Returnerer et standardiseret filnavn for udgivelser
    @Override
    public String getFileName() {
        String fileName = "Udgivelse-" + this.udgivelsesNr + "_Udgivelsesedato-" + this.udgivelsesDato;
        if (this.erFad()) {
            fileName += "_Fad";
        } else {
            fileName += "_Flasker-" + this.antalFlasker;
        }
        return fileName;
    }
}

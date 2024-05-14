package application.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Udgivelse {
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
    private List<Påfyldning> påfyldninger;

    public Udgivelse(double unitStørrelse, double prisPerUnit, boolean erFad, LocalDate udgivelsesDato, double alkoholProcent, double vandMængdeL, String medarbejder, List<Påfyldning> påfyldninger) {
        antalUdgivelser++;
        this.udgivelsesNr = antalUdgivelser;
        this.erFad = erFad;
        this.udgivelsesDato = udgivelsesDato;
        this.alkoholProcent = alkoholProcent;
        this.vandMængdeL = vandMængdeL;
        this.medarbejder = medarbejder;
        this.påfyldninger = påfyldninger;
        this.prisPerUnit = prisPerUnit;
        this.unitStørrelse = unitStørrelse;
        if (!this.erFad) {
            this.antalFlasker = beregnAntalFlasker();
        }
    }

    public int getUdgivelsesNr() {
        return udgivelsesNr;
    }

    public boolean erFad() {
        return erFad;
    }

    public LocalDate getUdgivelsesDato() {
        return udgivelsesDato;
    }

    public double getAlkoholProcent() {
        return alkoholProcent;
    }

    public int getAntalFlasker() {
        return antalFlasker;
    }

    @Override
    public String toString() {
        int size = 0;
        String s = "Udgivelse " + this.udgivelsesNr;
        if(erFad) {
            s += " Fad nr." + this.påfyldninger.getFirst().getFade().getLast().getFadNr() + " " + this.påfyldninger.getFirst().getFade().getLast().getStørrelseL() + "L";
        } else {
            s += " Påfyldning: ";
            for (Påfyldning påfyldning : påfyldninger) {
                s += påfyldning.getPåfyldningsNr();
                size++;
                if(size < this.påfyldninger.size()) {
                    s += ", ";
                }
            }
        }
        return s;
    }

    public double getTotalMængdePåfyldning() {
        double mængdeTotal = 0;
        for (Påfyldning påfyldning : påfyldninger) {
            for (double mængde : påfyldning.getDestillatMængder().values()) {
                mængdeTotal += mængde;
            }
        }
        return mængdeTotal;
    }

    private int beregnAntalFlasker() {
        double totalMængdePåfyldning = getTotalMængdePåfyldning();
        return (int) ((totalMængdePåfyldning + vandMængdeL) / unitStørrelse);
    }

    public String getBeskrivelse() {
        String s = "Udgivelse nr. " + this.udgivelsesNr + ", udgivet d. " + this.udgivelsesDato;
        if(erFad) {
            s += "\nUdgivelsen er fad nr. " + this.påfyldninger.getFirst().getFade().getLast().getFadNr();
        } else {
            s += "\nUdgivelsen består af " + this.antalFlasker + " flasker, som hver koster " + this.prisPerUnit;
        }
        s += "\nAlkoholprocenten i denne udgivelse er " + this.alkoholProcent;
        s += "Udgivelsen er ";
        if(this.vandMængdeL == 0) {
            s += "cask strength, og der er " + this.getTotalMængdePåfyldning() + " liter i udgivelsen.";
        } else {
            s += "single malt, og der er brugt " + this.vandMængdeL + " liter vand til at blande de " + this.getTotalMængdePåfyldning() + " liter whisky op med";
        }
        s += "\nUdgivelsen består af følgende påfyldninger af whisky:\n";
        for (Påfyldning påfyldning : påfyldninger) {
            s += påfyldning.getBeskrivelse() + "\n";
        }
        return s;
    }
}

package application.model;

import java.time.LocalDate;
import java.util.ArrayList;

public class Udgivelse {
    private static int antalUdgivelser = 0;
    private int udgivelsesNr;
    private int antalFlasker;
    private double prisPerFlaske;
    private boolean erFad;
    private LocalDate udgivelsesDato;
    private double alkoholProcent;
    private double vandMængdeL;
    private String medarbejder;
    private ArrayList<Påfyldning> påfyldninger;

    public Udgivelse(int antalFlasker, double prisPerFlaske, boolean erFad, LocalDate udgivelsesDato, double alkoholProcent, double vandMængdeL, String medarbejder) {
        antalUdgivelser++;
        this.udgivelsesNr = antalUdgivelser;
        this.antalFlasker = antalFlasker;
        this.prisPerFlaske = prisPerFlaske;
        this.erFad = erFad;
        this.udgivelsesDato = udgivelsesDato;
        this.alkoholProcent = alkoholProcent;
        this.vandMængdeL = vandMængdeL;
        this.medarbejder = medarbejder;
        this.påfyldninger = new ArrayList<>();
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
}

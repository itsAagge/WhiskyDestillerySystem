package application.model;

import java.time.LocalDate;

public class Korn implements Logable {
    private String markNavn;
    private String sort;
    private LocalDate høstDato;
    private double mængdeKg;

    //Constructor
    public Korn(String markNavn, String sort, LocalDate høstDato, double mængdeKg) {
        this.markNavn = markNavn;
        this.sort = sort;
        this.høstDato = høstDato;
        this.mængdeKg = mængdeKg;
    }

    //Getters
    public String getMarkNavn() {
        return markNavn;
    }

    public String getSort() {
        return sort;
    }

    public LocalDate getHøstDato() {
        return høstDato;
    }

    public double getMængdeKg() {
        return mængdeKg;
    }

    //To string og beskrivelses metoder
    @Override
    public String toString() {
        return this.høstDato.toString() + ". Sort: " + this.sort + " fra " + this.markNavn;
    }

    @Override
    public String getFileName() {
        return "Korn-" + this.sort + "_Høstdato-" + this.høstDato + "_Mængde-" + this.mængdeKg;
    }

    @Override
    public String getBeskrivelse() {
        String s = "Dette korn er høstet den: " + høstDato + " fra marken: " + markNavn + " og har sorten: " + sort + " og den samlede mængde i kg er: " + mængdeKg;
        return s;
    }

}

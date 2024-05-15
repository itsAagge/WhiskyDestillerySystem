package application.model;

import java.time.LocalDate;

public class Korn {
    private String markNavn;
    private String sort;
    private LocalDate høstDato;
    private int mængdeKg;

    //Constructor
    public Korn(String markNavn, String sort, LocalDate høstDato, int mængdeKg) {
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

    public int getMængdeKg() {
        return mængdeKg;
    }


    //To string og beskrivelses metoder
    @Override
    public String toString() {
        return this.høstDato.toString() + ". Sort: " + this.sort + " fra " + this.markNavn;
    }
}

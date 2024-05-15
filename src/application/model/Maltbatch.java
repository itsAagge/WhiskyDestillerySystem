package application.model;

import java.time.LocalDate;

public class Maltbatch {
    private double mængdeL;
    private LocalDate gæringStart;
    private LocalDate gæringSlut;
    private String gærType;
    private Korn korn;


    //Constructor
    public Maltbatch(double mængdeL, LocalDate gæringStart, LocalDate gæringSlut, String gærType, Korn korn) {
        this.mængdeL = mængdeL;
        this.gæringStart = gæringStart;
        this.gæringSlut = gæringSlut;
        this.gærType = gærType;
        this.korn = korn;
    }


    //Getters
    public double getMængdeL() {
        return mængdeL;
    }

    public LocalDate getGæringStart() {
        return gæringStart;
    }

    public LocalDate getGæringSlut() {
        return gæringSlut;
    }

    public String getGærType() {
        return gærType;
    }

    public Korn getKorn() {
        return korn;
    }


    //To string og beskriveles metoder
    @Override
    public String toString() {
        return this.gæringSlut.toString() + ". Korn: " + this.korn.getSort() + ". Gær: " + this.gærType;
    }

    public String getBeskrivelse() {
        String s = "Denne maltbatch har " + mængdeL + "L og er lavet på kornet: " + korn.getSort() + ". Den startede med at gære den: " + gæringStart + " og sluttede med at gære den: " + gæringSlut + " og gærTypen er: " + gærType;
        return s;
    }
}

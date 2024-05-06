package application.model;

import java.time.LocalDate;

public class Maltbatch {
    private double mængdeL;
    private LocalDate gæringStart;
    private LocalDate gæringSlut;
    private String gærType;

    public Maltbatch(double mængdeL, LocalDate gæringStart, LocalDate gæringSlut, String gærType) {
        this.mængdeL = mængdeL;
        this.gæringStart = gæringStart;
        this.gæringSlut = gæringSlut;
        this.gærType = gærType;
    }

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
}

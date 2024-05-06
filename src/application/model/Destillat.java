package application.model;

import java.time.LocalDate;

public class Destillat {
    private double mængdeL;
    private double alkoholprocent;
    private String medarbejder;
    private String rygemateriale;
    private String kommentar;
    private LocalDate destilleringsdato;

    public Destillat(double mængdeL, double alkoholprocent, String medarbejder, String rygemateriale, String kommentar, LocalDate destilleringsdato) {
        this.mængdeL = mængdeL;
        this.alkoholprocent = alkoholprocent;
        this.medarbejder = medarbejder;
        this.rygemateriale = rygemateriale;
        this.kommentar = kommentar;
        this.destilleringsdato = destilleringsdato;
    }

    public double getMængdeL() {
        return mængdeL;
    }

    public double getAlkoholprocent() {
        return alkoholprocent;
    }

    public String getMedarbejder() {
        return medarbejder;
    }

    public String getRygemateriale() {
        return rygemateriale;
    }

    public String getKommentar() {
        return kommentar;
    }

    public LocalDate getDestilleringsdato() {
        return destilleringsdato;
    }
}

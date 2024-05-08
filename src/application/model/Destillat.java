package application.model;

import java.time.LocalDate;

public class Destillat {
    private String spiritBatchNr;
    private double mængdeL;
    private double alkoholprocent;
    private String medarbejder;
    private String rygemateriale;
    private String kommentar;
    private LocalDate destilleringsdato;
    private Maltbatch maltbatch;

    public Destillat(String spiritBatchNr, double mængdeL, double alkoholprocent, String medarbejder, String rygemateriale, String kommentar, LocalDate destilleringsdato, Maltbatch maltbatch) {
        this.spiritBatchNr = spiritBatchNr;
        this.mængdeL = mængdeL;
        this.alkoholprocent = alkoholprocent;
        this.medarbejder = medarbejder;
        this.rygemateriale = rygemateriale;
        this.kommentar = kommentar;
        this.destilleringsdato = destilleringsdato;
        this.maltbatch = maltbatch;
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

    public Maltbatch getMaltbatch() {
        return maltbatch;
    }

    @Override
    public String toString() {
        return "Destillat " + this.spiritBatchNr;
    }
}

package application.model;

import java.time.LocalDate;
import java.util.ArrayList;

public class Destillat implements Logable {
    private String spiritBatchNr;
    private double mængdeL;
    private double alkoholprocent;
    private String medarbejder;
    private String rygemateriale;
    private String kommentar;
    private LocalDate destilleringsdato;
    private Maltbatch maltbatch;
    private ArrayList<Påfyldning> påfyldninger;

    //Constructor
    public Destillat(String spiritBatchNr, double mængdeL, double alkoholprocent, String medarbejder, String rygemateriale, String kommentar, LocalDate destilleringsdato, Maltbatch maltbatch) {
        this.spiritBatchNr = spiritBatchNr;
        this.mængdeL = mængdeL;
        this.alkoholprocent = alkoholprocent;
        this.medarbejder = medarbejder;
        this.rygemateriale = rygemateriale;
        this.kommentar = kommentar;
        this.destilleringsdato = destilleringsdato;
        this.maltbatch = maltbatch;
        this.påfyldninger = new ArrayList<>();
    }

    //Tilføjer en påfyldning til destillatet
    public void addPåfyldning(Påfyldning påfyldning) {
        if (!påfyldninger.contains(påfyldning)) {
            påfyldninger.add(påfyldning);
        }
    }

    //Getters
    public double getMængdeL() {
        return mængdeL;
    }

    public String getSpiritBatchNr() {
        return spiritBatchNr;
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

    public ArrayList<Påfyldning> getPåfyldninger() {
        return new ArrayList<>(påfyldninger);
    }


    //Setters
    public void setSpiritBatchNr(String spiritBatchNr) {
        this.spiritBatchNr = spiritBatchNr;
    }

    public void setMængdeL(double mængdeL) {
        this.mængdeL = mængdeL;
    }

    public void setAlkoholprocent(double alkoholprocent) {
        this.alkoholprocent = alkoholprocent;
    }

    public void setMedarbejder(String medarbejder) {
        this.medarbejder = medarbejder;
    }

    public void setRygemateriale(String rygemateriale) {
        this.rygemateriale = rygemateriale;
    }

    public void setKommentar(String kommentar) {
        this.kommentar = kommentar;
    }

    public void setDestilleringsdato(LocalDate destilleringsdato) {
        this.destilleringsdato = destilleringsdato;
    }

    public void setMaltbatch(Maltbatch maltbatch) {
        this.maltbatch = maltbatch;
    }

    //To string og beskrivelses metoder
    @Override
    public String toString() {
        return "Destillat " + this.spiritBatchNr + ". Tilbage: " + this.mængdeTilbage() + "L";
    }

    @Override
    public String getFileName() {
        return "Destillat-" + this.spiritBatchNr + "_Destilleringsdato-" + this.destilleringsdato + "_Mængde-" + this.mængdeL + "L";
    }

    @Override
    public String getBeskrivelse() {
        String s = "Dette destillat er blevet destilleret den. " + this.destilleringsdato + " og den indeholder " + this.mængdeL + " liter, med en alkohol procent på " + this.alkoholprocent
                + ". Denne destillering stammer fra en mark ved navn " + this.maltbatch.getKorn().getMarkNavn() + ". Denne korn sort er " + this.maltbatch.getKorn().getSort()
                + " og som er høstet den. " + this.maltbatch.getKorn().getHøstDato() + ". Denne korn er blevet taget til et malteri, hvor det er blevet maltet ved hjælp af gærtypen " + maltbatch.getGærType() + ", og gæret fra: " + maltbatch.getGæringStart() + " til " + maltbatch.getGæringSlut() + ".";
        if (rygemateriale != null) {
            s += " Whiskeyen er blevet røget på " + rygemateriale + ".";
        }
        return s;
    }

    public String getBeskrivelseTilListView() { //Til listviewet i systemet
        String s = this.getBeskrivelse();
        if (kommentar != null) {
            s += "\n" + kommentar;
        }
        return s;
    }

    //Finder den resterende mængde af destillatet ud fra hvor meget der er brugt i hver påfyldning
    public double mængdeTilbage() {
        double mængdeTilbage = this.mængdeL;
        for (Påfyldning påfyldning : påfyldninger) {
            mængdeTilbage -= påfyldning.getDestillatMængder().get(this);
        }
        return mængdeTilbage;
    }
}

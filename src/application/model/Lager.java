package application.model;

import java.util.ArrayList;

public class Lager {
    private String adresse;
    private String kvadratmeter;
    private ArrayList<Reol> reoler;

    public Lager(String adresse, String kvadratmeter) {
        this.adresse = adresse;
        this.kvadratmeter = kvadratmeter;
        this.reoler = new ArrayList<>();
    }

    public String getAdresse() {
        return adresse;
    }

    public String getKvadratmeter() {
        return kvadratmeter;
    }

    public void tilføjReol(int antalHylder, int maxPladsPåHylderne) {
        this.reoler.add(new Reol(this.reoler.size() + 1, antalHylder, maxPladsPåHylderne,this));
    }
}

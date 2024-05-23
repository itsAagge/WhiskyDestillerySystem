package application.model;

import java.util.ArrayList;
import java.util.List;

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

    public List<Reol> getReoler() {
        return new ArrayList<>(reoler);
    }

    public void tilføjReol(int antalReoler, int antalHylder, int maxPladsPåHylderne) {
        for (int i = 0; i < antalReoler; i++) {
            this.reoler.add(new Reol(this.reoler.size() + 1, antalHylder, maxPladsPåHylderne, this));
        }
    }

    @Override
    public String toString() {
        return adresse + ", Areal: " + kvadratmeter + " m2";
    }
}

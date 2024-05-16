package application.model;

import java.util.ArrayList;
import java.util.List;

public class Reol {
    private Lager lager;
    private int nr;
    private ArrayList<Hylde> hylder;

    Reol(int nr, int antalHylder, int maxPladsPåHylderne, Lager lager) {
        this.nr = nr;
        this.lager = lager;
        this.hylder = new ArrayList<>();
        for (int i = 1; i <= antalHylder; i++) {
            this.hylder.add(new Hylde(i, maxPladsPåHylderne, this));
        }
    }

    public List<Hylde> getHylder() {
        return new ArrayList<>(hylder);
    }

    public Lager getLager() {
        return lager;
    }

    public int getNr() {
        return nr;
    }

    public void setNr(int nr) {
        this.nr = nr;
    }

    @Override
    public String toString() {
        return "Reol nr: " + nr;
    }
}

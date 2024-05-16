package application.model;

import java.util.ArrayList;

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

    public Lager getLager() {
        return lager;
    }

    public int getNr() {
        return nr;
    }
}

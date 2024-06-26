package application.model;

import java.util.ArrayList;

public class Hylde {
    private Reol reol;
    private int nr;
    private int maxPlads;
    private ArrayList<Fad> fade;

    Hylde(int nr, int maxPlads, Reol reol) {
        this.nr = nr;
        this.maxPlads = maxPlads;
        this.reol = reol;
        this.fade = new ArrayList<>();
    }

    public ArrayList<Fad> getFade() {
        return fade;
    }

    public void addFad(Fad fad) {
        if (!this.fade.contains(fad)) {
            this.fade.add(fad);
        }
    }

    public void removeFad(Fad fad) {
        if (this.fade.contains(fad)) {
            this.fade.remove(fad);
        }
    }

    public boolean erLedigPlads(int antal) {
        return this.fade.size() + antal <= maxPlads;
    }

    @Override
    public String toString() {
        return "Hylde nr.: " + nr;
    }
}
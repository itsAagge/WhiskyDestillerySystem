package application.model;

import java.time.LocalDate;
import java.util.HashMap;

public class Påfyldning {
    private LocalDate påfyldningsDato;
    private LocalDate færdigDato;
    private boolean flyttetFraFad;
    private HashMap<Destillat, Double> destillatMængder = new HashMap<>();
    private Fad fad;

    public Påfyldning(LocalDate påfyldningsDato, LocalDate færdigDato, boolean flyttetFraFad, Fad fad) {
        this.påfyldningsDato = påfyldningsDato;
        this.færdigDato = færdigDato;
        this.flyttetFraFad = flyttetFraFad;
        this.fad = fad;
    }

    public void tilføjDestillatMedMængde(Destillat destillat, double mængde) {
        //TODO - check om der er plads i fadet
        if(tjekDestillatMængde(destillat) >= mængde) {
            destillatMængder.put(destillat, mængde);
        }
        else {
            throw new IllegalArgumentException("Der er ikke nok destillat tilbage");
        }
    }

    public double tjekDestillatMængde(Destillat destillat) {
        return destillat.getMængdeL();
    }

    public double tjekFadPLads() {
        return fad.getStørrelseL();
    }


}

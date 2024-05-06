package application.controller;

import application.model.Destillat;
import application.model.Fad;
import application.model.Korn;
import application.model.Maltbatch;
import storage.Storage;

import java.time.LocalDate;

public class Controller {






    public static Destillat opretDestillat(double mængdeL, double alkoholprocent, String medarbejder, String rygemateriale, String kommentar, LocalDate destilleringsdato) {
        Destillat destillat = new Destillat(mængdeL, alkoholprocent, medarbejder, rygemateriale, kommentar, destilleringsdato);
        Storage.tilføjDestillat(destillat);
        return destillat;
    }

    public static Fad opretFad(String fraLand, String tidligereIndhold, int størrelseL, String træType, double alderAfTidligereIndhold, int antalGangeBrugt) {
        Fad fad = new Fad(fraLand, tidligereIndhold, størrelseL, træType, alderAfTidligereIndhold, antalGangeBrugt);
        Storage.tilføjFad(fad);
        return fad;

    }

    public static Korn opretKorn(String markNavn, String sort, LocalDate høstDato, int mængdeKg) {
        Korn korn = new Korn(markNavn, sort, høstDato, mængdeKg);
        Storage.tilføjKorn(korn);
        return korn;
    }

    public static Maltbatch opretMaltbatch(double mængdeL, LocalDate gæringStart, LocalDate gæringSlut, String gærType) {
        Maltbatch maltbatch = new Maltbatch(mængdeL, gæringStart, gæringSlut, gærType);
        Storage.tilføjMaltBatch(maltbatch);
        return maltbatch;
    }

}

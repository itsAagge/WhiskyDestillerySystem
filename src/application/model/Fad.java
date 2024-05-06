package application.model;

public class Fad {
    private static int antalFade = 0;
    private int fadNr;
    private String fraLand;
    private String tidligereIndhold;
    private int størrelseL;
    private String træType;
    private double alderAfTidligereIndhold;
    private int antalGangeBrugt;
    private boolean erFyldt;

    public Fad(String fraLand, String tidligereIndhold, int størrelseL, String træType, double alderAfTidligereIndhold, int antalGangeBrugt) {
        antalFade++;
        this.fadNr = antalFade;
        this.fraLand = fraLand;
        this.tidligereIndhold = tidligereIndhold;
        this.størrelseL = størrelseL;
        this.træType = træType;
        this.alderAfTidligereIndhold = alderAfTidligereIndhold;
        this.antalGangeBrugt = antalGangeBrugt;
        this.erFyldt = false;
    }

    public static int getAntalFade() {
        return antalFade;
    }

    public int getFadNr() {
        return fadNr;
    }

    public String getFraLand() {
        return fraLand;
    }

    public String getTidligereIndhold() {
        return tidligereIndhold;
    }

    public int getStørrelseL() {
        return størrelseL;
    }

    public String getTræType() {
        return træType;
    }

    public double getAlderAfTidligereIndhold() {
        return alderAfTidligereIndhold;
    }

    public int getAntalGangeBrugt() {
        return antalGangeBrugt;
    }

    public boolean erFyldt() {
        return erFyldt;
    }

    @Override
    public String toString() {
        return "Fad nr. " + this.fadNr + ". Ex-" + this.tidligereIndhold + " fad. " + this.størrelseL + " L";
    }
}

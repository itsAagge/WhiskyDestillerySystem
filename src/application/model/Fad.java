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

    public Fad(String fraLand, String tidligereIndhold, int størrelseL, String træType, double alderAfTidligereIndhold, int antalGangeBrugt) {
        antalFade++;
        this.fadNr = antalFade;
        this.fraLand = fraLand;
        this.tidligereIndhold = tidligereIndhold;
        this.størrelseL = størrelseL;
        this.træType = træType;
        this.alderAfTidligereIndhold = alderAfTidligereIndhold;
        this.antalGangeBrugt = antalGangeBrugt;
    }
}

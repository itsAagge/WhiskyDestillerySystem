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
    private boolean erAktiv;
    private String leverandør;

    public Fad(String fraLand, String tidligereIndhold, int størrelseL, String træType, double alderAfTidligereIndhold, String leverandør) {
        antalFade++;
        this.fadNr = antalFade;
        this.fraLand = fraLand;
        this.tidligereIndhold = tidligereIndhold;
        this.størrelseL = størrelseL;
        this.træType = træType;
        this.alderAfTidligereIndhold = alderAfTidligereIndhold;
        this.antalGangeBrugt = 0;
        this.erFyldt = false;
        this.erAktiv = true;
        this.leverandør = leverandør;
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

    public boolean erAktiv() {
        return erAktiv;
    }

    public String getLeverandør() {
        return leverandør;
    }

    public void setAktiv(boolean bool) {
        this.erAktiv = bool;
    }

    public void setFraLand(String fraLand) {
        this.fraLand = fraLand;
    }

    public void setTidligereIndhold(String tidligereIndhold) {
        this.tidligereIndhold = tidligereIndhold;
    }

    public void setStørrelseL(int størrelseL) {
        this.størrelseL = størrelseL;
    }

    public void setTræType(String træType) {
        this.træType = træType;
    }

    public void setAlderAfTidligereIndhold(double alderAfTidligereIndhold) {
        this.alderAfTidligereIndhold = alderAfTidligereIndhold;
    }

    public void setLeverandør(String leverandør) {
        this.leverandør = leverandør;
    }

    @Override
    public String toString() {
        return "Fad nr. " + this.fadNr + ". Ex-" + this.tidligereIndhold + " fad. " + this.størrelseL + " L";
    }

    public String beskrivelse() {
        String s = "Fad nr. " + this.fadNr + " er fra " + this.leverandør + " i " + this.fraLand + " og har tidligere indeholdt " + this.tidligereIndhold
                + ", som lå på fadet i " + this.alderAfTidligereIndhold + " år. Fadet er lavet af " + this.træType
                + " og har en størrelse på " + this.størrelseL + " liter.";
        if(!erAktiv) {
            s += "\nFadet er inaktivt";
        } else {
            s += "\nFadet er aktivt";
        }
        return s;
    }
}

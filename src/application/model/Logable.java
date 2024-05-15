package application.model;

public interface Logable { //Alle klasser, der implementere dette, kan få logget deres beskrivelse
    public String getFileName();
    public String getBeskrivelse();
}

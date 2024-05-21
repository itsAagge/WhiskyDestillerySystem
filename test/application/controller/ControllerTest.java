package application.controller;

import application.model.*;
import application.model.output.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {

    private Controller controller = Controller.getTestController();
    //ArrayLister
    private ArrayList<Fad> fade = new ArrayList<>();
    private ArrayList<Destillat> destillater = new ArrayList<>();
    private ArrayList<Påfyldning> påfyldninger = new ArrayList<>();
    private ArrayList<Double> destillatMængder = new ArrayList<>();
    //Standard påfyldningsDato
    private LocalDate påfyldningsDato = LocalDate.of(2024, 01, 01);
    //Fade
    private Fad fad1 = new Fad("Spanien", "Sherry", 100, "Eg", 3, "Proveedor");
    private Fad fad2 = new Fad("Frankig", "Bourbon", 50, "Eg", 5, "Fournisseur");
    //Oprettelse af maltbatch
    Maltbatch maltbatch = new Maltbatch(1000, LocalDate.of(2023, 12, 12), LocalDate.of(2023, 12, 20), "Gær", new Korn("Mark's mark", "Træls sort", LocalDate.of(2023, 07, 10), 10000));


    @BeforeEach
    void setUp() {
        //ArrayList til fade og tilføjelse af fad1 og fad2
        fade.add(fad1);
        fade.add(fad2);

        //Oprettelse af destillater
        Destillat d1 = new Destillat("SpritBatch1", 200.0, 80.5, "Snævar", "Tørv", "Smager godt", LocalDate.of(2023, 12, 24), maltbatch);
        Destillat d2 = new Destillat("SpiritBatch2", 300.0, 78.7, "Snævar", null, "Smager dårligt", LocalDate.of(2023, 12, 31), maltbatch);

        //Oprettelse af mængder arraylist og tilføjelse af standard mængder
        destillatMængder.add(100.0);
        destillatMængder.add(25.0);
        //ArrayList til destillater og tilføjelse af d1 og d2
        destillater.add(d1);
        destillater.add(d2);
    }

    @Test
    @DisplayName("TC1: Grænse - færdigDato efter præcis 3 år")
    void opretPåfyldninger1() {
        List<Påfyldning> påfyldninger = controller.opretPåfyldninger(fade, påfyldningsDato, LocalDate.of(2027, 01, 01), destillater, destillatMængder);

        //Tjek på om fade er fyldt nu
        boolean expected = true;
        boolean actual = fade.get(0).erFyldt() && fade.get(1).erFyldt();
        assertEquals(expected, actual);

        //Tjek om fadene begge har en påfyldning nu
        assertEquals(1, fade.get(0).getPåfyldninger().size());
        assertEquals(1, fade.get(1).getPåfyldninger().size());

        //Tjek om der er lavet 2 påfyldninger
        int expected2 = 2;
        int actual2 = påfyldninger.size();
        assertEquals(expected2, actual2);
    }



    @Test
    @DisplayName("TC2: Destillater og mængder har ikke samme længde")
    void opretPåfyldninger2() {
        destillatMængder.add(40.0);

        Throwable excepction = assertThrows(IllegalArgumentException.class,() -> controller.opretPåfyldninger(fade, påfyldningsDato, LocalDate.of(2027, 01, 01), destillater, destillatMængder));
        assertEquals("Antal mængder og destillater er ikke det samme", excepction.getMessage());
    }

    @Test
    @DisplayName("TC3: Et fad er fyldt")
    void opretPåfyldninger3() {
        fad1.setFyldt(true);

        Throwable excepction = assertThrows(IllegalArgumentException.class,() -> controller.opretPåfyldninger(fade, påfyldningsDato, LocalDate.of(2027, 01, 01), destillater, destillatMængder));
        assertEquals("Et eller flere fade er allerede fyldte", excepction.getMessage());
    }

    @Test
    @DisplayName("TC4: Et fad er ikke aktivt")
    void opretPåfyldninger4() {
        fad1.setAktiv(false);

        Throwable excepction = assertThrows(IllegalArgumentException.class,() -> controller.opretPåfyldninger(fade, påfyldningsDato, LocalDate.of(2027, 01, 01), destillater, destillatMængder));
        assertEquals("Et eller flere valgte fade er deaktiveret, og kan derfor ikke bruges", excepction.getMessage());
    }

    @Test
    @DisplayName("TC5: Ikke nok fadplads")
    void opretPåfyldninger5() {
        destillater.add(new Destillat("SpiritBatch3", 500, 90, "Snævar", null, "Smager fantastisk", LocalDate.of(2023, 10, 10), maltbatch));
        destillatMængder.add(50.0);

        Throwable excepction = assertThrows(IllegalArgumentException.class,() -> controller.opretPåfyldninger(fade, påfyldningsDato, LocalDate.of(2027, 01, 01), destillater, destillatMængder));
        assertEquals("Der er ikke nok plads i fadene til denne mængde destillater", excepction.getMessage());
    }

    @Test
    @DisplayName("TC6: Ikke nok destillat tilbage")
    void opretPåfyldninger6() {
        destillater.add(new Destillat("SpiritBatch4", 10, 90, "Snævar", null, "Smager Træls", LocalDate.of(2023, 10, 10), maltbatch));
        destillatMængder.add(20.0);

        Throwable excepction = assertThrows(IllegalArgumentException.class,() -> controller.opretPåfyldninger(fade, påfyldningsDato, LocalDate.of(2027, 01, 01), destillater, destillatMængder));
        assertEquals("Der er ikke nok destillat tilbage", excepction.getMessage());
    }

    @Test
    @DisplayName("TC7: destillat og mængde arrayet er tomt")
    void opretPåfyldninger7() {
        ArrayList<Destillat> destillater = new ArrayList<>();
        ArrayList<Double> mængder = new ArrayList<>();
        Throwable excepction = assertThrows(IllegalArgumentException.class,() -> controller.opretPåfyldninger(fade, påfyldningsDato, LocalDate.of(2027, 01, 01), destillater, mængder));
        assertEquals("Der er ikke tilføjet en mængde og et destillat", excepction.getMessage());
    }

    //TODO: Spørg Benn ind til throws i test
    @Test
    @DisplayName("Log Fad til fil")
    void udtrækBeskrivelse() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        Fad fad = new Fad("Spanien", "Sherry", 250, "Eg", 8, "Fadfirma");
        Logger logger = controller.getLoggerStrategy("Fil");
        String expected = fad.getBeskrivelse();

        controller.udtrækBeskrivelse(logger, fad);

        File file = new File("resources/beskrivelser/");
        String[] directory = file.list();
        assertNotNull(directory);

        boolean fileInDirectory = false;
        int i = 0;
        while (!fileInDirectory && i < directory.length) {
            String s = directory[i];
            if (s.equals(fad.getFileName() + ".txt")) {
                fileInDirectory = true;
            } else i++;
        }
        assertTrue(fileInDirectory);

        String actual = "";
        File file1 = new File("resources/beskrivelser/" + fad.getFileName() + ".txt");
        try (Scanner scanner = new Scanner(file1)) {
            while (scanner.hasNextLine()) {
                actual += scanner.nextLine();
            }
        } catch (Exception ignored) {}
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Udtræk beskrivelse exception på Logger")
    void udtrækBeskrivelse1() {
        Fad fad = new Fad("Spanien", "Sherry", 250, "Eg", 8, "Fadfirma");
        Logger logger = null;

        Throwable exception = assertThrows(IllegalArgumentException.class,() -> controller.udtrækBeskrivelse(logger, fad));
        assertEquals("En type logger er ikke valgt", exception.getMessage());
    }

    //TODO: Spørg Benn ind til throws i test
    @Test
    @DisplayName("Udtræk beskrivelse exception på Loggable object")
    void udtrækBeskrivelse2() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        Fad fad = null;
        Logger logger = controller.getLoggerStrategy("Fil");;

        Throwable exception = assertThrows(IllegalArgumentException.class,() -> controller.udtrækBeskrivelse(logger, fad));
        assertEquals("Et objekt er ikke valgt", exception.getMessage());
    }

    @Test
    @DisplayName("Opretter udgivelse af fad")
    void opretUdgivelse1() {
        Påfyldning påfyldning1 = controller.opretPåfyldning(LocalDate.of(2024,12,12), null, fad1, destillater, destillatMængder);
        påfyldninger.add(påfyldning1);

        ArrayList<Double> mængder = new ArrayList<>();
        mængder.add(125.0);
        Udgivelse udgivelse = controller.opretUdgivelse(0, 25000,true, LocalDate.of(2024,12,30), 80,0,"Snævar", påfyldninger, mængder);

        assertTrue(controller.getUdgivelser().contains(udgivelse));

        //TODO destillat og mængder må ikke være tomme

    }

    @Test
    @DisplayName("Opretter udgivelse af flasker")
    void opretUdgivelse2() {
        Påfyldning påfyldning1 = controller.opretPåfyldning(LocalDate.of(2024,12,12), null, fad1, destillater, destillatMængder);
        Påfyldning påfyldning2 = controller.opretPåfyldning(LocalDate.of(2024,12,30), null, fad2, destillater, destillatMængder);

        påfyldninger.add(påfyldning1);
        påfyldninger.add(påfyldning2);

        ArrayList<Double> påFyldningsMængder = new ArrayList<>();
        påFyldningsMængder.add(100.00);
        påFyldningsMængder.add(25.0);

        Udgivelse udgivelse = controller.opretUdgivelse(0.7, 1000,false, LocalDate.of(2024,12,31), 80,100,"Snævar", påfyldninger, påFyldningsMængder);

        assertTrue(controller.getUdgivelser().contains(udgivelse));

        int expected = 2;
        int actual = udgivelse.getPåfyldningsMængder().size();

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Flere mængder end påfyldning")
    void opretUdgivelse3() {
        Påfyldning påfyldning1 = controller.opretPåfyldning(LocalDate.of(2024,12,12), null, fad1, destillater, destillatMængder);
        Påfyldning påfyldning2 = controller.opretPåfyldning(LocalDate.of(2024,12,30), null, fad2, destillater, destillatMængder);

        påfyldninger.add(påfyldning1);
        påfyldninger.add(påfyldning2);

        ArrayList<Double> påFyldningsMængder = new ArrayList<>();
        påFyldningsMængder.add(100.0);
        påFyldningsMængder.add(25.0);
        påFyldningsMængder.add(40.0);

        Throwable exception = assertThrows(IllegalArgumentException.class,() -> controller.opretUdgivelse(0.7, 1000,false, LocalDate.of(2024,12,31), 80,100,"Snævar", påfyldninger, påFyldningsMængder));
        assertEquals("Antal mængder og påfyldninger er ikke det samme", exception.getMessage());
    }

    @Test
    @DisplayName("Ikke nok påfyldning tilbage til udgivelse")
    void opretUdgivelse4() {
        Påfyldning påfyldning1 = controller.opretPåfyldning(LocalDate.of(2024,12,12), null, fad1, destillater, destillatMængder);
        Påfyldning påfyldning2 = controller.opretPåfyldning(LocalDate.of(2024,12,30), null, fad2, destillater, destillatMængder);

        påfyldninger.add(påfyldning1);
        påfyldninger.add(påfyldning2);

        ArrayList<Double> påFyldningsMængder = new ArrayList<>();
        påFyldningsMængder.add(1000.00);
        påFyldningsMængder.add(25.0);
        Throwable exception = assertThrows(IllegalArgumentException.class,() -> controller.opretUdgivelse(0.7, 1000,false, LocalDate.of(2024,12,31), 80,100,"Snævar", påfyldninger, påFyldningsMængder));
        assertEquals("Der er ikke nok påfyldning tilbage til dette", exception.getMessage());
    }

    @Test
    @DisplayName("Ikke nok påfyldning tilbage til udgivelse")
    void opretUdgivelse5() {
        ArrayList<Påfyldning> påfyldninger = new ArrayList<>();
        ArrayList<Double> mængder = new ArrayList<>();
        Throwable exception = assertThrows(IllegalArgumentException.class,() -> controller.opretUdgivelse(0.7, 1000,false, LocalDate.of(2024,12,31), 80,100,"Snævar", påfyldninger, mængder));
        assertEquals("Der er ikke tilføjet en mængde og en påfyldning", exception.getMessage());
    }
}
package controller;

import application.controller.Controller;
import application.model.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {

    private Controller controller = Controller.getTestController();

    //ArrayLister
    private ArrayList<Fad> fade = new ArrayList<>();
    private ArrayList<Destillat> destillater = new ArrayList<>();
    private ArrayList<Double> mængder = new ArrayList<>();

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
        Destillat d1 = new Destillat("SpritBatch1", 200, 80.5, "Snævar", "Tørv", "Smager godt", LocalDate.of(2023, 12, 24), maltbatch);
        Destillat d2 = new Destillat("SpiritBatch2", 300, 78.7, "Snævar", null, "Smager dårligt", LocalDate.of(2023, 12, 31), maltbatch);

        //ArrayList til destillater og tilføjelse af d1 og d2
        destillater.add(d1);
        destillater.add(d2);

        //Oprettelse af mængder arraylist og tilføjelse af standard mængder
        mængder.add(100.0);
        mængder.add(25.0);


    }

    @Test
    @DisplayName("TC1: Grænse - færdigDato efter præcis 3 år")
    void opretPåfyldninger1() {
        List<Påfyldning> påfyldninger = controller.opretPåfyldninger(fade, påfyldningsDato, LocalDate.of(2027, 01, 01), destillater, mængder);

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
        mængder.add(40.0);

        Throwable excepction = assertThrows(IllegalArgumentException.class,() -> controller.opretPåfyldninger(fade, påfyldningsDato, LocalDate.of(2027, 01, 01), destillater, mængder));
        assertEquals("Antal mængder og destillater er ikke det samme", excepction.getMessage());

    }

    @Test
    @DisplayName("TC3: Et fad er fyldt")
    void opretPåfyldninger3() {
        fad1.setFyldt(true);

        Throwable excepction = assertThrows(IllegalArgumentException.class,() -> controller.opretPåfyldninger(fade, påfyldningsDato, LocalDate.of(2027, 01, 01), destillater, mængder));
        assertEquals("Et eller flere fade er allerede fyldte", excepction.getMessage());


    }

    @Test
    @DisplayName("TC4: Et fad er ikke aktivt")
    void opretPåfyldninger4() {
        fad1.setAktiv(false);

        Throwable excepction = assertThrows(IllegalArgumentException.class,() -> controller.opretPåfyldninger(fade, påfyldningsDato, LocalDate.of(2027, 01, 01), destillater, mængder));
        assertEquals("Et eller flere valgte fade er deaktiveret, og kan derfor ikke bruges", excepction.getMessage());

    }

    @Test
    @DisplayName("TC5: Ikke nok fadplads")
    void opretPåfyldninger5() {
        destillater.add(new Destillat("SpiritBatch3", 500, 90, "Snævar", null, "Smager fantastisk", LocalDate.of(2023, 10, 10), maltbatch));
        mængder.add(50.0);

        Throwable excepction = assertThrows(IllegalArgumentException.class,() -> controller.opretPåfyldninger(fade, påfyldningsDato, LocalDate.of(2027, 01, 01), destillater, mængder));
        assertEquals("Der er ikke nok plads i fadene til denne mængde destillater", excepction.getMessage());

    }

    @Test
    @DisplayName("TC6: Ikke nok destillat tilbage")
    void opretPåfyldninger6() {
        destillater.add(new Destillat("SpiritBatch4", 10, 90, "Snævar", null, "Smager Træls", LocalDate.of(2023, 10, 10), maltbatch));
        mængder.add(20.0);

        Throwable excepction = assertThrows(IllegalArgumentException.class,() -> controller.opretPåfyldninger(fade, påfyldningsDato, LocalDate.of(2027, 01, 01), destillater, mængder));
        assertEquals("Der er ikke nok destillat tilbage", excepction.getMessage());

    }

    @Test
    void opretUdgivelse() {
    }

    @Test
    void udtrækBeskrivelse() {
    }
}
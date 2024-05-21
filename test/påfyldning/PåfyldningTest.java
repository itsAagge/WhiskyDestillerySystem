package påfyldning;

import application.controller.Controller;
import application.model.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class PåfyldningTest {

    private Controller controller = Controller.getTestController();
    private Påfyldning påfyldning;
    private Fad fad;

    @BeforeEach
    void setUp() {
        fad = new Fad("Spanien", "Sherry", 100, "Eg", 3, "leverandør");
        ArrayList<Destillat> destillats = new ArrayList<>();
        ArrayList<Double> mængder = new ArrayList<>();
        påfyldning = controller.opretPåfyldning(LocalDate.of(2024, 1, 1), null, fad, destillats, mængder);
    }

    @Test
    @DisplayName("TC1")
    void flytPåfyldning() {
        Fad fad2 = new Fad("Frankrig", "Bourbon", 200, "Eg", 5, "leverandør2");
        påfyldning.flytPåfyldning(fad2);

        Fad expected = fad2;
        Fad actual = påfyldning.getFade().getLast();

        assertEquals(expected, actual);
        assertTrue(fad2.erFyldt());
        assertFalse(fad.erFyldt());

    }

    @Test
    void tilføjUdgivelse() {
        ArrayList<Påfyldning> påfyldninger = new ArrayList<>();
        ArrayList<Double> mængder = new ArrayList<>();
        Udgivelse udgivelse = new Udgivelse(100, 1000, false, LocalDate.of(2026, 12, 24), 80, 100, "Snæver", påfyldninger, mængder);
        påfyldning.tilføjUdgivelse(udgivelse);

        assertTrue(påfyldning.getUdgivelser().contains(udgivelse));
        assertEquals(1, påfyldning.getUdgivelser().size());
    }

    @DisplayName("tilføjDestillatMedMængde TC1")
    @Test
    void tilføjDestillatMedMængde1() {
        ArrayList<Destillat> destillater = new ArrayList<>();
        ArrayList<Double> mængder = new ArrayList<>();
        Korn korn1 = controller.opretKorn("Mark 1", "Byg",LocalDate.of(2023,11,30), 200);
        Korn korn2 = controller.opretKorn("Mark 2", "Byg",LocalDate.of(2023,12,10), 200);
        Maltbatch maltbatch1 = controller.opretMaltbatch(200, LocalDate.of(2023, 12, 1), LocalDate.of(2023,12,12), "gær1", korn1);
        Maltbatch maltbatch2 = controller.opretMaltbatch(200, LocalDate.of(2023, 12, 12), LocalDate.of(2023,12,25), "gær1", korn2);
        Destillat destillat1 = controller.opretDestillat("1", 200, 80.5, "Snæver", "Tørv", "Smager godt", LocalDate.of(2023,12,24), maltbatch1);
        Destillat destillat2 = controller.opretDestillat("2", 300, 78.7, "Snævar", null, "Smager dårligt", LocalDate.of(2023, 12, 30), maltbatch2);
        destillater.add(destillat1);
        destillater.add(destillat2);
        mængder.add(100.0);
        mængder.add(25.0);

        påfyldning.tilføjDestillatMedMængde(destillater, mængder);
        assertEquals(destillater.size(), mængder.size());
        assertTrue(påfyldning.getDestillatMængder().containsKey(destillat1));
        assertTrue(påfyldning.getDestillatMængder().containsKey(destillat2));
        assertTrue(påfyldning.getDestillatMængder().containsValue(100.0));
        assertTrue(påfyldning.getDestillatMængder().containsValue(25.0));

    }

    @DisplayName("tilføjDestillatMedMængde: mængden er nu -100")
    @Test
    void tilføjDestillatMedMængde2() {
        ArrayList<Destillat> destillater = new ArrayList<>();
        ArrayList<Double> mængder = new ArrayList<>();
        Korn korn1 = controller.opretKorn("Mark 1", "Byg",LocalDate.of(2023,11,30), 200);
        Korn korn2 = controller.opretKorn("Mark 2", "Byg",LocalDate.of(2023,12,10), 200);
        Maltbatch maltbatch1 = controller.opretMaltbatch(200, LocalDate.of(2023, 12, 1), LocalDate.of(2023,12,12), "gær1", korn1);
        Maltbatch maltbatch2 = controller.opretMaltbatch(200, LocalDate.of(2023, 12, 12), LocalDate.of(2023,12,25), "gær1", korn2);
        Destillat destillat1 = controller.opretDestillat("1", 200, 80.5, "Snæver", "Tørv", "Smager godt", LocalDate.of(2023,12,24), maltbatch1);
        Destillat destillat2 = controller.opretDestillat("2", 300, 78.7, "Snævar", null, "Smager dårligt", LocalDate.of(2023, 12, 30), maltbatch2);
        destillater.add(destillat1);
        destillater.add(destillat2);
        mængder.add(-100.0);
        mængder.add(25.0);

        Throwable excepction = assertThrows(IllegalArgumentException.class,() -> påfyldning.tilføjDestillatMedMængde(destillater, mængder));
        assertEquals("Mængder må ikke være under 0", excepction.getMessage());

    }

    @DisplayName("mængdeTilbage TC1: mængdetilbage = 125")
    @Test
    void mængdeTilbage1() {
        ArrayList<Destillat> destillater = new ArrayList<>();
        ArrayList<Double> mængder = new ArrayList<>();
        Korn korn1 = controller.opretKorn("Mark 1", "Byg",LocalDate.of(2023,11,30), 200);
        Korn korn2 = controller.opretKorn("Mark 2", "Byg",LocalDate.of(2023,12,10), 200);
        Maltbatch maltbatch1 = controller.opretMaltbatch(200, LocalDate.of(2023, 12, 1), LocalDate.of(2023,12,12), "gær1", korn1);
        Maltbatch maltbatch2 = controller.opretMaltbatch(200, LocalDate.of(2023, 12, 12), LocalDate.of(2023,12,25), "gær1", korn2);
        Destillat destillat1 = controller.opretDestillat("1", 200, 80.5, "Snæver", "Tørv", "Smager godt", LocalDate.of(2023,12,24), maltbatch1);
        Destillat destillat2 = controller.opretDestillat("2", 300, 78.7, "Snævar", null, "Smager dårligt", LocalDate.of(2023, 12, 30), maltbatch2);
        destillater.add(destillat1);
        destillater.add(destillat2);
        mængder.add(100.0);
        mængder.add(25.0);
        påfyldning.tilføjDestillatMedMængde(destillater, mængder);


        ArrayList<Påfyldning> påfyldninger = new ArrayList<>();
        ArrayList<Double> mængder2 = new ArrayList<>();
        påfyldninger.add(påfyldning);
        mængder2.add(0.0);
        Udgivelse udgivelse = new Udgivelse(0.7, 1000, false, LocalDate.of(2026, 12, 24), 80, 100, "Snæver", påfyldninger, mængder);
        påfyldning.tilføjUdgivelse(udgivelse);
        udgivelse.tilføjPåfyldningmedMængde(påfyldninger, mængder2);

        double expected = påfyldning.mængdeTilbage();
        double actual = 125;

        assertEquals(expected, actual);

    }

    @DisplayName("mængdeTilbage TC2: Der er udgivet 50L")
    @Test
    void mængdeTilbage2() {
        ArrayList<Destillat> destillater = new ArrayList<>();
        ArrayList<Double> mængder = new ArrayList<>();
        Korn korn1 = controller.opretKorn("Mark 1", "Byg",LocalDate.of(2023,11,30), 200);
        Korn korn2 = controller.opretKorn("Mark 2", "Byg",LocalDate.of(2023,12,10), 200);
        Maltbatch maltbatch1 = controller.opretMaltbatch(200, LocalDate.of(2023, 12, 1), LocalDate.of(2023,12,12), "gær1", korn1);
        Maltbatch maltbatch2 = controller.opretMaltbatch(200, LocalDate.of(2023, 12, 12), LocalDate.of(2023,12,25), "gær1", korn2);
        Destillat destillat1 = controller.opretDestillat("1", 200, 80.5, "Snæver", "Tørv", "Smager godt", LocalDate.of(2023,12,24), maltbatch1);
        Destillat destillat2 = controller.opretDestillat("2", 300, 78.7, "Snævar", null, "Smager dårligt", LocalDate.of(2023, 12, 30), maltbatch2);
        destillater.add(destillat1);
        destillater.add(destillat2);
        mængder.add(100.0);
        mængder.add(25.0);
        påfyldning.tilføjDestillatMedMængde(destillater, mængder);


        ArrayList<Påfyldning> påfyldninger = new ArrayList<>();
        ArrayList<Double> mængder2 = new ArrayList<>();
        påfyldninger.add(påfyldning);
        mængder2.add(50.0);
        Udgivelse udgivelse = new Udgivelse(0.7, 1000, false, LocalDate.of(2026, 12, 24), 80, 100, "Snæver", påfyldninger, mængder);
        påfyldning.tilføjUdgivelse(udgivelse);
        udgivelse.tilføjPåfyldningmedMængde(påfyldninger, mængder2);

        double expected = påfyldning.mængdeTilbage();
        double actual = 75;

        assertEquals(expected, actual);

    }

    @DisplayName("mængdeTilbage TC3: 0 mængde tilbage")
    @Test
    void mængdeTilbage3() {
        ArrayList<Destillat> destillater = new ArrayList<>();
        ArrayList<Double> mængder = new ArrayList<>();
        Korn korn1 = controller.opretKorn("Mark 1", "Byg",LocalDate.of(2023,11,30), 200);
        Korn korn2 = controller.opretKorn("Mark 2", "Byg",LocalDate.of(2023,12,10), 200);
        Maltbatch maltbatch1 = controller.opretMaltbatch(200, LocalDate.of(2023, 12, 1), LocalDate.of(2023,12,12), "gær1", korn1);
        Maltbatch maltbatch2 = controller.opretMaltbatch(200, LocalDate.of(2023, 12, 12), LocalDate.of(2023,12,25), "gær1", korn2);
        Destillat destillat1 = controller.opretDestillat("1", 200, 80.5, "Snæver", "Tørv", "Smager godt", LocalDate.of(2023,12,24), maltbatch1);
        Destillat destillat2 = controller.opretDestillat("2", 300, 78.7, "Snævar", null, "Smager dårligt", LocalDate.of(2023, 12, 30), maltbatch2);
        destillater.add(destillat1);
        destillater.add(destillat2);
        mængder.add(100.0);
        mængder.add(25.0);
        påfyldning.tilføjDestillatMedMængde(destillater, mængder);


        ArrayList<Påfyldning> påfyldninger = new ArrayList<>();
        ArrayList<Double> mængder2 = new ArrayList<>();
        påfyldninger.add(påfyldning);
        mængder2.add(125.0);
        Udgivelse udgivelse = new Udgivelse(0.7, 1000, false, LocalDate.of(2026, 12, 24), 80, 100, "Snæver", påfyldninger, mængder);
        påfyldning.tilføjUdgivelse(udgivelse);
        udgivelse.tilføjPåfyldningmedMængde(påfyldninger, mængder2);

        double expected = påfyldning.mængdeTilbage();
        double actual = 0.0;

        assertEquals(expected, actual);

    }
}
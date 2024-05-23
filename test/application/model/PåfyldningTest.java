package application.model;

import application.controller.Controller;
import application.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PåfyldningTest {

    private Controller controller = Controller.getTestController();
    private List<Påfyldning> påfyldninger;
    private List<Fad> fade;
    private Fad fad;

    @BeforeEach
    void setUp() {
        fad = new Fad("Spanien", "Sherry", 200, "Eg", 3, "leverandør");
        fade = new ArrayList<>();
        fade.add(fad);
        Maltbatch maltbatch = new Maltbatch(1000, LocalDate.of(2023, 12, 12), LocalDate.of(2023, 12, 20), "Gær", new Korn("Mark's mark", "Træls sort", LocalDate.of(2023, 07, 10), 10000));
        Destillat d1 = new Destillat("SpritBatch1", 200.0, 80.5, "Snævar", "Tørv", "Smager godt", LocalDate.of(2023, 12, 24), maltbatch);
        Destillat d2 = new Destillat("SpiritBatch2", 300.0, 78.7, "Snævar", null, "Smager dårligt", LocalDate.of(2023, 12, 31), maltbatch);
        ArrayList<Destillat> destillater = new ArrayList<>();
        ArrayList<Double> mængder = new ArrayList<>();
        destillater.add(d1);
        destillater.add(d2);
        mængder.add(100.0);
        mængder.add(25.0);
        påfyldninger = controller.opretPåfyldninger(fade, LocalDate.of(2024, 1, 1), null, destillater, mængder);
    }

    @Test
    @DisplayName("TC1")
    void flytPåfyldning() {
        Fad fad2 = new Fad("Frankrig", "Bourbon", 200, "Eg", 5, "leverandør2");
        fade.add(fad2);
        påfyldninger.getFirst().flytFad(fad2);

        Fad expected = fad2;
        Fad actual = påfyldninger.getFirst().getFade().getLast();

        assertEquals(expected, actual);
        assertTrue(fad2.erFyldt());
        assertFalse(fad.erFyldt());

    }


    @Test
    void tilføjUdgivelse() {
        ArrayList<Påfyldning> påfyldninger = new ArrayList<>();
        ArrayList<Double> mængder = new ArrayList<>();
        Udgivelse udgivelse = new Udgivelse(100, 1000, false, LocalDate.of(2026, 12, 24), 80, 100, "Snæver", "Sall whisky kilde", 10, påfyldninger, mængder);
        this.påfyldninger.getFirst().tilføjUdgivelse(udgivelse);

        assertTrue(this.påfyldninger.getFirst().getUdgivelser().contains(udgivelse));
        assertEquals(1, this.påfyldninger.getFirst().getUdgivelser().size());
    }

    @DisplayName("tilføjDestillatMedMængde TC1")
    @Test
    void tilføjDestillatMedMængde1() {
        ArrayList<Destillat> destillater = new ArrayList<>();
        ArrayList<Double> mængder = new ArrayList<>();
        Korn korn1 = controller.opretKorn("Mark 1", "Byg", LocalDate.of(2023, 11, 30), 200);
        Korn korn2 = controller.opretKorn("Mark 2", "Byg", LocalDate.of(2023, 12, 10), 200);
        Maltbatch maltbatch1 = controller.opretMaltbatch(200, LocalDate.of(2023, 12, 1), LocalDate.of(2023, 12, 12), "gær1", korn1);
        Maltbatch maltbatch2 = controller.opretMaltbatch(200, LocalDate.of(2023, 12, 12), LocalDate.of(2023, 12, 25), "gær1", korn2);
        Destillat destillat1 = controller.opretDestillat("1", 200, 80.5, "Snæver", "Tørv", "Smager godt", LocalDate.of(2023, 12, 24), maltbatch1);
        Destillat destillat2 = controller.opretDestillat("2", 200, 78.7, "Snævar", null, "Smager dårligt", LocalDate.of(2023, 12, 30), maltbatch2);
        destillater.add(destillat1);
        destillater.add(destillat2);
        mængder.add(100.0);
        mængder.add(25.0);

        påfyldninger.getFirst().tilføjDestillatMedMængde(destillater, mængder);
        assertEquals(destillater.size(), mængder.size());
        assertTrue(påfyldninger.getFirst().getDestillatMængder().containsKey(destillat1));
        assertTrue(påfyldninger.getFirst().getDestillatMængder().containsKey(destillat2));
        assertTrue(påfyldninger.getFirst().getDestillatMængder().containsValue(100.0));
        assertTrue(påfyldninger.getFirst().getDestillatMængder().containsValue(25.0));

    }

    @DisplayName("mængdeTilbage TC1: mængdetilbage = 125")
    @Test
    void mængdeTilbage1() {
        ArrayList<Påfyldning> påfyldninger = new ArrayList<>();
        ArrayList<Double> mængder = new ArrayList<>();
        påfyldninger.add(this.påfyldninger.getFirst());
        mængder.add(0.0);
        Udgivelse udgivelse = controller.opretUdgivelse(0.7, 1000, false, LocalDate.of(2026, 12, 24), 80, 100, "Snæver", "Sall whisky kilde", 10, påfyldninger, mængder);
        this.påfyldninger.getFirst().tilføjUdgivelse(udgivelse);

        double expected = 125;
        double actual = this.påfyldninger.getFirst().mængdeTilbage();

        assertEquals(expected, actual);

    }

    @DisplayName("mængdeTilbage TC2: Der er udgivet 50L")
    @Test
    void mængdeTilbage2() {

        ArrayList<Påfyldning> påfyldninger = new ArrayList<>();
        ArrayList<Double> mængder = new ArrayList<>();
        påfyldninger.add(this.påfyldninger.getFirst());
        mængder.add(50.0);
        Udgivelse udgivelse = controller.opretUdgivelse(0.7, 1000, false, LocalDate.of(2026, 12, 24), 80, 100, "Snæver", "Sall whisky kilde", 10, påfyldninger, mængder);
        this.påfyldninger.getFirst().tilføjUdgivelse(udgivelse);

        double expected = this.påfyldninger.getFirst().mængdeTilbage();
        double actual = 75;

        assertEquals(expected, actual);

    }

    @DisplayName("mængdeTilbage TC3: 0 mængde tilbage")
    @Test
    void mængdeTilbage3() {

        ArrayList<Påfyldning> påfyldninger = new ArrayList<>();
        ArrayList<Double> mængder = new ArrayList<>();
        påfyldninger.add(this.påfyldninger.getFirst());
        mængder.add(125.0);
        Udgivelse udgivelse = controller.opretUdgivelse(0.7, 1000, false, LocalDate.of(2026, 12, 24), 80, 100, "Snæver", "Sall whisky kilde", 10, påfyldninger, mængder);
        this.påfyldninger.getFirst().tilføjUdgivelse(udgivelse);

        double expected = this.påfyldninger.getFirst().mængdeTilbage();
        double actual = 0.0;

        assertEquals(expected, actual);

    }

}
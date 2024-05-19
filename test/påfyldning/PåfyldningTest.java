package påfyldning;

import application.controller.Controller;
import application.model.Fad;
import application.model.Påfyldning;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class PåfyldningTest {

   Controller controller = Controller.getTestController();

    @BeforeAll
    void setUp() {
        Fad fad = new Fad("Spanien", "Sherry", 100, "Eg", 3, "leverandør");
        Påfyldning påfyldning = controller.opretPåfyldning(LocalDate.of(2024,1,1), null, fad, null, null);
    }

    @Test
    void getDestillatMængder() {

    }

    @Test
    void getFade() {
    }

    @Test
    void getPåfyldningsNr() {
    }

    @Test
    void getUdgivelser() {
    }

    @Test
    void tilføjUdgivelse() {
    }

    @Test
    void flytPåfyldning() {

    }

    @Test
    void addFad() {
    }

    @Test
    void tilføjDestillatMedMængde() {
    }

    @Test
    void testToString() {
    }

    @Test
    void getFileName() {
    }

    @Test
    void getBeskrivelse() {
    }

    @Test
    void getBeskrivelseKort() {
    }

    @Test
    void mængdeTilbage() {
    }
}
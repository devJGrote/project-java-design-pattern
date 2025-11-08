package de.inosofttech.example.gameoflife;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class SpielfeldTest {


    @Test
    void testInitialSpielfeld() {
        Spielfeld spielfeld = new Spielfeld(5, 5);
        assertAll(
            () -> assertTrue(spielfeld.getBreite() == 5),
            () -> assertTrue(spielfeld.getHoehe() == 5)
        );
    }

    @Test
    void testIstLebendigeZelle() {
        Spielfeld spielfeld = new Spielfeld(5, 5);
        spielfeld.setLebendigeZelle(2, 2);
        assertTrue(spielfeld.istLebendigeZelle(2, 2));
    }

    @Test
    void testZwanzigProzentZellenLebendig() {
        Spielfeld spielfeld = new Spielfeld(10, 10);

        spielfeld.initialLebendigeZellenProzent(20);

        

        assertTrue(spielfeld.getLebendigeZellen() == 20);
    }

}

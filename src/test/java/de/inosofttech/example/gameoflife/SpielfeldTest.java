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

    @Test
    void testGetNachbarnLebendigeZellen() {
        Spielfeld spielfeld = new Spielfeld(5, 5);
        spielfeld.setLebendigeZelle(1, 1);
        spielfeld.setLebendigeZelle(1, 2);
        spielfeld.setLebendigeZelle(1, 3);
        int nachbarn = spielfeld.getNachbarnLebendigeZellen(2, 2);
        assertTrue(nachbarn == 3);
    }

    @Test
    void testInitialSSpielfeldMitLebendigenZellen() {
        int breite = 5;
        int hoehe = 5;
        int anzahlProzentLebendigeZellen = 40;
        Spielfeld spielfeld = new Spielfeld(breite, hoehe, anzahlProzentLebendigeZellen);
        assertTrue(spielfeld.getLebendigeZellen() == 10);
    }
}

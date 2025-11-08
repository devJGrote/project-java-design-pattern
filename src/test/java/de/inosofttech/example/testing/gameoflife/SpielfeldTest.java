package de.inosofttech.example.testing.gameoflife;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import de.inosofttech.example.gameoflife.Spielfeld;

public class SpielfeldTest {
    // Testfälle für die Spielfeld-Klasse für Game of Life

    @Test
    public void testInitialisierung() {
        Spielfeld spielfeld = new Spielfeld(5, 5);
        for (int x = 0; x < 5; x++) {
            for (int y = 0; y < 5; y++) {
                assertFalse(spielfeld.istLebendig(x, y), "Zelle sollte tot sein bei Initialisierung");
            }
        }
    }

    @Test
    public void testGenerationVonSpielFeld() {
        Spielfeld spielfeld = new Spielfeld(5, 5);
        spielfeld.setZelleLebendig(0, 0);
        assertTrue(spielfeld.istLebendig(0, 0), "Zelle (0,0) sollte lebendig sein vor der Generation");
        spielfeld.addGeneration(1);
        assertFalse(spielfeld.istLebendig(0, 0), "Zelle (0,0) sollte tot sein nach einer Generation");
    }

    @Test
    public void testZaehleNachbarn() {
        Spielfeld spielfeld = new Spielfeld(5, 5);
        spielfeld.setZelleLebendig(1, 0);
        spielfeld.setZelleLebendig(0, 1);
        spielfeld.setZelleLebendig(1, 1);
        spielfeld.setZelleLebendig(2, 1);
        spielfeld.setZelleLebendig(1, 2);
        int anzahl = spielfeld.zaehleNachbarn(1, 1);
        assertEquals(5, anzahl, "Zelle (1,1) sollte 5 Nachbarn haben");
    }



    @Test
    public void testZelleLebendig() {
        Spielfeld spielfeld = new Spielfeld(3, 3);
        spielfeld.setZelleLebendig(0, 0);
        assertTrue(spielfeld.istLebendig(0, 0), "Zelle (0,0) sollte lebendig sein");
    }

    @Test
    public void testZelleTodUndLebendig() {
        Spielfeld spielfeld = new Spielfeld(3, 3);
        spielfeld.setZelleLebendig(0, 0);
        assertTrue(spielfeld.istLebendig(0, 0), "Zelle (0,0) sollte lebendig sein");
        spielfeld.setZelleTod(0, 0);
        assertFalse(spielfeld.istLebendig(0, 0), "Zelle (0,0) sollte tot sein");
    }
}

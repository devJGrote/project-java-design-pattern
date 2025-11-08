package de.inosofttech.example.testing.gameoflife;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

 public class SpielfeldTest {
   Spielfeld mySpielfeld;

    @BeforeAll
    public void setup() {
        int breite = 10;
        int hoehe = 10;
        mySpielfeld = new Spielfeld(breite, hoehe);
    }

    @Test
    public void testInitialisierung() {
        int breite = 10;
        int hoehe = 10;
        Spielfeld spielfeld = new Spielfeld(breite, hoehe);
        assertEquals(breite, spielfeld.getBreite());
        assertEquals(hoehe, spielfeld.getHoehe());
    }


    @Test
    public void testStartLebenGenerieren() {
      mySpielfeld.generiereStartLeben(20);
      int lebenCount = 0;
        for (int x = 0; x < mySpielfeld.getBreite(); x++) {
            for (int y = 0; y < mySpielfeld.getHoehe(); y++) {
                if (mySpielfeld.istZelleLebendig(x, y)) {
                    lebenCount++;
                }
            }
        }

        assertEquals(20, lebenCount);
    }

}

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
}

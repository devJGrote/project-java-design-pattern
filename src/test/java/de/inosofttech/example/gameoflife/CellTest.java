package de.inosofttech.example.gameoflife;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the Cell enum.
 */
class CellTest {

    @Test
    void testCellIsAlive() {
        assertTrue(Cell.ALIVE.isAlive());
        assertFalse(Cell.DEAD.isAlive());
    }

    @Test
    void testCellSymbol() {
        assertEquals('O', Cell.ALIVE.getSymbol());
        assertEquals('.', Cell.DEAD.getSymbol());
    }
}

package de.inosofttech.example.gameoflife;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the Grid class.
 */
class GridTest {

    private Grid grid;

    @BeforeEach
    void setUp() {
        grid = new Grid(5, 5);
    }

    @Test
    void testGridInitialization() {
        assertEquals(5, grid.getWidth());
        assertEquals(5, grid.getHeight());
        assertEquals(0, grid.countAliveCells());
    }

    @Test
    void testSetAndGetCell() {
        grid.setCell(2, 2, Cell.ALIVE);
        assertEquals(Cell.ALIVE, grid.getCell(2, 2));
        assertEquals(1, grid.countAliveCells());
    }

    @Test
    void testGetCellOutOfBounds() {
        // Out of bounds should return DEAD (edges are dead)
        assertEquals(Cell.DEAD, grid.getCell(-1, 0));
        assertEquals(Cell.DEAD, grid.getCell(0, -1));
        assertEquals(Cell.DEAD, grid.getCell(5, 0));
        assertEquals(Cell.DEAD, grid.getCell(0, 5));
    }

    @Test
    void testCountAliveNeighbors() {
        // Create a simple pattern: horizontal line of 3 cells
        grid.setCell(2, 1, Cell.ALIVE);
        grid.setCell(2, 2, Cell.ALIVE);
        grid.setCell(2, 3, Cell.ALIVE);

        // Middle cell should have 2 neighbors
        assertEquals(2, grid.countAliveNeighbors(2, 2));
        
        // Side cells should have 1 neighbor each
        assertEquals(1, grid.countAliveNeighbors(2, 1));
        assertEquals(1, grid.countAliveNeighbors(2, 3));

        // Cells above and below the middle should have 3 neighbors
        assertEquals(3, grid.countAliveNeighbors(1, 2));
        assertEquals(3, grid.countAliveNeighbors(3, 2));
    }

    @Test
    void testApplyRules_Underpopulation() {
        // Alive cell with less than 2 neighbors dies
        grid.setCell(2, 2, Cell.ALIVE);
        grid.setCell(2, 1, Cell.ALIVE); // 1 neighbor
        
        assertEquals(Cell.DEAD, grid.applyRules(2, 2));
    }

    @Test
    void testApplyRules_Survival() {
        // Alive cell with 2 or 3 neighbors survives
        grid.setCell(2, 2, Cell.ALIVE);
        grid.setCell(2, 1, Cell.ALIVE);
        grid.setCell(2, 3, Cell.ALIVE); // 2 neighbors
        
        assertEquals(Cell.ALIVE, grid.applyRules(2, 2));

        grid.setCell(1, 2, Cell.ALIVE); // 3 neighbors
        assertEquals(Cell.ALIVE, grid.applyRules(2, 2));
    }

    @Test
    void testApplyRules_Overpopulation() {
        // Alive cell with more than 3 neighbors dies
        grid.setCell(2, 2, Cell.ALIVE);
        grid.setCell(1, 1, Cell.ALIVE);
        grid.setCell(1, 2, Cell.ALIVE);
        grid.setCell(1, 3, Cell.ALIVE);
        grid.setCell(2, 1, Cell.ALIVE); // 4 neighbors
        
        assertEquals(Cell.DEAD, grid.applyRules(2, 2));
    }

    @Test
    void testApplyRules_Reproduction() {
        // Dead cell with exactly 3 neighbors becomes alive
        grid.setCell(2, 1, Cell.ALIVE);
        grid.setCell(2, 2, Cell.ALIVE);
        grid.setCell(2, 3, Cell.ALIVE);
        
        // Cell above should have 3 neighbors and become alive
        assertEquals(Cell.ALIVE, grid.applyRules(1, 2));
    }

    @Test
    void testNextGeneration_Blinker() {
        // Test the "blinker" pattern (oscillator with period 2)
        // Initial state: horizontal line
        grid.setCell(2, 1, Cell.ALIVE);
        grid.setCell(2, 2, Cell.ALIVE);
        grid.setCell(2, 3, Cell.ALIVE);

        Grid nextGen = grid.nextGeneration();

        // After one generation: vertical line
        assertEquals(Cell.DEAD, nextGen.getCell(2, 1));
        assertEquals(Cell.ALIVE, nextGen.getCell(2, 2));
        assertEquals(Cell.DEAD, nextGen.getCell(2, 3));
        assertEquals(Cell.ALIVE, nextGen.getCell(1, 2));
        assertEquals(Cell.ALIVE, nextGen.getCell(3, 2));
    }

    @Test
    void testInitializeRandom() {
        grid.initializeRandom(0.5);
        int aliveCount = grid.countAliveCells();
        
        // With 50% probability and 25 cells, expect some alive cells
        assertTrue(aliveCount > 0);
        assertTrue(aliveCount < 25);
    }

    @Test
    void testInitializeRandom_ZeroProbability() {
        grid.initializeRandom(0.0);
        assertEquals(0, grid.countAliveCells());
    }

    @Test
    void testToString() {
        grid.setCell(0, 0, Cell.ALIVE);
        String gridString = grid.toString();
        
        assertNotNull(gridString);
        assertTrue(gridString.contains("O"));
        assertTrue(gridString.contains("."));
    }
}

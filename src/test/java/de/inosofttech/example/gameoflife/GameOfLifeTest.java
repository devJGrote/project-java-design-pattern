package de.inosofttech.example.gameoflife;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the GameOfLife simulation class.
 */
class GameOfLifeTest {

    private GameOfLife game;

    @BeforeEach
    void setUp() {
        // Create a smaller game for testing
        game = new GameOfLife(10, 10, 0.3);
    }

    @Test
    void testInitialization() {
        assertNotNull(game.getCurrentGrid());
        assertEquals(0, game.getGeneration());
        assertTrue(game.getAliveCount() >= 0);
    }

    @Test
    void testDefaultConstructor() {
        GameOfLife defaultGame = new GameOfLife();
        assertEquals(100, defaultGame.getCurrentGrid().getWidth());
        assertEquals(100, defaultGame.getCurrentGrid().getHeight());
    }

    @Test
    void testNextGeneration() {
        int initialGeneration = game.getGeneration();
        game.nextGeneration();
        assertEquals(initialGeneration + 1, game.getGeneration());
    }

    @Test
    void testRunGenerations() {
        game.runGenerations(5);
        assertEquals(5, game.getGeneration());
    }

    @Test
    void testGetAliveCount() {
        int count = game.getAliveCount();
        assertTrue(count >= 0);
        assertTrue(count <= 100); // Max is 10x10 = 100 cells
    }

    @Test
    void testMultipleGenerations() {
        for (int i = 0; i < 10; i++) {
            int genBefore = game.getGeneration();
            game.nextGeneration();
            assertEquals(genBefore + 1, game.getGeneration());
        }
        assertEquals(10, game.getGeneration());
    }

    @Test
    void testCustomGridSize() {
        GameOfLife customGame = new GameOfLife(5, 5, 0.5);
        assertEquals(5, customGame.getCurrentGrid().getWidth());
        assertEquals(5, customGame.getCurrentGrid().getHeight());
    }

    @Test
    void testZeroInitialProbability() {
        GameOfLife emptyGame = new GameOfLife(10, 10, 0.0);
        assertEquals(0, emptyGame.getAliveCount());
        
        // Empty grid should stay empty
        emptyGame.nextGeneration();
        assertEquals(0, emptyGame.getAliveCount());
    }
}

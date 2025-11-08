package de.inosofttech.example.gameoflife;

import java.util.logging.Logger;

/**
 * Main simulation class for Conway's Game of Life.
 */
public class GameOfLife {
    private static final Logger LOGGER = Logger.getLogger(GameOfLife.class.getName());
    
    private Grid currentGrid;
    private int generation;

    /**
     * Create a new Game of Life simulation with given dimensions.
     * @param width Grid width
     * @param height Grid height
     * @param initialAliveProbability Probability of initial cells being alive (0.0 to 1.0)
     */
    public GameOfLife(int width, int height, double initialAliveProbability) {
        this.currentGrid = new Grid(width, height);
        this.currentGrid.initializeRandom(initialAliveProbability);
        this.generation = 0;
        
        LOGGER.info(String.format("Game of Life initialized: %dx%d grid", width, height));
        logCurrentState();
    }

    /**
     * Create a new Game of Life simulation with default 100x100 grid.
     */
    public GameOfLife() {
        this(100, 100, 0.3); // 30% initial alive probability
    }

    /**
     * Advance to the next generation.
     */
    public void nextGeneration() {
        currentGrid = currentGrid.nextGeneration();
        generation++;
        logCurrentState();
    }

    /**
     * Log the current state of the simulation.
     */
    private void logCurrentState() {
        int aliveCells = currentGrid.countAliveCells();
        LOGGER.info(String.format("Generation %d: %d alive cells", generation, aliveCells));
    }

    /**
     * Display the current grid (for smaller grids).
     */
    public void displayGrid() {
        LOGGER.info(String.format("=== Generation %d ===", generation));
        System.out.println(currentGrid.toString());
    }

    /**
     * Get current generation number.
     */
    public int getGeneration() {
        return generation;
    }

    /**
     * Get the current grid.
     */
    public Grid getCurrentGrid() {
        return currentGrid;
    }

    /**
     * Get count of alive cells.
     */
    public int getAliveCount() {
        return currentGrid.countAliveCells();
    }

    /**
     * Run simulation for a specified number of generations.
     */
    public void runGenerations(int count) {
        LOGGER.info(String.format("Running %d generations...", count));
        for (int i = 0; i < count; i++) {
            nextGeneration();
        }
        LOGGER.info("Simulation completed.");
    }
}

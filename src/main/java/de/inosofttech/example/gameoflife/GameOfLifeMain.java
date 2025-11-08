package de.inosofttech.example.gameoflife;

import java.util.Scanner;
import java.util.logging.Logger;

/**
 * Main entry point for Conway's Game of Life simulation.
 */
public class GameOfLifeMain {
    private static final Logger LOGGER = Logger.getLogger(GameOfLifeMain.class.getName());

    public static void main(String[] args) {
        // You can change these parameters as needed
        int width = 100;
        int height = 100;
        double initialAliveProbability = 0.3; // 30% cells start alive

        // Example 1: Standard 100x100 grid
        LOGGER.info("Starting Game of Life Simulation");
        GameOfLife game = new GameOfLife(width, height, initialAliveProbability);

        // Interactive mode - step through generations
        runInteractiveMode(game);

        // Example 2: Custom sized grid (uncomment to use)
        // runCustomExample();

        // Example 3: Small grid with visualization (uncomment to use)
        // runSmallGridExample();
    }

    /**
     * Interactive mode - press Enter to advance to next generation.
     */
    private static void runInteractiveMode(GameOfLife game) {
        Scanner scanner = new Scanner(System.in);
        LOGGER.info("Interactive Mode: Press Enter for next generation, type 'quit' to exit");

        while (true) {
            System.out.println("\nGeneration " + game.getGeneration() + 
                             " - Alive cells: " + game.getAliveCount());
            System.out.print("Press Enter for next generation (or type 'quit'): ");
            
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("quit")) {
                LOGGER.info("Simulation ended by user");
                break;
            }

            game.nextGeneration();
        }
        scanner.close();
    }

    /**
     * Example with custom grid size.
     */
    private static void runCustomExample() {
        LOGGER.info("=== Custom Grid Example ===");
        // Custom size: 50x50 grid with 25% initial alive cells
        GameOfLife customGame = new GameOfLife(50, 50, 0.25);
        
        // Run 10 generations automatically
        customGame.runGenerations(10);
    }

    /**
     * Example with small grid and visual output.
     */
    private static void runSmallGridExample() {
        LOGGER.info("=== Small Grid Visualization Example ===");
        // Small 20x20 grid for visual output
        GameOfLife smallGame = new GameOfLife(20, 20, 0.35);
        
        Scanner scanner = new Scanner(System.in);
        for (int i = 0; i < 5; i++) {
            smallGame.displayGrid();
            System.out.println("\nPress Enter to continue...");
            scanner.nextLine();
            smallGame.nextGeneration();
        }
        scanner.close();
    }
}

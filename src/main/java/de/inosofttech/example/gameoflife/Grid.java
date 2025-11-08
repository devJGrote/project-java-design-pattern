package de.inosofttech.example.gameoflife;

import java.util.Random;

/**
 * Represents the grid for Conway's Game of Life.
 */
public class Grid {
    private final int width;
    private final int height;
    private Cell[][] cells;

    public Grid(int width, int height) {
        this.width = width;
        this.height = height;
        this.cells = new Cell[height][width];
        initializeEmpty();
    }

    /**
     * Initialize all cells as dead.
     */
    private void initializeEmpty() {
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                cells[row][col] = Cell.DEAD;
            }
        }
    }

    /**
     * Initialize grid with random alive cells.
     * @param probability Probability of a cell being alive (0.0 to 1.0)
     */
    public void initializeRandom(double probability) {
        Random random = new Random();
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                cells[row][col] = random.nextDouble() < probability ? Cell.ALIVE : Cell.DEAD;
            }
        }
    }

    /**
     * Set a specific cell state.
     */
    public void setCell(int row, int col, Cell cell) {
        if (isValidPosition(row, col)) {
            cells[row][col] = cell;
        }
    }

    /**
     * Get the state of a specific cell.
     */
    public Cell getCell(int row, int col) {
        if (isValidPosition(row, col)) {
            return cells[row][col];
        }
        return Cell.DEAD; // Edges are dead
    }

    /**
     * Check if position is within grid bounds.
     */
    private boolean isValidPosition(int row, int col) {
        return row >= 0 && row < height && col >= 0 && col < width;
    }

    /**
     * Count alive neighbors for a given cell.
     * Edges are considered dead (no wrap-around).
     */
    public int countAliveNeighbors(int row, int col) {
        int count = 0;
        for (int dRow = -1; dRow <= 1; dRow++) {
            for (int dCol = -1; dCol <= 1; dCol++) {
                if (dRow == 0 && dCol == 0) {
                    continue; // Skip the cell itself
                }
                int newRow = row + dRow;
                int newCol = col + dCol;
                if (isValidPosition(newRow, newCol) && cells[newRow][newCol].isAlive()) {
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * Apply Game of Life rules to determine next state.
     * - Cell dies if neighbors < 2 (underpopulation)
     * - Cell survives if neighbors == 2 or 3
     * - Cell dies if neighbors > 3 (overpopulation)
     * - Cell becomes alive if exactly 3 neighbors (reproduction)
     */
    public Cell applyRules(int row, int col) {
        Cell currentCell = cells[row][col];
        int aliveNeighbors = countAliveNeighbors(row, col);

        if (currentCell.isAlive()) {
            // Alive cell: survives with 2 or 3 neighbors
            return (aliveNeighbors == 2 || aliveNeighbors == 3) ? Cell.ALIVE : Cell.DEAD;
        } else {
            // Dead cell: becomes alive with exactly 3 neighbors
            return (aliveNeighbors == 3) ? Cell.ALIVE : Cell.DEAD;
        }
    }

    /**
     * Calculate the next generation based on current state.
     */
    public Grid nextGeneration() {
        Grid nextGrid = new Grid(width, height);
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                nextGrid.setCell(row, col, applyRules(row, col));
            }
        }
        return nextGrid;
    }

    /**
     * Count total alive cells in the grid.
     */
    public int countAliveCells() {
        int count = 0;
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                if (cells[row][col].isAlive()) {
                    count++;
                }
            }
        }
        return count;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    /**
     * Get cells as boolean array (true = alive, false = dead).
     * For web interface.
     */
    public boolean[][] getCellsAsBoolean() {
        boolean[][] result = new boolean[height][width];
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                result[row][col] = cells[row][col].isAlive();
            }
        }
        return result;
    }

    /**
     * Generate a string representation of the grid.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                sb.append(cells[row][col].getSymbol());
            }
            sb.append('\n');
        }
        return sb.toString();
    }
}

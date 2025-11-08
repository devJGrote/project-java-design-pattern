package de.inosofttech.example.gameoflife;

/**
 * Represents a cell in the Game of Life grid.
 */
public enum Cell {
    ALIVE('O'),
    DEAD('.');

    private final char symbol;

    Cell(char symbol) {
        this.symbol = symbol;
    }

    public char getSymbol() {
        return symbol;
    }

    public boolean isAlive() {
        return this == ALIVE;
    }
}

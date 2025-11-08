package de.inosofttech.example.gameoflife.web;

/**
 * DTO for Game of Life state.
 */
public class GameOfLifeState {
    private int generation;
    private int aliveCount;
    private int width;
    private int height;
    private boolean[][] cells;

    public GameOfLifeState() {
    }

    public GameOfLifeState(int generation, int aliveCount, int width, int height, boolean[][] cells) {
        this.generation = generation;
        this.aliveCount = aliveCount;
        this.width = width;
        this.height = height;
        this.cells = cells;
    }

    public int getGeneration() {
        return generation;
    }

    public void setGeneration(int generation) {
        this.generation = generation;
    }

    public int getAliveCount() {
        return aliveCount;
    }

    public void setAliveCount(int aliveCount) {
        this.aliveCount = aliveCount;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public boolean[][] getCells() {
        return cells;
    }

    public void setCells(boolean[][] cells) {
        this.cells = cells;
    }
}

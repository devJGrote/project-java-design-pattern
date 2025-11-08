package de.inosofttech.example.viergewinnt;

/**
 * Repräsentiert das Spielfeld für "4 gewinnt".
 */
public class Board {
    private final int rows;
    private final int cols;
    private final char[][] grid;
    private static final char EMPTY = '.';

    /**
     * Erstellt ein neues Spielfeld.
     *
     * @param rows Anzahl der Zeilen
     * @param cols Anzahl der Spalten
     */
    public Board(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.grid = new char[rows][cols];
        initializeBoard();
    }

    /**
     * Initialisiert das Spielfeld mit leeren Feldern.
     */
    private void initializeBoard() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                grid[i][j] = EMPTY;
            }
        }
    }

    /**
     * Setzt einen Spielstein in eine Spalte.
     *
     * @param col    Spaltennummer (0-basiert)
     * @param symbol Symbol des Spielers
     * @return true wenn erfolgreich, false wenn Spalte voll ist
     */
    public boolean dropPiece(int col, char symbol) {
        if (col < 0 || col >= cols) {
            return false;
        }

        // Finde die unterste freie Zeile in dieser Spalte
        for (int row = rows - 1; row >= 0; row--) {
            if (grid[row][col] == EMPTY) {
                grid[row][col] = symbol;
                return true;
            }
        }

        return false; // Spalte ist voll
    }

    /**
     * Prüft, ob eine Spalte noch Platz hat.
     *
     * @param col Spaltennummer
     * @return true wenn die Spalte noch Platz hat
     */
    public boolean isColumnAvailable(int col) {
        if (col < 0 || col >= cols) {
            return false;
        }
        return grid[0][col] == EMPTY;
    }

    /**
     * Prüft, ob das Spielfeld voll ist.
     *
     * @return true wenn alle Felder belegt sind
     */
    public boolean isFull() {
        for (int col = 0; col < cols; col++) {
            if (grid[0][col] == EMPTY) {
                return false;
            }
        }
        return true;
    }

    /**
     * Gibt das Spielfeld als String zurück.
     *
     * @return Textdarstellung des Spielfelds
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        
        // Spaltennummern anzeigen
        for (int col = 0; col < cols; col++) {
            sb.append(" ").append(col);
        }
        sb.append("\n");

        // Spielfeld anzeigen
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                sb.append("|").append(grid[row][col]);
            }
            sb.append("|\n");
        }

        // Untere Grenze
        for (int col = 0; col < cols; col++) {
            sb.append("--");
        }
        sb.append("-");

        return sb.toString();
    }

    /**
     * Gibt das Spielfeld als 2D-Array zurück (für JSON-Serialisierung).
     *
     * @return Kopie des Spielfeld-Arrays
     */
    public char[][] getGrid() {
        char[][] copy = new char[rows][cols];
        for (int i = 0; i < rows; i++) {
            System.arraycopy(grid[i], 0, copy[i], 0, cols);
        }
        return copy;
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    /**
     * Setzt das Spielfeld zurück.
     */
    public void reset() {
        initializeBoard();
    }
}

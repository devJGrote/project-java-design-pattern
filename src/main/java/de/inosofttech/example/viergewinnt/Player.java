package de.inosofttech.example.viergewinnt;

/**
 * Repräsentiert einen Spieler im "4 gewinnt" Spiel.
 */
public class Player {
    private final String name;
    private final String color;
    private final char symbol;

    /**
     * Erstellt einen neuen Spieler.
     *
     * @param name   Name des Spielers
     * @param color  Farbe des Spielers (z.B. "GELB", "ROT")
     * @param symbol Symbol für das Spielfeld (z.B. 'G', 'R')
     */
    public Player(String name, String color, char symbol) {
        this.name = name;
        this.color = color;
        this.symbol = symbol;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public char getSymbol() {
        return symbol;
    }

    @Override
    public String toString() {
        return name + " (" + color + ")";
    }
}

package de.inosofttech.example.viergewinnt;

import java.util.Scanner;

/**
 * Hauptklasse zum Starten des "4 gewinnt" Spiels.
 * Kann entweder im Konsolen- oder Web-Modus gestartet werden.
 */
public class ConnectFourMain {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("=================================");
        System.out.println("   Willkommen bei 4 Gewinnt!    ");
        System.out.println("=================================");
        System.out.println();
        System.out.println("Wähle einen Modus:");
        System.out.println("1. Konsolen-Modus (Terminal)");
        System.out.println("2. Web-Modus (Browser)");
        System.out.print("Deine Wahl (1 oder 2): ");
        
        String choice = scanner.nextLine().trim();
        
        if (choice.equals("2")) {
            startWebMode();
        } else {
            startConsoleMode(scanner);
        }
        
        scanner.close();
    }

    /**
     * Startet den Web-Server für das Browser-Spiel.
     */
    private static void startWebMode() {
        try {
            System.out.println("\nStarte Web-Server...");
            ConnectFourWebServer.main(new String[]{});
        } catch (Exception e) {
            System.err.println("Fehler beim Starten des Web-Servers: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Startet das Spiel im Konsolen-Modus.
     */
    private static void startConsoleMode(Scanner scanner) {
        System.out.println("\n=== Konsolen-Modus ===\n");
        
        // Spielfeldgröße abfragen
        System.out.print("Anzahl der Zeilen (Standard: 6): ");
        String rowsInput = scanner.nextLine().trim();
        int rows = rowsInput.isEmpty() ? 6 : Integer.parseInt(rowsInput);
        
        System.out.print("Anzahl der Spalten (Standard: 7): ");
        String colsInput = scanner.nextLine().trim();
        int cols = colsInput.isEmpty() ? 7 : Integer.parseInt(colsInput);
        
        // Spiel erstellen
        ConnectFourGame game = new ConnectFourGame(rows, cols);
        
        System.out.println("\nSpiel startet!");
        System.out.println("Spieler 1: GELB (G)");
        System.out.println("Spieler 2: ROT (R)");
        System.out.println();
        
        // Spielschleife
        while (!game.isGameOver()) {
            // Spielfeld anzeigen
            System.out.println(game.getBoard());
            System.out.println();
            
            // Aktuellen Spieler anzeigen
            Player currentPlayer = game.getCurrentPlayer();
            System.out.println("Aktueller Spieler: " + currentPlayer);
            
            // Zug abfragen
            int column = -1;
            boolean validInput = false;
            
            while (!validInput) {
                System.out.print("Wähle eine Spalte (0-" + (cols - 1) + "): ");
                String input = scanner.nextLine().trim();
                
                try {
                    column = Integer.parseInt(input);
                    
                    if (column < 0 || column >= cols) {
                        System.out.println("Ungültige Spalte! Bitte wähle zwischen 0 und " + (cols - 1));
                    } else if (!game.isColumnAvailable(column)) {
                        System.out.println("Diese Spalte ist voll! Wähle eine andere.");
                    } else {
                        validInput = true;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Bitte gib eine gültige Zahl ein!");
                }
            }
            
            // Zug ausführen
            game.makeMove(column);
            System.out.println();
        }
        
        // Spielfeld anzeigen
        System.out.println(game.getBoard());
        System.out.println();
        
        // Ergebnis anzeigen
        Player winner = game.getWinner();
        if (winner != null) {
            System.out.println("=================================");
            System.out.println("  🎉 " + winner + " hat gewonnen! 🎉");
            System.out.println("=================================");
        } else {
            System.out.println("=================================");
            System.out.println("      🤝 Unentschieden! 🤝");
            System.out.println("=================================");
        }
    }
}

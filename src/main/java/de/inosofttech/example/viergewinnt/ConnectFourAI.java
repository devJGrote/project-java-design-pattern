package de.inosofttech.example.viergewinnt;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * KI-Spieler für "4 gewinnt" mit verschiedenen Schwierigkeitsgraden.
 */
public class ConnectFourAI {
    
    public enum Difficulty {
        EASY,    // Zufällige Züge
        MEDIUM,  // Blockiert Gegner und sucht Gewinnzüge
        HARD     // Verwendet Minimax-Algorithmus
    }
    
    private final Difficulty difficulty;
    private final Random random;
    
    public ConnectFourAI(Difficulty difficulty) {
        this.difficulty = difficulty;
        this.random = new Random();
    }
    
    /**
     * Berechnet den besten Zug für die KI.
     *
     * @param game Aktuelles Spiel
     * @return Spaltennummer für den Zug
     */
    public int getMove(ConnectFourGame game) {
        switch (difficulty) {
            case EASY:
                return getEasyMove(game);
            case MEDIUM:
                return getMediumMove(game);
            case HARD:
                return getHardMove(game);
            default:
                return getEasyMove(game);
        }
    }
    
    /**
     * Leichter Modus: Wählt eine zufällige gültige Spalte.
     */
    private int getEasyMove(ConnectFourGame game) {
        List<Integer> validColumns = getValidColumns(game);
        return validColumns.get(random.nextInt(validColumns.size()));
    }
    
    /**
     * Mittlerer Modus: Blockiert Gewinnzüge des Gegners und sucht eigene Gewinnmöglichkeiten.
     */
    private int getMediumMove(ConnectFourGame game) {
        // 1. Prüfe ob KI gewinnen kann
        int winningMove = findWinningMove(game, game.getCurrentPlayer().getSymbol());
        if (winningMove != -1) {
            return winningMove;
        }
        
        // 2. Blockiere Gewinnzug des Gegners
        char opponentSymbol = getOpponentSymbol(game);
        int blockingMove = findWinningMove(game, opponentSymbol);
        if (blockingMove != -1) {
            return blockingMove;
        }
        
        // 3. Versuche in der Mitte zu spielen (strategisch besser)
        int centerCol = game.getBoard().getCols() / 2;
        if (game.isColumnAvailable(centerCol)) {
            return centerCol;
        }
        
        // 4. Wähle zufällige Spalte
        return getEasyMove(game);
    }
    
    /**
     * Schwerer Modus: Verwendet Minimax-Algorithmus mit Alpha-Beta-Pruning.
     */
    private int getHardMove(ConnectFourGame game) {
        int bestScore = Integer.MIN_VALUE;
        int bestMove = -1;
        int alpha = Integer.MIN_VALUE;
        int beta = Integer.MAX_VALUE;
        
        List<Integer> validColumns = getValidColumns(game);
        
        for (int col : validColumns) {
            // Simuliere Zug
            ConnectFourGame simGame = cloneGame(game);
            simGame.makeMove(col);
            
            // Berechne Score mit Minimax
            int score = minimax(simGame, 4, alpha, beta, false);
            
            if (score > bestScore) {
                bestScore = score;
                bestMove = col;
            }
            
            alpha = Math.max(alpha, score);
        }
        
        return bestMove != -1 ? bestMove : getEasyMove(game);
    }
    
    /**
     * Minimax-Algorithmus mit Alpha-Beta-Pruning.
     */
    private int minimax(ConnectFourGame game, int depth, int alpha, int beta, boolean maximizing) {
        // Abbruchbedingungen
        if (depth == 0 || game.isGameOver()) {
            return evaluateBoard(game);
        }
        
        List<Integer> validColumns = getValidColumns(game);
        
        if (maximizing) {
            int maxScore = Integer.MIN_VALUE;
            for (int col : validColumns) {
                ConnectFourGame simGame = cloneGame(game);
                simGame.makeMove(col);
                int score = minimax(simGame, depth - 1, alpha, beta, false);
                maxScore = Math.max(maxScore, score);
                alpha = Math.max(alpha, score);
                if (beta <= alpha) {
                    break; // Beta cutoff
                }
            }
            return maxScore;
        } else {
            int minScore = Integer.MAX_VALUE;
            for (int col : validColumns) {
                ConnectFourGame simGame = cloneGame(game);
                simGame.makeMove(col);
                int score = minimax(simGame, depth - 1, alpha, beta, true);
                minScore = Math.min(minScore, score);
                beta = Math.min(beta, score);
                if (beta <= alpha) {
                    break; // Alpha cutoff
                }
            }
            return minScore;
        }
    }
    
    /**
     * Bewertet das Spielfeld für die KI.
     */
    private int evaluateBoard(ConnectFourGame game) {
        if (game.isGameOver() && game.getWinner() != null) {
            // KI hat gewonnen
            if (game.getWinner().getSymbol() == game.getCurrentPlayer().getSymbol()) {
                return 1000;
            } else {
                return -1000;
            }
        }
        
        // Bewerte Position basierend auf möglichen Gewinnlinien
        return 0; // Vereinfacht - könnte erweitert werden
    }
    
    /**
     * Findet einen Gewinnzug für das gegebene Symbol.
     */
    private int findWinningMove(ConnectFourGame game, char symbol) {
        for (int col = 0; col < game.getBoard().getCols(); col++) {
            if (game.isColumnAvailable(col)) {
                // Simuliere Zug
                ConnectFourGame simGame = cloneGame(game);
                
                // Temporär das Symbol setzen
                Board board = simGame.getBoard();
                board.dropPiece(col, symbol);
                
                // Prüfe ob dieser Zug gewinnt
                if (checkWinForSymbol(board, symbol)) {
                    return col;
                }
            }
        }
        return -1; // Kein Gewinnzug gefunden
    }
    
    /**
     * Prüft ob das gegebene Symbol gewonnen hat.
     */
    private boolean checkWinForSymbol(Board board, char symbol) {
        char[][] grid = board.getGrid();
        int rows = board.getRows();
        int cols = board.getCols();
        
        // Horizontal
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col <= cols - 4; col++) {
                if (grid[row][col] == symbol &&
                    grid[row][col + 1] == symbol &&
                    grid[row][col + 2] == symbol &&
                    grid[row][col + 3] == symbol) {
                    return true;
                }
            }
        }
        
        // Vertikal
        for (int row = 0; row <= rows - 4; row++) {
            for (int col = 0; col < cols; col++) {
                if (grid[row][col] == symbol &&
                    grid[row + 1][col] == symbol &&
                    grid[row + 2][col] == symbol &&
                    grid[row + 3][col] == symbol) {
                    return true;
                }
            }
        }
        
        // Diagonal (unten-links nach oben-rechts)
        for (int row = 3; row < rows; row++) {
            for (int col = 0; col <= cols - 4; col++) {
                if (grid[row][col] == symbol &&
                    grid[row - 1][col + 1] == symbol &&
                    grid[row - 2][col + 2] == symbol &&
                    grid[row - 3][col + 3] == symbol) {
                    return true;
                }
            }
        }
        
        // Diagonal (oben-links nach unten-rechts)
        for (int row = 0; row <= rows - 4; row++) {
            for (int col = 0; col <= cols - 4; col++) {
                if (grid[row][col] == symbol &&
                    grid[row + 1][col + 1] == symbol &&
                    grid[row + 2][col + 2] == symbol &&
                    grid[row + 3][col + 3] == symbol) {
                    return true;
                }
            }
        }
        
        return false;
    }
    
    /**
     * Gibt alle gültigen Spalten zurück.
     */
    private List<Integer> getValidColumns(ConnectFourGame game) {
        List<Integer> validColumns = new ArrayList<>();
        for (int col = 0; col < game.getBoard().getCols(); col++) {
            if (game.isColumnAvailable(col)) {
                validColumns.add(col);
            }
        }
        return validColumns;
    }
    
    /**
     * Gibt das Symbol des Gegners zurück.
     */
    private char getOpponentSymbol(ConnectFourGame game) {
        Player current = game.getCurrentPlayer();
        Player opponent = (current == game.getPlayer1()) ? game.getPlayer2() : game.getPlayer1();
        return opponent.getSymbol();
    }
    
    /**
     * Klont ein Spiel für Simulationen.
     */
    private ConnectFourGame cloneGame(ConnectFourGame original) {
        // Erstelle neues Spiel mit gleichen Dimensionen
        ConnectFourGame clone = new ConnectFourGame(
            original.getBoard().getRows(),
            original.getBoard().getCols(),
            original.getPlayer1().getName(),
            original.getPlayer1().getColor(),
            original.getPlayer1().getSymbol(),
            original.getPlayer2().getName(),
            original.getPlayer2().getColor(),
            original.getPlayer2().getSymbol()
        );
        
        // Kopiere Spielfeld
        char[][] originalGrid = original.getBoard().getGrid();
        for (int row = 0; row < originalGrid.length; row++) {
            for (int col = 0; col < originalGrid[0].length; col++) {
                if (originalGrid[row][col] != '.') {
                    clone.getBoard().dropPiece(col, originalGrid[row][col]);
                }
            }
        }
        
        return clone;
    }
}

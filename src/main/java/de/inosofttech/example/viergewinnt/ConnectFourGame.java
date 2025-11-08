package de.inosofttech.example.viergewinnt;

/**
 * Hauptspiellogik für "4 gewinnt".
 */
public class ConnectFourGame {
    private final Board board;
    private final Player player1;
    private final Player player2;
    private Player currentPlayer;
    private boolean gameOver;
    private Player winner;
    private boolean isAIMode;
    private ConnectFourAI ai;

    /**
     * Erstellt ein neues Spiel (Standard: Gelb vs Rot).
     *
     * @param rows Anzahl der Zeilen
     * @param cols Anzahl der Spalten
     */
    public ConnectFourGame(int rows, int cols) {
        this(rows, cols, "Spieler 1", "GELB", 'Y', "Spieler 2", "ROT", 'R');
    }

    /**
     * Erstellt ein neues Spiel mit benutzerdefinierten Spielern.
     *
     * @param rows Anzahl der Zeilen
     * @param cols Anzahl der Spalten
     * @param player1Name Name von Spieler 1
     * @param player1Color Farbe von Spieler 1
     * @param player1Symbol Symbol von Spieler 1
     * @param player2Name Name von Spieler 2
     * @param player2Color Farbe von Spieler 2
     * @param player2Symbol Symbol von Spieler 2
     */
    public ConnectFourGame(int rows, int cols, String player1Name, String player1Color, char player1Symbol,
                          String player2Name, String player2Color, char player2Symbol) {
        this.board = new Board(rows, cols);
        this.player1 = new Player(player1Name, player1Color, player1Symbol);
        this.player2 = new Player(player2Name, player2Color, player2Symbol);
        this.currentPlayer = player1;
        this.gameOver = false;
        this.winner = null;
        this.isAIMode = false;
        this.ai = null;
    }

    /**
     * Aktiviert den KI-Modus.
     *
     * @param difficulty Schwierigkeitsgrad der KI
     */
    public void enableAI(ConnectFourAI.Difficulty difficulty) {
        this.isAIMode = true;
        this.ai = new ConnectFourAI(difficulty);
    }

    /**
     * Führt einen Spielzug aus.
     *
     * @param col Spaltennummer
     * @return true wenn der Zug erfolgreich war
     */
    public boolean makeMove(int col) {
        if (gameOver) {
            return false;
        }

        if (!board.dropPiece(col, currentPlayer.getSymbol())) {
            return false;
        }

        // Prüfe auf Gewinn
        if (checkWin()) {
            gameOver = true;
            winner = currentPlayer;
            return true;
        }

        // Prüfe auf Unentschieden
        if (board.isFull()) {
            gameOver = true;
            return true;
        }

        // Wechsle Spieler
        switchPlayer();
        return true;
    }

    /**
     * Wechselt den aktuellen Spieler.
     */
    private void switchPlayer() {
        currentPlayer = (currentPlayer == player1) ? player2 : player1;
    }

    /**
     * Prüft, ob der aktuelle Spieler gewonnen hat.
     *
     * @return true wenn ein Gewinn vorliegt
     */
    private boolean checkWin() {
        char[][] grid = board.getGrid();
        char symbol = currentPlayer.getSymbol();

        // Prüfe horizontale Linien
        for (int row = 0; row < board.getRows(); row++) {
            for (int col = 0; col <= board.getCols() - 4; col++) {
                if (grid[row][col] == symbol &&
                    grid[row][col + 1] == symbol &&
                    grid[row][col + 2] == symbol &&
                    grid[row][col + 3] == symbol) {
                    return true;
                }
            }
        }

        // Prüfe vertikale Linien
        for (int row = 0; row <= board.getRows() - 4; row++) {
            for (int col = 0; col < board.getCols(); col++) {
                if (grid[row][col] == symbol &&
                    grid[row + 1][col] == symbol &&
                    grid[row + 2][col] == symbol &&
                    grid[row + 3][col] == symbol) {
                    return true;
                }
            }
        }

        // Prüfe diagonale Linien (von links unten nach rechts oben)
        for (int row = 3; row < board.getRows(); row++) {
            for (int col = 0; col <= board.getCols() - 4; col++) {
                if (grid[row][col] == symbol &&
                    grid[row - 1][col + 1] == symbol &&
                    grid[row - 2][col + 2] == symbol &&
                    grid[row - 3][col + 3] == symbol) {
                    return true;
                }
            }
        }

        // Prüfe diagonale Linien (von links oben nach rechts unten)
        for (int row = 0; row <= board.getRows() - 4; row++) {
            for (int col = 0; col <= board.getCols() - 4; col++) {
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
     * Setzt das Spiel zurück.
     */
    public void reset() {
        board.reset();
        currentPlayer = player1;
        gameOver = false;
        winner = null;
    }

    /**
     * Lässt die KI einen Zug machen (mit Verzögerung).
     *
     * @return Spaltennummer des KI-Zugs, oder -1 wenn kein KI-Modus aktiv
     */
    public int getAIMove() {
        if (!isAIMode || ai == null || gameOver) {
            return -1;
        }
        return ai.getMove(this);
    }

    public Board getBoard() {
        return board;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public Player getWinner() {
        return winner;
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public boolean isAIMode() {
        return isAIMode;
    }

    /**
     * Prüft ob eine Spalte verfügbar ist.
     *
     * @param col Spaltennummer
     * @return true wenn die Spalte verfügbar ist
     */
    public boolean isColumnAvailable(int col) {
        return board.isColumnAvailable(col);
    }
}

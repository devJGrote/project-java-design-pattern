package de.inosofttech.example.viergewinnt;

/**
 * Repräsentiert den Zustand des Spiels für die Web-Schnittstelle.
 */
public class GameState {
    private char[][] grid;
    private String currentPlayer;
    private String currentPlayerColor;
    private boolean gameOver;
    private String winner;
    private String winnerColor;
    private boolean isDraw;
    private int rows;
    private int cols;
    private boolean isAIMode;
    private String player1Name;
    private String player1Color;
    private char player1Symbol;
    private String player2Name;
    private String player2Color;
    private char player2Symbol;

    public GameState() {
    }

    public GameState(ConnectFourGame game) {
        this.grid = game.getBoard().getGrid();
        this.currentPlayer = game.getCurrentPlayer().getName();
        this.currentPlayerColor = game.getCurrentPlayer().getColor();
        this.gameOver = game.isGameOver();
        this.rows = game.getBoard().getRows();
        this.cols = game.getBoard().getCols();
        this.isAIMode = game.isAIMode();
        
        // Spieler-Informationen
        this.player1Name = game.getPlayer1().getName();
        this.player1Color = game.getPlayer1().getColor();
        this.player1Symbol = game.getPlayer1().getSymbol();
        this.player2Name = game.getPlayer2().getName();
        this.player2Color = game.getPlayer2().getColor();
        this.player2Symbol = game.getPlayer2().getSymbol();
        
        if (game.isGameOver()) {
            Player winner = game.getWinner();
            if (winner != null) {
                this.winner = winner.getName();
                this.winnerColor = winner.getColor();
                this.isDraw = false;
            } else {
                this.isDraw = true;
            }
        }
    }

    // Getter und Setter
    public char[][] getGrid() {
        return grid;
    }

    public void setGrid(char[][] grid) {
        this.grid = grid;
    }

    public String getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(String currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public String getCurrentPlayerColor() {
        return currentPlayerColor;
    }

    public void setCurrentPlayerColor(String currentPlayerColor) {
        this.currentPlayerColor = currentPlayerColor;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    public String getWinnerColor() {
        return winnerColor;
    }

    public void setWinnerColor(String winnerColor) {
        this.winnerColor = winnerColor;
    }

    public boolean isDraw() {
        return isDraw;
    }

    public void setDraw(boolean draw) {
        isDraw = draw;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getCols() {
        return cols;
    }

    public void setCols(int cols) {
        this.cols = cols;
    }

    public boolean isAIMode() {
        return isAIMode;
    }

    public void setAIMode(boolean AIMode) {
        isAIMode = AIMode;
    }

    public String getPlayer1Name() {
        return player1Name;
    }

    public void setPlayer1Name(String player1Name) {
        this.player1Name = player1Name;
    }

    public String getPlayer1Color() {
        return player1Color;
    }

    public void setPlayer1Color(String player1Color) {
        this.player1Color = player1Color;
    }

    public char getPlayer1Symbol() {
        return player1Symbol;
    }

    public void setPlayer1Symbol(char player1Symbol) {
        this.player1Symbol = player1Symbol;
    }

    public String getPlayer2Name() {
        return player2Name;
    }

    public void setPlayer2Name(String player2Name) {
        this.player2Name = player2Name;
    }

    public String getPlayer2Color() {
        return player2Color;
    }

    public void setPlayer2Color(String player2Color) {
        this.player2Color = player2Color;
    }

    public char getPlayer2Symbol() {
        return player2Symbol;
    }

    public void setPlayer2Symbol(char player2Symbol) {
        this.player2Symbol = player2Symbol;
    }
}

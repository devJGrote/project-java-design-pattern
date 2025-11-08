package de.inosofttech.example.gameoflife;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class MainBoard {

    public static void main(String[] args) throws InterruptedException {
        int x = 50;
        int y = 50;
        var board = generateBoard(x,y);

        while (true) {
            clearScreen();
            board.printBoard();
            board.next();
            TimeUnit.SECONDS.sleep(1);
        }
    }

    static private Board generateBoard(int breite, int hoehe) {
        boolean[][] board = new boolean[breite][hoehe];
        var rand = new Random();

        for (int x = 0; x < breite; x++) {
            for (int y = 0; y < hoehe; y++) {
                board[x][y] = rand.nextBoolean();
            }
        }

        return new Board(board);
    }

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

}

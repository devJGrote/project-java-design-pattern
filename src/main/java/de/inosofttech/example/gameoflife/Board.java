package de.inosofttech.example.gameoflife;

public class Board {
    
    boolean boardArray[][];
    int neighbours[][] = new int[][] { {-1, -1}, {-1, 0}, {-1, 1},
                                            {0, -1},          {0, 1},
                                            {1, -1},  {1, 0},  {1, 1} };


    Board(boolean[][] boardArray) {
        this.boardArray = boardArray;
    }

    void next() {
        boolean newBoard[][] = new boolean[boardArray.length][boardArray[0].length];

        for (int x = 0; x < boardArray.length; x++) {
            for (int y = 0; y < boardArray[0].length; y++) {
                int neighbors = getNeighbors(x, y);
                if (neighbors < 2) {
                    newBoard[x][y] = false;
                } else if (neighbors == 2) {
                    newBoard[x][y] = boardArray[x][y];
                } else if (neighbors == 3) {
                    newBoard[x][y] = true;
                } else if (neighbors > 3) {
                    newBoard[x][y] = false;
                }
            }
        }

        boardArray = newBoard;
    }

    public void printBoard(){
        String PRINT_SIGN_TRUE = "x";
        String PRINT_SIGN_FALSE = " ";
        for (int x = 0; x < boardArray.length; x++) {
            if(x >= 1){
                System.out.println();
            }

            for (int y = 0; y < boardArray[0].length; y++) {
                if(boardArray[x][y]) {
                    System.out.print(PRINT_SIGN_TRUE);
                 
                } else {
                    System.out.print(PRINT_SIGN_FALSE);
                }
             
            }
        
        }
    }

    public int getNeighbors(int x, int y) {
        int count = 0;
        for (int i = 0; i < neighbours.length; i++) {
            int newX = x + neighbours[i][0];
            int newY = y + neighbours[i][1];
            if (isInBound(newX, newY) && boardArray[newX][newY]) {
                count++;
            }
        }
        return count;
    
    }


    public boolean isInBound(int x, int y) {
        return x >= 0 && x < boardArray.length && y >= 0 && y < boardArray[0].length;
    }

}
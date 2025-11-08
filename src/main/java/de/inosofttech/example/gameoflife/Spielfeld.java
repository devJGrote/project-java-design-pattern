package de.inosofttech.example.gameoflife;

import java.util.function.BooleanSupplier;

public class Spielfeld {
    private int breite;
    private int hoehe;
    private boolean[][] zellen;

    public Spielfeld(int i, int j) {
        
        this.breite = i;
        this.hoehe = j;
        this.zellen = new boolean[i][j];
    }

    public int getBreite() {
        return breite;
    }


    public int getHoehe() {
        return hoehe;
    }

    public BooleanSupplier istLebendigeZelle(int i, int j) {
       return () -> zellen[i][j];
    }

    public void setLebendigeZelle(int i, int j) {
        zellen[i][j] = true;
    }

    public void initialLebendigeZellenProzent(int i) {
        int lebendigeZellen = (breite * hoehe * i) / 100;
        int count = 0;
        for (int x = 0; x < breite; x++) {
            for (int y = 0; y < hoehe; y++) {
                if (count < lebendigeZellen) {
                    zellen[x][y] = true;
                    count++;
                } else {
                    return;
                }
            }
        }
    }
        

    public int getLebendigeZellen() {
        int count = 0;
        for (int x = 0; x < breite; x++) {
            for (int y = 0; y < hoehe; y++) {
                if (zellen[x][y]) {
                    count++;
                }
            }
        }
        return count;
    }

}

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

    public Spielfeld(int breite, int hoehe, int anzahlProzentLebendigeZellen) {
        this.breite = breite;
        this.hoehe = hoehe;
        this.zellen = new boolean[breite][hoehe];
        initialLebendigeZellenProzent(anzahlProzentLebendigeZellen);
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

    public int getNachbarnLebendigeZellen(int i, int j) {
        int count = 0;
        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                if (x == 0 && y == 0) {
                    continue;
                }
                int ni = i + x;
                int nj = j + y;
                if (ni >= 0 && ni < breite && nj >= 0 && nj < hoehe) {
                    if (zellen[ni][nj]) {
                        count++;
                    }
                }
            }
        }
        return count;
    }

    public void stirbtZell(int i, int j) {
        zellen[i][j] = false;
    }

    public boolean getFeld(int i, int j) {
        return zellen[i][j];
    }

}

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

}

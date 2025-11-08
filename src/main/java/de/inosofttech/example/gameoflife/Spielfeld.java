package de.inosofttech.example.gameoflife;

public class Spielfeld {
    private int breite;
    private int hoehe;

    public Spielfeld(int i, int j) {
        
        this.breite = i;
        this.hoehe = j;
    }

    public int getBreite() {
        return breite;
    }


    public int getHoehe() {
        return hoehe;
    }

}

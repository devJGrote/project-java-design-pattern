package de.inosofttech.example.viergewinnt;

public class Spielfeld {
    private int spalten;
    private int reihen;
    private char[][] feld;

    public Spielfeld(int spalten, int reihen) {
        this.spalten = spalten;
        this.reihen = reihen;
        this.feld = new char[reihen][spalten];
    }

    public int getSpalten() {
        return spalten;
    }

    public int getReihen() {
        return reihen;
    }

    public void spielerZug(int spalte, Spieler spielerEins) {
        char zeichen = spielerEins.getZeichen();
        int reihe = getFreiesFeldInSpalte(spalte);
        this.feld[reihe][spalte] = zeichen;
    }

    public char[][] getFeld() {
        return feld;
    }

    public int getFreiesFeldInSpalte(int spalte) {
        for (int i = 0; i < reihen; i++) {
            if (feld[spalte][i] == '\u0000') {
                return i;
            }
        }
        return -1;
    }

}

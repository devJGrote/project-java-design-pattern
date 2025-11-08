package de.inosofttech.example.testing.gameoflife;

import static org.mockito.ArgumentMatchers.same;

import java.util.ArrayList;
import java.util.List;

public class Spielfeld {

    private int breite;
    private int hoehe;
    boolean[][] feld;

    public Spielfeld(int breite, int hoehe) {
        this.breite = breite;
        this.hoehe = hoehe;
        feld = new boolean[breite][hoehe];
    }

    public int getBreite() {
        return breite;
    }

    public int getHoehe() { 
        return hoehe;
    }

    public void generiereStartLeben(int anzahlLeben) {
        List<int[]> positionen = new ArrayList<>();
        for (int j = 0; j < anzahlLeben; j++) {
            int[] position = getZufaelligeFeldPosition(positionen, breite, hoehe);
            positionen.add(position);
            setLeben(position);
        }
    }

    private void setLeben(int[] position) {
        feld[position[0]][position[1]] = true;
    }

    private int[] getZufaelligeFeldPosition(int breite, int hoehe) {
        int x = (int) (Math.random() * breite);
        int y = (int) (Math.random() * hoehe);
        return new int[] { x, y };
    }

    public boolean istZelleLebendig(int x, int y) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'istZelleLebendig'");
    }

}

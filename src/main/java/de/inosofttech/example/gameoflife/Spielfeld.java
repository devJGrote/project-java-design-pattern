package de.inosofttech.example.gameoflife;

public class Spielfeld {
    private boolean[][] zellen;

    public Spielfeld(int breite, int hoehe) {
        zellen = new boolean[breite][hoehe];
    }

    public boolean istLebendig(int breite, int hoehe) {
        return zellen[breite][hoehe];
    }


    public void setZelleLebendig(int breite, int hoehe) {
        zellen[breite][hoehe] = true;
    }

    public void setZelleTod(int breite, int hoehe) {
        zellen[breite][hoehe] = false;
    }

    public void addGeneration(int i) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addGeneration'");
    }

    public int zaehleNachbarn(int i, int j) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'zaehleNachbarn'");
    }


}

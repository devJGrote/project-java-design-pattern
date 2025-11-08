package de.inosofttech.example.testing.viergewinnt;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import de.inosofttech.example.viergewinnt.Spieler;
import de.inosofttech.example.viergewinnt.Spielfeld;

public class SpielfeldTest {

    private static Spielfeld standardSpielfeld;

    @BeforeAll
    public static void setUp() {
        standardSpielfeld = new Spielfeld(7, 6);
    }

    @Test
    public void testSpielFeldInitialisierung() {
        int spalten = 7;
        int reihen = 6;
        Spielfeld spielfeld = new Spielfeld(spalten, reihen);
        assert spielfeld.getSpalten() == 7;
        assert spielfeld.getReihen() == 6;
    }


    @Test
    public void testSpielerZug() {
        char spielerEinsZeichen = 'O';
        char spielerZweiZeichen = 'X';

        Spieler spielerEins = new Spieler(spielerEinsZeichen);
        Spieler spielerZwei = new Spieler(spielerZweiZeichen);

        standardSpielfeld.spielerZug(5, spielerEins);
        assert standardSpielfeld.getFeld()[5][0] == spielerEins.getZeichen();

        standardSpielfeld.spielerZug(3, spielerZwei);
        assert standardSpielfeld.getFeld()[3][0] == spielerZwei.getZeichen();

    }

    @Test
    public void testSpielerZugInVollesFeld() {
        char spielerEinsZeichen = 'O';
      
        Spieler spielerEins = new Spieler(spielerEinsZeichen);
        standardSpielfeld.spielerZug(5, spielerEins);
        standardSpielfeld.spielerZug(5, spielerEins);
        standardSpielfeld.spielerZug(5, spielerEins);

        assert(standardSpielfeld.getFreiesFeldInSpalte(5) == 3);
    }
}

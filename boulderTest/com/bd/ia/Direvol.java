package boulderTest.com.bd.ia;

import boulderTest.Map;
import boulderTest.NoRockfordException;
import boulderTest.Partie;

import java.awt.*;

/**
 * Created by marin on 18/03/2017.
 */


public class Direvol extends Rockford {
    private Evolue e;

    public Direvol(int nbMutations, Map laMap) throws NoRockfordException {
        String cheminInitial = new Partie(laMap.clone(), new Directif(), false).getChemin();
        e = new Evolue(nbMutations, laMap.clone(), true, cheminInitial);
    }

    public Point getDeplacement(Point posRockford, Map laMap) {
        return e.getDeplacement(posRockford, laMap);
    }

    public String toString() {
        return "direvol";
    }
}

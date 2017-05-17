package boulderTest.com.bd.ia;

import boulderTest.Map;

import java.awt.*;

/**
 * Created by marin on 18/03/2017.
 */
public class Simplet extends Rockford {

    public Point getDeplacement(Point posRockford, Map laMap) {
        return charToPos(posRockford,getDeplacementRandom(posRockford,laMap));
    }

    public String toString(){
        return "simplet";
    }
}

package boulderTest.com.bd.ia;

import boulderTest.Map;

import java.awt.*;

/**
 * Created by marin on 18/03/2017.
 */
public class Evolue extends Rockford {
    private int nbMutations;


    public Point getDeplacement(Point posRockford, Map laMap) {
        return charToPos(posRockford,deplacementInteressant(posRockford,laMap));
    }

    private Point charToPos(Point posRockford, char c){
        switch (c) {
            case 'U':
                return new Point((int) posRockford.getX()-1, (int) posRockford.getY());
            case 'D':
                return new Point((int) posRockford.getX()+1, (int) posRockford.getY());
            case 'L':
                return new Point((int) posRockford.getX(), (int) posRockford.getY()-1);
            case 'R':
                return new Point((int) posRockford.getX(), (int) posRockford.getY()+1);
            default:
                return posRockford;//Toute autre valeur = immobile
        }
    }

}

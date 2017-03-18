package boulderTest;

import java.awt.*;

/**
 * Created by marin on 18/03/2017.
 */
public class Simplet extends Rockford {

    public Point getDeplacement(Point posRockford, Map laMap) {
        int choixRandom; // Int random entre 0 et 5
        Point essaieDeDeplacement;
        do{
            choixRandom = (int) (Math.random() * 6); // Int random entre 0 et 5
            switch (choixRandom){
                case 1:
                    essaieDeDeplacement = new Point((int) posRockford.getX() + 1, (int) posRockford.getY());
                    break;
                case 2:
                    essaieDeDeplacement = new Point((int) posRockford.getX() - 1, (int) posRockford.getY());
                    break;
                case 3:
                    essaieDeDeplacement = new Point((int) posRockford.getX(), (int) posRockford.getY() + 1);
                    break;
                case 4:
                    essaieDeDeplacement = new Point((int) posRockford.getX(), (int) posRockford.getY() - 1);
                    break;
                case 0:
                default:
                    essaieDeDeplacement = posRockford;
                    break;
            }
        }while(!deplacementPossible(essaieDeDeplacement,posRockford,laMap));
        return essaieDeDeplacement;
    }
}

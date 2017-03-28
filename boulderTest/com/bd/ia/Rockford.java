package boulderTest.com.bd.ia;

import boulderTest.Map;
import boulderTest.com.bd.ia.IAlgorithme;

import java.awt.*;

/**
 * Created by marin on 17/03/2017.
 */
public abstract class Rockford implements IAlgorithme {

    protected boolean deplacementPossible(Point pos, Point posRockford, Map laMap) {
        if ((pos.getX() < 0 || pos.getX() > laMap.getHauteur() || pos.getY() < 0 || pos.getY() > laMap.getLargeur()))
            return false;
        char c = laMap.getElement(pos);
        if (c == ' ' || c == '.' || c == 'd' || c == 'X') return true;
        else if (c == 'w' || c == 'W') return false;
        else if (c == 'r') return rocPoussable(pos, posRockford, laMap);
        else return true;
    }

    protected boolean caseDangereuse(Point pos, Map laMap){
        switch (laMap.getElement(pos)){
            case 'F':
            case 'Q':
            case 'q':
            case 'O':
            case 'o':
            case 'c':
            case 'b':
            case 'B':
            case 'C':
            case 'a':
                return true;
            default:
                return false;
        }
    }

    protected char deplacementInteressant(Point posRockford, Map laMap) {
        char c;
        c = testAutour(posRockford, laMap, 'X');
        if (c != ' ') return c;
        c = testAutour(posRockford, laMap, 'D');
        if (c != ' ') return c;
        int choixRandom; // Int random entre 0 et 5
        Point essaieDeDeplacement;
        do {
            choixRandom = (int) (Math.random() * 5); // Int random entre 0 et 4
            switch (choixRandom) {
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
        } while (!deplacementPossible(essaieDeDeplacement, posRockford, laMap) || caseDangereuse(essaieDeDeplacement,laMap));
        return posToChar(posRockford,essaieDeDeplacement);
    }



    private char testAutour(Point posRockford, Map laMap, char charATrouver){
        Point haut = new Point((int)posRockford.getX()-1,(int)posRockford.getY());
        Point bas = new Point((int)posRockford.getX()+1,(int)posRockford.getY());
        Point gauche = new Point((int)posRockford.getX(),(int)posRockford.getY()-1);
        Point droite = new Point((int)posRockford.getX(),(int)posRockford.getY()+1);
        if(laMap.getElement(haut) == charATrouver) return 'U';
        else if(laMap.getElement(bas) == charATrouver) return 'D';
        else if(laMap.getElement(gauche) == charATrouver) return 'L';
        else if(laMap.getElement(droite) == charATrouver) return 'R';
        else return ' ';
    }

    private char posToChar(Point posRockford, Point posApres){
        if(posApres.getX() > posRockford.getX()) return 'D';
        else if(posApres.getX() < posRockford.getX()) return 'U';
        else if(posApres.getY() > posRockford.getY()) return 'R';
        else if(posApres.getY() < posRockford.getY()) return 'L';
        else return 'I';
    }





    private boolean rocPoussable(Point pos, Point posRockford, Map laMap) {
        Point posApresRoc = new Point((int) pos.getX() * 2 - (int) posRockford.getX(), (int) pos.getY() * 2 - (int) posRockford.getY());//formule pour avoir la prochaine case dans la continuité de la direction choisie
        return (laMap.getElement(posApresRoc) == ' ');
    }
}

package boulderTest.com.bd.ia;

import boulderTest.Map;

import java.awt.*;
import java.util.Scanner;

/**
 * Created by marin on 17/03/2017.
 */
public class Joueur extends Rockford {

    private boolean journal;

    public Joueur(boolean journal) {
        this.journal = journal;
        if (journal) {
            System.out.println("\nDéplacez Rockford avec les touches z q s d et appuyez sur entrée pour valider le déplacement.\nToute autre caractère immobilisera Rockford pour ce tour.\n");
        }
    }

    public Point getDeplacement(Point posRockford, Map laMap) {
        char choix = '.';
        Point positionApresDeplacement;
        do {
           choix = choixDeplacement();

            switch (choix) {
                case 'z':
                    positionApresDeplacement = new Point((int) posRockford.getX() - 1, (int) posRockford.getY());
                    break;
                case 's':
                    positionApresDeplacement = new Point((int) posRockford.getX() + 1, (int) posRockford.getY());
                    break;
                case 'q':
                    positionApresDeplacement = new Point((int) posRockford.getX(), (int) posRockford.getY() - 1);
                    break;
                case 'd':
                    positionApresDeplacement = new Point((int) posRockford.getX(), (int) posRockford.getY() + 1);
                    break;
                default:
                    positionApresDeplacement = posRockford;
                    break;
            }
            if (!deplacementPossible(positionApresDeplacement,posRockford,laMap)&& journal) System.out.println("Déplacement impossible !");
        } while (!deplacementPossible(positionApresDeplacement,posRockford,laMap));
        return positionApresDeplacement;
    }

    private char choixDeplacement() {
        if(journal) {
            System.out.println("Entrez une direction...   :");
        }
        Scanner sc = new Scanner(System.in);
        String s = sc.nextLine();
        if (!s.equals("")) return (s.charAt(0));
        else return choixDeplacement();
    }

    public String toString(){
        return "joueur";
    }
}

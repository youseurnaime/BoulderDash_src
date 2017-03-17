package boulderTest;

import java.awt.*;
import java.util.Scanner;

/**
 * Created by clement on 14/03/17.
 */
public class IA {//Tout ce qui est relatif au choix de direction de Rockford
    int niveauIa; // 0 pour humain

    public IA(int niveauIa) {
        this.niveauIa = niveauIa;
    }

    public void tourIa(int niveauIa){
        switch (niveauIa){
            case 0 ://humain
                break;
            case 1://simplet
                break;
            case 2://evolué
                break;
            case 3://directif
                break;
            case 4://directif évolué;
                break;
            case 5://parfait
                break;
            default://sortir une exeption
                break;
        }
    }

    /*private boolean rocPoussable(Point pos) {//pos du roc en parametre
        Point posApresRoc = new Point((int) pos.getX() * 2 - (int) posRockford.getX(), (int) pos.getY() * 2 - (int) posRockford.getY());//formule pour avoir la prochaine case dans la continuité de la direction choisie
        return (laMap.getElement(posApresRoc) == ' ');
    }

    private boolean deplacementPossible(Point pos) {
        if ((pos.getX() < 0 || pos.getX() > laMap.getHauteur() || pos.getY() < 0 || pos.getY() > laMap.getLargeur()))
            return false;
        char c = laMap.getElement(pos);
        if (c == ' ' || c == '.' || c == 'd' || c == 'X') return true;
        else if (c == 'w' || c == 'W') return false;
        else if (c == 'r') return rocPoussable(pos);
        else return true;
    }
    private char choixDeplacement() {
        System.out.println("Entrez une direction...   :");
        Scanner sc = new Scanner(System.in);
        String s = sc.nextLine();
        if (!s.equals("")) return (s.charAt(0));
        else return choixDeplacement();
    }
    private char getDeplacement() {//lit les déplacements dans la chaine des fichiers .dash
        int i = 0;
        while (i < lesDeplacements.length && lesDeplacements[i] == 0)
            i++;//0 = traité, on cherche le 1er char non traité dans la chaine
        if (i == lesDeplacements.length)
            return '0';//quand il n'y a plus de caractères a lire on renvoie 0 pour le signaler
        System.out.println(lesDeplacements[i]);
        switch (lesDeplacements[i]) {
            case 'U':
                return 'z';
            case 'D':
                return 's';
            case 'L':
                return 'q';
            case 'R':
                return 'd';
            default:
                return 'i';//Toute autre valeur = immobile
        }
    }
    private void tourhumain(){

    }
    private void toursimplet(){

    }
    private void tourevolue(){

    }
    private void tourdirectif(){

    }
    private void directifevolue(){

    }
    private void parfait(){

    }*/
}

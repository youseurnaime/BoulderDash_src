package boulderTest;

import java.awt.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;


public class Map {
    private String name;
    private int[] caveTime;
    private int diamondsRequired;
    private int diamondValue; //= valeur du diamant + le bonus
    private int amoebaTime;
    private int magicWallTime;
    private char[][] laMap; //dans laMap[i][j], i = hauteur j = largeur
    private int hauteurMap;
    private int largeurMap;
    private Point posSortie;
    private boolean sortieOuverte;
    private int tourAvantAmibe;
    private int bonusDiamant; //si une libellule meurt --> a changer pour faire apparaitre des diamants
    Hashtable<Point, Character> mobToAdd;

    public Map(String name, ArrayList<Integer> caveTime, int diamondsRequired, ArrayList<Integer> diamondValue, int amoebaTime, int magicWallTime, ArrayList<String> laMap) {
        this.name = name;
        this.caveTime = new int[caveTime.size()];
        for (int i = 0; i < caveTime.size(); i++)
            this.caveTime[i] = caveTime.get(i); //on transforme l'arrayList en tableau
        this.diamondsRequired = diamondsRequired;
        this.diamondValue = 0;
        for (int i = 0; i < diamondValue.size(); i++) this.diamondValue += diamondValue.get(i);
        this.amoebaTime = amoebaTime;
        this.magicWallTime = magicWallTime;
        hauteurMap = laMap.size();
        largeurMap = laMap.get(0).length();
        this.laMap = new char[hauteurMap][largeurMap];
        //création du tableau aux bonnes dimensions et double boucle pour le remplir
        for (int j = 0; j < hauteurMap; j++) {
            for (int i = 0; i < largeurMap; i++) {
                this.laMap[j][i] = formatageCaractere(laMap.get(j).charAt(i));
            }
        }
        trouverSortie();
        this.sortieOuverte = false;
        this.laMap[(int) posSortie.getX()][(int) posSortie.getY()] = ' ';
        tourAvantAmibe = amoebaTime;
        this.bonusDiamant = 0;
    }
    public int getTourAvantAmibe(){
        return tourAvantAmibe;
    }
    public void setTourAvantAmibe(int x){
        this.tourAvantAmibe=x;
    }
    public int getAmoebaTime(){
        return this.amoebaTime;
    }

    public char formatageCaractere(char a) {//Afin qu'il n'y est qu'un caractere par element
        if (a == 'F' || a == 'Q' || a == 'o' || a == 'O' || a == 'Q')
            return 'F';//Dans tout ces cas c'est une luciole alors il y a qu'un caractere
        else if (a == 'B' || a == 'b' || a == 'c' || a == 'C') return 'C';
        else if (a == 'P') return 'R';//sujet : rockford = R fichiers : rockford = P
        else return a;
    }

    public void testOuvrirSortie(int nbDiamonds) {
        if (!sortieOuverte) {
            if (nbDiamonds >= diamondsRequired) {
                this.sortieOuverte = true;
                laMap[(int) posSortie.getX()][(int) posSortie.getY()] = 'X';
            } else {
                this.sortieOuverte = false;
                laMap[(int) posSortie.getX()][(int) posSortie.getY()] = ' ';
            }
        }
    }

    public char[][] getLaMap() {
        return this.laMap;
    }

    public void setLaMap(char[][] map) {
        this.laMap = map;
    }

    public int getBonusDiamant() {
        return bonusDiamant;
    }

    public int getDiamondValue() {
        return diamondValue;
    }

    public boolean sortieOuverte() {
        return sortieOuverte;
    }

    public String getNom() {
        return this.name;
    }

    public int getHauteur() {
        return this.hauteurMap;
    }

    public int getLargeur() {
        return this.largeurMap;
    }

    private void trouverSortie() {
        Point pos = null;
        for (int i = 0; i < hauteurMap; i++) {
            for (int j = 0; j < largeurMap; j++) {
                if (laMap[i][j] == 'X') pos = new Point(i, j);
            }
        }
        this.posSortie = pos;
    }

    public Point trouverRockford() {
        Point pos = null;
        for (int i = 0; i < hauteurMap; i++) {
            for (int j = 0; j < largeurMap; j++) {
                if (laMap[i][j] == 'R') pos = new Point(i, j);
            }
        }
        return pos;
    }
    public char getElement(Point pos) {
        return (laMap[(int) pos.getX()][(int) pos.getY()]);
    }

    public void removeElement(Point pos) {
        laMap[(int) pos.getX()][(int) pos.getY()] = ' ';
    }

    public void addElement(Point pos, char lui) {
        laMap[(int) pos.getX()][(int) pos.getY()] = lui;
    }

    public int getCaveTime() {
        return (this.caveTime[0]); //Flemme de gérer les différents niveaux pour le moment
    }

    public String ecranDeJeu() {
        String s = "";
        for (int i = 0; i < laMap.length; i++) {
            for (int j = 0; j < laMap[i].length; j++) {
                if (laMap[i][j] == 'F' || laMap[i][j] == 'q' || laMap[i][j] == 'Q' || laMap[i][j] == 'o' || laMap[i][j] == 'O') {
                    s += 'F'; // Luciole
                } else if (laMap[i][j] == 'B' || laMap[i][j] == 'b' || laMap[i][j] == 'C' || laMap[i][j] == 'c') {
                    s += 'C'; //libellule
                } else {
                    s += laMap[i][j];
                }

            }
            s += "\n";
        }
        return s;
    }

    public String toString() {
        String s = "Name : " + name + " | CaveTime : ";
        for (int i = 0; i < caveTime.length; i++) s += caveTime[i] + " ";
        s += "| Diamonds required : " + diamondsRequired + " | Diamond value : " + diamondValue + " | Amoeba Time : " + amoebaTime + " | Magic Wall Time : " + magicWallTime + "\n\n";
        for (int i = 0; i < laMap.length; i++) {
            for (int j = 0; j < laMap[i].length; j++) {
                s += laMap[i][j];
            }
            s += "\n";
        }
        return s;
    }


}
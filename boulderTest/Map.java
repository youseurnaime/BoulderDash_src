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


    public boolean majMap() { //renvoie faux si rockford meure
        mobToAdd = new Hashtable<Point, Character>();

        bonusDiamant = 0;
        tourAvantAmibe--;

        for (int i = 1; i < hauteurMap - 1; i++) {
            for (int j = 1; j < largeurMap - 1; j++) {
                switch (laMap[i][j]) {
                    case 'r':
                        if (!gravite('r', i, j)) return false;
                        break;
                    case 'd':
                        gravite('d', i, j);
                        break;

                    case 'F':
                    case 'Q':
                    case 'q':
                    case 'O':
                    case 'o':
                        if (!deplacerLuciole(i, j)) return false;
                        break;
                    case 'C':
                    case 'c':
                    case 'B':
                    case 'b':
                        if (!deplacerLibellule(i, j)) return false;
                        break;
                }
            }
        }

        if (tourAvantAmibe == 0 && amoebaTime != 0) {
            if (!grandirAmibe()) return false;
            tourAvantAmibe = amoebaTime;
        }

        Enumeration<Point> mobPos = mobToAdd.keys();
        Point currentPos = null;
        while (mobPos.hasMoreElements()) {
            currentPos = mobPos.nextElement();
            laMap[(int) currentPos.getX()][(int) currentPos.getY()] = mobToAdd.get(currentPos);
            if (laMap[(int) currentPos.getX()][(int) currentPos.getY()] == 'R') {
                System.out.println("Un animal a dévoré Rockford !");
                return false;
            }

        }

        return true;
    }

    private void mortDeLibellule(int i, int j) {
        int nbDiam = 0;
        int distance = 1;
        while (nbDiam < 9 && i - distance > 0 && j - distance > 0 && i + distance < hauteurMap && j + distance < largeurMap) {
            for (int x = i - distance; x < i + distance; x++) {
                for (int y = j - distance; y < j + distance; y++) {
                    if (laMap[x][y] == ' ') {
                        laMap[x][y] = 'd';
                        nbDiam++;
                    }
                }
            }
            distance++;
        }
    }

    private boolean deplacerLuciole(int i, int j) { // c'est pas bo --> a réécrire avec des for
        if (laMap[i][j + 1] != ' ' && laMap[i][j + 1] != 'R' && laMap[i][j + 1] != 'a' && laMap[i][j - 1] != ' ' && laMap[i][j - 1] != 'R' && laMap[i][j - 1] != 'a' && laMap[i - 1][j] != ' ' && laMap[i - 1][j] != 'R' && laMap[i - 1][j] != 'a' && laMap[i + 1][j] != ' ' && laMap[i + 1][j] != 'R' && laMap[i + 1][j] != 'a')
            return true; //Si l'animal est bloqué
        switch (laMap[i][j]) {
            case 'F':
            case 'Q':
                if (laMap[i][j - 1] == ' ' || laMap[i][j - 1] == 'R' || laMap[i][j - 1] == 'a') {
                    if (laMap[i][j - 1] == 'R') {
                        System.out.println("Une luciole a mangé Rockford !");
                        return false;
                    } else {
                        if (laMap[i][j - 1] == ' ') mobToAdd.put(new Point(i, j - 1), 'Q');
                        laMap[i][j] = ' ';
                    }
                    break;
                } else {
                    laMap[i][j] = 'q';
                    return deplacerLuciole(i, j);
                }
            case 'q':


                if (laMap[i - 1][j] == ' ' || laMap[i - 1][j] == 'R' || laMap[i - 1][j] == 'a') {
                    if (laMap[i - 1][j] == 'R') {
                        System.out.println("Une luciole a mangé Rockford !");
                        return false;
                    } else {
                        if (laMap[i - 1][j] == ' ') mobToAdd.put(new Point(i - 1, j), 'q');
                        laMap[i][j] = ' ';
                    }
                    break;
                } else {
                    laMap[i][j] = 'O';
                    return deplacerLuciole(i, j);
                }
            case 'O':
                if (laMap[i][j + 1] == ' ' || laMap[i][j + 1] == 'R' || laMap[i][j + 1] == 'a') {
                    if (laMap[i][j + 1] == 'R') {
                        System.out.println("Une luciole a mangé Rockford !");
                        return false;
                    } else {
                        if (laMap[i][j + 1] == ' ') mobToAdd.put(new Point(i, j + 1), 'O');
                        laMap[i][j] = ' ';
                    }
                    break;
                } else {
                    laMap[i][j] = 'o';
                    return deplacerLuciole(i, j);
                }
            case 'o':
                if (laMap[i + 1][j] == ' ' || laMap[i + 1][j] == 'R' || laMap[i + 1][j] == 'a') {
                    if (laMap[i + 1][j] == 'R') {
                        System.out.println("Une luciole a mangé Rockford !");
                        return false;
                    } else {
                        if (laMap[i + 1][j] == ' ') mobToAdd.put(new Point(i + 1, j), 'o');
                        laMap[i][j] = ' ';
                    }
                    break;
                } else {
                    laMap[i][j] = 'Q';
                    return deplacerLuciole(i, j);
                }
        }

        return true;
    }

    private boolean deplacerLibellule(int i, int j) { // c'est pas bo --> a réécrire avec des for
        if (laMap[i][j + 1] != ' ' && laMap[i][j + 1] != 'R' && laMap[i][j + 1] != 'a' && laMap[i][j - 1] != ' ' && laMap[i][j - 1] != 'R' && laMap[i][j - 1] != 'a' && laMap[i - 1][j] != ' ' && laMap[i - 1][j] != 'R' && laMap[i - 1][j] != 'a' && laMap[i + 1][j] != ' ' && laMap[i + 1][j] != 'R' && laMap[i + 1][j] != 'a')
            return true; //Si l'animal est bloqué
        switch (laMap[i][j]) {
            case 'C':
                if (laMap[i][j + 1] == ' ' || laMap[i][j + 1] == 'R' || laMap[i][j + 1] == 'a') {
                    if (laMap[i][j + 1] == 'R') {
                        System.out.println("Une libellule a mangé Rockford !");
                        return false;
                    } else {
                        if (laMap[i][j + 1] == ' ') {
                            mobToAdd.put(new Point(i, j + 1), 'C');
                            laMap[i][j] = ' ';
                        }

                    }
                    break;
                } else {
                    laMap[i][j] = 'c';
                    return deplacerLibellule(i, j);
                }
            case 'c':
                if (laMap[i - 1][j] == ' ' || laMap[i - 1][j] == 'R' || laMap[i - 1][j] == 'a') {
                    if (laMap[i - 1][j] == 'R') {
                        System.out.println("Une libellule a mangé Rockford !");
                        return false;
                    } else {
                        if (laMap[i - 1][j] == ' ') mobToAdd.put(new Point(i - 1, j), 'c');
                        laMap[i][j] = ' ';
                    }
                    break;
                } else {
                    laMap[i][j] = 'b';
                    return deplacerLibellule(i, j);
                }
            case 'b':
                if (laMap[i][j - 1] == ' ' || laMap[i][j - 1] == 'R' || laMap[i][j - 1] == 'a') {
                    if (laMap[i][j - 1] == 'R') {
                        System.out.println("Une libellule a mangé Rockford !");
                        return false;
                    } else {
                        if (laMap[i][j - 1] == ' ') mobToAdd.put(new Point(i, j - 1), 'b');
                        laMap[i][j] = ' ';
                    }
                    break;
                } else {
                    laMap[i][j] = 'B';
                    return deplacerLibellule(i, j);
                }
            case 'B':
                if (laMap[i + 1][j] == ' ' || laMap[i + 1][j] == 'R' || laMap[i + 1][j] == 'a') {
                    if (laMap[i + 1][j] == 'R') {
                        System.out.println("Une libellule a mangé Rockford !");
                        return false;
                    } else {
                        if (laMap[i + 1][j] == ' ') mobToAdd.put(new Point(i + 1, j), 'B');
                        laMap[i][j] = ' ';
                    }
                    break;
                } else {
                    laMap[i][j] = 'C';
                    return deplacerLibellule(i, j);
                }
        }

        return true;
    }


    private boolean grandirAmibe() { //renvoie faux si rockford meure
        ArrayList<Point> lesCases = new ArrayList<Point>();

        for (int i = 1; i < hauteurMap - 1; i++) {
            for (int j = 1; j < largeurMap - 1; j++) {
                if (laMap[i][j] == ' ' || laMap[i][j] == '.' || laMap[i][j] == 'R' || laMap[i][j] == 'C' || laMap[i][j] == 'F') {
                    if (laMap[i - 1][j] == 'a' || laMap[i + 1][j] == 'a' || laMap[i][j - 1] == 'a' || laMap[i][j + 1] == 'a') {
                        lesCases.add(new Point(i, j));

                    }
                }
            }
        }
        if (lesCases.isEmpty()) {
            for (int i = 1; i < hauteurMap - 1; i++) {
                for (int j = 1; j < largeurMap - 1; j++) {
                    if (laMap[i][j] == 'a')
                        laMap[i][j] = 'r'; //si l'amibe ne peut plus grandire elle se transforme en rocs
                }
            }
        } else {
            int nombreAleatoire = (int) (Math.random() * (lesCases.size()));
            if (laMap[(int) lesCases.get(nombreAleatoire).getX()][(int) lesCases.get(nombreAleatoire).getY()] == 'R') {
                System.out.println("Rockford est mort dans une amibe !");
                return false;
            } else if ((laMap[(int) lesCases.get(nombreAleatoire).getX()][(int) lesCases.get(nombreAleatoire).getY()] == 'C' || laMap[(int) lesCases.get(nombreAleatoire).getX()][(int) lesCases.get(nombreAleatoire).getY()] == 'c' || laMap[(int) lesCases.get(nombreAleatoire).getX()][(int) lesCases.get(nombreAleatoire).getY()] == 'B') || laMap[(int) lesCases.get(nombreAleatoire).getX()][(int) lesCases.get(nombreAleatoire).getY()] == 'b') {
                mortDeLibellule((int) lesCases.get(nombreAleatoire).getX(), (int) lesCases.get(nombreAleatoire).getY());
            }
            laMap[(int) lesCases.get(nombreAleatoire).getX()][(int) lesCases.get(nombreAleatoire).getY()] = 'a';
        }


        return true;
    }

    private boolean gravite(char c, int i, int j) { //renvoie faux si rockford meure
        switch (laMap[i + 1][j]) {
            case ' ':
                laMap[i][j] = ' ';
                laMap[i + 1][j] = c;
                break;

            case 'r':
                if (j > 0) {
                    if (laMap[i + 1][j - 1] == ' ' || laMap[i + 1][j - 1] == 'R') {
                        if (laMap[i + 1][j - 1] == 'R') {
                            laMap[i][j] = ' ';
                            laMap[i + 1][j - 1] = c;
                            System.out.println(ecranDeJeu());
                            System.out.println("Rockford est mort sous un rocher !");
                            return false;
                        } else if (laMap[i + 1][j - 1] == 'C' || laMap[i + 1][j - 1] == 'c' || laMap[i + 1][j - 1] == 'B' || laMap[i + 1][j - 1] == 'b') {
                            laMap[i][j] = ' ';
                            laMap[i + 1][j - 1] = c;
                            mortDeLibellule(i + 1, j - 1);
                        }
                        laMap[i][j] = ' ';
                        laMap[i + 1][j - 1] = c;

                    }
                }
                if (j < largeurMap) {
                    if (laMap[i + 1][j + 1] == ' ' || laMap[i + 1][j + 1] == 'R') {
                        if (laMap[i + 1][j + 1] == 'R') {
                            laMap[i][j] = ' ';
                            laMap[i + 1][j + 1] = c;
                            System.out.println(ecranDeJeu());
                            System.out.println("Rockford est mort sous un rocher !");
                            return false;
                        } else if (laMap[i + 1][j + 1] == 'C' || laMap[i + 1][j + 1] == 'c' || laMap[i + 1][j + 1] == 'B' || laMap[i + 1][j + 1] == 'b') {
                            laMap[i][j] = ' ';
                            laMap[i + 1][j - 1] = c;
                            mortDeLibellule(i + 1, j - 1);
                        }


                    }
                }
                break;

        }
        return true;
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
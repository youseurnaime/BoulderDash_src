package boulderTest;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.awt.Point;

/**
 * Created by clement on 14/03/17.
 */
public class Mobs {


    public static Map majMob(Map laMap) {
        Hashtable<Point, Character> mobToAdd = new Hashtable<Point, Character>();
        int hauteurMap = laMap.getHauteur();
        int largeurMap = laMap.getLargeur();
        for (int i = 1; i < hauteurMap - 1; i++) {
            for (int j = 1; j < largeurMap - 1; j++) {
                switch (laMap.getLaMap()[i][j]) {
                    case 'r':
                    case 'd':
                        gravite(laMap.getLaMap()[i][j], i, j, laMap);
                        break;

                    case 'F':
                    case 'Q':
                    case 'q':
                    case 'O':
                    case 'o':
                        deplacerLuciole(i, j);
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
        return laMap;
    }

    private static void mortDeLibellule(int i, int j, char[][] laMap) {
        int nbDiam = 0;
        int distance = 1;
        char map[][] = laMap.getLaMap();
        while (nbDiam < 9 && i - distance > 0 && j - distance > 0 && i + distance < laMap.getHauteur() && j + distance < laMap.getLargeur()) {
            for (int x = i - distance; x < i + distance; x++) {
                for (int y = j - distance; y < j + distance; y++) {
                    if (map[x][y] == ' ') {
                        map[x][y] = 'd';
                        nbDiam++;
                    }
                }
            }
            distance++;
        }
        laMap.setLaMap(map);
    }

    private static Hashtable<Point, Character> deplacerLuciole(int i, int j, char[][] map, Hashtable<Point, Character> mobToAdd) { // c'est pas bo --> a réécrire avec des for

        if (map[i][j + 1] != ' ' && map[i][j + 1] != 'R' && map[i][j + 1] != 'a' && map[i][j - 1] != ' ' && map[i][j - 1] != 'R' && map[i][j - 1] != 'a' && map[i - 1][j] != ' ' && map[i - 1][j] != 'R' && map[i - 1][j] != 'a' && map[i + 1][j] != ' ' && map[i + 1][j] != 'R' && map[i + 1][j] != 'a')
            return true; //Si l'animal est bloqué
        switch (map[i][j]) {
            case 'F':
            case 'Q':
                if (map[i][j - 1] == ' ' || map[i][j - 1] == 'R' || map[i][j - 1] == 'a') {

                    if (map[i][j - 1] != 'a') {
                        mobToAdd.put(new Point(i, j - 1), 'Q');
                    }
                    map[i][j] = ' ';
                    return mobToAdd;
                } else {
                    map[i][j] = 'q';
                    return deplacerLuciole(i, j, map, mobToAdd);
                }
            case 'q':
                if (map[i - 1][j] == ' ' || map[i - 1][j] == 'R' || map[i - 1][j] == 'a') {

                    if (map[i - 1][j] != 'a') {
                        mobToAdd.put(new Point(i - 1, j), 'q');
                    }
                    map[i][j] = ' ';
                    return mobToAdd;
                } else {
                    map[i][j] = 'O';
                    return deplacerLuciole(i, j, map, mobToAdd);
                }
            case 'O':
                if (map[i][j + 1] == ' ' || map[i][j + 1] == 'R' || map[i][j + 1] == 'a') {

                    if (map[i][j + 1] != 'a') {
                        mobToAdd.put(new Point(i, j + 1), 'O');
                    }
                    map[i][j] = ' ';
                    return mobToAdd;
                } else {
                    map[i][j] = 'o';
                    return deplacerLuciole(i, j, map, mobToAdd);
                }
            case 'o':
                if (map[i + 1][j] == ' ' || map[i + 1][j] == 'R' || map[i + 1][j] == 'a') {

                    if (map[i + 1][j] != 'a') {
                        mobToAdd.put(new Point(i + 1, j), 'o');
                    }
                    map[i][j] = ' ';
                    return mobToAdd;
                } else {
                    map[i][j] = 'Q';
                    return deplacerLuciole(i, j, map, mobToAdd);
                }
        }
        return mobToAdd;
    }

    private Hashtable<Point, Character> deplacerLibellule(int i, int j) { // c'est pas bo --> a réécrire avec des for
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

    private static void gravite(char c, int i, int j, Map laMap) {
        char[][] map = laMap.getLaMap();
        int largeurMap = laMap.getLargeur();
        switch (map[i + 1][j]) {
            case ' ':
            case 'R':
                map[i][j] = ' ';
                map[i + 1][j] = c;
                break;

            case 'r':
                if (j > 0) {
                    if (map[i + 1][j - 1] == ' ' || map[i + 1][j - 1] == 'R') {
                        map[i][j] = ' ';
                        map[i + 1][j - 1] = c;
                    } else if (map[i + 1][j - 1] == 'C' || map[i + 1][j - 1] == 'c' || map[i + 1][j - 1] == 'B' || map[i + 1][j - 1] == 'b') {
                        map[i][j] = ' ';
                        map[i + 1][j - 1] = c;
                        mortDeLibellule(i + 1, j - 1, map);
                    }
                }
                if (j < largeurMap) {
                    if (map[i + 1][j + 1] == ' ' || map[i + 1][j + 1] == 'R') {
                        map[i][j] = ' ';
                        map[i + 1][j + 1] = c;
                    } else if (map[i + 1][j + 1] == 'C' || map[i + 1][j + 1] == 'c' || map[i + 1][j + 1] == 'B' || map[i + 1][j + 1] == 'b') {
                        map[i][j] = ' ';
                        map[i + 1][j - 1] = c;
                        mortDeLibellule(i + 1, j - 1, map);
                    }
                }
                break;
        }
    }
}
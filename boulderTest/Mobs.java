package boulderTest;

import java.awt.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

/**
 * Created by clement on 14/03/17.
 */
public class Mobs {//Tout ce qui est relatif au actioins du monde


    public static Map majMob(Map laMap) {
        Hashtable<Point, Character> mobToAdd = new Hashtable<Point, Character>();
        int hauteurMap = laMap.getHauteur();
        int largeurMap = laMap.getLargeur();
        char[][] map= laMap.getLaMap();
        for (int i = 1; i < hauteurMap - 1; i++) {
            for (int j = 1; j < largeurMap - 1; j++) {
                switch (laMap.getLaMap()[i][j]) {
                    case 'r':
                    case 'd':
                        gravite(map[i][j], i, j, laMap);
                        break;

                    case 'F':
                    case 'Q':
                    case 'q':
                    case 'O':
                    case 'o':
                        deplacerLuciole(i, j, map,mobToAdd);
                        break;
                    case 'C':
                    case 'c':
                    case 'B':
                    case 'b':
                        deplacerLibellule(i, j, map,mobToAdd);
                        break;
                }
            }
        }

        laMap.setTourAvantAmibe(laMap.getTourAvantAmibe()-1);

        if (laMap.getTourAvantAmibe() == 0 && laMap.getAmoebaTime() != 0) {
            grandirAmibe(laMap);
            laMap.setTourAvantAmibe( laMap.getAmoebaTime());
        }

        map=laMap.getLaMap();
        Enumeration<Point> mobPos = mobToAdd.keys();
        Point currentPos = null;
        while (mobPos.hasMoreElements()) {
            currentPos = mobPos.nextElement();
            map[(int) currentPos.getX()][(int) currentPos.getY()] = mobToAdd.get(currentPos);
            if (map[(int) currentPos.getX()][(int) currentPos.getY()] == 'R') {
                System.out.println("Un animal a dévoré Rockford !");
            }
        }
        return laMap;
    }

    private static char[][] mortDeLibellule(int i, int j, char [][] map) {
        int nbDiam = 0;
        int distance = 1;
        while (nbDiam < 9 && i - distance > 0 && j - distance > 0 && i + distance <map.length && j + distance < map[0].length) {
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
        return map;
    }

    private static Hashtable<Point, Character> deplacerLuciole(int i, int j, char[][] map, Hashtable<Point, Character> mobToAdd) { // c'est pas bo --> a réécrire avec des for

        if (map[i][j + 1] != ' ' && map[i][j + 1] != 'R' && map[i][j + 1] != 'a' && map[i][j - 1] != ' ' && map[i][j - 1] != 'R' && map[i][j - 1] != 'a' && map[i - 1][j] != ' ' && map[i - 1][j] != 'R' && map[i - 1][j] != 'a' && map[i + 1][j] != ' ' && map[i + 1][j] != 'R' && map[i + 1][j] != 'a')
            return mobToAdd;

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

    private static Hashtable<Point, Character> deplacerLibellule(int i, int j, char[][] map, Hashtable<Point, Character> mobToAdd) { // c'est pas bo --> a réécrire avec des for
        if (map[i][j + 1] != ' ' && map[i][j + 1] != 'R' && map[i][j + 1] != 'a' && map[i][j - 1] != ' ' && map[i][j - 1] != 'R' && map[i][j - 1] != 'a' && map[i - 1][j] != ' ' && map[i - 1][j] != 'R' && map[i - 1][j] != 'a' && map[i + 1][j] != ' ' && map[i + 1][j] != 'R' && map[i + 1][j] != 'a')
            return mobToAdd; //Si l'animal est bloqué

        switch (map[i][j]) {
            case 'C':
                if (map[i][j + 1] == ' ' || map[i][j + 1] == 'R'||map[i][j + 1] == 'a') {
                    map[i][j] = ' ';
                        if (map[i][j + 1] == ' '|| map[i][j + 1] == 'R') {
                            mobToAdd.put(new Point(i, j + 1), 'C');
                        }
                        if(map[i][j + 1] == 'a') {
                            map=mortDeLibellule(i,j,map);
                        }
                    break;
                } else {
                    map[i][j] = 'c';
                    return deplacerLibellule(i, j,map,mobToAdd);
                }
            case 'c':
                if (map[i-1][j] == ' ' || map[i-1][j] == 'R'||map[i-1][j] == 'a') {
                    map[i][j] = ' ';
                    if (map[i-1][j] == ' '|| map[i-1][j] == 'R') {
                        mobToAdd.put(new Point(i-1, j), 'c');
                    }
                    if(map[i][j + 1] == 'a') {
                        map=mortDeLibellule(i,j,map);
                    }
                    break;
                } else {
                    map[i][j] = 'b';
                    return deplacerLibellule(i, j,map,mobToAdd);
                }
            case 'b':
                if (map[i][j - 1] == ' ' || map[i][j - 1] == 'R'||map[i][j - 1] == 'a') {
                    map[i][j] = ' ';
                    if (map[i][j - 1] == ' '|| map[i][j - 1] == 'R') {
                        mobToAdd.put(new Point(i, j - 1), 'b');
                    }
                    if(map[i][j - 1] == 'a') {
                        map=mortDeLibellule(i,j,map);
                    }
                    break;
                } else {
                    map[i][j] = 'B';
                    return deplacerLibellule(i, j,map,mobToAdd);
                }
            case 'B':
                if (map[i+1][j] == ' ' || map[i+1][j] == 'R'||map[i+1][j] == 'a') {
                    map[i][j] = ' ';
                    if (map[i+1][j] == ' '|| map[i+1][j] == 'R') {
                        mobToAdd.put(new Point(i+1, j), 'B');
                    }
                    if(map[i][j + 1] == 'a') {
                        map=mortDeLibellule(i,j,map);
                    }
                    break;
                } else {
                    map[i][j] = 'C';
                    return deplacerLibellule(i, j,map,mobToAdd);
                }
        }

        return mobToAdd;
    }

    private static void grandirAmibe(Map laMap) { //renvoie faux si rockford meure
        ArrayList<Point> lesCases = new ArrayList<Point>();
        char[][] map=laMap.getLaMap();
        int hauteurMap=laMap.getHauteur();
        int largeurMap=laMap.getLargeur();
        for (int i = 1; i < hauteurMap - 1; i++) {
            for (int j = 1; j < largeurMap - 1; j++) {
                if (map[i][j] == ' ' || map[i][j] == '.' || map[i][j] == 'R' || map[i][j] == 'C' || map[i][j] == 'F') {
                    if (map[i - 1][j] == 'a' || map[i + 1][j] == 'a' || map[i][j - 1] == 'a' || map[i][j + 1] == 'a') {
                        lesCases.add(new Point(i, j));

                    }
                }
            }
        }
        if (lesCases.isEmpty()) {
            for (int i = 1; i < hauteurMap - 1; i++) {
                for (int j = 1; j < largeurMap - 1; j++) {
                    if (map[i][j] == 'a')
                        map[i][j] = 'r'; //si l'amibe ne peut plus grandire elle se transforme en rocs
                }
            }
        } else {
            int nombreAleatoire = (int) (Math.random() * (lesCases.size()));
            if ((map[(int) lesCases.get(nombreAleatoire).getX()][(int) lesCases.get(nombreAleatoire).getY()] == 'C' || map[(int) lesCases.get(nombreAleatoire).getX()][(int) lesCases.get(nombreAleatoire).getY()] == 'c' || map[(int) lesCases.get(nombreAleatoire).getX()][(int) lesCases.get(nombreAleatoire).getY()] == 'B') || map[(int) lesCases.get(nombreAleatoire).getX()][(int) lesCases.get(nombreAleatoire).getY()] == 'b') {
                mortDeLibellule((int) lesCases.get(nombreAleatoire).getX(), (int) lesCases.get(nombreAleatoire).getY(),map);
            }
            map[(int) lesCases.get(nombreAleatoire).getX()][(int) lesCases.get(nombreAleatoire).getY()] = 'a';
        }
        laMap.setLaMap(map);
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
                        map=mortDeLibellule(i + 1, j - 1, map);
                    }
                }
                if (j < largeurMap) {
                    if (map[i + 1][j + 1] == ' ' || map[i + 1][j + 1] == 'R') {
                        map[i][j] = ' ';
                        map[i + 1][j + 1] = c;
                    } else if (map[i + 1][j + 1] == 'C' || map[i + 1][j + 1] == 'c' || map[i + 1][j + 1] == 'B' || map[i + 1][j + 1] == 'b') {
                        map[i][j] = ' ';
                        map[i + 1][j - 1] = c;
                        map=mortDeLibellule(i + 1, j - 1, map);
                    }
                }
                break;
        }
        laMap.setLaMap(map);
    }
}
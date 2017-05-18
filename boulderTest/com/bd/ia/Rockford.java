package boulderTest.com.bd.ia;

import boulderTest.Map;
import boulderTest.com.bd.ia.IAlgorithme;

import java.awt.*;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.StringTokenizer;
import java.util.Vector;

import com.sun.xml.internal.ws.api.streaming.XMLStreamReaderFactory;
import org.jgraph.*;
import org.jgraph.graph.*;
import org.jgraph.graph.DefaultEdge;
import org.jgrapht.*;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.*;


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


    public static boolean rocPoussable(Point pos, Point posRockford, Map laMap) {
        Point posApresRoc = new Point((int) pos.getX() * 2 - (int) posRockford.getX(), (int) pos.getY() * 2 - (int) posRockford.getY());//formule pour avoir la prochaine case dans la continuité de la direction choisie
        return (laMap.getElement(posApresRoc) == ' ');
    }


    protected char getDeplacementRandom(Point posRockford, Map laMap) {
        int choixRandom;
        char deplacement;
        Point essaieDeDeplacement;
        do {
            choixRandom = (int) (Math.random() * 5); // Int random entre 0 et 4
            switch (choixRandom) {
                case 1:
                    deplacement = 'U';
                    break;
                case 2:
                    deplacement = 'D';
                    break;
                case 3:
                    deplacement = 'L';
                    break;
                case 4:
                    deplacement = 'R';
                    break;
                case 0:
                default:
                    deplacement = 'I';
                    break;
            }
            essaieDeDeplacement = charToPos(posRockford, deplacement);
        } while (!deplacementPossible(essaieDeDeplacement, posRockford, laMap));
        return deplacement;
    }

    public static Point charToPos(Point posRockford, char c) {
        switch (c) {
            case 'U':
                return new Point((int) posRockford.getX() - 1, (int) posRockford.getY());
            case 'D':
                return new Point((int) posRockford.getX() + 1, (int) posRockford.getY());
            case 'L':
                return new Point((int) posRockford.getX(), (int) posRockford.getY() - 1);
            case 'R':
                return new Point((int) posRockford.getX(), (int) posRockford.getY() + 1);
            default:
                return posRockford;//Toute autre valeur = immobile
        }
    }

    public SimpleGraph<String, DefaultEdge> mapToGraph(Map laMap) {
        SimpleGraph<String, DefaultEdge> leGraph =
                new SimpleGraph(DefaultEdge.class);
        char[][] grille = laMap.getLaMap();

        for (int i = 0; i < grille.length; i++) {
            for (int j = 0; j < grille[0].length; j++) {
                leGraph.addVertex(i + "," + j);
                if (i != 0) {
                    if (deplacementPossible(new Point(i - 1, j), new Point(i, j), laMap)) {
                        leGraph.addEdge((i - 1) + "," + j, i + "," + j);
                    }
                }

                if (j != 0) {
                    if (deplacementPossible(new Point(i, j - 1), new Point(i, j), laMap)) {
                        leGraph.addEdge(i + "," + (j - 1), i + "," + j);
                    }
                }
            }
        }
        return leGraph;
    }

    public Point shortestPath(Graph leGraph, Map laMap, Point posRockford, Point posObjectif) {
        DijkstraShortestPath despe = new DijkstraShortestPath<String, DefaultEdge>(leGraph);
        GraphPath chemin = despe.getPath(posToVertex(posRockford), posToVertex(posObjectif));
        if (chemin == null) return null;
        return (vertexToPos((String) chemin.getVertexList().get(1)));

    }

    public boolean ciblesAccessibles(Graph leGraph, Point posRockford, Map laMap) {
        ArrayList<Point> lesPos = laMap.trouverLesCibles('d');
        if (laMap.sortieOuverte()) lesPos.add(laMap.trouverLesCibles('X').get(0));
        boolean oui = false;
        for (int i = 0; i < lesPos.size(); i++) {
            if (shortestPath(leGraph, laMap, posRockford, lesPos.get(i)) != null) oui = true;
        }
        return oui;
    }

    private Point vertexToPos(String v) {
        StringTokenizer st = new StringTokenizer(v, ",");
        return new Point(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
    }

    private String posToVertex(Point p) {
        return ((int) p.getX() + "," + (int) p.getY());
    }


}

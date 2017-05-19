package boulderTest.com.bd.ia;

import boulderTest.Map;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import java.awt.*;
import java.util.NoSuchElementException;

/**
 * Created by marin on 18/03/2017.
 */
public class Directif extends Rockford {

    private SimpleGraph leGraph;
    private Point cibleActuelle;
    private boolean init;

    public Directif() {
        this.init = false;
    }

    public Point getDeplacement(Point posRockford, Map laMap) {
        leGraph = mapToGraph(laMap);
        if (laMap.cibleLaPlusProche() == null) {
            System.out.println("Rockford ne voit ni diamants ni sortie. Il est perdu et fait des déplacements aléatoires...\nL'IA directif n'est pas adaptée pour ce niveau.");
            return charToPos(posRockford, getDeplacementRandom(posRockford, laMap));
        }
        if (!ciblesAccessibles(leGraph, posRockford, laMap)) {
            return charToPos(posRockford, getDeplacementRandom(posRockford, laMap));
        }
        if (!init) {
            init = true;
            cibleActuelle = laMap.cibleLaPlusProche();
        }

        if (posRockford.equals(cibleActuelle)) {
            cibleActuelle = laMap.cibleLaPlusProche();
        }
        Point futurDeplacement = shortestPath(leGraph, laMap, posRockford, cibleActuelle);
        while (futurDeplacement == null) {
            cibleActuelle = laMap.getCibleRandom();
            futurDeplacement = shortestPath(leGraph, laMap, posRockford, cibleActuelle);
        }
        return (futurDeplacement);
    }

    public String toString() {
        return "directif";
    }
}

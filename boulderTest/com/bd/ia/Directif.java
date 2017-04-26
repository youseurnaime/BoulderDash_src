package boulderTest.com.bd.ia;

import boulderTest.Map;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import java.awt.*;

/**
 * Created by marin on 18/03/2017.
 */
public class Directif extends Rockford {

    private SimpleGraph leGraph;

    public Directif(Map laMap){
        leGraph = mapToGraph(laMap);
    }

    public Point getDeplacement(Point posRockford, Map laMap) {
        return null;
    }
}

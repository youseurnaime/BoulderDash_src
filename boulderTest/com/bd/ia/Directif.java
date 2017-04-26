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
    private Point cibleActuelle;
    private boolean init;

    public Directif(){
        this.init = false;
    }

    public Point getDeplacement(Point posRockford, Map laMap) {
        leGraph = mapToGraph(laMap);
        if(!init){
            init = true;
            cibleActuelle = laMap.cibleLaPlusProche();
        }

        if(posRockford.equals(cibleActuelle)){
            cibleActuelle = laMap.cibleLaPlusProche();
        }
        System.out.println(cibleActuelle.toString());
        return(shortestPath(leGraph,laMap,posRockford,cibleActuelle));
    }
}

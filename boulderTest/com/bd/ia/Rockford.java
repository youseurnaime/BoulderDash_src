package boulderTest.com.bd.ia;

import boulderTest.Map;
import boulderTest.com.bd.ia.IAlgorithme;

import java.awt.*;

import org.jgraph.*;
import org.jgraph.graph.*;
import org.jgrapht.*;
import org.jgrapht.graph.DefaultDirectedGraph;


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


    protected char getDeplacementRandom(Point posRockford, Map laMap){
        int choixRandom;
        char deplacement;
        Point essaieDeDeplacement;
        do{
            choixRandom = (int) (Math.random() * 5); // Int random entre 0 et 4
            switch (choixRandom){
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
            essaieDeDeplacement = charToPos(posRockford,deplacement);
        }while(!deplacementPossible(essaieDeDeplacement,posRockford,laMap));
        return deplacement;
    }

    public static Point charToPos(Point posRockford, char c){
        switch (c) {
            case 'U':
                return new Point((int) posRockford.getX()-1, (int) posRockford.getY());
            case 'D':
                return new Point((int) posRockford.getX()+1, (int) posRockford.getY());
            case 'L':
                return new Point((int) posRockford.getX(), (int) posRockford.getY()-1);
            case 'R':
                return new Point((int) posRockford.getX(), (int) posRockford.getY()+1);
            default:
                return posRockford;//Toute autre valeur = immobile
        }
    }

    public DirectedGraph mapToGraph(Map laMap){
        DirectedGraph<String, DefaultEdge> leGraph =
                new DefaultDirectedGraph<String, DefaultEdge>(DefaultEdge.class);
        char[][]grille=laMap.getLaMap();

        for (int i=0;i<grille.length;i++){
            for (int j=0;j<grille[0].length;j++){
                leGraph.addVertex(i+","+j);
                if(i!=0){
                    if(deplacementPossible(new Point(i-1,j),new Point(i,j),laMap)){
                        leGraph.addEdge((i-1)+","+j,i+","+j);
                        leGraph.addEdge(i+","+j,(i-1)+","+j);
                    }
                }

                if (j!=0){
                    if(deplacementPossible(new Point(i,j-1),new Point(i,j),laMap)){
                        leGraph.addEdge(i+","+(j-1),i+","+j);
                        leGraph.addEdge(i+","+j,i+","+(j-1));
                    }
                }
            }
        }
        return leGraph;
    }

    public Object[] shortestPath(){
        return null;
    }

    public Point[]PlusProcheDiamant(Map laMap, Point posRockford){
        char[][]grille=laMap.getLaMap();
        Point diamPlusProche=null;
        double distance=grille.length*grille[0].length; // CHemin le plus long possible
        Point[]tabDiam=new Point[laMap.nbDiam()];
        for (Point i:tabDiam) {
            // Graph ://///
        }
        return null;
    }
}
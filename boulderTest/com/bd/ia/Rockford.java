package boulderTest.com.bd.ia;

import boulderTest.Map;
import boulderTest.com.bd.ia.IAlgorithme;

import java.awt.*;

// JGraphX
import javax.swing.JFrame;
import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxGeometry;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;


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

    public mxGraph mapToGraph(Map laMap){
        Object v1=null;
        char[][]grille=laMap.getLaMap();
        mxGraph graph = new mxGraph();
        Object parent = graph.getDefaultParent();
        Object[] sauvegarde= new Object[grille.length];//a toute les vertex de la ligne d'avant
        graph.getModel().beginUpdate();
        for (int i=0;i<grille.length;i++){
            for (int j=0;j<grille[0].length;j++){
                Object v2 = graph.insertVertex(parent,"("+i+";"+j+")",new Point(i,j),i,j,i,j);
                if(i!=0){
                    if(deplacementPossible(new Point(i-1,j),new Point(i,j),laMap)){
                        graph.insertEdge(parent, null, "("+(i-1)+";"+j+")->("+i+";"+j+")", v1, v2);
                        graph.insertEdge(parent, null, "("+(i)+";"+j+")->("+(i-1)+";"+j+")", v2, v1);
                    }
                }

                if (j!=0){
                    if(deplacementPossible(new Point(i,j-1),new Point(i,j),laMap)){
                        graph.insertEdge(parent, null, "("+i+";"+(j-1)+")->("+i+";"+j+")", v1, sauvegarde[i]);
                        graph.insertEdge(parent, null, "("+(i)+";"+j+")->("+i+";"+(j-1)+")", sauvegarde[i], v1);
                    }
                }
                v1=v2;
                sauvegarde[i]=v2;
            }
        }
        graph.getModel().endUpdate();
        return graph;
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

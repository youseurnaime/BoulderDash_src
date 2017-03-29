package boulderTest.com.bd.ia;

import boulderTest.Map;

import java.awt.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

/**
 * Created by marin on 18/03/2017.
 */
public class Evolue extends Rockford {
    private int nbMutations;
    private ArrayList<Simulation> population;

    final int effectifMax = 40; //nombre d'individus max
    final int nbInitDeplacement = 5;

    public Evolue(int nbMutations){
        this.nbMutations = nbMutations;
        this.population = new ArrayList<Simulation>();
    }




    public Point getDeplacement(Point posRockford, Map laMap) {
        return null;
    }

    private String algoEvolue(Point posRockford, Map laMap){
        return "";
    }

    private void init(Map laMap){
        population.add(new Simulation(laMap,""));
    }

    private void mutation(){
        int tailleInit = population.size();
        Simulation fils;
        for(int i = 0 ; i < tailleInit ; i++){
           fils = population.get(i);
           fils.jouerTour(getDeplacementRandom(fils.getPosRockford(),fils.getLaMap()));
           population.add(fils);
        }
    }

    private void selection(){
        
    }

}

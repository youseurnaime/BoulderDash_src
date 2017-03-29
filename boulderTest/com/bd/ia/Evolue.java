package boulderTest.com.bd.ia;

import boulderTest.Map;

import java.awt.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by marin on 18/03/2017.
 */
public class Evolue extends Rockford {
    private int nbMutations;
    private ArrayList<Simulation> population;
    private ConcurrentLinkedQueue<Character> lesDeplacements;

    final int effectifMax = 40; //nombre d'individus max

    public Evolue(int nbMutations,Map laMap){
        this.nbMutations = nbMutations;
        this.population = new ArrayList<Simulation>();
        lesDeplacements = new ConcurrentLinkedQueue<Character>();
        System.out.println("Calcul du joueur évolué...");
        String strDeplacements = algoEvolue(laMap);
        System.out.println("ok !\n\n");
        System.out.println(strDeplacements);

        for(int i = 0 ; i < strDeplacements.length() ; i++){
            lesDeplacements.add(strDeplacements.charAt(i));
        }

    }




    public Point getDeplacement(Point posRockford, Map laMap) {
        char c = lesDeplacements.remove();
        return charToPos(posRockford,c);
    }

    private String algoEvolue(Map laMap) {
        init(laMap);
        for(int i = 0 ; i < nbMutations ; i++){
            System.out.println("MUTATION "+i);
            mutation(laMap);
            selection();
        }
        return selectionFinale();
    }

    private void init(Map laMap){
        population.add(new Simulation(laMap,""));
    }

    private void mutation(Map laMap){
        int tailleInit = population.size();
        Simulation fils;
        char modif;
        for(int i = 0 ; i < tailleInit ; i++){
            modif = getDeplacementRandom(population.get(i).getPosRockford(),population.get(i).getLaMap());
           fils = new Simulation(laMap,population.get(i).getChemin()+modif);
           if(fils.evaluer() >= 0) population.add(fils); //on élimine directement les simulations ou rockford meurt
        }
    }

    private void selection(){
        int evalMin = 9999999;//faudrait changer ca
        int indexMin = -1;
        if(population.size() > effectifMax){
            for(int i = 0 ; i < population.size() ; i++){
                System.out.println(population.get(i).getChemin());
                if(population.get(i).evaluer() < evalMin){
                    indexMin = i;
                    evalMin = population.get(i).evaluer();
                }
            }
            population.remove(indexMin);
            selection();
        }
    }

    private String selectionFinale(){
        String chemin;
        int evalMax = 0;
        int indexMax = -1;
        if(population.isEmpty()) return "";
        for(int i = 0 ; i < population.size() ; i++){
            System.out.println(population.get(i).evaluer());
            if(population.get(i).evaluer() > evalMax && population.get(i).isNiveauReussi()){
                indexMax = i;
                evalMax = population.get(i).evaluer();
            }
        }
        if(indexMax == -1){
            for(int i = 0 ; i < population.size() ; i++){
                if(population.get(i).evaluer() > evalMax){
                    indexMax = i;
                    evalMax = population.get(i).evaluer();
                }
            }
        }
        return population.get(indexMax).getChemin();
    }

}

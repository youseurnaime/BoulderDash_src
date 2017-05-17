package boulderTest.com.bd.ia;

import boulderTest.Map;
import boulderTest.Partie;

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
    private ArrayList<String> population;
    private ConcurrentLinkedQueue<Character> lesDeplacements;

    final int EFFECTIF_MAX = 25; //nombre d'individus max

    public Evolue(int nbMutations,Map laMap, boolean manuel, String chemin){
        this.nbMutations = nbMutations;
        this.population = new ArrayList<String>();
        lesDeplacements = new ConcurrentLinkedQueue<Character>();
        System.out.println("Calcul du joueur évolué...");
        String strDeplacements = algoEvolue(laMap.clone(),manuel,chemin);
        System.out.println("ok !\n\n");
        System.out.println(strDeplacements);

        for(int i = 0 ; i < strDeplacements.length() ; i++){
            lesDeplacements.add(strDeplacements.charAt(i));
        }

    }

    private void testAfficherArrayList(Map laMap){
        for(int i = 0 ; i < population.size() ; i++){
            try{
                System.out.println(i+" : "+population.get(i)+" "+new Simulation(laMap.clone(),population.get(i)).evaluer());
            }catch (Exception e){

            }
        }
    }



    public Point getDeplacement(Point posRockford, Map laMap) {
        char c = lesDeplacements.remove();
        return charToPos(posRockford,c);
    }

    private String algoEvolue(Map laMap,boolean manuel, String chemin) {
        init(laMap,manuel,chemin);
        for(int i = 0 ; i < nbMutations ; i++){
            System.out.println("MUTATION "+i);
            mutation();
            testAfficherArrayList(laMap);
            selection(laMap);
        }
        return selectionFinale(laMap);
    }

    private void init(Map laMap,boolean manuel, String chem){
        boolean cheminValide = false;
        String chemin;
        do{
            if(!manuel) {
                try {
                    chemin = cheminRandom(laMap.getCaveTime());
                    new Simulation(laMap.clone(), chemin);
                    population.add(chemin);
                } catch (Exception e) {

                }
            } else {
                population.add(chem);
            }

        }while(population.size() < EFFECTIF_MAX);

    }

    private void mutation (){
        int tailleInit = population.size();
        int rand;
        for(int i = 0 ; i < tailleInit ; i++){
            rand = (int) (Math.random() * population.get(i).length()); //on tire une position au hasard;
            population.add(changeChar(population.get(i),rand,directionRandom()));
        }
    }

    private void selection(Map laMap){
        double evalMin;
        int index;
        double eval;
        while(population.size() > EFFECTIF_MAX){
            evalMin = 99999;
            index = -1;
            for(int i = 0 ; i < population.size() ; i++){
                try{
                    eval = new Simulation(laMap.clone(),population.get(i)).evaluer();
                    if(eval < evalMin){
                        index = i;
                        evalMin = eval;
                    }
                }catch (CheminNonValideException e){
                    if(e.getNumTour() - 1000 < evalMin){
                        index = i;
                        evalMin = e.getNumTour() - 1000;
                    }
                }
            }
            population.remove(index);
        }
    }

    private String selectionFinale(Map laMap){
        double evalMax = -1000;
        int index = -1;
        for (int i = 0 ; i < population.size() ; i++){//on tente d'abord de sélectionner un chemin qui gagne
            try{
                if(new Simulation(laMap.clone(),population.get(i)).isRockfordAlive() && new Simulation(laMap.clone(),population.get(i)).evaluer() > evalMax){
                    index = i;
                    evalMax = new Simulation(laMap.clone(),population.get(i)).evaluer();
                }
            }catch (CheminNonValideException e){

            }
        }
        if (index != -1) return population.get(index);

        for (int i = 0 ; i < population.size() ; i++){//on tente d'abord de sélectionner un chemin qui gagne
            try{
                if(new Simulation(laMap.clone(),population.get(i)).evaluer() > evalMax) {
                    index = i;
                    evalMax = new Simulation(laMap.clone(),population.get(i)).evaluer();
                }
            }catch (CheminNonValideException e){
                if(e.getNumTour() - 1000 > evalMax){
                    index = i;
                    evalMax = e.getNumTour() - 1000;
                }
            }
        }
        return population.get(index);
    }

    private String cheminRandom(int taille){
        String s = "";
        int rand;
        for(int i = 0 ; i < taille ; i++){
            rand = (int) (Math.random() * 5); // Int random entre 0 et 4
            switch (rand){
                case 1:
                    s += 'U';
                    break;
                case 2:
                    s += 'D';
                    break;
                case 3:
                    s += 'L';
                    break;
                case 4:
                    s += 'R';
                    break;
                case 0:
                default:
                    s += 'I';
                    break;
            }
        }
        return s;
    }

    private char directionRandom(){
        int rand = (int) (Math.random() * 5); // Int random entre 0 et 4
        switch (rand) {
            case 1:
                return 'U';
            case 2:
                return 'D';
            case 3:
                return 'L';
            case 4:
                return 'R';
        }
        return 'I';
    }

    private String changeChar(String chaine, int idx, char monCharRempl) {
        char[] tab = chaine.toCharArray();
        tab[idx] = monCharRempl;
        return String.valueOf(tab);
    }

    public String toString(){
        return "evolue";
    }

}

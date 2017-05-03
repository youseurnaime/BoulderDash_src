package boulderTest.com.bd.ia;

import boulderTest.Map;
import boulderTest.NoRockfordException;
import boulderTest.Partie;
import org.jgrapht.Graph;
import org.jgrapht.graph.SimpleGraph;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by marin on 18/03/2017.
 */

/**
 * Bon, je pense que c'est pas claire pour toi alors je m'éxplique,
 * ce que je veux c'est pouvoir faire une simulation avec une chaine de caractere ca c'est facile
 * mais je veux faire une simulation a chaque tours et recuperer la map de fin
 * ainsi je peux oklm continuer l'algo génétique jusqu'a la mort de rockford ou la fin du temps.
 * A la fin la string sera envoyer a l'algo genetique qui fera simplement son taf.
 */
public class Direvol extends Rockford {
    private Evolue e;

    public Direvol(int nbMutations, Map laMap) throws NoRockfordException{
        String cheminInitial = new Partie(laMap.clone(),new Directif(),false).getChemin();
        e = new Evolue(nbMutations,laMap.clone(),true,cheminInitial);
    }
    /*public Direvol(int nbMutations, Map laMap) {
        this.nbMutations = nbMutations;
        this.population = new ArrayList<String>();
        lesDeplacements = new ConcurrentLinkedQueue<Character>();
        System.out.println("Calcul du joueur Directif évolué...");
        String strDeplacements = algoEvolue(laMap.clone());
        System.out.println("ok !\n\n");
        System.out.println(strDeplacements);

        for (int i = 0; i < strDeplacements.length(); i++) {
            lesDeplacements.add(strDeplacements.charAt(i));
        }

    }

    private String algoEvolue(Map laMap) {
        init(laMap);
        for (int i = 0; i < nbMutations; i++) {
            System.out.println("MUTATION " + i);
            mutation();
            testAfficherArrayList(laMap);
            selection(laMap);
        }
        return selectionFinale(laMap);
    }

    private Point cibleRandom(Map laMap, Graph leGraph) {//renvoie null si ce n'est pas trouvé
        if (!ciblesAccessibles(leGraph, laMap.trouverRockford(), laMap)) return null;
        if (!laMap.sortieOuverte()) {
            int nombreAleatoire = (int) (Math.random() * ((diamAccessible(laMap).size() - 1) + 1));
            return diamAccessible(laMap).get(nombreAleatoire);
        } else {
            //TODO Point destination= sortie
            return null;
        }
    }

    private Point directifRandom(Map laMap) {
        Point posRockford = laMap.trouverRockford();
        leGraph = mapToGraph(laMap);
        if (cibleRandom(laMap, leGraph) == null) {
            System.out.println("Rockford ne voit ni diamants ni sortie. Il est perdu et fait des déplacements aléatoires...\nL'IA directif n'est pas adaptée pour ce niveau.");
            return charToPos(posRockford, getDeplacementRandom(posRockford, laMap));
        }
        if (!ciblesAccessibles(leGraph, posRockford, laMap)) {
            return charToPos(posRockford, getDeplacementRandom(posRockford, laMap));
        }
        if (!init) {
            init = true;
            cibleActuelle = cibleRandom(laMap, leGraph);
        }

        if (posRockford.equals(cibleActuelle)) {
            cibleActuelle = cibleRandom(laMap, leGraph);
        }
        Point futurDeplacement = shortestPath(leGraph, laMap, posRockford, cibleActuelle);
        while (futurDeplacement == null) {
            cibleActuelle = laMap.getCibleRandom();
            futurDeplacement = shortestPath(leGraph, laMap, posRockford, cibleActuelle);
        }
        System.out.println("Rockford : " + posRockford.toString());
        System.out.println("Cible : " + cibleActuelle.toString());
        return (futurDeplacement);
    }

    /**
     * C'est le plus gros du taf, je suis pas encore sur de l'approche par laquelle commencer,
     * mais je pense qu'il faudrais rajouter des methodes a la classe simulation, j'ai pas touché pour pas casser
     * private String cheminDirRandom(Map laMap){
     * String chemin= " ";
     * Simulation sim = new Simulation(laMap);
     * int caveTime=laMap.getCaveTime()
     * String s = "i";
     * for (int i=0; i<caveTime-1;i++){
     * s=+directifRandom();
     * }
     * }


    private LinkedList<Point> diamAccessible(Map laMap) {//Tableau des point accessible, mais je pense que LikedList c'est plus efficace
        LinkedList<Point> diamAccess = new LinkedList<>();

        //TODO
        return diamAccess;
    }

    private void init(Map laMap) {
        boolean cheminValide = false;
        String chemin;
        do {
            try {
                chemin = cheminDirRandom(laMap.getCaveTime());//Il Faut faire un chemin directif vers Diamant Random
                new Simulation(laMap.clone(), chemin);
                population.add(chemin);
            } catch (Exception e) {

            }

        } while (population.size() < effectifMax);

    }
    */
    public Point getDeplacement(Point posRockford, Map laMap) {
        return e.getDeplacement(posRockford,laMap);
    }
}

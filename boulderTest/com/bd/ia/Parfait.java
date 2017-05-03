package boulderTest.com.bd.ia;

import boulderTest.Map;
import org.jgrapht.graph.SimpleGraph;

import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by ma32017.
 */
public class Parfait extends Rockford {
    private ArrayList<String> lesChemins;
    private ConcurrentLinkedQueue<Character> lesDeplacements;

    public Parfait(Map laMap){
        lesChemins = new ArrayList<>();
        init(laMap);
        int longueurMax = laMap.getCaveTime();
        String chemin = null;
        int i=1;
        do{
            chemin = testerLesChemins(laMap);
            System.out.println(lesChemins.toString());

            if(chemin==null)creerFilles(laMap);
            i++;
            System.out.println("Chemin de longueur "+i);

        }while (chemin== null&&longueurMax>i);
        if(chemin!=null){
            System.out.println("Rockford a trouvé la sortie avec le chemin : "+chemin);
            lesDeplacements = new ConcurrentLinkedQueue<Character>();
            for(int j = 0 ; j < chemin.length() ; j++){
                lesDeplacements.add(chemin.charAt(j));
            }
        }

    }

    private void init(Map laMap){
        Point posRockord = laMap.trouverRockford();
        if(deplacementPossible(new Point(posRockord.x+1,posRockord.y),posRockord,laMap)){
            lesChemins.add("D");
        }
        if(deplacementPossible(new Point(posRockord.x-1,posRockord.y),posRockord,laMap)){
            lesChemins.add("U");
        }
        if(deplacementPossible(new Point(posRockord.x,posRockord.y+1),posRockord,laMap)){
            lesChemins.add("R");
        }
        if(deplacementPossible(new Point(posRockord.x,posRockord.y-1),posRockord,laMap)){
            lesChemins.add("L");
        }
        /*
        if(deplacementPossible(new Point(posRockord.x,posRockord.y),posRockord,laMap)){
            lesChemins.add("I");
        }
        */

    }

    private String testerLesChemins(Map laMap){
        for(int i = 0 ; i < lesChemins.size() ; i++){
            try{
                Simulation s = new Simulation(laMap,lesChemins.get(i));

                if(s.getLaMap().getElement(new Point(s.getPosRockford().x+1,s.getPosRockford().y)) == 'X') return lesChemins.get(i)+'D';
                if(s.getLaMap().getElement(new Point(s.getPosRockford().x-1,s.getPosRockford().y)) == 'X') return lesChemins.get(i)+'U';
                if(s.getLaMap().getElement(new Point(s.getPosRockford().x,s.getPosRockford().y+1)) == 'X') return lesChemins.get(i)+'R';
                if(s.getLaMap().getElement(new Point(s.getPosRockford().x,s.getPosRockford().y-1)) == 'X') return lesChemins.get(i)+'L';
            }catch (Exception e){

            }
        }
        return null;
    }
    private void creerFilles(Map laMap){
        ArrayList <String> sortie= new ArrayList<>();
        for(int i = 0 ; i < lesChemins.size() ; i++){
            try {
                Simulation s = new Simulation(laMap, lesChemins.get(i));
                Point posRockord = s.getPosRockford();

                if(deplacementPossible(new Point((posRockord.x)+1,posRockord.y),posRockord,laMap)){
                    sortie.add(lesChemins.get(i)+"D");
                }
                if(deplacementPossible(new Point((posRockord.x)-1,posRockord.y),posRockord,laMap)){
                    sortie.add(lesChemins.get(i)+"U");
                }

                if(deplacementPossible(new Point(posRockord.x,(posRockord.y)-1),posRockord,laMap)){
                    sortie.add(lesChemins.get(i)+"L");
                }
                if(deplacementPossible(new Point(posRockord.x,(posRockord.y)+1),posRockord,laMap)){
                    sortie.add(lesChemins.get(i)+"R");
                }
                /*
                if(deplacementPossible(new Point(posRockord.x,posRockord.y),posRockord,laMap)){
                    sortie.add(lesChemins.get(i)+"I");
                }
                */
            } catch (Exception e){

            }

        }
        this.lesChemins=sortie;
    }

    public Point getDeplacement(Point posRockford, Map laMap) {
        char c = lesDeplacements.remove();
        return charToPos(posRockford,c);
    }


}

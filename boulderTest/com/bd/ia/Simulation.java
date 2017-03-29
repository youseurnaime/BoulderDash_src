package boulderTest.com.bd.ia;

import boulderTest.Map;
import boulderTest.Mobs;

import java.awt.*;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by clement on 28/03/17.
 */
public class Simulation{
    private Map laMap;
    private int score;
    private int time;
    private int diamonds;
    private ConcurrentLinkedQueue<Character> lesDeplacements;
    private boolean rockfordAlive;
    private Point posRockford;
    private boolean niveauFini;
    private String chemin;

    public Simulation (Map laMap,String chemin){
        this.laMap=laMap;
        this.chemin = "";
        this.lesDeplacements= new ConcurrentLinkedQueue<Character>();
        for (int i=0;i<chemin.length();i++){
            lesDeplacements.add(chemin.charAt(i));
        }
        this.score=this.diamonds=0;
        this.time=laMap.getCaveTime();
        this.rockfordAlive=true;
        this.posRockford=laMap.trouverRockford();
        this.niveauFini=false;
        while(!niveauFini) niveauFini=tour(' ');

    }

    public void jouerTour(char c){
        tour(c);
    }

    private Point getDeplacement() {
        char c = lesDeplacements.remove();
        chemin += c;
        return Rockford.charToPos(posRockford,c);
    }

    private boolean tour(char c){ // c : ' ' si on prend un deplacement dans la pile ou le deplacement a jouer
        laMap = Mobs.majMob(laMap);
        if(laMap.trouverRockford() == null){
            rockfordAlive = false;
            return false;
        }
        if (time == 0) {
            rockfordAlive = false;
            return false;
        }
        Point positionApresDeplacement;

        if(c == ' ') positionApresDeplacement = getDeplacement();
        else{
            chemin += c;
            positionApresDeplacement = Rockford.charToPos(posRockford,c);
        }

        laMap.removeElement(posRockford);
        switch (laMap.getElement(positionApresDeplacement)) {
            case 'X':
                return true;

            case 'r':
                Point posApresRoc = new Point((int) positionApresDeplacement.getX() * 2 - (int) posRockford.getX(), (int) positionApresDeplacement.getY() * 2 - (int) posRockford.getY());
                laMap.addElement(posApresRoc, 'r');
                break;
            case 'F':
            case 'Q':
            case 'q':
            case 'O':
            case 'o':
                rockfordAlive=false;
                return true;
            case 'c':
            case 'b':
            case 'B':
            case 'C':
                rockfordAlive=false;
                return true;
            case 'a':
                rockfordAlive=false;
                return true;
            case '.':
                break;
            case ' ':
                break;
            default: // Diamant
                score += laMap.getDiamondValue();
                diamonds++;
                laMap.testOuvrirSortie(diamonds);
                break;
        }

        posRockford = positionApresDeplacement;
        laMap.addElement(posRockford, 'R');
        time--;
        return false;
    }

    public boolean isRockfordAlive() {
        return rockfordAlive;
    }

    public int getTime() {

        return time;
    }

    public int getDiamonds() {

        return diamonds;
    }

    public int getScore() {

        return score;
    }

    public Map getLaMap(){
        return laMap;
    }

    public Point getPosRockford(){
        return posRockford;
    }

    public String getChemin(){
        return chemin;
    }

    public boolean equals(Simulation s){
        return(chemin == s.getChemin());
    }
}

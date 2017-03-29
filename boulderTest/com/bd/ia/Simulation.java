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
    private ConcurrentLinkedQueue<Character> lesDeplacements;
    private int score;
    private int time;
    private int diamonds;
    private boolean rockfordAlive;
    private Point posRockford;
    private boolean niveauFini;
    private String chemin;

    public Simulation (Map laMap, String lesDepl){
        this.laMap=laMap;
        this.chemin = lesDepl;
        this.score= 0;
        this.diamonds=0;
        this.time=laMap.getCaveTime();
        this.rockfordAlive=true;
        this.posRockford=laMap.trouverRockford();
        this.niveauFini=false;
        this.lesDeplacements= new ConcurrentLinkedQueue<Character>();
        for (int i=0;i<lesDepl.length();i++) {
            lesDeplacements.add(lesDepl.charAt(i));
        }
        while(!niveauFini && rockfordAlive) niveauFini = tour(' ');
    }

    public void jouerTour(char c){
        tour(c);
    }

    private boolean tour(char c){
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


        if(c == ' '){
            positionApresDeplacement = getDeplacement(posRockford,laMap);
        }else{
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

    public boolean isNiveauReussi(){
        return (niveauFini&&rockfordAlive);
    }

    public Point getDeplacement(Point posRockford, Map laMap) {
        char c = lesDeplacements.remove();
        return Rockford.charToPos(posRockford,c);
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

    public int evaluer(){
        if(!rockfordAlive) return -1;
        else return ((1+score)*time/laMap.getCaveTime());
    }
}

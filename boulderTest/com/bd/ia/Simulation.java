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

    public Simulation (Map laMap, String lesDepl) throws CheminNonValideException{

        this.laMap= laMap.clone();
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
        int numTour = 0;
        while(!niveauFini && rockfordAlive && !lesDeplacements.isEmpty()){
            try{
                numTour++;
                niveauFini = tour(' ');
            }catch (CheminNonValideException e){
                throw new CheminNonValideException(numTour);
            }
        }
    }


    private boolean tour(char c) throws CheminNonValideException{
        laMap = Mobs.majMob(laMap);
        if(laMap.trouverRockford() == null){
            rockfordAlive = false;
            return true;
        }
        if (time == 0) {
            rockfordAlive = false;
            return true;
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
            case 'W': throw new CheminNonValideException(-1);
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

    public boolean deplacementPossible(char d){
        Point pos = Rockford.charToPos(posRockford,d);
        if ((pos.getX() < 0 || pos.getX() > laMap.getHauteur() || pos.getY() < 0 || pos.getY() > laMap.getLargeur()))
            return false;
        char c = laMap.getElement(pos);
        if (c == ' ' || c == '.' || c == 'd' || c == 'X') return true;
        else if (c == 'w' || c == 'W') return false;
        else if (c == 'r') return Rockford.rocPoussable(pos, posRockford, laMap);
        else return true;
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
        return laMap.clone();
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

    public double evaluer(){
        int note = 0;
        for(int i = 1 ; i < chemin.length() ; i++){
            switch (chemin.charAt(i-1)){//diminution de la note si rockford revient sur ses pas
                case 'U':
                    if(chemin.charAt(i) == 'D') note -= 3;
                    break;
                case 'D':
                    if(chemin.charAt(i) == 'U') note -= 3;
                    break;
                case 'L':
                    if(chemin.charAt(i) == 'R') note -= 3;
                    break;
                case 'R':
                    if(chemin.charAt(i) == 'L') note -= 3;
                    break;
                case 'I':
                    note--;
                    break;
            }
        }
        note += score;
        if(!rockfordAlive) note -= 500;
        if(laMap.sortieOuverte()) note += 100;
        if(rockfordAlive && niveauFini) note += 1000;
        return note;
    }

    public String toString(){
        return laMap.ecranDeJeu();
    }
}

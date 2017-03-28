package boulderTest;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;

public class Partie { // Tout cee qui est relatif au deroulement de la partie aux affichages de jeu ect
    private Map laMap;
    private Point posRockford;
    private Rockford rockford;
    private int score;
    private int time;
    private int diamonds;
    private String name;
    private String historique;
    private boolean journal;

    public Partie(Map laMap, Rockford rockford, boolean journal) throws NoRockfordException { //constructeur pour une partie controlée par le joueur
        this.rockford = rockford;
        this.laMap = laMap;
        this.posRockford = laMap.trouverRockford();
        if (this.posRockford == null) throw new NoRockfordException();
        this.score = 0;
        this.time = laMap.getCaveTime();
        this.name = laMap.getNom();
        this.diamonds = 0;
        boolean rockfordAlive = true;
        historique = "";
        historique += "#" + this.name + "\n";
        this.journal=journal;
        while (rockfordAlive) rockfordAlive = tour();

        sauvegarderHistorique();
    }


    private void mortRockford() {
        effacerEcran();
        if(journal) {
            System.out.println("PERDU !");
        }
        afficherStat();
        historique += "\n#Rockford est MORT";
    }

    private void sauvegarderHistorique() {
        historique += " Score : "+score+" - Temps : "+time+" - Diamants : "+diamonds;
        try {

            FileOutputStream fos = new FileOutputStream(new File(name + ".dash"));
            byte byteHistorique[] = historique.getBytes();
            fos.write(byteHistorique);
            fos.close();
        } catch (Exception e) {
            System.out.println("Erreur de sauvegarde de l'historique de partie");
        }
    }

    private boolean tour(){
        laMap = Mobs.majMob(laMap);
        afficherMap();

        if(laMap.trouverRockford() == null){
            mortRockford();
            return false;
        }
        if (time == 0) {
            if(journal) {
                System.out.println("Temps écoulé !");
            }
            historique += "\n#Temps écoulé !";
            return false;
        }

        Point positionApresDeplacement = rockford.getDeplacement(posRockford,laMap);
        majHistorique(positionApresDeplacement);
        laMap.removeElement(posRockford);

        switch (laMap.getElement(positionApresDeplacement)) {

            case 'X':
                if(journal) {
                    System.out.println("Rockford a rejoint la sortie, Bravo !\n");
                    afficherStat();
                }
                historique += "\n#Rockford a rejoint la sortie";
                return false;
            case 'r':
                Point posApresRoc = new Point((int) positionApresDeplacement.getX() * 2 - (int) posRockford.getX(), (int) positionApresDeplacement.getY() * 2 - (int) posRockford.getY());
                laMap.addElement(posApresRoc, 'r');
                break;
            case 'F':
            case 'Q':
            case 'q':
            case 'O':
            case 'o':
                if(journal) {
                    System.out.println("Contact avec une luciole");
                }
                mortRockford();
                return false;
            case 'c':
            case 'b':
            case 'B':
            case 'C':
                if(journal) {
                    System.out.println("Contact avec une libellule");
                }
                mortRockford();
                return false;
            case 'a':
                if(journal) {
                    System.out.println("Contact avec l'amibe");
                }
                mortRockford();
                return false;
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
        effacerEcran();
        return true;
    }

    private void afficherMap() {
        if(journal) {
            System.out.println(name + "\nScore : " + score + "\tTemps : " + time + "\tDiamants : " + diamonds + "\n");
            if (laMap.sortieOuverte()) System.out.println("Sortie ouverte !");
            System.out.println(laMap.ecranDeJeu());
        }
    }

    private void afficherStat(){
        System.out.println("Score : "+score+"\tTemps : "+time+"\tDiamants : "+diamonds);
    }

    private void majHistorique(Point posApresDeplacement){
        if(posApresDeplacement.getX() < posRockford.getX()) historique += "U";
        else if(posApresDeplacement.getX() > posRockford.getX()) historique += "D";
        else if(posApresDeplacement.getY() < posRockford.getY()) historique += "L";
        else if(posApresDeplacement.getY() > posRockford.getY()) historique += "R";
        else historique += "I";
    }


    private void effacerEcran() {
        if(journal) {
            System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");//loooool
        }
    }
}

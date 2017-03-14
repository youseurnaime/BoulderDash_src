package boulderTest;

import java.io.*;
import java.util.Scanner;
import java.awt.Point;

public class Partie {
	private Map laMap;
	private Point posRockford;
	private int score;
	private int time;
	private int diamonds;
	private String name;
	private String historique;
	private char[] lesDeplacements; //Vaudra null si la partie est jouée par l'utilisateur

	public Partie(Map laMap) throws NoRockfordException{ //constructeur pour une partie controlée par le joueur
	    this.lesDeplacements = null;
		this.laMap = laMap;
		this.posRockford = laMap.trouverRockford();
		if(this.posRockford==null) throw new NoRockfordException();
		System.out.println(laMap.getNom()+"\n\nDéplacez Rockford avec les touches z q s d et appuyez sur entrée pour valider le déplacement.\nToute autre caractère immobilisera Rockford pour ce tour.\nA tout moment vous pouvez quitter en tapant 0.\nEntrez une valeur pour lancer la partie...");
		Scanner sc = new Scanner(System.in);
		String s = "";
		do{
			s = sc.next();
		}while(s.equals(""));
		this.score = 0;
		this.time = laMap.getCaveTime();
		this.name = laMap.getNom();
		this.diamonds = 0;
		boolean rockfordAlive = true;
		historique = "";
		historique += "#"+this.name+"\n";
		while(rockfordAlive) rockfordAlive = tour();

		sauvegarderHistorique();
	}

	public Partie(Map laMap, String lesDeplacements) throws NoRockfordException{ //constructeur pour une partie avec un chemin défini
        this.lesDeplacements = lesDeplacements.toCharArray();
        this.laMap = laMap;
        this.posRockford = laMap.trouverRockford();
        if(this.posRockford==null) throw new NoRockfordException();
        boolean attendre = true;
        Scanner sc = new Scanner(System.in);
        String s = "";
        System.out.println("Entrez \"o\" pour jouer immédiatement toute la partie.");
        System.out.println("Entrez une autre valeur et vous devrez taper une valeur à la fin de chaque tour pour passer au suivant.");
        do{
            s = sc.next();
        }while(s.equals(""));
        if(s.equals("o")) attendre = false;
        this.score = 0;
        this.time = laMap.getCaveTime();
        this.name = laMap.getNom();
        this.diamonds = 0;
        boolean rockfordAlive = true;
        int numTour = 0;
        while(rockfordAlive){
            rockfordAlive = tour();
            if(attendre) s = sc.nextLine();
        }

    }

	private void mortRockford(){
		effacerEcran();
		System.out.println("PERDU!");
		historique +="\n#Rockford est MORT";
	}

	private void sauvegarderHistorique(){
	    try{

            FileOutputStream fos = new FileOutputStream(new File(name+".dash"));
            byte byteHistorique[] = historique.getBytes();
            fos.write(byteHistorique);
            fos.close();
        }catch(Exception e){
	        System.out.println("Erreur de sauvegarde");
        }
    }

    private boolean tour() { //Renvoie true si Rockford est toujours en vie à l'issue de ce tour
        effacerEcran();
        Point positionApresDeplacement;
        char choix = '.';
        score += laMap.getBonusDiamant()*laMap.getDiamondValue();
        diamonds += laMap.getBonusDiamant();
        afficherMap();
        if(lesDeplacements != null) choix = getDeplacement();
        do {
            if(lesDeplacements == null) choix = choixDeplacement();

            switch (choix) {
                case '0':
                    return false;
                case 'z':
                    positionApresDeplacement = new Point((int)posRockford.getX() - 1,(int) posRockford.getY());
                    historique+="U";
                    break;
                case 's':
                    positionApresDeplacement = new Point((int)posRockford.getX() + 1, (int)posRockford.getY());
                    historique+="D";
                    break;
                case 'q':
                    positionApresDeplacement = new Point((int)posRockford.getX(), (int)posRockford.getY() - 1);
                    historique+="L";
                    break;
                case 'd':
                    positionApresDeplacement = new Point((int)posRockford.getX(), (int)posRockford.getY() + 1);
                    historique+="R";
                    break;
                default:
                    historique+="I";
                    positionApresDeplacement = posRockford;
                    break;
            }
            if (!deplacementPossible(positionApresDeplacement)) System.out.println("Déplacement impossible !");
        } while (!deplacementPossible(positionApresDeplacement));

        laMap.removeElement(posRockford);
        switch (laMap.getElement(positionApresDeplacement)) {

            case 'X':
                System.out.println("Bravo !");
                historique +="\n#Rockford a rejoint la sortie";
                return false;
            case 'r':
                Point posApresRoc = new Point((int) positionApresDeplacement.getX() * 2 - (int) posRockford.getX(),(int) positionApresDeplacement.getY() * 2 - (int) posRockford.getY());
                laMap.addElement(posApresRoc, 'r');
                break;
            case 'F':
            case 'Q':
            case 'q':
            case 'O':
            case 'o':
                System.out.println("Contact avec luciole :(");
                mortRockford();
                return false;
            case 'c':
            case 'b':
            case 'B':
            case 'C':
                System.out.println("Contact avec libellule :(");
                mortRockford();
                return false;
            case 'a':
                System.out.println("Contact avec l'amibe :(");
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
        if(time==0){
        	System.out.println("Temps écoulé !");
        	historique+="\nTemps écoulé !";
        	return false;
        }
        if(!laMap.majMap()){
            mortRockford();
            return false;
        } else {
            return true;
        }
    }

	private boolean deplacementPossible(Point pos){
		if((pos.getX() < 0 || pos.getX() > laMap.getHauteur() || pos.getY() < 0 || pos.getY() > laMap.getLargeur())) return false;
		char c = laMap.getElement(pos);
		if(c == ' ' || c == '.' || c == 'd' || c == 'X') return true;
		else if(c == 'w' || c == 'W') return false;
		else if(c == 'r') return rocPoussable(pos);
		else return true;
	}
	
	private boolean rocPoussable(Point pos){//pos du roc en parametre
		Point posApresRoc = new Point((int) pos.getX()*2- (int) posRockford.getX(),(int) pos.getY()*2- (int) posRockford.getY());//formule pour avoir la prochaine case dans la continuité de la direction choisie
		return (laMap.getElement(posApresRoc) == ' ');
	}
	
	private char choixDeplacement(){
		System.out.println("Entrez une direction...   :");
		Scanner sc = new Scanner(System.in);
		String s = sc.nextLine();
		if(!s.equals("")) return(s.charAt(0));
		else return choixDeplacement();
	}

	private void afficherMap(){
        System.out.println(name+"\ntime: "+time+"\tscore: "+score+"\tdiamonds: "+diamonds+"\n");
        if(laMap.sortieOuverte()) System.out.println("Sortie ouverte !");
        System.out.println(laMap.ecranDeJeu());
    }

	private char getDeplacement(){//lit les déplacements dans la chaine des fichiers .dash
	    int i = 0;
	    while(i < lesDeplacements.length && lesDeplacements[i] == 0) i++;//0 = traité, on cherche le 1er char non traité dans la chaine
        if(i == lesDeplacements.length) return '0';//quand il n'y a plus de caractères a lire on renvoie 0 pour le signaler
        System.out.println(lesDeplacements[i]);
        switch(lesDeplacements[i]){
            case 'U':
                return 'z';
            case 'D':
                return 's';
            case 'L':
                return 'q';
            case 'R':
                return 'd';
            default:
                return 'i';//Toute autre valeur = immobile
        }
    }
	
	private void effacerEcran(){
		System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");//loooool
	}
}

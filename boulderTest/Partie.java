package boulderTest;

import java.util.Scanner;

public class Partie {
	private Map laMap;
	private Position posRockford;
	private int score;
	private int time;
	private int diamonds;
	private String name;
	
	public Partie(Map laMap) throws NoRockfordException{
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
		while(rockfordAlive) rockfordAlive = tour();
	}

    private boolean tour() { //Renvoie true si Rockford est toujours en vie à l'issue de ce tour
        effacerEcran();
        Position positionApresDeplacement;
        char choix;
        //score += laMap.getBonusDiamant()*laMap.getDiamondValue();
       // diamonds += laMap.getBonusDiamant();
        do {
            choix = choixDeplacement();

            switch (choix) {
                case '0':
                    return false;
                case 'z':
                    positionApresDeplacement = new Position(posRockford.getX() - 1, posRockford.getY());
                    break;
                case 's':
                    positionApresDeplacement = new Position(posRockford.getX() + 1, posRockford.getY());
                    break;
                case 'q':
                    positionApresDeplacement = new Position(posRockford.getX(), posRockford.getY() - 1);
                    break;
                case 'd':
                    positionApresDeplacement = new Position(posRockford.getX(), posRockford.getY() + 1);
                    break;
                default:
                    positionApresDeplacement = posRockford;
                    break;
            }
            if (!deplacementPossible(positionApresDeplacement)) System.out.println("Déplacement impossible !");
        } while (!deplacementPossible(positionApresDeplacement));

        laMap.removeElement(posRockford);
        switch (laMap.getElement(positionApresDeplacement)) {

            case 'X':
                System.out.println("Bravo !");
                return false;
            case 'r':
                Position posApresRoc = new Position(positionApresDeplacement.getX() * 2 - posRockford.getX(), positionApresDeplacement.getY() * 2 - posRockford.getY());
                laMap.addElement(posApresRoc, 'r');
                break;
            case 'F':
                System.out.println("Contact avec luciole :(");
                return false;
            case 'C':
                System.out.println("Contact avec libellule :(");
                return false;
            case 'a':
                System.out.println("Contact avec l'amibe :(");
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
        	return false;
        }
        return laMap.majMap();
    }
	
	private boolean deplacementPossible(Position pos){
		if(pos.getX() < 0 || pos.getX() > laMap.getHauteur() || pos.getY() < 0 || pos.getY() > laMap.getLargeur()) return false;
		char c = laMap.getElement(pos);
		if(c == ' ' || c == '.' || c == 'd' || c == 'X') return true;
		else if(c == 'w' || c == 'W') return false;
		else if(c == 'r') return rocPoussable(pos);
		else return true;
	}
	
	private boolean rocPoussable(Position pos){//pos du roc en parametre
		Position posApresRoc = new Position(pos.getX()*2-posRockford.getX(),pos.getY()*2-posRockford.getY());//formule pour avoir la prochaine case dans la continuité de la direction choisie
		return (laMap.getElement(posApresRoc) == ' ');
	}
	
	private char choixDeplacement(){
		System.out.println(name+"\ntime: "+time+"\tscore: "+score+"\tdiamonds: "+diamonds+"\n");
		if(laMap.sortieOuverte()) System.out.println("Sortie ouverte !");
		System.out.println(laMap.ecranDeJeu());
		System.out.println("Entrez une direction...   :");
		Scanner sc = new Scanner(System.in);
		String s = sc.nextLine();
		if(!s.equals("")) return(s.charAt(0));
		else return choixDeplacement();
	}
	
	private void effacerEcran(){
		System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");//loooool
	}
}

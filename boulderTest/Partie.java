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
		this.laMap.chargerNiveau();
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
	
	private boolean tour(){ //Renvoie true si Rockford est toujours en vie à l'issue de ce tour
		effacerEcran();
		char choix = choixDeplacement();
		if(choix == '0') return false;
		//TODO
		return true;
	}
	
	private char choixDeplacement(){
		System.out.println(name+"\ntime: "+time+"\tscore: "+score+"\tdiamonds: "+diamonds+"\n");
		System.out.println(laMap.ecranDeJeu());
		System.out.println("Entrez une direction...   :");
		Scanner sc = new Scanner(System.in);
		String s = sc.nextLine();
		return(s.charAt(0));
	}
	
	private void effacerEcran(){
		System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");//loooool
	}
}

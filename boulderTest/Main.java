package boulderTest;

import java.io.*;

public class Main {

	public static void main(String[] args) {
		String fichierNiveau="src/assets/FireFox50.bd.bdcff";
		boolean fichierValide=true;
		int choixNiveau=-1;
		if(args.length == 0){
			// No arguments
		} else {
			switch (args.length){
				case 1:
					switch (args[0]){
						case "-name":
							System.out.println("BEGOUEN DEMEAUX Marine\nFABRE Marin\nSENAT Clement\nDE SA Thomas");
							System.exit(0);
							break;
						case "-h" :
							// arg -h
							break;
						default :
							System.out.println(usage());
							System.exit(2);
					}
					break;
				case 2:
					switch (args[0]){
						case "-lis":
							fichierNiveau=args[1];
							break;
						case "-joue":
							fichierNiveau=args[1];
							break;
						}
					break;
				case 4:
					if(args[0]=="-joue"){
						fichierNiveau=args[1];
						choixNiveau=Integer.parseInt(args[3]);
					} else {
						System.out.println(usage());
						System.exit(2);
						break;
					}
				case 5:
					// -cal
					break;
				case 6:
					// -rejoue
					break;
				case 7:
					// -simul
					break;
				default:
					System.out.println(usage());
					System.exit(2);
			}
		}
		System.out.println("Bonjour");
		
		try{
			EnsembleNiveau lesNiveaux = new EnsembleNiveau(fichierNiveau);
			System.out.println(lesNiveaux.toString());
			new Partie(lesNiveaux.choisirNiveau(choixNiveau));

		}catch(Exception e){
			System.out.println(e.getMessage());
		}

	}
	public static String usage(){
		return "Usage : $ java -jar boulder_dash.jar [-name][-h][-lis fichier.bdcff][-joue fichier.bdcff -niveau N]\n" +
				"[-cal strategie fichier.bdcff -niveau N][-rejoue fichier.dash fichier.bdcff -niveau N]\n" +
				"[-simul N strategie strategie fichier.bdcff -niveau N]\n" +
				"Voir PDF pour plus dinfos sur les commandes";
	}

}

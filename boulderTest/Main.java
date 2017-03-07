package boulderTest;

import java.io.*;
import java.util.StringTokenizer;

public class Main {

	public static void main(String[] args) {
		String fichierNiveau="assets/FireFox50.bd.bdcff";
		String fichierChemin="";
		int choixNiveau=-1;
		if(args.length != 0){
			switch (args.length){
				case 1:
					switch (args[0]){
						case "-name":
							System.out.println("BEGOUEN DEMEAUX Marine\nFABRE Marin\nSENAT Clement\nDE SA Thomas");
							System.exit(0);
							break;
						case "-h" :
							System.out.println(usage());
							break;
						default :

							System.exit(2);
					}
					break;
				case 2:
					switch (args[0]){
						case "-lis":
							fichierNiveau=args[1];
							try{
								EnsembleNiveau lesNiveaux = new EnsembleNiveau(fichierNiveau);
								System.out.println(lesNiveaux.toString());
							}catch(Exception e){
								System.out.println("Erreur de lecture du fichier");
							}
							System.exit(0);
							break;
						case "-joue":
							fichierNiveau = args[1];
							break;
						default:
							System.out.println(usage());
							System.exit(2);
						}
					break;
				case 3:
					switch(args[0]){
						case "-joue":
							fichierNiveau=args[1];
							choixNiveau=Integer.parseInt(args[2]);
							break;
						case "-rejoue":
							fichierNiveau=args[2];
							try{
								fichierChemin = dashToString(args[1]);
							}catch(Exception e){
								System.out.println("Erreur de lecture du fichier .dash");
								System.exit(2);
							}
							break;
						default:
							System.out.println(usage());
							System.exit(2);
							break;
					}
					break;
				case 4:
					switch(args[0]){
						case "-rejoue":
							fichierNiveau=args[2];
							try{
								fichierChemin = dashToString(args[1]);
							}catch(Exception e){
								System.out.println("Erreur de lecture du fichier .dash");
								System.exit(2);
							}
							choixNiveau=Integer.parseInt(args[3]);
							break;
						default:
							System.out.println(usage());
							System.exit(2);
							break;
					}
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

	private static String dashToString(String fichier) throws FileFormatException{
		StringTokenizer str = new StringTokenizer(fichier, ".");//On vérifie qu'on a un .dash
		String extension = "";
		while (str.hasMoreElements()) {
			extension = str.nextToken();
		}
		if (!extension.equals("dash")) throw new FileFormatException();

		File fichierNiveau = new File(fichier);
		String ligne = "";
		String chemin = "";
		try {
			BufferedReader br = new BufferedReader(new FileReader(fichier));
			while ((ligne = br.readLine()) != null) {

				if (!ligne.contains("#")) {
					chemin += ligne;
				}

			}
		}catch(Exception e){
			System.out.println("Erreur de lecture du fichier .dash");
		}

		return chemin;
	}

}

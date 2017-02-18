package boulderTest;

public class Main {

	public static void main(String[] args) {
		String fichierNiveau="src/assets/FireFox50.bd.bdcff";
		int choixNiveau=-1;
		if(args==null){
			// No arguments
		} else {
			switch (args.length){
				case 1:
					switch (args[0]){
					case "-name":
						// arg -name
						break;
					case "-h" :
						// arg -h
						break;
					}
					break;
				case 2:
					switch (args[0]){
						case "-lis":
							fichierNiveau=args[1];
						}
					break;
				case 4:
					if(args[0]=="-joue"){
						fichierNiveau=args[1];
						choixNiveau=Integer.parseInt(args[3]);
					} else {
						System.out.println(usage());
					}
					default:
						System.out.println(usage());
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

package boulderTest;

public class Main {

	public static void main(String[] args) {
		String fichierNiveau="src/assets/FireFox50.bd.bdcff";
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
			}
		}
		System.out.println("Bonjour");
		
		try{
			EnsembleNiveau lesNiveaux = new EnsembleNiveau(fichierNiveau);
			System.out.println(lesNiveaux.toString());
			new Partie(lesNiveaux.choisirNiveau());

		}catch(Exception e){
			System.out.println(e.getMessage());
		}

	}

}

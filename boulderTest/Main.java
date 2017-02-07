package boulderTest;

public class Main {

	public static void main(String[] args) {
		System.out.println("Bonjour");
		try{
			EnsembleNiveau lesNiveaux = new EnsembleNiveau("niveaux.bdcff");
			System.out.println(lesNiveaux.toString());
			lesNiveaux.choisirNiveau();
		}catch(Exception e){
			System.out.println("Mauvais format");
		}

	}

}

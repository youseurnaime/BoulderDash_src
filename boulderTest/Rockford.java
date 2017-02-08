package boulderTest;
import java.util.Scanner; 


public class Rockford extends Elements{
	public Rockford(){
		super('R');
	}
	public char getDirectionChoisie(){
		char c=' ';
		Scanner sc = new Scanner(System.in);
		do {
			if (c!=' ') System.out.println("Désolé veuillez choisir une dirréction valable");//c'est pas la plus propre des facons de faire ca.
			String str = sc.nextLine();
			c = str.charAt(0);//sert a recuperer le premier caractere du string
		}while (c!='z'||c!='q'||c!='s'||c!='d');
		return c;
	}
	public void deplacement(){
		boolean deplace=false;
		do{
			switch (getDirectionChoisie()){
				case 'z' :
					//Il faut un getPositionRockford et utiliser coordoneesToTypeElements
					break;
				case 'q' :
					
					break;
				case 's' :
					
					break;
				case 'd' :
					
					break;
			}
		}while(!deplace);
			
	}
	public void tour(){
		
	}
}
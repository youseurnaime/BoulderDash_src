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
	
	public boolean reaction(char c){//TODO Ne gere pas la mort du perso Je pense qu'il faudrait laisser le perso 
		//avancer sur un mob puis au debut du tours map et a la fin on regarde si on le tue 
		switch (c){
		
		case '.' :
			return true;
		case 'r' :
			//voire si la direction choisie est un coté et si il y a un caillou qui bloc le mouvement
			break;

		case 'd' :
			//ajouter les points
			return true;
		
		case 'X' :
			//voire si sortie est ouverte
			
		default:
			return false;
	}
		return false;
	}
	
	public void deplacement(){
		boolean deplacementlibre=false;
		char c = ' ';
		int y = Map.typeElementstoY('R');
		int x = Map.typeElementstoX('R');
		
		do{
			switch (getDirectionChoisie()){
			
				case 'z' :
					c=(Map.coordoneesToTypeElements(x, y-1));
					deplacementlibre =reaction (c);
					break;
				case 'q' :
					
					c=(Map.coordoneesToTypeElements(x-1, y));
					deplacementlibre =reaction (c);
					break;
				case 's' :
					
					c=(Map.coordoneesToTypeElements(x, y+1));
					deplacementlibre =reaction (c);
					break;
				case 'd' :
					
					c=(Map.coordoneesToTypeElements(x+1, y));
					deplacementlibre =reaction (c);
					
					break;
			}
		}while(!deplacementlibre);
			
	}
	public void tour(){
		
	}
}
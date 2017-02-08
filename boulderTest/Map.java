package boulderTest;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

public class Map {
	private String name;
	private int[] caveTime;
	private int diamondsRequired;
	private int diamondValue; //= valeur du diamant + le bonus
	private int amoebaTime;
	private int magicWallTime;
	private char[][] laMap;
	private static Hashtable<Position,Elements> lesElements;
	private int hauteurMap;
	private int largeurMap;
	
	public Map(String name, ArrayList<Integer> caveTime, int diamondsRequired, ArrayList<Integer> diamondValue, int amoebaTime, int magicWallTime, ArrayList<String> laMap){
		this.lesElements = new Hashtable<Position,Elements>();
		this.name = name;
		this.caveTime = new int[caveTime.size()];
		for(int i = 0 ; i < caveTime.size() ; i++) this.caveTime[i] = caveTime.get(i); //on transforme l'arrayList en tableau
		this.diamondsRequired = diamondsRequired;
		this.diamondValue = 0;
		for(int i = 0 ; i < diamondValue.size() ; i++) this.diamondValue += diamondValue.get(i);
		this.amoebaTime = amoebaTime;
		this.magicWallTime = magicWallTime;
		hauteurMap = laMap.size();
		largeurMap = laMap.get(0).length();
		this.laMap = new char[hauteurMap][largeurMap];
		//création du tableau aux bonnes dimensions et double boucle pour le remplir
		for(int j = 0 ; j < hauteurMap ; j++){
			for(int i = 0 ; i < largeurMap ; i++){
				this.laMap[j][i] = formatageCaractere(laMap.get(j).charAt(i));
			}
		}
	}
	
	public char formatageCaractere(char a){//Afin qu'il n'y est qu'un caractere par element
		if (a=='F'||a=='Q'||a=='o'||a=='O'||a=='Q') return 'F';//Dans tout ces cas c'est une luciole alors il y a qu'un caractere
		else if(a=='B'||a=='b'||a=='c'||a=='C') return 'C';
		else return a;
	}
	
	public String getNom(){
		return this.name;
	}
	
	private void charToElements(){ //Crée un hashtable a partir d'un double tableau de char
		char c='a';
	
		for(int i = 0 ; i < hauteurMap ; i++){
			for(int j = 0 ; j < largeurMap ; j++){
				c=laMap[i][j];
				switch (c){
					case 'R' :
						lesElements.put(new Position(i,j), new Rockford());
						break;
					
					case '.' :
						lesElements.put(new Position (i,j), new Poussiere());
						break;
			
					case 'r' :
						lesElements.put(new Position (i,j), new Roc());
						break;
			
					case 'd' :
						lesElements.put(new Position (i,j), new Diamant());
						break;
			
					case 'w' :
						lesElements.put(new Position (i,j), new Mur());
						break;
						
					case 'W' :
						lesElements.put(new Position (i,j), new TitanMur());
						break;
					
					case 'X' :
						lesElements.put(new Position (i,j), new Sortie());
						break;
						
					default:
						
						break;
				}
			}
		}
		
	}
	
	private void elementsToChar(){ //crée la map visuelle à partir de l'hashtable d'elements
		this.laMap = new char[hauteurMap][largeurMap];
		Enumeration<Position> pos = lesElements.keys();
		Position currentPos;
		while(pos.hasMoreElements()){
			currentPos = pos.nextElement();
			laMap[currentPos.getX()][currentPos.getY()] = lesElements.get(currentPos).getRepresentation();
		}
		
	}

	public static int typeElementstoX(char c){//On ne peut l'uttiliser que pour Rockford la sortie ou le PD
		//TODO 
		Hashtable<Position,Elements> lesElement;
		Enumeration<Position> pos = lesElements.keys();
		Position currentPos;
		while(pos.hasMoreElements()){
			currentPos = pos.nextElement();
			if(lesElements.get(currentPos).getRepresentation()==c)return currentPos.getX();
		}
		return -1;//Dans le cas ou rien n'a été trouvé
	}
	
	public static int typeElementstoY(char c){//On ne peut l'uttiliser que pour Rockford la sortie ou le PD
		Hashtable<Position,Elements> lesElement;
		Enumeration<Position> pos = lesElements.keys();
		Position currentPos;
		while(pos.hasMoreElements()){
			currentPos = pos.nextElement();
			if(lesElements.get(currentPos).getRepresentation()==c)return currentPos.getY();
		}
		return -1;
	}
	
	public static char coordoneesToTypeElements(int x, int y){//renvoie un element depuis les coordonnées
		Hashtable<Position,Elements> lesElement;
		Enumeration<Position> pos = lesElements.keys();
		Position currentPos;
		while(pos.hasMoreElements()){
			currentPos = pos.nextElement();
			if(currentPos.getX()== x &&currentPos.getY()== y )return lesElements.get(currentPos).getRepresentation();
			
		}
		return '0';//La représentation n'existe pas 
	}
	
	public String toString(){
		String s = "Name : "+name+" | CaveTime : ";
		for(int i = 0 ; i < caveTime.length ; i++) s += caveTime[i]+" ";
		s += "| Diamonds required : "+diamondsRequired+" | Diamond value : "+diamondValue+" | Amoeba Time : "+amoebaTime+" | Magic Wall Time : "+magicWallTime+"\n\n";
		for(int i = 0 ; i < laMap.length ; i++){
			for(int j = 0 ; j < laMap[i].length ; j++){
				s += laMap[i][j];
			}
			s += "\n";
		}
		return s;
	}
	
}

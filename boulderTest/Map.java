package boulderTest;

import java.util.ArrayList;
import java.util.Hashtable;

public class Map {
	private String name;
	private int[] caveTime;
	private int diamondsRequired;
	private int diamondValue; //= valeur du diamant + le bonus
	private int amoebaTime;
	private int magicWallTime;
	private char[][] laMap;
	private Hashtable<Position,Elements> lesElements;
	
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
		this.laMap = new char[laMap.size()][laMap.get(0).length()];
		//création du tableau aux bonnes dimensions et double boucle pour le remplir
		for(int j = 0 ; j < laMap.size() ; j++){
			for(int i = 0 ; i < laMap.get(j).length() ; i++){
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
	protected void iniAshTab (char[][] tab ){
		char c='a';
	
		for(int i = 0 ; i < tab.length ; i++){
			for(int j = 0 ; j < tab[i].length ; j++){
				c=tab[i][j];
				switch (c){
					case ' ' :
						
						break;
						
					case '.' :
						lesElements.put(new Position (i,j), new Poussiere());
						break;
			
					case 'r' :
						lesElements.put(new Position (i,j), new ());
						break;
			
					case 'd' :
						lesElements.put(new Position (i,j), new ());
						break;
			
					case 'w' :
						lesElements.put(new Position (i,j), new ());
						break;
						
					case 'W' :
						lesElements.put(new Position (i,j), new ());
						break;
					
					case 'M' :
						lesElements.put(new Position (i,j), new ());
						break;
						
					default:
						
						break;
		}
		
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

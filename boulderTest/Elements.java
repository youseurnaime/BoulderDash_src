package boulderTest;

public abstract class Elements {
	
	private char representation;
	
	public Elements(char c){
		this.representation = c;
	}
	
	public char getRepresentation(){
		return representation;
	}
	
	public void changerRepresentation(char c){
		this.representation = c;
	}
}

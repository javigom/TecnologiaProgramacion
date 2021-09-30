package tp.p1.util;

import tp.p1.game.Direction;

public class UCMShip {

	//Atributos
	private int fila;
	private int columna;
	private int strength;
	private String draw;
	public static int initialStrenght = 3;
	public static int damage = 1; //No se usa, solo para mostrar la info
	
	//Constructor
	public UCMShip(int f, int c){
		setPosition(f, c);
		this.strength = initialStrenght;
		this.draw = "^__^";
	}
	
	//Métodos
	
	//Establece coordenadas de la nave
	private void setPosition(int f, int c){
		this.fila = f;
		this.columna = c;
	}
	
	//Devuelve la vida de la nave
	public int getStrength() {
		return strength;
	}
	
	//Devuelve la fila
	public int getFila() {
		return fila;
	}
	
	//Devuelve la columna
	public int getColumna() {
		return columna;
	}
	
	//Devuelve el string de la nave
	public String toString() {
		return draw;
	}
	
	//Avanza numCasillas en una direccion o en otra
	public void avanzar(int numCasillas, Direction dir) {
		
		if(dir == Direction.RIGHT) {
			setPosition(this.fila, this.columna+numCasillas);
		}
		
		else {
			setPosition(this.fila, this.columna-numCasillas);
		}
	}
	
	//Le resta damage a la resistencia
	public void attack(int damage) {
		this.strength -= damage;
	}
	
	//Cambia el string cuando es eliminada
	public void endString() {
		this.draw = "!xx!";
	}
	
	public String getInfo() {
		return this.draw + " - Harm: 1 - Points: " + initialStrenght;
	}
}

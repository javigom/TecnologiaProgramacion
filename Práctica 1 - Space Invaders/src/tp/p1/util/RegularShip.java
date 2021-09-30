package tp.p1.util;

import tp.p1.game.Direction;

public class RegularShip {

	//Atributos
	private int fila;
	private int columna;
	private int strength;
	private static final int POINTS = 5;
	public static int INITIALSTRENGHT = 2;
	
	//Constructores
	public RegularShip(int f, int c){
		setPosition(f, c);
		this.strength = INITIALSTRENGHT;
	}
	
	//Métodos
	
	//Establece coordenadas de la nave
	private void setPosition(int f, int c){
		this.fila = f;
		this.columna = c;
	}
	
	//Devuelve la resistencia
	public int getStrength() {
		return this.strength;
	}
	
	//Devuelve la fila
	public int getFila() {
		return this.fila;
	}
	
	//Devuelve la columna
	public int getColumna() {
		return this.columna;
	}
	
	//Devuelve los puntos
	public int getPoints() {
		return POINTS;
	}
	
	//Le resta damage a la resistencia
	public void attack(int damage) {
		this.strength -= damage;
	}
	
	//Devuelve el string de la nave
	public String toString() {
		return "R[" + this.strength + "]";
	}
	
	//Avanza la nave una posicion en función de la dirección
	public void move(Direction dir) {
		Direction d;
		d = Direction.RIGHT;
		
		if(dir == d) {
			setPosition(this.fila, this.columna+1);
		}
		
		else {
			setPosition(this.fila, this.columna-1);
		}
	}
	
	//Avanza la nave una posición hacia abajo
	public void moveDown() {
		setPosition(this.fila+1, this.columna);
	}
}

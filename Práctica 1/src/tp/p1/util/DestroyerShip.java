package tp.p1.util;

import java.util.Random;

import tp.p1.game.Direction;
import tp.p1.game.Game;
import tp.p1.game.Level;

public class DestroyerShip {

	//Atributos
	private int fila;
	private int columna;
	private int strength;
	private static final int POINTS = 10;
	private EnemyBomb bomb;
	private Game g;
	
	//Constructores
	public DestroyerShip(int f, int c, Game game){
		setPosition(f, c);
		this.strength = 1;
		this.bomb = null;
		this.g = game;
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
		return "D[" + this.strength + "]";
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
	
	//Si no existe ninguna bomba, calcula la posibilidad de lanzarla
	public void throwBomb(Random rnd, Level lvl) {
		
		if(this.bomb == null) {
			if(rnd.nextDouble() < lvl.getFrecuency()) {
				this.bomb = new EnemyBomb(this.fila, this.columna);
				g.insertBombList(this.bomb);
			}
		}
	}
	
	//Elimina la bomba
	public boolean deleteBomb(int f, int c) {
		boolean delete = false;
		if(this.bomb != null && (this.bomb.getFila() == f) && (this.bomb.getColumna() == c)) {
			delete = true;
			this.bomb = null;
		}
		return delete;
	}
	
	//Si la bomba ha llegado a la fila f, la elimina
	public void updateBomb(int f) {
		if(this.bomb != null && this.bomb.getFila() == f) {
			g.deleteBomb(this.bomb.getFila(), this.bomb.getColumna());
		}
	}
}

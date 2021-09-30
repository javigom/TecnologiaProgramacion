package tp.p2.util;


import tp.p2.game.Direction;
import tp.p2.game.Level;

public class RegularShipList {
	
	//Atributos
	private RegularShip regularList[];
	private int regularCounter;
	private int maxShip;
	
	
	//Constructor
	public RegularShipList(Level l) {
		
		this.maxShip = l.getNumRegular();		
		this.regularList = new RegularShip[maxShip];
		this.regularCounter = 0;
	}
	
	//Métodos
	
	//Devuelve el numero de naves
	public int getNumShip(){
		return this.regularCounter;
	}
	
	//Devuelve el tamaño del array
	public int getMaxShip() {
		return this.maxShip;
	}
	
	//Añade una nave a la lista
	public void addRegularShip(RegularShip ship) {
		this.regularList[this.regularCounter] = ship;
		this.regularCounter++;	
	}
	
	//Elimina las naves que tienen 0 de vida de la lista y devuelve el total de los puntos
	public int removeRegularShip(){
		
		int points = 0, i = 0;
		while ( i < this.regularCounter) {
			
			if(this.regularList[i].getStrength() <= 0) {
				points = points + this.regularList[i].getPoints();
				this.regularList[i] = this.regularList[this.regularCounter - 1];
				this.regularList[this.regularCounter - 1] = null;
				this.regularCounter--;
			}
			
			else {
				i++;
			}
		}
		
		return points;
	}
	
	//Compruebo si existe una nave en las coordenadas f, c
	public boolean isFree(int f, int c) {
		boolean free = true;
		int i = 0;
		
		while(free && i < this.regularCounter) {
			if((this.regularList[i].getFila() == f) && (this.regularList[i].getColumna() == c)) {
				free = false;
			}
			i++;
		}
		
		return free;
	}
	
	//Devuelve string la nave de una posicion f, c (Sólo si se cumple!isFree)
	public String drawShip(int f, int c) {
		String draw = null;
		int i = 0;
		while(i < this.regularCounter) {
			if((this.regularList[i].getFila() == f) && (this.regularList[i].getColumna() == c)) {
				draw = this.regularList[i].toString();
			}
			i++;
		}
		return draw;
	}
	
	//Mueve todas las naves una posicion a la derecha o izquierda
	public void move(Direction dir) {
		for(int i = 0; i < this.regularCounter; i++) {
			this.regularList[i].move(dir);
		}
	}
	
	//Mueve todas las naves una posicion hacia abajo
	public void moveDown() {
		for(int i = 0; i<this.regularCounter;i++) {
			this.regularList[i].moveDown();
		}
	}
	
	//Comprueba si alguna nave se encuentra en la columna n
	public boolean limitCol(int n)
	{
		boolean limit = false;
		for(int i = 0; i<this.regularCounter;i++) {
			if(this.regularList[i].getColumna() == n) {
				limit = true;
			}
		}
		return limit;
	}
	
	//Le resta un punto a la nave con coordenadas f, c
	public void attackShip(int f, int c, int damage) {
		boolean encontrado = false;
		int i = 0;
	
		while(!encontrado && i < regularCounter) {
			if((this.regularList[i].getFila() == f) && (this.regularList[i].getColumna() == c)) {
				this.regularList[i].attack(damage);
				encontrado = true;
			}
			
			i++;
		}
	}
	
	//Devuelve true si alguna nave se encuentra en la fila n
	public boolean limitFila(int n)
	{
		boolean limit = false;
		for(int i = 0; i < this.regularCounter;i++) {
			if(this.regularList[i].getFila() == n) {
				limit = true;
			}
		}
		return limit;
	}
	
	//Le resta damage a la resistencia de todas las naves
	public void attackAll(int damage) {
		for(int i = 0; i < this.regularCounter; i++) {
			this.regularList[i].attack(damage);
		}
	}
}

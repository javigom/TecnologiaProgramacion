package tp.p1.util;

import java.util.Random;

import tp.p1.game.Direction;
import tp.p1.game.Level;

public class DestroyerShipList {

	//Atributos
	private DestroyerShip destroyerList[];
	private int destroyerCounter;
	private int maxShip;
	
	//Constructor
	public DestroyerShipList(Level l) {
		
		this.maxShip = l.getNumDestroyer();
		this.destroyerList = new DestroyerShip[maxShip];
		this.destroyerCounter = 0;
	}
		
	//Métodos
	
	//Devuelve el numero de naves
	public int getNumShip(){
		return this.destroyerCounter;
	}
		
	//Devuelve el tamaño del array
	public int getMaxShip() {
		return this.maxShip;
	}
	
	//Añade una nave a la lista
	public void addRegularShip(DestroyerShip ship) {
		this.destroyerList[this.destroyerCounter] = ship;
		this.destroyerCounter++;
			
	}
	//Elimina las naves que tienen 0 de vida de la lista
	public int removeDestroyerShip(){
		int points = 0, i = 0;
		
		while ( i < this.destroyerCounter) {
			if(this.destroyerList[i].getStrength() <= 0) {
				points = points + this.destroyerList[i].getPoints();
				this.destroyerList[i] = this.destroyerList[this.destroyerCounter - 1];
				this.destroyerList[this.destroyerCounter - 1] = null;
				this.destroyerCounter--;
				
				/*
				for(int j = 0; j < this.destroyerCounter - 1; j++) {
					this.destroyerList[j] = this.destroyerList[j + 1];
				}
				this.destroyerList[this.destroyerCounter - 1] = null;
				this.destroyerCounter--;
				*/
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
			
		while(free && i < this.destroyerCounter) {
			if((this.destroyerList[i].getFila() == f) && (this.destroyerList[i].getColumna() == c)) {
				free = false;
			}
			i++;
		}
			
		return free;
	}
		
	//Devuelve string la nave de una posicion f, c (Sólo si se cumple !isFree)
	public String drawShip(int f, int c) {
		String draw = null;
		int i = 0;
		while(i < this.destroyerCounter) {
			if((this.destroyerList[i].getFila() == f) && (this.destroyerList[i].getColumna() == c)) {
				draw = this.destroyerList[i].toString();
			}
			i++;
		}
		return draw;
	}
	
	//Mueve todas las naves una posicion a la derecha o izquierda
	public void move(Direction dir) {
		for(int i = 0; i < this.destroyerCounter; i++) {
			this.destroyerList[i].move(dir);
		}
	}
		
	//Mueve todas las naves una posicion hacia abajo
	public void moveDown() {
		for(int i = 0; i < this.destroyerCounter;i++) {
			this.destroyerList[i].moveDown();
		}
	}
	
	//Comprueba si alguna nave se encuentra en la columna n
	public boolean limitCol(int n)
	{
		boolean limit = false;
		for(int i = 0; i < this.destroyerCounter;i++) {
			if(this.destroyerList[i].getColumna() == n) {
				limit = true;
			}
		}
		return limit;
	}
	
	//Comprueba si alguna nave se encuentra en la fila n
	public boolean limitFila(int n)
	{
		boolean limit = false;
		for(int i = 0; i < this.destroyerCounter; i++) {
			if(this.destroyerList[i].getFila() == n) {
				limit = true;
			}
		}
		return limit;
	}
	
	//Le resta un punto a la nave con coordenadas f, c
	public void attackShip(int f, int c, int damage) {
		boolean encontrado = false;
		int i = 0;
	
		while(!encontrado && i < this.destroyerCounter) {
			if((this.destroyerList[i].getFila() == f) && (this.destroyerList[i].getColumna() == c)) {
				this.destroyerList[i].attack(damage);
				encontrado = true;
			}
			
			i++;
		}
	}
	
	//Comprueba la probabilidad de que cada nave lanze una bomba
	public void throwBomb(Random rnd, Level lvl) {
		for(int i = 0; i < this.destroyerCounter; i++) {
			this.destroyerList[i].throwBomb(rnd, lvl);
		}
	}
	
	//Elimina la bomba de la posicion f, c de la nave
	public void deleteBomb(int f, int c) {
		boolean encontrado = false;
		int i = 0;
		while(!encontrado && i < this.destroyerCounter) {
			if(this.destroyerList[i].deleteBomb(f, c)) {
				encontrado = true;
			}
			i++;
		}
	}
	
	//Elimina las bombas de la lista que hayan llegado a la fila f
	public void updateBomb(int f) {
		for(int i = 0; i < this.destroyerCounter; i++) {
			this.destroyerList[i].updateBomb(f);
		}
	}
	
	//Le resta damage a todas las naves de la lista
	public void attackAll(int damage) {
		for(int i = 0; i < this.destroyerCounter; i++) {
			this.destroyerList[i].attack(damage);
		}
	}
}
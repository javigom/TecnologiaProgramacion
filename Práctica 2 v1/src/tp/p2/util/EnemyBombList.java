package tp.p2.util;

import tp.p2.game.Level;

public class EnemyBombList {

	//Atributos
	private EnemyBomb bombList[];
	private int bombCounter;
	private int maxBomb;

	//Constructor
	public EnemyBombList(Level l) {
		
		this.maxBomb = l.getNumDestroyer();
		bombList = new EnemyBomb[maxBomb];
		this.bombCounter = 0;
	}
	
	//Métodos
	
	//Devuelve el numero de bombas
	public int getNumBomb(){
		return this.bombCounter;
	}
		
	//Devuelve el tamaño del array
	public int getMaxBomb() {
		return this.maxBomb;
	}
		
	//Añade una nave a la lista
	public void addBomb(EnemyBomb bomb) {
		bombList[bombCounter] = bomb;
		bombCounter++;
	}
	
	//Elimina una bomba de la lista
	public void deleteBomb(int x, int y) {
		int i = 0;
		boolean encontrado = false;
		while(i < this.bombCounter && !encontrado) {
			if((this.bombList[i].getFila() == x) && (this.bombList[i].getColumna() == y)){
				encontrado = true;
				this.bombList[i] = null;
				this.bombList[i] = this.bombList[bombCounter-1];
				bombCounter--;
			}
			i++;
		}
	}
	
	//Devuelve true si no existe una bomba en las coordenadas f, c
	public boolean isFree(int f, int c) {
		boolean free = true;
		int i = 0;
		
		while(free && i < bombCounter) {
			if((bombList[i].getFila() == f) && (bombList[i].getColumna() == c)) {
				free = false;
			}
			i++;
		}
		
		return free;
	}
	
	//Devuelve el string de la posicion f, c (Sólo es llamada si se cumple !isFree)
	public String drawBomb(int f, int c) {
		String draw = null;
		int i = 0;
		while(i < bombCounter) {
			if((bombList[i].getFila() == f) && (bombList[i].getColumna() == c)) {
				draw = bombList[i].toString();
			}
			i++;
		}
		return draw;
	}
	
	//Mueve todas las bombas de la lista
	public void moveBomb() {
		for(int i = 0; i < this.bombCounter; i++) {
			this.bombList[i].avanzar();
		}
	}
	
	//Devuelve el daño de la bomba de la posicion f, c (Sólo es llamada si se cumple !isFree)
	public int getDamage(int f, int c) {
		int damage = 0, i = 0;
		while(i < bombCounter) {
			if((bombList[i].getFila() == f) && (bombList[i].getColumna() == c)) {
				damage = bombList[i].getDamage();
			}
			i++;
		}
		return damage;
	}
}
package tp.p1.game;

import java.util.Random;

import tp.p1.utils.*;
import tp.p1.game.*;

public class Game {

	
	private RegularShipList rl = new RegularShipList();
	private Level lvl;
	private Random rnd;
	
	private int numFilas;
	private int numColumnas;
	
	private RegularShipList regularList;
	private UCMShip ship;
	private int numCiclos;
	private int puntosJugador;
	
	public Game(Level l, Random r, int filas, int columnas) {
		this.lvl = l;
		this.rnd = r;
		this.numFilas = filas;
		this.numColumnas = columnas;
		this.regularList = new RegularShipList(numFilas*numColumnas); 
		this.ship = new UCMShip(7, 4, 3, 1);
		
	}

	public String characterAtToString(int i, int j) {
		if((ship.getFila()==i) && (ship.getColumna() == j)) {
			return ship.toString();
		}
		
		return null;
	}
	
	

}

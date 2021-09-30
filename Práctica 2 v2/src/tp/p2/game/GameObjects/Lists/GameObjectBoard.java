package tp.p2.game.GameObjects.Lists;

import tp.p2.game.Game;
import tp.p2.game.GameObjects.Bomb;
import tp.p2.game.GameObjects.ExplosiveAlien;
import tp.p2.game.GameObjects.GameObject;

public class GameObjectBoard {

	//Atributos
	
	private GameObject[] objects;
	private int currentObjects;
	private GameObject ovni;
	private final int SHOCKWAVEDAMAGE = 1;
	//Constructor
	
	public GameObjectBoard(int width, int height) {
		objects = new GameObject[width*height];
		this.currentObjects = 0;
	}
	
	//Métodos
	
	//Devuelve el numero de objetos actuales
	public int getCurrentObjects() {
		return this.currentObjects;
	}
	
	//Añade el objeto object a la lista
	public void add(GameObject object) {
		objects[this.currentObjects] = object;
		this.currentObjects++;
	}
		
	//Devuelve el indice del objeto cuya posición coincida con r, c
	private int getIndex(int r, int c) {
		int pos = -1, i = 0;
		boolean get = false;
		while(i < this.currentObjects && !get) {
			
			if(objects[i].getRows() == r && objects[i].getCols() == c) {
				get = true;
				pos = i;
			}
			i++;
		}
		
		return pos;
	}
	
	//Eliina el objeto object de la lista
	private void remove(GameObject object) {
		
		int pos = 0;
		boolean removed = false;
		
		while(!removed && pos < this.currentObjects) {
			if(object == objects[pos]) {
				objects[pos] = null;
				removed = true;
				
				for(int i = pos; i < this.currentObjects - 1; i++) {
					objects[i] = objects[i + 1];
				}
				
				this.currentObjects--;
			}
			
			else {
				pos++;
			}
		}
	}
	
	//Actualiza el juego
	public void update() {
		for(int i = 0; i < this.currentObjects; i++) {		
			objects[i].move();
			checkAttacks(objects[i]);
			
			System.out.println(objects[i] + " columna: " + objects[i].getCols() + " fila: " + objects[i].getRows() + " vida: " + objects[i].getLive());
		}
		
		removeDead();		
	}
	
	//Comprueba colisiones del objeto object con otros
	private void checkAttacks(GameObject object) {
		
		boolean found = false;
		int i = 0;
		
		while(!found && i < this.currentObjects) {
			
			if(object.getRows() == objects[i].getRows() && object.getCols() == objects[i].getCols() && object != objects[i]) {
				found = true;
				object.performAttack(objects[i]);
			}
			
			i++;
		}
	}
	
	//Computer action
	public void computerAction() {
		for(int i = 0; i < this.currentObjects; i++) {
			objects[i].computerAction();
		}		
	}
	
	//Elimina las naves con 0 de vida
	private void removeDead() {
		int i = 0;
		while(i < this.currentObjects - 1) {
			if(objects[i].isOut() || ((objects[i] != ovni) && !objects[i].isAlive())) {
				objects[i].onDelete();
				remove(objects[i]);
			}
			
			else if(objects[i] == ovni && !objects[i].isAlive()) { //NUNCA elimina al ovni de la lista
				objects[i].onDelete();
				i++;
			}
			
			else {
				i++;
			}
		}
	}
	
	//Busca la nave destroyer cuya referencia tenga la bomba b y la elimina
	public void searchBomb(Bomb b) {
		int i = 0;
		
		boolean delete = false;
		while(!delete && i < currentObjects) {
			if(objects[i].deleteBomb(b))
				delete = true;
			
			i++;
		}
	}
	
	//Guardo una referencia del ovni para evitar eliminarlo al colisionar/salir del tablero
	public void saveOvni(GameObject ship) {
		ovni = ship;
	}
	
	//Realiza el ataque del ShockWave
	public void shockWaveAttack() {
		for(int i = 0; i < this.currentObjects; i++) {
			objects[i].receiveShockWave(SHOCKWAVEDAMAGE);
		}
	}
	
	public void changeRegular(Game game, GameObject regular) {
		int pos = getIndex(regular.getRows(), regular.getCols());
		
		GameObject obj = new ExplosiveAlien(game, regular.getRows(), regular.getCols(), regular.getCyclesToMove());
			
		if(pos != -1) {
			remove(objects[pos]);
			add(obj);
		}
	}
	
	public void explosion(int f, int c) {
		int pos = -1;
		for(int i = 0; i < 8; i++) {
			
			switch(i) {
				case 0: pos = getIndex(f - 1, c - 1);
					break;
				case 1: pos = getIndex(f - 1, c);
					break;
				case 2: pos = getIndex(f - 1, c + 1);
					break;
				case 3: pos = getIndex(f, c + 1);
					break;
				case 4: pos = getIndex(f + 1, c + 1);
					break;
				case 5: pos = getIndex(f + 1, c);
					break;
				case 6: pos = getIndex(f + 1, c - 1);
					break;
				case 7: pos = getIndex(f, c - 1);
					break;
			}
			
			if(pos != -1) explosiveAttack(1, pos);
		}	
	}
	
	private void explosiveAttack(int damage, int pos) {
		objects[pos].receiveExplosive(damage);
	}
	
	//Devuelve string
	public String toString(int r, int c) {
		String draw = "";
		int pos = getIndex(r, c);
		if (pos != -1) {
			draw = objects[pos].toString();
		}
		
		return draw;
	}
}

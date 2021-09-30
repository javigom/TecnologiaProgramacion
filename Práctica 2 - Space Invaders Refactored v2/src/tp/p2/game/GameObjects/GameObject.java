package tp.p2.game.GameObjects;

import tp.p2.game.Game;

public abstract class GameObject implements IAttack {
	
	//Atributos
	
	protected int row, col;
	protected int live;
	protected Game game;
	
	//Constructor
	
	public GameObject(Game game, int row, int col, int live) {
		this.game = game;
		this.row = row;
		this.col = col;
		this.live = live;
	}
	
	//Métodos
	
	//Devuelve la fila
	public int getRows() {
		return this.row;
	}
	
	//Devuelve la columna
	public int getCols() {
		return this.col;
	}
	
	//Devuelve true si la vida es mayor que 0
	public boolean isAlive() {
		boolean alive = false;
		if(this.live > 0) {
			alive = true;
		}
		return alive;
	}
	
	//Obtiene la vida
	public int getLive() {
		return this.live;
	}
	
	//Devuelve true si r, c coincide con las coordenadas
	public boolean isOnPosition(int r, int c) {
		boolean position = false;
		if(this.row == r && this.col == c) {
			position = true;
		}
		return position;
	}
	
	//Resta a la vida el valor de damage
	public void getDamage(int damage) {
		
		this.live = damage >= this.live ? 0 : this.live - damage;
	}
	
	//Comprueba si está fuera del tablero
	public boolean isOut() {
		return !game.isOnBoard(this.row, this.col);
	}
	
	//Métodos abstractos
	public abstract void computerAction();
	public abstract void onDelete();
	public abstract void move();
	public abstract String toString();
	
	//Elimina bomba (referencia de las naves destroyer, usada en dicha clase)
	public boolean deleteBomb(Bomb b) {
		return false;
	}
	
	public boolean receiveMissileAttack(int damage) {
		getDamage(damage);
		return true;
	}
	
	public boolean receiveBombAttack(int damage) {
		getDamage(damage);
		return true;
	}
	
	public boolean receiveExplosive(int damage) {
		getDamage(damage);
		return true;
	}
	
	public int getCyclesToMove() {
		return 0;
	}
}

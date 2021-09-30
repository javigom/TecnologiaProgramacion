package tp.p2.game.GameObjects;

import tp.p2.game.Game;

public class Bomb extends Weapon {

	//Atributos
	
	private static final int BOMBINITIALLIVE = 1;
	private static final int BOMBDAMAGE = 1;
	
	//Constructor
	
	public Bomb(Game game, int row, int col) {
		super(game, row, col, BOMBINITIALLIVE, BOMBDAMAGE);
	}
	
	//Métodos
	
	//Avanza trayectoria
	public void move() {
		this.row++;
		
	}
	
	//Llama a game para que elimine la referencia de su nave destructora
	public void onDelete() {
		game.deleteBomb(this);
	}

	//Realiza ataque
	public boolean weaponAttack(GameObject other) {
		
		if(other.receiveBombAttack(BOMBDAMAGE)) {
			return true;
		}
		else {
			return false;
		}
	}
		
	//Recibe ataque misil
	public boolean receiveMissileAttack(int damage) {
		getDamage(damage);
		return true;
	}
	
	//Devuelve string
	public String toString() {
		return ".";
	}
	
	public String serialize() {
		return ""; 
	}
	
}

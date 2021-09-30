package tp.p2.game.GameObjects;

import tp.p2.game.Game;

public class Supermissile extends Weapon {

	//Atributos
	
	private static final int SUPERMISSILEINITIALLIVE = 1;
	private static final int SUPERMISSILEDAMAGE = 2;
	
	//Constructor
	
	public Supermissile(Game game, int row, int col) {
		super(game, row, col, SUPERMISSILEINITIALLIVE, SUPERMISSILEDAMAGE);
	}

	//Métodos
	
	//Activa el misil mediante un callback
	public void onDelete() {
		game.enableMissile();	
	}

	//Avanza trayectoria
	public void move() {
		this.row--;
	}
	
	//Realiza ataque
	public boolean weaponAttack(GameObject other) {
		if(other.receiveMissileAttack(SUPERMISSILEDAMAGE))
			return true;
		else
			return false;
	}
	
	//Recibe ataque bomba
	public boolean receiveBombAttack(int damage) {
		getDamage(damage);
		return true;
	}
	
	//Devuelve string
	public String toString() {
		return "^";
	}
	
	//String serializer
	public String serialize() {
		return "X;" + this.row + "," + this.col + "\n"; 
	}
}
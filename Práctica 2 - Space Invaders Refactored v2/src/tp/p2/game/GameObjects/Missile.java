package tp.p2.game.GameObjects;

import tp.p2.game.Game;

public class Missile extends Weapon {

	//Atributos
	
	private static final int MISSILEINITIALLIVE = 1;
	private static final int MISSILEDAMAGE = 1;
	
	//Constructor
	
	public Missile(Game game, int row, int col) {
		super(game, row, col, MISSILEINITIALLIVE, MISSILEDAMAGE);
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
	
	public boolean weaponAttack(GameObject other) {
		if(other.receiveMissileAttack(MISSILEDAMAGE))
			return true;
		else
			return false;
	}
	
	//Devuelve string
	public String toString() {
		return "*";
	}
}

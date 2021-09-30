package tp.p2.game.GameObjects;

import tp.p2.game.Game;

public abstract class Weapon extends GameObject{

	//Atributos
	
	protected int damage;
	
	//Constructor
	
	public Weapon(Game game, int row, int col, int live, int damage) {
		super(game, row, col, live);
		this.damage = damage;
	}
	
	//Métodos
	
	public void computerAction() {}
	
	//Realiza un ataque a other
	public boolean performAttack(GameObject other){
		boolean done = false;
		if(isAlive() && other.isAlive()) {
			if(other.isOnPosition(this.row, this.col)) {
				if(weaponAttack(other)) {
					done = true;
					this.live--;
				}	
			}
		}
		return done;
	}

	protected abstract boolean weaponAttack(GameObject other);
}

package tp.p2.game.GameObjects;

import tp.p2.game.Game;

public abstract class EnemyShip extends Ship {
	
	//Atributos
	protected int enemyPoints;
	
	//Constructor
	public EnemyShip(Game game, int row, int col, int live, int points) {
		super(game, row, col, live);
		this.enemyPoints = points;
	}
	
	public boolean receiveShockWave(int damage) {
		this.getDamage(damage);
		return true;
	}
}

package tp.p2.game.GameObjects;

import tp.p2.game.Game;

public class ExplosiveAlien extends AlienShip {

	//Atributos
	
	private static final int EXPLOSIVEINITIALLIVE = 2;
	private static final int EXPLOSIVERPOINTS = 10;
	
	//Constructor
	
	public ExplosiveAlien(Game game, int row, int col, int cyclesToMove) {
		super(game, row, col, EXPLOSIVEINITIALLIVE, EXPLOSIVERPOINTS);
		this.cyclesToMove = cyclesToMove;
	}
	
	//Métodos
	
	public void onDelete(){
		game.explosion(this.row, this.col);
	}
	
	//Devuelve string
	public String toString() {
		return "E[" + this.live + "]";
	}
}
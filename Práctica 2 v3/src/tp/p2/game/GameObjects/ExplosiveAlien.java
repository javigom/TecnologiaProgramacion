package tp.p2.game.GameObjects;

import tp.p2.game.Game;

public class ExplosiveAlien extends AlienShip {

	//Atributos
	
	private static final int EXPLOSIVEINITIALLIVE = 2;
	private static final int EXPLOSIVEPOINTS = 10;
	
	//Constructor
	
	public ExplosiveAlien(Game game, int row, int col, int cyclesToMove) {
		super(game, row, col, EXPLOSIVEINITIALLIVE, EXPLOSIVEPOINTS);
		this.cyclesToMove = cyclesToMove;
	}
	
	//Métodos
	
	//OnDelete nave explosiva
	public void onDelete(){
		lessAliens();
		game.explosion(this.row, this.col);
		game.receivePoints(EXPLOSIVEPOINTS);
	}
	
	//Devuelve string
	public String toString() {
		return "E[" + this.live + "]";
	}
	
	//String serializer
	public String serialize() {
		return "E;" + this.row + "," + this.col + ";" + this.live + ";" + this.cyclesToMove + ";" + AlienShip.dir + "\n"; 
	}
}
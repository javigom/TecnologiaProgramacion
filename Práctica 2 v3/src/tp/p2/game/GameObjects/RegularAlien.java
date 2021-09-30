package tp.p2.game.GameObjects;

import tp.p2.game.Game;

public class RegularAlien extends AlienShip {

	//Atributos
	
	private static final int REGULARINITIALLIVE = 2;
	private static final int REGULARPOINTS = 10;
	
	//Constructor
	
	public RegularAlien(Game game, int row, int col) {
		super(game, row, col, REGULARINITIALLIVE, REGULARPOINTS);
		AlienShip.remainingAliens = LEVEL.getNumRegular() + LEVEL.getNumDestroyerAliens();
		AlienShip.moveAliens = AlienShip.remainingAliens;
		haveLanded = false;
	}
	
	//Métodos
	
	//Computer action
	public void computerAction() {
		if(IExecuteRandomActions.canGenerateRandomExplosive(game)) {
			game.changeRegular(this);
		}
	}
	
	//Devuelve string
	public String toString() {
		return "R[" + this.live + "]";
	}
	
	//String serializer
	public String serialize() {
		return "R;" + this.row + "," + this.col + ";" + this.live + ";" + this.cyclesToMove + ";" + AlienShip.dir + "\n"; 
	}
}
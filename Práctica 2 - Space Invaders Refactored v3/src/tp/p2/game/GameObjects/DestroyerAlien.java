package tp.p2.game.GameObjects;

import tp.p2.game.Game;

public class DestroyerAlien extends AlienShip{
	
	//Atributos
	
	private static final int DESTROYERINITIALLIVE = 1;
	private static final int DESTROYERPOINTS = 10;
	private Bomb bomba = null;
	
	//Constructor
	
	public DestroyerAlien(Game game, int row, int col) {
		super(game, row, col, DESTROYERINITIALLIVE, DESTROYERPOINTS);
		AlienShip.remainingAliens = LEVEL.getNumRegular() + LEVEL.getNumDestroyerAliens();
		AlienShip.moveAliens = AlienShip.remainingAliens;
		haveLanded = false;
	}
	
	//Métodos
	
	//Comprueba la probabilidad de lanzar una bomba
	public void computerAction() {
		if(bomba == null && IExecuteRandomActions.canGenerateRandomBomb(game)) {
			bomba = new Bomb(game, this.row, this.col);
			if(limit && cyclesToMove != 0) {
				bomba.move();
			}
			game.addObject(bomba);
		}
	}
	
	//Elimina la referencia a su bomba (llamada desde game)
	public boolean deleteBomb(Bomb b) {
		
		boolean delete = false;
		
		if(b == bomba) {
			bomba = null;
			delete = true;
		}
		
		return delete;
	}
	
	//Devuelve string
	public String toString() {
		return "D[" + this.live + "]";
	}
	
	//String serializer
	public String serialize() {
		String ser = "D;" + this.row + "," + this.col + ";" + this.live + ";" + this.cyclesToMove + ";" + AlienShip.dir + "; "; 
		
		if(this.bomba != null) {
			ser = ser + "B;" + this.bomba.getRows() + "," + this.bomba.getCols();
		}
		
		return ser + "\n";
	}
}

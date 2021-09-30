package tp.p2.game.GameObjects;

import tp.p2.game.Direction;
import tp.p2.game.Game;

public class UCMShip extends Ship {

	//Atributos
	
	private static final int UCMINITIALLIVE = 3;
	private int playerPoints;
	private Weapon missile;
	private int availableSuper;
	
	//Constructor
	
	public UCMShip(Game game, int row, int col) {
		super(game, row, col, UCMINITIALLIVE);
		this.playerPoints = 0;
		this.missile = null;
		this.availableSuper = 0;
	}
	
	//Métodos

	//Mueve la nave mediante el comando
	public boolean move(int numCells, Direction dir) {
		boolean moved = true;
		
		if(dir == Direction.LEFT && col - numCells >= 0) {
			col = col - numCells;
		}
		
		else if(dir == Direction.RIGHT && col + numCells <= Game.DIM_COLS) {
			col = col + numCells;
		}
		
		else {
			moved = false;
		}

		return moved;
	}
	
	public void onDelete() {}
	
	public void reveivePoints(int points) {
		this.playerPoints += points;
	}
	
	public boolean shootMissile(boolean superMissile) {
		boolean created = false;
		if(missile == null) {
			
			if(superMissile == false) {
				missile = new Missile(game, this.getRows(), this.getCols());
				game.addObject(missile);
				created = true;
			}
			
			else if (this.availableSuper > 0) {
				missile = new Supermissile(game, this.getRows(), this.getCols());
				this.availableSuper--;
				game.addObject(missile);
				created = true;
			}
		}
		return created;
	}
	
	public void enableMissile() {	
		missile = null;
	}
	
	public boolean buySuper() {
		boolean buyied = false;
		if(this.playerPoints >= 20) {
			this.playerPoints -= 20;
			buyied = true;
			this.availableSuper++;
		}
		
		return buyied;
	}
	
	//Devuelve un string con su estado
	public String stateToString() {
		return "Life: " + this.live + "\n" +
				"Points: " + this.playerPoints + "\n" +
				"Available Super Missile: " + this.availableSuper + "\n";
	}
	
	//Devuelve string
	public String toString() {
		return "^__^";
	}
}

package tp.p2.game.GameObjects;

import tp.p2.exception.CommandExecuteException;
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
	public boolean move(int numCells, Direction dir) throws CommandExecuteException {
		boolean moved = true;
		
		if(dir == Direction.LEFT && col - numCells >= 0) {
			col = col - numCells;
		}
		
		else if(dir == Direction.RIGHT && col + numCells <= Game.DIM_COLS) {
			col = col + numCells;
		}
		
		else {
			moved = false;
			throw new CommandExecuteException("Failed to move: Cannot perform move: ship too near border.");
		}

		return moved;
	}
	
	//No es necesaria
	public void onDelete() {}
	
	//Suma points a los puntos del jugador
	public void reveivePoints(int points) {
		this.playerPoints += points;
	}
	
	//Dispara un misil/supermisil
	public boolean shootMissile(boolean superMissile) throws CommandExecuteException{
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
			
			else
				throw new CommandExecuteException("Failed to shoot: Cannot fire supermissile: no supermissile available.");
		}
		
		else
			throw new CommandExecuteException("Failed to shoot: Cannot fire missile: missile already exists on board.");
		
		return created;
	}
	
	//Activa el misil poniendolo a nulo
	public void enableMissile() {	
		missile = null;
	}
	
	//Compra un supermisil
	public boolean buySuper() throws CommandExecuteException {
		boolean buyied = false;
		if(this.playerPoints >= 20) {
			this.playerPoints -= 20;
			buyied = true;
			this.availableSuper++;
		}
		
		else {
			throw new CommandExecuteException("Failed to buy: Cannot buy a supermissile: 20 points required. You have: " + this.playerPoints);
		}
		
		return buyied;
	}
	
	//Recibe ataque de la bomba
	public boolean receiveBombAttack(int damage) {
		getDamage(damage);
		return true;
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
	
	//String serializer
	public String serialize() {
		return "P;" + this.row + "," + this.col + ";" + this.live + ";" + this.playerPoints + ";" + game.shockwaveBool() + ";" + this.availableSuper + "\n"; 
	}
}

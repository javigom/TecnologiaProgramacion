package tp.p2.game.GameObjects;

import tp.p2.game.Direction;
import tp.p2.game.Game;
import tp.p2.game.Level;

public abstract class AlienShip extends EnemyShip{
	
	//Atributos
	protected static int remainingAliens = 0;
	protected static boolean limit = false;
	protected static Direction dir = Direction.RIGHT;
	protected static int moveAliens = 0;
	protected int cyclesToMove = 0;
	protected static boolean haveLanded;
	protected final Level LEVEL = game.getLevel();
	private final int SPEED = LEVEL.getSpeed();
	
	//Constructor
	public AlienShip(Game game, int row, int col, int live, int points) {
		super(game, row, col, live, points);	
	}

	//Métodos

	//Desplaza las naves por el tablero
	public void move() {
				
		if(cyclesToMove == 0) {
			cyclesToMove = SPEED + 1;
			
			if(dir == Direction.RIGHT)
				this.col++;
			else
				this.col--;
			
			if(this.col == 0 || this.col == Game.DIM_COLS - 1)
				limit = true;
		}
		
		else if (limit == true) {
			this.row++;
			moveAliens--;
			
			if (moveAliens == 0) {
				limit = false;
				moveAliens = remainingAliens;
				
				if(dir == Direction.RIGHT)
					dir = Direction.LEFT;
				else
					dir = Direction.RIGHT;
			}
		}
		
		cyclesToMove--;
		
		if(this.row == Game.DIM_ROWS - 1) {
			haveLanded = true;
		}
	}
		
	//Incrementa el numero de aliens, invocada cuando se añaden a la lista
	protected void plusAliens() {
		remainingAliens++;
		moveAliens++;
	}
	
	//Devuelve true cuando no quedan naves enemigas
	public static boolean allDead() {
		return (remainingAliens == 0);
	}

	//OnDelete, resta 1 al contador y hace un callback a game para sumar los puntos
	public void onDelete() {
		lessAliens();
		game.receivePoints(this.enemyPoints);
	}
	
	//Devuelve el numero de naves enemigas
	public static String getRemainingAliens() {
		return remainingAliens + "\n";
	}

	//True si han llegado a la ultima fila
	public static boolean haveLanded() {	
		return haveLanded;
	}
	
	//Número de ciclos que faltan para moverse
	public int getCyclesToMove() {
		return this.cyclesToMove;
	}
		
	//Decrementa numAliens, invocada cuando se elimina una nave
	protected void lessAliens() {
		remainingAliens--;
		moveAliens--;
	}
	
}
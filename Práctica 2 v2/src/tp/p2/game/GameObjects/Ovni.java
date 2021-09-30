package tp.p2.game.GameObjects;

import tp.p2.game.Game;

public class Ovni extends EnemyShip {
	
	//Atributos
	
	private static final int OVNIINITIALLIVE = 1;
	private static final int OVNIPOINTS = 25;
	private boolean exists;
	public boolean dontMove;
	
	//Constructor
	
	public Ovni(Game game) {
		super(game, 0, 8, OVNIINITIALLIVE, OVNIPOINTS);
		this.exists = false;
		this.dontMove = false;
	}

	//Métodos
	
	//Avanza trayectoria
	public void move() {
		if(this.exists && !dontMove)
			this.col--;
		else if(dontMove){
			this.dontMove = false;
		}
	}

	
	public void computerAction() {

		if(!this.exists && IExecuteRandomActions.canGenerateRandomOvni(game)) {
			this.exists = true;
			this.dontMove = true;
		}
		else if(this.col == 0) {
			this.exists = false;
			this.col = 8;
		}
		
	}
	
	public void onDelete() {
		this.exists = false;
		this.col = 8;
		game.receivePoints(this.enemyPoints);
		game.enableShockWave();
		this.live = OVNIINITIALLIVE;
	}
	
	//Devuelve string
	public String toString() {
		
		if(this.exists)
			return "O[" + this.live + "]";
		else
			return "";
	}
}

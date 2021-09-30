package tp.p2.game.GameObjects;

import tp.p2.game.Game;

public abstract class Ship extends GameObject implements IAttack{
	
	//Constructor
	
	public Ship(Game game, int row, int col, int live) {
		super(game, row, col, live);
	}

	//Métodos
	
	
	public void computerAction() {}
	public void move() {}
}
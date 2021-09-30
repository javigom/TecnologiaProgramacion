package tp.p2.game.GameObjects;

import tp.p2.game.Direction;

public interface IPlayerController {

	//Player actions
	public boolean move(int numCells, Direction dir);
	public boolean shootMissile(boolean superMissile);
	public boolean shockWave();
	
	//Callbacks
	public void receivePoints(int points);
	public void enableShockWave();
	public void enableMissile();
}

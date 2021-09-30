package tp.p2.game.GameObjects;

import tp.p2.exception.CommandExecuteException;
import tp.p2.game.Direction;

public interface IPlayerController {

	//Player actions
	public boolean move(int numCells, Direction dir) throws CommandExecuteException;
	public boolean shootMissile(boolean superMissile) throws CommandExecuteException;
	public boolean shockWave() throws CommandExecuteException;
	
	//Callbacks
	public void receivePoints(int points);
	public void enableShockWave();
	public void enableMissile();
}

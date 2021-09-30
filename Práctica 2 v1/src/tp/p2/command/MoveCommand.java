package tp.p2.command;

import tp.p2.game.Direction;
import tp.p2.game.Game;

public class MoveCommand extends Command {
	
	private int numCells;
	private Direction moveDirection;
	
	public MoveCommand(){
		super("move", "m", "[M]ove <left|right><1|2>" ,"Moves UCM-Ship to the indicated direction.");
	}
	
	public boolean execute(Game game) {
		return game.moveUCMShip(this.numCells, this.moveDirection);
	}
	
	public Command parse(String[] commandWords) {
		Command c = null;
		int num;		
		
		if(matchCommandName(commandWords[0]) && commandWords.length == 3) {
			
			if(commandWords[1].equals("right")) {
				this.moveDirection = Direction.RIGHT;
				num = Integer.parseInt(commandWords[2]);
				if(num == 1 || num == 2){
					this.numCells = num;
					c = this;
				}
			}
			
			else if(commandWords[1].equals("left")) {
				this.moveDirection = Direction.LEFT;
				num = Integer.parseInt(commandWords[2]);
				if(num == 1 || num == 2){
					this.numCells = num;
					c = this;
				}
			}
		}
		
		return c;
	}	
}

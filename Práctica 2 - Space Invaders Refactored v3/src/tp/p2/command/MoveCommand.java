package tp.p2.command;

import tp.p2.exception.CommandExecuteException;
import tp.p2.exception.CommandParseException;
import tp.p2.game.Direction;
import tp.p2.game.Game;

public class MoveCommand extends Command {
	
	private int numCells;
	private Direction moveDirection;
	
	public MoveCommand(){
		super("move", "m", "[M]ove <left|right> <1|2>" ,"Moves UCM-Ship to the indicated direction.");
	}
	
	//Ejecuta el comando
	public boolean execute(Game game) throws CommandExecuteException {
		return game.move(this.numCells, this.moveDirection);
	}
	
	//Comprueba que el comando esté bien escrito
	public Command parse(String[] commandWords) throws CommandParseException{
		Command c = null;
		int num;
		
		if(matchCommandName(commandWords[0])) {
			
			if (commandWords.length == 3) {
				
				try {
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
						else
							throw new CommandParseException("Usage: [M]ove <left|right> <1|2>: " + incorrectArgsMsg + ": <1|2> must be 1 or 2.");
					}
				
				}catch(NumberFormatException ex) {
					System.err.println("Usage: [M]ove <left|right> <1|2>: " + incorrectArgsMsg + ": <1|2> must be a number.");
				}
			}
			
			else {
				throw new CommandParseException("Usage: [M]ove <left|right> <1|2>: "+ incorrectNumArgsMsg);
			}
		}
				
		return c;
	}	
}

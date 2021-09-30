package tp.p2.command;

import tp.p2.exception.CommandExecuteException;
import tp.p2.exception.CommandParseException;
import tp.p2.game.Game;

public class ShootCommand extends Command {
	
	boolean superMissile;
	
	public ShootCommand(){
		super("shoot", "s", "[S]hoot / [S]hoot super" , "UCM-Ship launches a missile / supermissile.");
		
	}

	//Ejecuta el comando
	public boolean execute(Game game) throws CommandExecuteException {
		return game.shootMissile(this.superMissile);
	}

	//Comprueba que el comando esté bien escrito
	public Command parse(String[] commandWords) throws CommandParseException{
		Command c = null;
		if(matchCommandName(commandWords[0])) {
			
			if((commandWords.length == 2) || (commandWords.length == 1)) {
				if(commandWords.length == 1) {
					this.superMissile = false;
					c = this;
				}
				
				else if(commandWords[1].equals("super")) {
					this.superMissile = true;
					c = this;
				}
				
				else {
					throw new CommandParseException("Usage: [S]hoot / [S]hoot super: " + incorrectArgsMsg);
				}
				
			}
			
			else {
				throw new CommandParseException("Usage: [S]hoot / [S]hoot super: " + incorrectNumArgsMsg);
			}
		}
		
		return c;
	}
}

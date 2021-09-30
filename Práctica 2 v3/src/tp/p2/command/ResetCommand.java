package tp.p2.command;

import tp.p2.exception.CommandParseException;
import tp.p2.game.Game;

public class ResetCommand extends Command{

	public ResetCommand(){
		super("reset", "r", "[R]eset" , "Starts a new game.");
		
	}

	//Ejecuta el comando
	public boolean execute(Game game) {
		game.reset();
		return true;
	}

	//Comprueba que el comando esté bien escrito
	public Command parse(String[] commandWords) throws CommandParseException{
		Command c = null;
		if(matchCommandName(commandWords[0])) {
			if(commandWords.length == 1) {
				c = this;
			}
			else {
				throw new CommandParseException("Usage: [R]eset: "+ incorrectNumArgsMsg);
			}
		}
		return c;
	}

}

package tp.p2.command;

import tp.p2.exception.CommandParseException;
import tp.p2.game.Game;

public class ExitCommand extends Command{
	
	public ExitCommand(){
		super("exit", "e", "[E]xit" ,"Terminates the program.");
		
	}

	//Ejecuta el comando
	public boolean execute(Game game) {
		game.exit();
		return false;
	}

	//Comprueba que el comando esté bien escrito
	public Command parse(String[] commandWords) throws CommandParseException {
		Command c = null;
		if(matchCommandName(commandWords[0])) {
			if(commandWords.length == 1) {
				c = this;
			}
			else {
				throw new CommandParseException("Usage: [E]xit: " + incorrectNumArgsMsg);
			}
		}
		return c;
	}
}

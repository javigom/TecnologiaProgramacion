package tp.p2.command;

import tp.p2.exception.CommandParseException;
import tp.p2.game.Game;

public class StringifyCommand extends Command{
	
	
	public StringifyCommand() {
		super("stringify", "t", "s[T]ringify", "Prints the game as plain text");
		
	}

	//Ejecuta el comando
	public boolean execute(Game game) {
		System.out.print(game.serialize());
		System.out.println();
		return false;
	}

	//Comprueba que el comando esté bien escrito
	public Command parse(String[] commandWords) throws CommandParseException{
		Command c = null;
		if(matchCommandName(commandWords[0])) {
			if(commandWords.length == 1) {
				c = this;
			}
			else {
				throw new CommandParseException("Usage: s[T]ringify: " + incorrectNumArgsMsg);
			}
		}
		return c;
	}
}
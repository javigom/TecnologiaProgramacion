package tp.p2.command;

import tp.p2.exception.CommandExecuteException;
import tp.p2.exception.CommandParseException;
import tp.p2.game.Game;

public class BuyCommand extends Command {

	public BuyCommand(){
		super("buy", "b", "[B]uy", "Buys a SuperMissile.");
	}
	
	//Ejecuta el comando
	public boolean execute(Game game) throws CommandExecuteException{
		return game.buySuper();
	}

	//Comprueba que el comando esté bien escrito
	public Command parse(String[] commandWords) throws CommandParseException {
		Command c = null;
		if(matchCommandName(commandWords[0])) {
			if(commandWords.length == 1) {
				c = this;
			}
			else {
				throw new CommandParseException("Usage: [B]uy: " + incorrectNumArgsMsg);
			}
			
		}
		return c;
	}

}

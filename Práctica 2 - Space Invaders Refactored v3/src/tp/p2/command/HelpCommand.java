package tp.p2.command;

import tp.p2.exception.CommandParseException;
import tp.p2.game.Game;

public class HelpCommand extends Command {

	public HelpCommand(){
		super("help", "h", "[H]elp", "Prints this help message.");
	}
	
	//Ejecuta el comando
	public boolean execute(Game game) {
		System.out.println(CommandGenerator.commandHelp());
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
				throw new CommandParseException("Usage: [H]elp: "+ incorrectNumArgsMsg);
			}
		}
		return c;
	}

}

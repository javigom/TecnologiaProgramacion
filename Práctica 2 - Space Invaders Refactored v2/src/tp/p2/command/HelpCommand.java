package tp.p2.command;

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
	public Command parse(String[] commandWords) {
		Command c = null;
		if(matchCommandName(commandWords[0])) {
			c = this;
		}
		return c;
	}

}

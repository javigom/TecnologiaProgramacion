package tp.p2.command;

import tp.p2.game.Game;

public class HelpCommand extends Command {

	public HelpCommand(){
		super("help", "h", "[H]elp", "Prints this help message.");
	}
	
	public boolean execute(Game game) {
		System.out.println(CommandGenerator.commandHelp());
		return false;
	}

	public Command parse(String[] commandWords) {
		Command c = null;
		if(matchCommandName(commandWords[0])) {
			c = this;
		}
		return c;
	}

}

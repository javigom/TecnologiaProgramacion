package tp.p2.command;

import tp.p2.game.Game;

public class ResetCommand extends Command{

	public ResetCommand(){
		super("reset", "r", "[R]eset" , "Starts a new game.");
		
	}

	public boolean execute(Game game) {
		game.reset();
		return true;
	}

	public Command parse(String[] commandWords) {
		Command c = null;
		if(matchCommandName(commandWords[0])) {
			c = this;
		}
		return c;
	}

}

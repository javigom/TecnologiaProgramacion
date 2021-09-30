package tp.p2.command;

import tp.p2.game.Game;

public class ExitCommand extends Command{
	
	public ExitCommand(){
		super("exit", "e", "[E]xit" ,"Terminates the program.");
		
	}

	public boolean execute(Game game) {
		game.endGame();
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

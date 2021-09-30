package tp.p2.command;

import tp.p2.game.Game;

public class ShootCommand extends Command {
	
	public ShootCommand(){
		super("shoot", "s", "[S]hoot" , "UCM-Ship launches a missile.");
		
	}

	public boolean execute(Game game) {
		return game.disparoUCM();
	}

	public Command parse(String[] commandWords) {
		Command c = null;
		if(matchCommandName(commandWords[0])) {
			c = this;
		}
		return c;
	}
}

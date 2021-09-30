package tp.p2.command;

import tp.p2.game.Game;

public class UpdateCommand extends Command {

	public UpdateCommand(){
			super("update", "u", "[U]pdate" , "Skips one cycle.");

	}

	public boolean execute(Game game) {
		return true;
	}

	public Command parse(String[] commandWords) {
		Command c = null;
		if(matchCommandName(commandWords[0]) || commandWords[0].contentEquals("")) {
			c = this;
		}
		return c;
	}

}

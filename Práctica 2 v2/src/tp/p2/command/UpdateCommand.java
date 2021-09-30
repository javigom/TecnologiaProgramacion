package tp.p2.command;

import tp.p2.game.Game;

public class UpdateCommand extends Command {

	public UpdateCommand(){
			super("update", "u", "[U]pdate" , "Skips one cycle.");

	}

	//Ejecuta el comando
	public boolean execute(Game game) {
		return true;
	}

	//Comprueba que el comando esté bien escrito
	public Command parse(String[] commandWords) {
		Command c = null;
		if(matchCommandName(commandWords[0]) || commandWords[0].contentEquals("")) {
			c = this;
		}
		return c;
	}

}

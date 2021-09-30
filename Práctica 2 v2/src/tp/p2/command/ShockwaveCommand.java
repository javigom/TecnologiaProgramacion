package tp.p2.command;

import tp.p2.game.Game;

public class ShockwaveCommand extends Command {

	public ShockwaveCommand(){
		super("shockwave", "w", "shock[W]ave" , "UCM-Ship releases a shock wave.");
		
	}

	//Ejecuta el comando
	public boolean execute(Game game) {
		return game.shockWave();
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

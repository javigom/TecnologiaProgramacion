package tp.p2.command;

import tp.p2.game.Game;

public class BuyCommand extends Command {

	public BuyCommand(){
		super("buy", "b", "[B]uy", "Buys a SuperMissile.");
	}
	
	//Ejecuta el comando
	public boolean execute(Game game) {
		return game.buySuper();
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

package tp.p2.command;

import tp.p2.game.Game;

public class ExitCommand extends Command{
	
	public ExitCommand(){
		super("exit", "e", "[E]xit" ,"Terminates the program.");
		
	}

	//Ejecuta el comando
	public boolean execute(Game game) {
		game.exit();
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

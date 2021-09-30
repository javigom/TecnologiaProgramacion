package tp.p2.command;

import tp.p2.game.Game;

public class ListCommand extends Command{

	public ListCommand(){
		super("list", "l", "[L]ist" ,"Prints the list of available ships.");
		
	}
	
	//Ejecuta el comando
	public boolean execute(Game game) {
	//	game.showList();
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
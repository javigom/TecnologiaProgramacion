package tp.p2.command;

import tp.p2.exception.CommandParseException;
import tp.p2.game.Game;

public class ListCommand extends Command{

	public ListCommand(){
		super("list", "l", "[L]ist" ,"Prints the list of available ships.");
		
	}
	
	//Ejecuta el comando
	public boolean execute(Game game) {
		System.out.println("[R]egular ship: Points: 5 - Harm: 0 - Shield: 2");
		System.out.println("[D]estroyer ship: Points: 10 - Harm: 1 - Shield: 1");
		System.out.println("[E]xplosive ship: Points: 5 - Harm: 1 - Shield: 2");
		System.out.println("[O]vni: Points: 25 - Harm: 0 - Shield: 1");
		System.out.println("^__^ - Harm: 1 - Points: 3");
		return false;
	}
	
	//Comprueba que el comando esté bien escrito
	public Command parse(String[] commandWords) throws CommandParseException{
		Command c = null;
		if(matchCommandName(commandWords[0])) {
			if(commandWords.length == 1) {
				c = this;
			}
			else {
				throw new CommandParseException("Usage: [L]ist: "+ incorrectNumArgsMsg);
			}
		}
		return c;
	}
	
}
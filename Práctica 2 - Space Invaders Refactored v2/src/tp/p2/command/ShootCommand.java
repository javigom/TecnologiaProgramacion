package tp.p2.command;

import tp.p2.game.Game;

public class ShootCommand extends Command {
	
	boolean superMissile;
	
	public ShootCommand(){
		super("shoot", "s", "[S]hoot" , "UCM-Ship launches a missile.");
		
	}

	//Ejecuta el comando
	public boolean execute(Game game) {
		return game.shootMissile(this.superMissile);
	}

	//Comprueba que el comando esté bien escrito
	public Command parse(String[] commandWords) {
		Command c = null;
		if(matchCommandName(commandWords[0]) && ((commandWords.length == 2) || commandWords.length == 1)) {
			
			if(commandWords.length == 1) {
				this.superMissile = false;
			}
			
			else {
				if(commandWords[1].equals("super"))
					this.superMissile = true;
			}
			
			c = this;
		}
		
		return c;
	}
}

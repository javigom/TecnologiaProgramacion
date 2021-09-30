package tp.p2.view;

import tp.p2.game.Game;

public class Stringifier extends GamePrinter {

	public String toString(Game game) {
		return game.serialize();	
	}
	
}

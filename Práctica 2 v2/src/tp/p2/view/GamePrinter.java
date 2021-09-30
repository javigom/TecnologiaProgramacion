package tp.p2.view;

import tp.p2.game.Game;

public interface GamePrinter {
	String toString();
	public GamePrinter parse(String name);
	public String helpText();
}
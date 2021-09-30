package tp.p2.game.GameObjects.Lists;

import tp.p2.game.Game;
import tp.p2.game.Level;
import tp.p2.game.GameObjects.DestroyerAlien;
import tp.p2.game.GameObjects.Ovni;
import tp.p2.game.GameObjects.RegularAlien;
import tp.p2.game.GameObjects.Ship;

public class BoardInitializer {

	//Atributos
	
	private Level level;
	private GameObjectBoard board;
	private Game game;
	
	private Ship ship;
	
	//Constructor
	public GameObjectBoard initialize(Game game, Level level) {
		this.level = level;
		this.game = game;
		board = new GameObjectBoard(Game.DIM_COLS, Game.DIM_ROWS);
				
		initializeOvni();
		initializeRegularAliens();
		initializeDestroyerAliens();
		
		return board;	
	}
	
	//Métodos
	
	//Inicializa el ovni
	private void initializeOvni() {
		ship = new Ovni(game);
		board.add(ship);
		board.saveOvni(ship);
	}
	
	//Inicializa las naves comunes
	private void initializeRegularAliens() {
		int initCol = 2, initRow = 1;
		for(int i = 0; i < level.getNumRegular(); i++) {
			ship = new RegularAlien(game, initRow, initCol);
			board.add(ship);
			initCol++;
			
			if(initCol == 6) {
				initCol= 2;
				initRow++;
			}
		}
	}
	
	//Inicializa las naves destructoras
	private void initializeDestroyerAliens(){
		int initCol = 3, initRow = 3;
		
		if(this.level == Level.EASY) {
			initRow--;
		}
		else if(this.level == Level.INSANE) {
			initCol--;
		}
		
		for(int i = 0; i < level.getNumDestroyerAliens(); i++) {
			ship = new DestroyerAlien(game, initRow, initCol);
			board.add(ship);
			initCol++;
		}
	}
}

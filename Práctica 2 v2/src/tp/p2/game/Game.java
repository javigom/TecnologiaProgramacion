package tp.p2.game;

import java.util.Random;
import tp.p2.game.Level;
import tp.p2.game.GameObjects.AlienShip;
import tp.p2.game.GameObjects.Bomb;
import tp.p2.game.GameObjects.GameObject;
import tp.p2.game.GameObjects.IPlayerController;
import tp.p2.game.GameObjects.UCMShip;
import tp.p2.game.GameObjects.Lists.BoardInitializer;
import tp.p2.game.GameObjects.Lists.GameObjectBoard;
import tp.p2.view.BoardPrinter;

public class Game implements IPlayerController {

	
	//Atributos
	
	public static final int DIM_ROWS = 8;
	public static final int DIM_COLS = 9;
	
	private Random rand;
	private Level level;
	private int currentCycle;
	private boolean doExit;
	private boolean shockWave;
	
	private UCMShip player;
	
	GameObjectBoard board;
	private BoardInitializer initializer;
	
	//Constructor
	
	public Game(Level level, Random random) {
		this.rand = random;
		this.level = level;
		initializer = new BoardInitializer();
		initGame();
	}
	
	//Métodos
	
	//Inicializa el juego con los parámetros por defecto
	public void initGame() {
		
		currentCycle = 0;
		board = initializer.initialize(this, level);
		player = new UCMShip(this, DIM_ROWS - 1, DIM_COLS / 2);
		board.add(player);
		
		this.doExit = false;		
		this.shockWave = false;
	}
	
	//Devuelve el random
	public Random getRandom() {
		return this.rand;
	}
	
	//Devuelve el nivel
	public Level getLevel() {
		return this.level;
	}
	
	//Reinicia el juego
	public void reset() {
		initGame();
	}
	
	//Añade un objeto al board
	public void addObject(GameObject object) {
		board.add(object);
	}
	
	//Devuelve el string de la posición r, c
	public String positionToString(int r, int c) {
		return board.toString(r, c);
	}
	
	//Comprueba si el juego ha terminado
	public boolean isFinished() {
		return playerWin() || aliensWin() || doExit;
	}
	
	//Comprueba si ha terminado la partida
	public boolean aliensWin() {
		return !player.isAlive() || AlienShip.haveLanded();
	}
	
	//Comprueba si ha ganado el jugador
	private boolean playerWin() {
		return AlienShip.allDead();
	}
	
	//Actualiza el tablero
	public void update() {
		board.computerAction();
		board.update();
		currentCycle++;
	}
	
	//Devuelve true si las coordenadas r, c se encuentran dentro del tablero
	public boolean isOnBoard(int r, int c) {
		return r >= 0 && c >= 0 && r < DIM_ROWS && c < DIM_COLS;
	}
	
	//Sale del juego
	public void exit() {
		this.doExit = true;
	}
	
	//Imprime el tablero y la información
	public String toString() {
		BoardPrinter print = new BoardPrinter(this, DIM_ROWS, DIM_COLS);

		return "Cycles: " + this.currentCycle + "\n" +
				player.stateToString() + 
				"Remaining aliens: " + (AlienShip.getRemainingAliens()) +
				"ShockWave: " + toStringShockWave() + "\n" +
				print.toString();
	}
	
	//Devuelvee un string con el mensaje de ganador
	public String getWinnerMessage() {
		if(playerWin()) return "Player win!";
		else if(aliensWin()) return "Aliens win!";
		else if(doExit) return "Player exits the game";
		else return "This should not happen";
	}
	
	//Invocado por el comando move para mover al jugador
	public boolean move(int numCells, Direction level) {
		return player.move(numCells, level);
	}

	//Invocado por el comando shoot para disparar el misil
	public boolean shootMissile(boolean superMissile) {
		if (player.shootMissile(superMissile)) {
			return true;
		}
		else {
			return false;
		}
	}

	//Invocado por el comando shockwave para utilizarlo
	public boolean shockWave() {
		boolean executed = false;
		
		if(this.shockWave) {
			
			this.shockWave = false;
			executed = true;
			board.shockWaveAttack();
		}
		
		return executed;
	}
	
	public boolean buySuper() {
		return player.buySuper();
	}
	
	public void changeRegular(GameObject regular) {
		board.changeRegular(this, regular);
	}
	
	public void explosion(int f, int c) {
		board.explosion(f, c);
	}
	
	private String toStringShockWave() {
		if(this.shockWave) {
			return "Yes";
		}
		else {
			return "No";
		}
	}

	//Callback que recibe los puntos que debe sumar al player
	public void receivePoints(int points) {
		this.player.reveivePoints(points);
	}

	//Callback que activa el shockwave cuando eliminas al ovni
	public void enableShockWave() {
		this.shockWave = true;
	}

	//Callback que permite dispara un nuevo misil
	public void enableMissile() {	
		player.enableMissile();
	}
	
	//Elimina la bomba b de la lista de objetos
	public void deleteBomb(Bomb b) {
		board.searchBomb(b);
	}

}
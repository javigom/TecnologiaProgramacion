package tp.p1.game;

import java.util.Random;
import tp.p1.util.*;

public class Game {
	
	//Atributos de game
	
	//Variables constantes
	public static final int numFilas = 8;
	public static final int numColumnas = 9;
	public static final int filaInitUCM = 7;
	public static final int colInitUCM = 4;
	public static final int damageShockwave = 1;
	
	//Contadores
	private int numCiclos;
	private int playerPoints;
	private int numAliens;
	
	//Level y random
	private Level lvl;
	private Random rnd;
	
	//Listas y objetos
	private RegularShipList regularList;
	private DestroyerShipList destroyerList;
	private EnemyBombList bombList;
	private Direction directionShip;
	
	private UCMShip ship;
	private UCMShoot shoot;
	
	private Ovni ovni;
	private boolean shockwave;
	
	
	//Constructor de game
	public Game(Level l, Random r) {
		this.init(l, r);
	}
	
	//Métodos
	
	//Inicializa parámetros de Game
	private void init(Level l, Random r) {
		this.lvl = l;
		this.rnd = r;
		
		this.numCiclos = 0;
		this.playerPoints = 0;
		this.numAliens = 0; //numRegularShip + numDestroyerShip
		
		this.regularList = new RegularShipList(l); 
		this.destroyerList = new DestroyerShipList(l);
		this.bombList = new EnemyBombList(l);
		iniciaListaRegular();
		iniciaListaDestroyer();
		directionShip = Direction.LEFT;
	
		this.ship = new UCMShip(filaInitUCM, colInitUCM);
		this.shoot = null;
		
		this.ovni = null;
		this.shockwave = false;
	}
	
	//Función para reiniciar el juego llamada por controller
	public void reset() {
		init(this.lvl, this.rnd);
	}
	
	//Inicia el array del objeto regularList, lista de las naves enemigas comunes
	public void iniciaListaRegular() {
		int fila = 1;
		int columna = 3;
		for(int i = 0; i < regularList.getMaxShip(); i++) {
			RegularShip rship = new RegularShip(fila, columna);
			regularList.addRegularShip(rship);
			this.numAliens++;
			if(columna == 6 && this.lvl != Level.EASY) {
				columna = 3;
				fila = fila + 1;
			}
			else
				columna++;
		}
	}
	
	//Inicia el array del objeto regularList, lista de las naves enemigas destructoras
	public void iniciaListaDestroyer() {
		int fila, columna;
		if(this.lvl == Level.EASY) {
			fila = 2;
			columna = 4;
		}
		
		else if(this.lvl == Level.HARD) {
			fila = 3;
			columna = 4;
		}
		
		else {
			fila = 3;
			columna = 3;
		}
		
		for(int i = 0; i < destroyerList.getMaxShip(); i++) {
			DestroyerShip dship = new DestroyerShip(fila, columna, this);
			destroyerList.addRegularShip(dship);
			this.numAliens++;
			columna++;
		}
	}
	
	//Dibuja el tablero de game
	public void pintaTablero(Game game) {
		System.out.println("Life: " + this.ship.getStrength());
		System.out.println("Number of cycles: " + this.numCiclos);
		System.out.println("Points: " + this.playerPoints);
		System.out.println("Remaining aliens: " + this.numAliens);
		System.out.print("shockWave: ");
		if(this.shockwave) {
			System.out.println("YES");
		}
		else {
			System.out.println("NO");
		}
		GamePrinter print = new GamePrinter(game, numFilas, numColumnas);
		System.out.println(print.toString());
	}
	
	//Dada una posicion, comprueba si existe un objeto en ella y devuelvo su string
	public String characterAtToString(int i, int j) {
		
		String draw;
		//Compruebo si está la nave del jugador
		if((this.ship.getFila() == i) && (this.ship.getColumna() == j)) {
			draw = ship.toString();
		}
		
		//Compruebo si está alguna nave enemiga común
		else if(!this.regularList.isFree(i, j)) {
			draw = regularList.drawShip(i, j);
		}
		
		//Compruebo si está alguna nave enemiga destructora
		else if(!this.destroyerList.isFree(i, j)) {
			draw = destroyerList.drawShip(i, j);
		}
		
		//Compruebo si está un proyectil del usuario
		else if(this.shoot != null && (this.shoot.getFila() == i) && (this.shoot.getColumna() == j)) {
			draw = shoot.toString();
		}
		
		//Compruebo si está una bomba enemiga
		else if(!this.bombList.isFree(i, j)) {
			draw = this.bombList.drawBomb(i, j);
		}
		
		//Compruebo si está el ovni
		else if(this.ovni != null && this.ovni.getFila() == i && this.ovni.getColumna() == j) {
			draw = this.ovni.toString();
		}
		
		//Si no hay ningún objeto, devuelvo espacio en blanco
		else {
			draw = " ";
		}
		
		return draw;
	}
	
	//Método que mueve la nave UCM en el tablero
	public boolean moveUCMShip(int desplazamiento, Direction dir) {
		boolean moved = true;
		Direction d;
		d = Direction.RIGHT;
		
		if(dir == d) {
			if((ship.getColumna() + desplazamiento) >= numColumnas)
				moved = false;
			else
				ship.avanzar(desplazamiento, dir);
		}
		
		else {
			if((ship.getColumna() - desplazamiento) < 0)
				moved = false;
			else
				ship.avanzar(desplazamiento, dir);
		}
		
		return moved;
	}
	
	//Computer action, comprueba probabilidad de lanzar una bomba y que aparezca el ovni
	public void computerAction() {
		this.destroyerList.throwBomb(this.rnd, this.lvl);
		
		if(this.ovni == null && (this.rnd.nextDouble() < this.lvl.getFrecuencyOvni())) {
			this.ovni = new Ovni();
		}
	}
	
	//Update
	public void update() {
		
		//Avanzace trayectoria misil UCM
		this.avanzaShoot();
		
		//Comprueba si existen colisiones del misil ucm con naves/bombas enemigas
		this.collitionShoot();
		
		//Avanza posiciones de las bombas
		this.bombList.moveBomb();
		
		//Comprueba colisiones de las bombas enemigas con la nave ucm
		this.collitionBomb();
		
		//Si la nave UCM-Ship llega a 0 puntos de vida, se cambia el string
		if(this.ship.getStrength() == 0) {
			this.ship.endString();
		}
		
		//Actualiza puntos del jugador y elimina las naves si llegan a 0 puntos de vida
		this.playerPoints = this.playerPoints + this.regularList.removeRegularShip();
		this.playerPoints = this.playerPoints + this.destroyerList.removeDestroyerShip();
		this.numAliens = this.regularList.getNumShip() + this.destroyerList.getNumShip();
				
		//Elimina las bombas que hayan llegado al final del tablero
		this.destroyerList.updateBomb(Game.numFilas + 1);
		
		//Actualizo posicion de naves enemigas
		this.moveEnemyShip();
		
		//Actualizo posicion del ovni
		if(this.ovni != null) {
			this.ovni.move();
		}
		
		//Si el ovni llega al final del tablero, desaparece
		if(this.ovni != null && this.ovni.limitCol(-1)) {
			this.ovni = null;
		}
		
		//Comprueba si existen colisiones del misil ucm con naves/bombas enemigas
		this.collitionShoot();
		
		//Aumento numero de ciclos
		this.numCiclos++;
	}
	
	//Método que mueve todas las naves enemigas
	public void moveEnemyShip() {
		if((regularList.limitCol(0) || (destroyerList.limitCol(0))) && this.directionShip == Direction.LEFT) {
			this.directionShip = Direction.RIGHT;
			regularList.moveDown();
			destroyerList.moveDown();
		}
		else if((regularList.limitCol(numColumnas-1) || (destroyerList.limitCol(numColumnas-1))) && this.directionShip == Direction.RIGHT) {
			this.directionShip = Direction.LEFT;
			regularList.moveDown();
			destroyerList.moveDown();
		}
		else if (this.numCiclos%this.lvl.getSpeed() == 0) {
			regularList.move(this.directionShip);
			destroyerList.move(this.directionShip);
		}
	}
	
	//Crea un objeto disparo de la nave UCMShip
	public boolean disparoUCM() {
		boolean creaDisparo = false;
		if(this.shoot == null) {
			this.shoot = new UCMShoot(this.ship.getFila(), this.ship.getColumna());
			creaDisparo = true;
		}
		
		return creaDisparo;
	}
	
	//Avanza el disparo del jugador siempre que exista
	public void avanzaShoot() {
		if(this.shoot != null) {
			this.shoot.avanzar();
			
			if(this.shoot.getFila() == -1) {
				this.shoot = null;
			}	
		}		
	}
	
	//Compruebo colisiones del misil del jugador con otros objetos siempre que exista
	public void collitionShoot() {
		
		if(this.shoot != null) {
			
			//Compruebo con las regular
			if(!this.regularList.isFree(this.shoot.getFila(),this.shoot.getColumna())){
				this.regularList.attackShip(this.shoot.getFila(),this.shoot.getColumna(), this.shoot.getDamage());
				this.shoot = null;
			}
			
			//Compruebo con las destroyer
			else if(!this.destroyerList.isFree(this.shoot.getFila(),this.shoot.getColumna())){
				this.destroyerList.attackShip(this.shoot.getFila(),this.shoot.getColumna(), this.shoot.getDamage());
				this.shoot = null;
			}
			
			//Compruebo con las bombas enemigas
			else if(!this.bombList.isFree(this.shoot.getFila(),this.shoot.getColumna())){
				this.deleteBomb(this.shoot.getFila(), this.shoot.getColumna());
				this.shoot = null;	
			}
			
			//Compruebo con el ovni
			else if (this.ovni != null && this.shoot.getFila() == this.ovni.getFila() && this.shoot.getColumna() == this.ovni.getColumna()) {
				this.playerPoints = this.playerPoints + this.ovni.getPuntos();
				this.ovni.attack(this.shoot.getDamage());
				shockwave = true;
				this.shoot = null;
				if(this.ovni.getStrength() == 0)
					this.ovni = null;
			}
		}
	}
	
	//Compruebo colisiones de las bombas enemigas con la nave UCMShip
	public void collitionBomb() {
		if(!this.bombList.isFree(this.ship.getFila(), this.ship.getColumna())) {
			this.ship.attack(this.bombList.getDamage(this.ship.getFila(), this.ship.getColumna()));
			this.deleteBomb(this.ship.getFila(), this.ship.getColumna());
		}
	}
	
	//Inserto una bomba en la lista (generada en el objeto DestroyerShip
	public void insertBombList(EnemyBomb b) {
		this.bombList.addBomb(b);
	}
	
	//Elimino una bomba que esta en las coordenadas x, y
	public void deleteBomb(int x, int y) {
		this.destroyerList.deleteBomb(x, y); //Primero elimino la referencia de la nave Destroyer
		this.bombList.deleteBomb(x, y); //Luego la elimino de la lista
	}
	
	//Utilizo el Shockwave le resto su valor a todas las naves
	public boolean shockwave() {
		boolean sw = false;
		if(this.shockwave) {
			this.regularList.attackAll(damageShockwave);
			this.destroyerList.attackAll(damageShockwave);
			this.shockwave = false;
			sw = true;
			if(this.ovni != null) {
				this.ovni.attack(damageShockwave);
				this.shockwave = true;
				if(this.ovni.getStrength() == 0)
					this.ovni = null;
			}
		}
		return sw;
	}
	
	//Muestra la ayuda
	public void showHelp() {
		System.out.print("[M]ove <left|right><1|2>: Moves UCM-Ship to the indicated direction.\n");
		System.out.print("[S]hoot: UCM-Ship launches a missile.\n");
		System.out.print("shock[W]ave: UCM-Ship releases a shock wave.\n");
		System.out.print("[L]ist: Prints the list of available ships.\n");
		System.out.print("[R]eset: Starts a new game.\n");
		System.out.print("[H]elp: Prints this help message.\n");
		System.out.print("[E]xit: Terminates the program.\n");
		System.out.print("[N]one: Skips one cycle.\n");	
	}
	
	//Muestra información de las naves actuales
	public void showList() {
		System.out.println("[R]egular ship: Points: 5 - Harm: 0 - Shield: 2");
		System.out.println("[D]estroyer ship: Points: 10 - Harm: 1 - Shield: 1");
		System.out.println("[O]vni: Points: 25 - Harm: 0 - Shield: 1");
		System.out.println(this.ship.getInfo());
	}
	
	//Devuelve true si han ganado los aliens
	public boolean winAliens() {
		boolean win = false;
		
		if(this.ship.getStrength() ==  0 || this.regularList.limitFila(numFilas - 1) || this.destroyerList.limitFila(numFilas - 1))
			win = true;
			
		return win;
	}
	
	//Devuelve true si ha ganado el jugador
	public boolean winPlayer() {
		boolean win = false;
	
		if(this.numAliens == 0)
			win = true;
		
		return win;
	}
}

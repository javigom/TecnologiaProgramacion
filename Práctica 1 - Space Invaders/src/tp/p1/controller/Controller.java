package tp.p1.controller;

import java.util.Scanner;

import tp.p1.game.*;
import tp.p1.game.Direction;

public class Controller {

	//Atributos
	
	//Game y Scanner
	private Game game;
	private Scanner in;
	
	//Variables booleanas para los bucles
	private boolean end;
	private boolean error;
	private boolean move;
	private boolean errorArgs;
	private boolean pause;
	private boolean reset;
	
	//Constructor
	public Controller(Game g, Scanner sc) {
		this.game = g;
		this.in = sc;
		this.end = false;
	}
	
	//Método único que ejecuta el juego
	public void run() {
		
		do {
			// 1. Draw: Pinta tablero y muestra información del juego	
			game.pintaTablero(game);
		
			// 2. User command
			do {
				this.error = false; //True si el comando está mal escrito
				this.move = true; //False si no se puede llevar a cabo una accion (move, shoot, etc.)
				this.errorArgs = false; //True si el comando está bien escrito pero los argumentos no
				this.pause = false; //True si se ejecuta un comando que no cuenta para el juego (lista, help)
				this.reset = false; //True si se ejecuta el comando reset
				
				//Lectura del comando y procesado del mismo
				System.out.print("Command > ");
				String command = in.nextLine();
				command = command.toLowerCase(); //minúsculas
				String[] c = command.split(" ");
				int num;
			
				//MOVE
				if(((c[0].equals("move")) || ((c[0].contentEquals("m")) && (c[0].length() == 1)))){
					if(c.length == 3) {	
						num = Integer.parseInt(c[2]);
						if((c[1].equals("right"))){
		
							if(num == 1)
								move = game.moveUCMShip(1, Direction.RIGHT);
							else if(num == 2)
								move = game.moveUCMShip(2, Direction.RIGHT);
							else
								error = true;
						}
						else if(c[1].equals("left")) {
							
							if(c[2].equals("1"))
								move = game.moveUCMShip(1, Direction.LEFT);
							else if(c[2].equals("2"))
								move = game.moveUCMShip(2, Direction.LEFT);
							else
								error = true;
						}
						else
							error = true;
					}
					else {
						System.out.print("Incorrect arguments in the command!\n");
						errorArgs = true;
					}
				}
				
				//SHOOT
				else if(c.length == 1 && (c[0].equals("shoot") || ((c[0].contentEquals("s")) && (c[0].length() == 1))))
				{
					if(!game.disparoUCM())
						move = false;		
				}
				
				//SHOCKWAVE
				else if(c.length == 1 && (c[0].equals("shockwave") || ((c[0].contentEquals("w")) && (c[0].length() == 1)))) {
					if(!game.shockwave()) {
						move = false;
					}
				}
				
				//LIST
				else if(c.length == 1 && (c[0].equals("list") || ((c[0].contentEquals("l")) && (c[0].length() == 1)))) {
					game.showList();
					this.pause = true;
				}
				
				//RESET
				else if(c.length == 1 && (c[0].equals("reset") || ((c[0].contentEquals("r")) && (c[0].length() == 1)))) {
					game.reset();
					this.reset = true;
				}
					
				//HELP
				else if(c.length == 1 && (c[0].equals("help") || ((c[0].contentEquals("h")) && (c[0].length() == 1)))) {
					game.showHelp();
					this.pause = true;
				}
				
				//EXIT
				else if(c.length == 1 && (c[0].equals("exit") || ((c[0].contentEquals("e")) && (c[0].length() == 1)))) {
					this.end = true;
					System.out.println("You have left the game. See you later!");
				}
				
				//NONE
				else if(c.length == 1 && ((c[0].equals("none") || (c[0].contentEquals("n")) && (c[0].length() == 1)) || c[0].contentEquals(""))) {
					//No hago nada
				}
				
				else {
					this.error = true;
				}
					
				//Mensajes en caso de error
				if(this.error)
					System.out.println("Wrong command!");
				
				if(!this.move)
					System.out.println("This action cannot be performed!");
			
			} while(this.error || !this.move || this.errorArgs || this.pause);
			
			//Si se ha reiniciado el juego, no ejecuto las siguientes acciones
			if(!this.reset) {
				// 3. Computer Action
				game.computerAction();
				
				// 4. Update
				game.update();
				
				//Comeprueba si ha acabado el juego, muestra el tablero final y el ganador
				if(game.winAliens() || game.winPlayer()) {
					game.pintaTablero(game);
					this.end = true;
					if(game.winAliens())
						System.out.println("Try again! Aliens win!");
					else if(game.winPlayer()) 
						System.out.println("Good Job! Player win!");
				}
			}
			
		} while(!this.end);
	}
}
package tp.p2.controller;

import java.util.Scanner;

import tp.p2.command.Command;
import tp.p2.command.CommandGenerator;
import tp.p2.game.Game;

public class Controller {
	
	//Atributos
	private Game game;
	private Scanner in;
	private static final String unknownCommandMsg = "Wrong command!\n";
	private static final String PROMPT = "Command > ";
	
	//Constructor
	public Controller(Game g, Scanner sc) {
		this.game = g;
		this.in = sc;
	}	
	
	//Método único que ejecuta el juego
	public void run() {
		System.out.println(game);
		
		while(!game.isFinished()){
				
			System.out.print(PROMPT);
			String[]  words = in.nextLine().toLowerCase().trim().split ("\\s+");
			//try{
				Command command = CommandGenerator.parseCommand(words);
				if(command != null) {
					if(command.execute(game)) {
						
						if(!command.getName().contentEquals("reset")) {
							game.update();
						}	
						System.out.println(game);
					}
				}
			
				else{
					System.out.format(unknownCommandMsg);
				}
			//}
		}
		
		System.out.println(game.getWinnerMessage());
	}
}

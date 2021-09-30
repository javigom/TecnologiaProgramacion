package tp.p2;

import java.util.Random;
import java.util.Scanner;

import tp.p2.controller.Controller;
import tp.p2.game.*;

public class Main {

	public static void main(String[] args) throws Exception {
		
		//Inicializacion variables
		int seed;
		boolean ok = true;
		
		Level lvl = Level.EASY;
		Random rnd = new Random(System.currentTimeMillis());
		
		//Comprueba que el argumento level esté bien escrito
		
		if(args.length == 0 || args.length > 2) {
			System.err.println("Usage: Main <EASY|HARD|INSANE> [seed]\n"); 
		}
		
		else {
			
			if(args[0].equalsIgnoreCase("easy"))
				lvl = Level.EASY;
			else if(args[0].equalsIgnoreCase("hard"))
				lvl = Level.HARD;
			else if(args[0].equalsIgnoreCase("insane"))
				lvl = Level.INSANE;
			else {
				ok = false;
				System.err.println("Usage: Main <EASY|HARD|INSANE> [seed]: level must be one of: EASY, HARD, INSANE\n");
			}
			
			//Si tiene dos argumentos, entonces existe una semilla
			if(args.length==2) {
				try {
					
				seed = Integer.parseInt(args[1]);
				rnd = new Random(seed);
				
				}catch(NumberFormatException ex){
					System.err.println("Usage: Main <EASY|HARD|INSANE> [seed]: Invalid seed argument value, using random seed\n");
				}
			}
			
			//Si el nivel es correcto, inicia el juego
			if(ok) {
				Game game = new Game (lvl, rnd);
				Scanner sc = new Scanner(System.in);
				Controller c = new Controller(game, sc);
				c.run();
			}
		}	 
	}
}

package tp.p2;

import java.util.Random;
import java.util.Scanner;

import tp.p2.controller.Controller;
import tp.p2.game.*;

public class Main {

	public static void main(String[] args) {
		
		//Inicializacion variables
		int seed;
		boolean ok = true;
		
		Level lvl = Level.EASY;
		Random rnd;
		
		//Comprueba que el argumento level esté bien escrito
		if(args[0].equalsIgnoreCase("easy"))
			lvl = Level.EASY;
		else if(args[0].equalsIgnoreCase("hard"))
			lvl = Level.HARD;
		else if(args[0].equalsIgnoreCase("insane"))
			lvl = Level.INSANE;
		else
			ok = false;
		
		//Si tiene dos argumentos, entonces existe una semilla
		if(args.length==2) {
			seed = Integer.parseInt(args[1]);
			rnd = new Random(seed);
		}
		else {
			rnd = new Random(System.currentTimeMillis());
		}
		
		//Si el nivel es correcto, inicia el juego
		if(ok) {
			Game game = new Game (lvl, rnd);
			Scanner sc = new Scanner(System.in);
			Controller c = new Controller(game, sc);
			c.run();
		}
		else 
			System.out.println("An error occurred while reading the input arguments");  
	}
}

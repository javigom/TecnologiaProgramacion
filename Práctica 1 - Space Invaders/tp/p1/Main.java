package tp.p1;

import java.util.Random;
import java.util.Scanner;

import tp.p1.controller.Controller;
import tp.p1.game.*;
import tp.p1.utils.UCMShip;

public class Main {
	
	
		
	public static void main(String[] args) {
		/*
		Level lvl = Level.EASY;
		Random rnd = new Random();
		Game game = new Game (lvl, rnd, 7, 4);
		Scanner sc = new Scanner(System.in);
		Controller c = new Controller(game, sc);
		c.run();
		*/
		UCMShip ucm = new UCMShip(7, 4, 3, 1);
		ucm.toString();
	}

}

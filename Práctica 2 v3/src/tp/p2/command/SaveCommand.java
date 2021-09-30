package tp.p2.command;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import tp.p2.exception.CommandExecuteException;
import tp.p2.exception.CommandParseException;
import tp.p2.game.Game;
import tp.p2.view.GamePrinter;
import tp.p2.view.Stringifier;

public class SaveCommand extends Command{

	private String fileName;
	
	public SaveCommand() {
		super("save", "v", "sa[V]e" ,"Save the game into a file.");
	}

	
	public boolean execute(Game game) throws CommandExecuteException {
		try (FileWriter fw = new FileWriter(fileName)) {
			
			BufferedWriter bw = new BufferedWriter(fw);
			
			GamePrinter printer = new Stringifier();
			
			bw.write(printer.toString(game));
			
			bw.close();
			
			System.out.println("Game successfully saved in file " + fileName + ". Use the load command to reload it");
			
		}catch(IOException ex){
			throw new CommandExecuteException("An unexpected error ocurred creating/opening file.");
		}
		return false;
	}

	public Command parse(String[] commandWords) throws CommandParseException {
		Command c = null;
		if(matchCommandName(commandWords[0])) {
			if(commandWords.length == 2) {
				fileName = commandWords[1] + ".dat";
				c = this;
			}
			else {
				throw new CommandParseException("Usage: sa[V]e: "+ incorrectNumArgsMsg);
			}
		}
		return c;
	}

}

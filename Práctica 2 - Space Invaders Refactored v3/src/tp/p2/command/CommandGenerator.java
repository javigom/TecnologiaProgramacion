package tp.p2.command;

import tp.p2.exception.CommandParseException;

public class CommandGenerator {

	private static Command[] availableCommands = {
		
			new ListCommand(),
			new HelpCommand(),
			new ResetCommand(),
			new ExitCommand(),
			new UpdateCommand(),
			new MoveCommand(),
			new ShockwaveCommand(),
			new ShootCommand(),
			new BuyCommand(),
			new SaveCommand(),
			new StringifyCommand()
	};
	
	public static Command parseCommand(String[] commandWords) throws CommandParseException {
		Command c = null;
		int i = 0;
		boolean fount = false;
		while(!fount && i < availableCommands.length) {
			if(availableCommands[i].parse(commandWords) != null) {
				fount = true;
				c = availableCommands[i].parse(commandWords);
			}
			i++;
		}
		return c;
	}
	
	public static String commandHelp() {
		String help = "";
		for(int i = 0; i < availableCommands.length; i++) {
			help = help + availableCommands[i].helpText();
		}
		return help;
	}
}

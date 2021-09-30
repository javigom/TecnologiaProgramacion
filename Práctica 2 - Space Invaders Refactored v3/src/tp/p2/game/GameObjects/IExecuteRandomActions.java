package tp.p2.game.GameObjects;

import tp.p2.game.Game;

public interface IExecuteRandomActions {

	static boolean canGenerateRandomOvni(Game game) {
		return game.getRandom().nextDouble() < game.getLevel().getOvniFrecuency();
	}
	
	static boolean canGenerateRandomBomb(Game game) {
		return game.getRandom().nextDouble() < game.getLevel().getShootFrecuency();
	}
	
	static boolean canGenerateRandomExplosive(Game game) {
		return game.getRandom().nextDouble() < 0.05;
	}
}
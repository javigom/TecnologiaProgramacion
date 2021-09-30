package tp.p2.game.GameObjects;

public interface IAttack {
	
	default boolean performAttack(GameObject other) {
		return false;
	}
	
	default boolean receiveMissileAttack(int damage) {
		return false;
	}
	
	default boolean receiveBombAttack(int damage) {
		return false;
	}
	
	default boolean receiveShockWave(int damage) {
		return false;
	}
	
	default boolean receiveExplosive(int damage) {
		return false;
	}
}

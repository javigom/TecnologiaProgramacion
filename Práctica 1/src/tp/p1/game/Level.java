package tp.p1.game;

public enum Level {
	EASY (3, 4, 2, 0.1, 0.5), HARD (2, 8, 2, 0.3, 0.2), INSANE (1, 8, 4, 0.5, 0.1);
	
	//Atributos
	private int speed;
	private int numRegular;
	private int numDestroyer;
	private double frecuency;
	private double frecuencyOvni;
	
	//Constructor de Level
	Level(int s, int nRegular, int nDestroyer, double f, double o) {
		this.speed = s;
		this.numRegular = nRegular;
		this.numDestroyer = nDestroyer;
		this.frecuency = f;
		this.frecuencyOvni = o;
	}
	
	//Métodos
	
	//Devuelve el numero de naves regular
	public int getNumRegular(){
		return this.numRegular;
	}
	
	//Devuelve el numero de naves destroyer
	public int getNumDestroyer() {
		return this.numDestroyer;
	}
	
	//Devuelve la velocidad/numero de ciclos
	public int getSpeed() {
		return this.speed;
	}
	
	//Devuelve la frecuencia de disparo
	public double getFrecuency() {
		return this.frecuency;
	}
	
	//Devuelve la frecuencia de aparicion del ovni
	public double getFrecuencyOvni() {
		return this.frecuencyOvni;
	}
}


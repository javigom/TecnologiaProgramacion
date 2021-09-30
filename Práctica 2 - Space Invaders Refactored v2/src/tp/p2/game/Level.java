package tp.p2.game;

public enum Level {
	EASY (3, 4, 2, 0.1, 0.5, 1),
	HARD (2, 8, 2, 0.3, 0.2, 2), 
	INSANE (1, 8, 4, 0.5, 0.1, 3);
	
	//Atributos
	private int speed;
	private int numRegular;
	private int numDestroyer;
	private double shootFrecuency;
	private double ovniFrecuency;
	
	private int numRowsOfRegularAliens;
	
	//Constructor de Level
	Level(int s, int nRegular, int nDestroyer, double f, double o, int n) {
		this.speed = s;
		this.numRegular = nRegular;
		this.numDestroyer = nDestroyer;
		this.shootFrecuency = f;
		this.ovniFrecuency = o;
		
		this.numRowsOfRegularAliens = n;
	}
	
	//Métodos
	
	//Devuelve el numero de naves regular
	public int getNumRegular(){
		return this.numRegular;
	}
	
	//Devuelve el numero de naves destroyer
	public int getNumDestroyerAliens() {
		return this.numDestroyer;
	}
	
	//Devuelve la velocidad/numero de ciclos
	public int getSpeed() {
		return this.speed;
	}
	
	//Devuelve la frecuencia de disparo
	public double getShootFrecuency() {
		return this.shootFrecuency;
	}
	
	//Devuelve la frecuencia de aparicion del ovni
	public double getOvniFrecuency() {
		return this.ovniFrecuency;
	}
	
	public int getNumRowsOfRegularAliens() {
		return this.numRowsOfRegularAliens;
	}
	
	public int getNumRegularAliensPerRow() {
		return this.numRegular / this.numRowsOfRegularAliens;
	}
	
	public int getNumDestroyerAliensPerRow() {
		return getNumDestroyerAliens();
	}
	
	public static Level fromParam(String param) {
		for(Level level : Level.values())
			if(level.name().equalsIgnoreCase(param)) return level;
		return EASY;
	}
	
	public Double getTurnExplodeFreq() {
		return 0.05;
	}
	
}

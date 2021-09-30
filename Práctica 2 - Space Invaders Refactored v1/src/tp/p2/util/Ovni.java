package tp.p2.util;

public class Ovni {

	//Atributos
	private int fila;
	private int columna;
	private int strength;
	private static final int INITIALSTRENGHT = 1;
	private static final int POINTS = 25;
	
	//Constructores
	public Ovni(){
		setPosicion(0, 9);
		this.strength = INITIALSTRENGHT; 
	}
	
	//Métodos
	
	//Establece las coordenadas del ovni
	private void setPosicion(int f, int c){
		this.fila = f;
		this.columna = c;
	}
	
	//Devuelve la vida/resistencia del ovni
	public int getStrength() {
		return this.strength;
	}
	
	//Devuelve la fila
	public int getFila() {
		return this.fila;
	}
	
	//Devuelve la columna
	public int getColumna() {
		return this.columna;
	}
	
	//Devuelve los puntos
	public int getPuntos() {
		return POINTS;
	}
	
	//Devuelve el string del ovni
	public String toString() {
		return "O[" + this.strength + "]";
	}
	
	//Desplaza al ovni una posicion a la izquierda
	public void move() {
		this.setPosicion(this.fila, this.columna - 1);
	}
	
	//Devuelve true si se encuentra en la columna c
	public boolean limitCol(int c) {
		boolean limite = false;
		if(this.columna == c) {
			limite = true;
		}
		return limite;
	}
	
	//Le resta a la resistencia el valor de damage
	public void attack(int damage) {
		this.strength -= damage;
	}
}

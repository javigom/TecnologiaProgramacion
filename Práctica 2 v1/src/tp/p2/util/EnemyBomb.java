package tp.p2.util;

public class EnemyBomb {

	//Atributos
	private int fila;
	private int columna;
	private static final int DAMAGE = 1;
	
	//Constructor
	public EnemyBomb(int f, int c) {
		setPosition(f, c);
	}
	
	//Métodos
	
	//Establece la posición de la bomba
	private void setPosition(int f, int c) {
		this.fila = f;
		this.columna = c;
	}
	
	//Avanza una posicion hacia abajo
	public void avanzar() {
		setPosition(this.fila +1, this.columna);
	}
	
	//Devuelve la fila
	public int getFila() {
		return fila;
	}
	
	//Devuelve la columna
	public int getColumna() {
		return columna;
	}
	
	//Devuelve el string de la bomba
	public String toString() {
		return ".";
	}
	
	//Devuelve el daño que hace la nave
	public int getDamage() {
		return DAMAGE;
	}

}

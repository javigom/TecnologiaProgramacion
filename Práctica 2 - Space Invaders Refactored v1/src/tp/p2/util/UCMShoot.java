package tp.p2.util;

public class UCMShoot {
	
	//Atributos
	private int fila;
	private int columna;
	public static final int DAMAGE = 1;
	
	//Constructor
	public UCMShoot(int f, int c) {
		setPosition(f, c);
	}
	
	//Métodos
	
	//Establece las coordenadas del disparo
	private void setPosition(int f, int c) {
		this.fila = f;
		this.columna = c;
	}
	
	//Avanza el disparo una posicion hacia arriba
	public void avanzar() {
		setPosition(this.fila - 1, this.columna);
	}
	
	//Devuelve la fila
	public int getFila() {
		return fila;
	}
	
	//Devuelve la columna
	public int getColumna() {
		return columna;
	}
	
	//Devuelve el string del disparo
	public String toString() {
		return "ºº";
	}
	
	//Devuelve el daño que hace el misil
	public int getDamage() {
		return DAMAGE;
	}
}
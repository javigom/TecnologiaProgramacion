package tp.p1.utils;

public class RegularShip {

	//Atributos
	private int fila;
	private int columna;
	private int resistencia;
	private int puntos;
	
	//Constructores
	public RegularShip(int f, int c, int r){
		setPosicion(f, c);
		this.resistencia = r;
		this.puntos = 5;
	}
	
	//Métodos
	public void setPosicion(int f, int c){
		this.fila = f;
		this.columna = c;
	}
	
	public void setResistencia(int r){
		this.resistencia = r;
	}
	
	public void setPuntos(int p) {
		this.puntos=p;
	}
	
	public int getResistencia() {
		return resistencia;
	}
	
	public int getFila() {
		return fila;
	}
	
	public int getColumna() {
		return columna;
	}
	
	public int getPuntos() {
		return puntos;
	}
	
	public void attack() {
		this.resistencia--;
	}

}

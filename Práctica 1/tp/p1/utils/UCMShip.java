package tp.p1.utils;

public class UCMShip {

	//Atributos
	private int fila;
	private int columna;
	private int resistencia;
	private int damage;
	private String draw;
	
	//Constructores
	public UCMShip(int f, int c, int r, int d){
		setPosicion(f, c);
		this.resistencia = r;
		this.damage = d;
		this.draw = "^__^";
	}
	
	//Métodos
	public void setPosicion(int f, int c){
		this.fila = f;
		this.columna = c;
	}
	
	public void setResistencia(int r){
		this.resistencia = r;
	}
	
	public void setDamage(int d) {
		this.damage = d;
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
	
	public int getDamage() {
		return damage;
	}
	
	public String toString() {
		return draw;
	}
}

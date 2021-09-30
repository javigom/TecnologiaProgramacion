package tp.p1.utils;

public class DestroyerShip {

	//Atributos
		private int fila;
		private int columna;
		private int resistencia;
		private int damage;
		private int puntos;
		
		//Constructores
		public UCMShip(int f, int c, int r, int d){
			setPosicion(f, c);
			this.resistencia = r;
			this.damage = d;
			this.puntos = 10;
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
		
		public void setPuntos(int p) {
			this.puntos = p;
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
		
		public int getPuntos() {
			return puntos;
		}

}

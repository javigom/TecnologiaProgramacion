package tp.p1.utils;

import tp.p1.utils.*;
public class RegularShipList {
	
	private RegularShip regularList[];
	private int regularCounter;
	private int maxShip;
	
	public RegularShipList() {
		this(56);
	}
	
	public RegularShipList(int initialShip) {
		this.maxShip = initialShip;
		regularList = new RegularShip[maxShip];
		this.regularCounter = 0;
	}
	
	public void addRegularShip(RegularShip ship) {
		regularList[regularCounter] = ship;
		regularCounter++;
		
		//if(regularCounter == (maxShip - 1))
	}
	
	public void attackShip(int f, int c) {
		boolean encontrado = false;
		int i = 0;
	
		while(!encontrado && i < regularCounter) {
			if((regularList[i].getFila() == f) && (regularList[i].getColumna() == c)) {
				regularList[i].attack();
				encontrado = true;
			}
			
			i++;
		}
	}
	
	public void removeRegularShip(){
		
		for (int i = 0; i < regularCounter; i++) {
			if(regularList[i].getResistencia() == 0) {
				regularList[i] = regularList[regularCounter - 1];
				regularList[regularCounter - 1] = null;
				regularCounter--;
			}
		}
	}
			
	public boolean isFree(int f, int c) {
		boolean free = true;
		int i = 0;
		
		while(free && i < regularCounter) {
			if((regularList[i].getFila() == f) && (regularList[i].getColumna() == c)) {
				free = false;
			}
			i++;
		}
		
		return free;
	}
	
	
	
	
	
	
	
	
	

}

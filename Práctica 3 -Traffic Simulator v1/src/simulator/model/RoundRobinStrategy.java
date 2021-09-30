package simulator.model;

import java.util.List;

public class RoundRobinStrategy implements LightSwitchingStrategy {

	//	ATRIBUTOS
	
	private int timeSlot;

	//	CONSTRUCTOR
	
	public RoundRobinStrategy(int timeSlot) {
		this.timeSlot = timeSlot;
	}
	
	//	MÉTODOS
	
	//Elige el siguiente cruce que se pondrá en verde
	public int chooseNextGreen(List<Road> roads, List<List<Vehicle>> qs, int currGreen, int lastSwitchingTime, int currTime) {

		int nextGreen = currGreen;
		
		//Si el cruce no tiene carreteras entrantes
		if(roads.size() == 0) {
			nextGreen = -1;
		}
		
		//Si no hay ninguno en verde
		else if(currGreen == -1) {
			nextGreen = 0;
		}
		
		//Si aún no ha transcurrido el periodo timeSlot
		else if(currTime - lastSwitchingTime < timeSlot) {
			nextGreen = currGreen;
		}
		
		//En otro caso, elijo el siguiente
		else {
			nextGreen = (currGreen + 1) % roads.size();
		}
		
		return nextGreen;
	}
}
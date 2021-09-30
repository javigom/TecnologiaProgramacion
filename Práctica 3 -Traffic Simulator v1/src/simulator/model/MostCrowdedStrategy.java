package simulator.model;

import java.util.Iterator;
import java.util.List;

public class MostCrowdedStrategy implements LightSwitchingStrategy {

	//	ATRIBUTOS
	
	private int timeSlot;
	
	//	CONSTRUCTOR
	
	public MostCrowdedStrategy(int timeSlot) {
		this.timeSlot = timeSlot;
	}
	
	//	MÉTODOS
	
	//Elige la siguiente carretera que se pondrá en verde
	public int chooseNextGreen(List<Road> roads, List<List<Vehicle>> qs, int currGreen, int lastSwitchingTime, int currTime) {
		
		//Inicializo el siguiente al actual
		int nextGreen = currGreen;
		
		//Si no tiene carreteras
		if(roads.size() == 0) {
			nextGreen = -1;
		}
		
		//Si actualmente ninguno está en verde
		else if (currGreen == -1){
			int i = 0, longList = 0;
			Iterator<List<Vehicle>> it = qs.iterator();
			
			while(it.hasNext()) {
				
				List<Vehicle> lAux = it.next();
				if(lAux.size() > longList) {
					nextGreen = i;
					longList = lAux.size();
				}
				
				i++;
			}
		}
		
		//Si el tiempo transcurrido es menor que el timeSlot, dejo el semáforo que estaba
		else if(currTime - lastSwitchingTime < timeSlot) {
			nextGreen = currGreen;
		}
		
		//En otro caso, pongo en verde al siguiente de la lista
		else {
			int i = currGreen + 1, longList = 0;
			nextGreen = currGreen;
			
			while(i != currGreen) {
				
				if(longList < qs.get(i).size()) {
					longList = qs.get(i).size();
					nextGreen = i;
				}
				
				i = (i + 1)%roads.size();
			}
			
		}
		
		return nextGreen;
	}
}

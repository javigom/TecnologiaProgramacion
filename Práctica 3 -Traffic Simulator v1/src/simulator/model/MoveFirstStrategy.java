package simulator.model;

import java.util.ArrayList;
import java.util.List;

public class MoveFirstStrategy implements DequeuingStrategy {

	//	CONSTRUCTOR
	
	public MoveFirstStrategy() {
		
	}
	
	//	MÉTODOS
	
	//Devuelve una lista con el primer vehículo de la lista
	public List<Vehicle> dequeue(List<Vehicle> q){
		
		List<Vehicle> list = new ArrayList<Vehicle>();
		if(q.size() > 0) {
			list.add(q.get(0));
		}
		
		return list;
	}
}

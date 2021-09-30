package simulator.model;

import java.util.Iterator;
import java.util.List;

import simulator.misc.Pair;

public class SetContClassEvent extends Event {
	
	//	ATRIBUTOS
	
	private List <Pair<String, Integer>> cs;
	
	//	CONSTRUCTOR
	
	public SetContClassEvent(int time, List <Pair<String, Integer>> cs) {
		super(time);
		if(cs != null) {
			this.cs = cs;
		}
		
		else {
			throw new IllegalArgumentException("La lista no puede ser nula.");
		}	
	}

	//	MÉTODOS
	
	protected void execute(RoadMap map) {
		Iterator <Pair<String, Integer>> it = cs.iterator();
		while(it.hasNext()) {
			Pair<String, Integer> w = it.next();
			Vehicle v = map.getVehicle(w.getFirst());
			
			if(v != null) {
				v.setContaminationClass(w.getSecond());
			}
			
			else {
				throw new IllegalArgumentException("Vehículo no encontrado.");
			}
			
		}
	}
		
	public String toString() {
		String s = "Change CO2 Class: [";
		 
		Iterator <Pair<String, Integer>> it = cs.iterator();
		while(it.hasNext()) {
			Pair<String, Integer> w = it.next();
			s = s  + "(" + w.getFirst() + ", " + w.getSecond() + ")";
			
			if(it.hasNext()) {
				s = s + ", ";
			}
		}
		
		s = s + "]";
		
		return s;
	}

}

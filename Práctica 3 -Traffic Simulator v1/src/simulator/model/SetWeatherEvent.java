package simulator.model;

import java.util.Iterator;
import java.util.List;

import simulator.misc.Pair;

public class SetWeatherEvent extends Event {

	//	ATRIBUTOS
	
	private List<Pair<String, Weather>> ws;
	
	//	CONSTRUCTOR
	
	public SetWeatherEvent(int time, List<Pair<String, Weather>> ws) {
		super(time);
		if(ws != null) {
			this.ws = ws;
		}
		
		else {
			throw new IllegalArgumentException("La lista no puede ser nula.");
		}
	}

	//MÉTODOS
	
	protected void execute(RoadMap map) {
		Iterator <Pair<String, Weather>> it = ws.iterator();
		while(it.hasNext()) {
			Pair<String, Weather> w = it.next();
			Road r = map.getRoad((String) w.getFirst());
			
			if(r != null) {
				r.setWeather((Weather) w.getSecond());
			}
			
			else {
				throw new IllegalArgumentException("Carretera no encontrada.");
			}
		}
	}
}

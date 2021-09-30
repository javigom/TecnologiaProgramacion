package simulator.model;

import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

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
	
	public String toString() {
		String s = "Change Weather: [";
		 
		Iterator<Pair<String, Weather>> it = ws.iterator();
		while(it.hasNext()) {
			Pair<String, Weather> w = it.next();
			s = s  + "(" + w.getFirst() + ", " + w.getSecond() + ")";
			
			if(it.hasNext()) {
				s = s + ", ";
			}
		}
		
		s = s + "]";
		
		return s;
	}

	@Override
	protected JSONObject saveStatus() {
		JSONObject info = new JSONObject();
		info.put("type", "set_weather");
		
		JSONObject data = new JSONObject();
		data.put("time", this.getTime());
		
		JSONArray array = new JSONArray();
		for(Pair<String, Weather> p: this.ws) {
			JSONObject json = new JSONObject();
			json.put("road", p.getFirst());
			json.put("weather", p.getSecond());
			array.put(json);
		}
		
		data.put("info", array);
		info.put("data", data);
		
		return info;
	}
}

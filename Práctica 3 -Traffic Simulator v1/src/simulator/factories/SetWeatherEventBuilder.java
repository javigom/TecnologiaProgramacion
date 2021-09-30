package simulator.factories;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.misc.Pair;
import simulator.model.Event;
import simulator.model.SetWeatherEvent;
import simulator.model.Weather;

public class SetWeatherEventBuilder extends Builder<Event>{

	//	CONSTRUCTOR
	
	public SetWeatherEventBuilder(String type) {
		super(type);
	}

	//	MÉTODOS
	
	//Devuelve el evento que establece el tiempo de una carretera
	protected Event createTheInstance(JSONObject data) {
		
		if(!data.has("time") || !data.has("info")) {
			throw new IllegalArgumentException("Valores no encontrados en el JSON");
		}
		
		int time = data.getInt("time");
		JSONArray jsonList = data.getJSONArray("info");
		List<Pair<String, Weather>> ws = new ArrayList<>();
		
		for(int i = 0; i < jsonList.length(); i++) {
			
			JSONObject o = jsonList.getJSONObject(i);
			String road = o.getString("road");
			Weather weather =  Weather.valueOf(o.getString("weather"));
			ws.add(new Pair<String, Weather>(road, weather));
		} 
		
		return new SetWeatherEvent(time, ws);
	}
}

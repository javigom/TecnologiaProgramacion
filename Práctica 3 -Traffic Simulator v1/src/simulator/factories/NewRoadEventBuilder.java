package simulator.factories;

import org.json.JSONObject;

import simulator.model.Event;
import simulator.model.Weather;

public abstract class NewRoadEventBuilder extends Builder<Event> {

	//	ATRIBUTOS
	
	protected int time;
	protected String id;
	protected String src;
	protected String dest;
	protected int lenght;
	protected int co2limit;
	protected int maxSpeed;
	protected Weather weather;
	
	//	CONSTRUCTOR
	
	NewRoadEventBuilder(String type) {
		super(type);
	}

	//	MÉTODOS
	
	//Devuelve el evento de la carretera
	protected Event createTheInstance(JSONObject data) {
		
		if(!data.has("time") || !data.has("id") || !data.has("src") || !data.has("dest") || !data.has("dest") 
				|| !data.has("length") || !data.has("co2limit") || !data.has("maxspeed") || !data.has("weather")) {
			
			throw new IllegalArgumentException("Valores no encontrados en el JSON");
		}
	
		this.time = data.getInt("time");
		this.id = data.getString("id");
		this.src = data.getString("src");
		this.dest = data.getString("dest");
		this.lenght = data.getInt("length");
		this.co2limit = data.getInt("co2limit");
		this. maxSpeed = data.getInt("maxspeed");		
		this.weather = Weather.valueOf(data.getString("weather"));
		
		return createTheRoad();
	}
	
	protected abstract Event createTheRoad();
}

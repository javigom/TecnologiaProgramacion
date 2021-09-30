package simulator.factories;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.model.Event;
import simulator.model.NewVehicleEvent;

public class NewVehicleEventBuilder extends Builder<Event> {

	//	CONSTRUCTOR
	
	public NewVehicleEventBuilder(String type) {
		super(type);
	}

	//	MÉTODOS
	
	//Devuelve el evento del vehiculo
	protected Event createTheInstance(JSONObject data) {
		
		if(!data.has("time") || !data.has("id") || !data.has("maxspeed") || !data.has("class") || !data.has("itinerary")) {
			throw new IllegalArgumentException("Valores no encontrados en el JSON");
		}
		
		int time = data.getInt("time");
		String id = data.getString("id");
		int maxSpeed = data.getInt("maxspeed");
		int contClass = data.getInt("class"); 
		List <String> itinerary = new ArrayList<>();
		JSONArray itineraryArray = data.getJSONArray("itinerary");
		
		for(int i = 0; i < itineraryArray.length(); i++) {
			itinerary.add(itineraryArray.getString(i));
		}
		
		return new NewVehicleEvent(time, id, maxSpeed, contClass, itinerary);
	}

}
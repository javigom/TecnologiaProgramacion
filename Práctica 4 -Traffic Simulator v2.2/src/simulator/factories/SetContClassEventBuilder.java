package simulator.factories;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.misc.Pair;
import simulator.model.Event;
import simulator.model.SetContClassEvent;

public class SetContClassEventBuilder extends Builder<Event>{

	//	CONSTRUCTOR
	
	public SetContClassEventBuilder(String type) {
		super(type);
	}

	//	MÉTODOS
	
	//Devuelve el evento que establece la contaminación de un vehículo
	protected Event createTheInstance(JSONObject data) {

		if(!data.has("time") || !data.has("info")) {
			throw new IllegalArgumentException("SetContClass values ​​not found in the JSON file");
		}
		
		int time = data.getInt("time");
		JSONArray jsonList = data.getJSONArray("info");
		List <Pair<String, Integer>> cs = new ArrayList<>();
		
		for(int i = 0; i < jsonList.length(); i++) {
			
			JSONObject o = jsonList.getJSONObject(i);
			String vehicle = o.getString("vehicle");
			Integer contClass =  o.getInt("class");
			cs.add(new Pair<String, Integer>(vehicle, contClass));
		} 
		
		return new SetContClassEvent(time, cs);
	}

}

package simulator.factories;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.model.DequeuingStrategy;
import simulator.model.Event;
import simulator.model.LightSwitchingStrategy;
import simulator.model.NewJuntionEvent;

public class NewJunctionEventBuilder extends Builder<Event> {

	//	ATRIBUTOS
	
	private Factory<LightSwitchingStrategy> lssFactory;
	private Factory<DequeuingStrategy> dqsFactory;
	
	//	CONSTRUCTOR
	
	public NewJunctionEventBuilder(String type, Factory<LightSwitchingStrategy> lssFactory, Factory<DequeuingStrategy> dqsFactory) {
		super(type);
		
		if(lssFactory != null && dqsFactory != null) {
			this.lssFactory = lssFactory;
			this.dqsFactory = dqsFactory;
		}
		
		else {
			throw new IllegalArgumentException("Valores nulos en las factorias.");
		}
	}

	//	MÉTODOS
	
	//Devuelve el evento del cruce
	protected Event createTheInstance(JSONObject data) {
		
		if(!data.has("time") || !data.has("id") || !data.has("coor")) {
			throw new IllegalArgumentException("Valores no encontrados en el fichero JSON.");
		}
			
		int time = data.getInt("time");
		String id = data.getString("id");
		JSONArray coorArray = data.getJSONArray("coor");
		int xCoor = coorArray.getInt(0);
		int yCoor = coorArray.getInt(1);
		
		LightSwitchingStrategy lss = lssFactory.createInstance(data.getJSONObject("ls_strategy"));
		DequeuingStrategy dqs = dqsFactory.createInstance(data.getJSONObject("dq_strategy"));
		
		return new NewJuntionEvent(time, id, lss, dqs, xCoor, yCoor);
		
	}
}
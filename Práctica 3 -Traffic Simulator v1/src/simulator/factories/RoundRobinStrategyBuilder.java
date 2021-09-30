package simulator.factories;

import org.json.JSONObject;
import simulator.model.LightSwitchingStrategy;
import simulator.model.RoundRobinStrategy;

public class RoundRobinStrategyBuilder extends Builder<LightSwitchingStrategy>{

	//	CONSTRUCTOR
	
	public RoundRobinStrategyBuilder(String type) {
		super(type);
	}

	//	MÉTODOS
	
	//Devuelve la estrategia de cambio de semáforo RoundRobin
	protected LightSwitchingStrategy createTheInstance(JSONObject data) {
		int timeSlot = 1;
		if(data.has("timeslot")) {
			timeSlot = data.getInt("timeslot"); 
		}
		
		return new RoundRobinStrategy(timeSlot);
	}
}

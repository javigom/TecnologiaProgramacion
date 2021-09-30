package simulator.factories;

import org.json.JSONObject;

import simulator.model.LightSwitchingStrategy;
import simulator.model.MostCrowdedStrategy;

public class MostCrowdedStrategyBuilder  extends Builder<LightSwitchingStrategy>{

	//	CONSTRUCTOR
	
	public MostCrowdedStrategyBuilder(String type) {
		super(type);
	}

	//	MÉTODOS
	
	//Devuelve la estrategia de cambio de semáforo MostCrowded
	protected LightSwitchingStrategy createTheInstance(JSONObject data) {
		int timeSlot = 1;
		if(data.has("timeslot")) {
			timeSlot = data.getInt("timeslot"); 
		}
		
		return new MostCrowdedStrategy(timeSlot);
	}
}

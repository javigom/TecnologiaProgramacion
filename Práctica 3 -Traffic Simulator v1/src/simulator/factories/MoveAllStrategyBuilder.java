package simulator.factories;

import org.json.JSONObject;

import simulator.model.DequeuingStrategy;
import simulator.model.MoveAllStrategy;

public class MoveAllStrategyBuilder extends Builder<DequeuingStrategy> {

	//	CONSTRUCTOR
	
	public MoveAllStrategyBuilder(String type) {
		super(type);
	}

	//	MÉTODOS
	
	//Devuelve la estrategia de extracción de la cola MoveAll
	protected DequeuingStrategy createTheInstance(JSONObject data) {
		return new MoveAllStrategy();
	}
}
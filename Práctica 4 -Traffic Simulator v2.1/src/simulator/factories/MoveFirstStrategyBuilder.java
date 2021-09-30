package simulator.factories;

import org.json.JSONObject;

import simulator.model.DequeuingStrategy;
import simulator.model.MoveFirstStrategy;

public class MoveFirstStrategyBuilder extends Builder<DequeuingStrategy> {

	//	CONSTRUCTOR
	
	public MoveFirstStrategyBuilder(String type) {
		super(type);
	}

	//	MÉTODOS
	
	//Devuelve la estrategia de extracción de la cola MoveFirst
	protected DequeuingStrategy createTheInstance(JSONObject data) {
		return new MoveFirstStrategy();
	}
}
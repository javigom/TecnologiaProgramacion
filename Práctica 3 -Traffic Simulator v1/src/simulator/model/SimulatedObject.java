package simulator.model;

import org.json.JSONObject;

public abstract class SimulatedObject {

	//	ATRIBUTOS
	
	protected String _id;

	//	CONSTRUCTOR
	
	SimulatedObject(String id) {
		if (id == null)
			throw new IllegalArgumentException("Simulated object identifier cannot be null");
		else
			_id = id;

	}

	//MÉTODOS
	
	public String getId() {
		return _id;
	}

	
	public String toString() {
		return _id;
	}

	abstract void advance(int time);

	abstract public JSONObject report();
}

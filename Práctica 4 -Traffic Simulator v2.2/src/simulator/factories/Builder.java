package simulator.factories;

import org.json.JSONObject;

public abstract class Builder<T> {
	
	//	ATRIBUTOS
	
	protected String _type;

	//	CONSTRUCTOR
	
	Builder(String type) {
		if (type == null)
			throw new IllegalArgumentException("Invalid type: " + type);
		else
			_type = type;
	}

	//	MÉTODOS
	
	public T createInstance(JSONObject info) {

		T b = null;

		if (_type != null && _type.equals(info.getString("type"))) {
			b = createTheInstance(info.has("data") ? info.getJSONObject("data") : null);
		}

		return b;
	}

	protected abstract T createTheInstance(JSONObject data);
}

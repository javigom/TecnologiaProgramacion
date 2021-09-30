package simulator.factories;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

public class BuilderBasedFactory<T> implements Factory<T> {

	//	ATRIBUTOS
	
	private List<Builder<T>> _builders;

	//	CONSTRUCTOR
	public BuilderBasedFactory(List<Builder<T>> builders) {
		_builders = new ArrayList<>(builders);
	}

	//	MÉTODOS
	
	public T createInstance(JSONObject info) {
		if (info != null) {
			for (Builder<T> bb : _builders) {
				T o = bb.createInstance(info);
				if (o != null)
					return o;
			}
		}

		throw new IllegalArgumentException("Invalid value for createInstance: " + info);
	}
}

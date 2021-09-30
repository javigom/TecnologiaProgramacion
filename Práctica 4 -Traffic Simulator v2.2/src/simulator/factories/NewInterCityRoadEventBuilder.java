package simulator.factories;

import simulator.model.Event;
import simulator.model.NewInterCityRoadEvent;

public class NewInterCityRoadEventBuilder extends NewRoadEventBuilder{
	
	//	CONSTRUCTOR
	
	public NewInterCityRoadEventBuilder(String type) {
		super(type);
	}

	//	MÉTODOS
	
	//Devuelve el evento de la carretera interurbana
	protected Event createTheRoad() {
		return new NewInterCityRoadEvent(this.time, this.id, this.src, this.dest, this.lenght, this.co2limit, this.maxSpeed, this.weather);
	}
}

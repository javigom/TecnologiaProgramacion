package simulator.factories;

import simulator.model.Event;
import simulator.model.NewCityRoadEvent;

public class NewCityRoadEventBuilder extends NewRoadEventBuilder {

	//	CONSTRUCTOR
	
	public NewCityRoadEventBuilder(String type) {
		super(type);
	}

	//	MÉTODOS
	
	//Devuelve el evento de la carretera urbana
	protected Event createTheRoad() {
		return new NewCityRoadEvent(this.time, this.id, this.src, this.dest, this.lenght, this.co2limit, this.maxSpeed, this.weather);
	}
}
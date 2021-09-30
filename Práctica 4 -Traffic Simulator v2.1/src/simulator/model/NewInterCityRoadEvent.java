package simulator.model;

public class NewInterCityRoadEvent extends NewRoadEvent {

	//	CONSTRUCTOR
	
	public NewInterCityRoadEvent(int time, String id, String srcJun, String destJun, int lenght, int co2limit, int maxSpeed, Weather weather) {
		super(time, id, srcJun, destJun, lenght, co2limit, maxSpeed, weather);
	}

	//	MÉTODOS
	
	//Crea una carretera interurbana
	protected Road createRoadObject() {
		return new InterCityRoad(this.id, this.src, this.dest, this.maxSpeed, this.co2limit, this.lenght, this.weather);
	}

	public String toString() {
		return "New Inter City Road '" + this.id + "'";
	}

}

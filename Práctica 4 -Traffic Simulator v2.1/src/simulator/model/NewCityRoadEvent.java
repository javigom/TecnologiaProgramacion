package simulator.model;

public class NewCityRoadEvent extends NewRoadEvent {

	//	CONSTRUCTOR
	
	public NewCityRoadEvent(int time, String id, String srcJun, String destJun, int lenght, int co2limit, int maxSpeed, Weather weather) {
		super(time, id, srcJun, destJun, lenght, co2limit, maxSpeed, weather);
	}

	//	MÉTODOS
	
	//Crea una carretera urbana
	protected Road createRoadObject() {
		return new CityRoad(this.id, this.src, this.dest, this.maxSpeed, this.co2limit, this.lenght, this.weather);
	}
	
	public String toString() {
		return "New City Road '" + this.id + "'";
	}
}

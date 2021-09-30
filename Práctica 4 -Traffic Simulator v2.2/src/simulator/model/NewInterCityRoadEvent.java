package simulator.model;

import org.json.JSONObject;

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
	
	@Override
	protected JSONObject saveStatus() {
		JSONObject info = new JSONObject();
		info.put("type", "new_inter_city_road");
		
		JSONObject data = new JSONObject();
		data.put("time", this.getTime());
		data.put("id", this.id);
		data.put("src", this.srcJun);
		data.put("dest", this.destJun);
		data.put("length", this.lenght);
		data.put("co2limit", this.co2limit);
		data.put("maxspeed", this.maxSpeed);
		data.put("weather", this.weather);
		
		info.put("data", data);
		
		return info;
	}
	
}

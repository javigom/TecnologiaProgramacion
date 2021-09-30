package simulator.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.JSONObject;

public class NewVehicleEvent extends Event {

	//	ATRIBUTOS
	
	private String id;
	private int maxSpeed;
	private int contClass;
	private List<String> itinerary;
	
	//	CONSTRUCTOR
	
	public NewVehicleEvent(int time, String id, int maxSpeed, int contClass, List<String> itinerary) {
		super(time);
		this.id = id;
		this.maxSpeed = maxSpeed;
		this.contClass = contClass;
		this.itinerary = itinerary;
	}

	//MÉTODOS
	
	//Añade el nuevo vehículo al mapa
	protected void execute(RoadMap map) {
		
		ArrayList<Junction> jItinerary = new ArrayList<Junction>();
		Iterator <String> it = itinerary.iterator();
		
		while(it.hasNext()) {
			jItinerary.add(map.getJunction(it.next()));
		}
		
		Vehicle v = new Vehicle(this.id, this.maxSpeed, this.contClass, jItinerary);
		map.addVehicle(v);
		v.moveToNextRoad();
	}
	
	public String toString() {
		return "New Vehicle '" + this.id + "'";
	}

	@Override
	protected JSONObject saveStatus() {
		JSONObject info = new JSONObject();
		info.put("type", "new_vehicle");
		
		JSONObject data = new JSONObject();
		data.put("time", this.getTime());
		data.put("id", this.id);
		data.put("maxspeed", this.maxSpeed);
		data.put("class", this.contClass);
		data.put("itinerary", this.itinerary);
		
		info.put("data", data);
		
		return info;
	}
}

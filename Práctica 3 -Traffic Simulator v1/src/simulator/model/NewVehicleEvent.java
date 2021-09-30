package simulator.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
}

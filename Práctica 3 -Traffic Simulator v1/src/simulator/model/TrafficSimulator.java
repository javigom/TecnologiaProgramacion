package simulator.model;

import java.util.List;

import org.json.JSONObject;

import simulator.misc.SortedArrayList;

public class TrafficSimulator {

	//	ATRIBUTOS
	
	private RoadMap roadMap;
	private List<Event> eventList;
	private int timeSlot;
	
	//	CONSTRUCTOR
	
	public TrafficSimulator() {
		this.roadMap = new RoadMap();
		this.eventList = new SortedArrayList<Event>();
		this.timeSlot = 0;
	}
	
	//	MÉTODOS
	
	//Añade el evento event a la lista de eventos
	public void addEvent(Event event) {
		this.eventList.add(event); //Lo añade ordenado ?
	}
	
	//Avanza el estado de la simulación
	public void advance() {
		this.timeSlot++;
		
		int i = 0;
		
		while(this.eventList.size() > 0 && this.eventList.get(i).getTime() == this.timeSlot) {
			this.eventList.get(i).execute(this.roadMap);
			this.eventList.remove(i);
		}
		
		this.roadMap.advanceJunction(this.timeSlot);
		this.roadMap.advanceRoad(this.timeSlot);
	}
	
	//Limpia el mapa de carreteras y la lista de eventos
	public void reset() {
		this.roadMap.reset();
		this.eventList.clear();
		this.timeSlot = 0;
	}

	//Devuelve el estado del simulador en formato JSON
	public JSONObject report() {
		JSONObject info = new JSONObject();
		info.put("time", this.timeSlot);
		info.put("state", this.roadMap.report());
		return info;
	}
}

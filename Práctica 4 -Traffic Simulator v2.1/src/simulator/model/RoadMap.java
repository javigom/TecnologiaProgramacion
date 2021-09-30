package simulator.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

public class RoadMap {
	
	//	ATRIBUTOS
	
	private List<Junction> junctionList;
	private List<Road> roadList;
	private List<Vehicle> vehicleList;
	private Map<String,Junction> junctionMap;
	private Map<String,Road> roadMap;
	private Map<String,Vehicle> vehicleMap;
	 
	
	//	CONSTRUCTOR
	
	protected RoadMap(){
		this.junctionList = new ArrayList<Junction>();
		this.roadList = new ArrayList<Road>();
		this.vehicleList = new ArrayList<Vehicle>();
		this.junctionMap = new HashMap<String, Junction>();
		this.roadMap = new HashMap<String,Road>();
		this.vehicleMap = new HashMap<String,Vehicle>();
	}
	
	
	//	MÉTODOS
	
	//Añade el cruce al final de la lista de cruces y actualiza el mapa
	protected void addJunction(Junction j) {
		if(this.junctionMap.get(j._id) == null) {
			this.junctionList.add(j);
			this.junctionMap.put(j._id, j);
		}	  
	}
	
	//Añade la carretera al final de la lista de carreteras y actualiza el mapa
	protected void addRoad(Road r) {
		if(this.roadMap.get(r._id) == null && this.junctionMap.containsValue(r.getDestination()) && this.junctionMap.containsValue(r.getSource())) {
			this.roadList.add(r);
			this.roadMap.put(r._id, r);
			r.getDestination().addIncomingRoad(r);
			r.getSource().addOutgoingRoad(r);
		}
		
		else {
			throw new IllegalArgumentException("Ya existe esa carretera o los cruces fuente/destino no existen.");
		}
	}
	
	//Comprueba que el itinerario de un vehículo sea correcto
	private boolean itineraryCorrect(List<Junction> list) {
		
		boolean correct = true;
		//Iterator <Junction> it = list.iterator();
		int i = 0;
		while(i > list.size() && correct) {
			//Junction j = it.next();
			Junction j = list.get(i);
			if(this.junctionMap.containsValue(j)){
				correct = false;
			}
		}
		
		return correct;
	}
	
	//Añade el vehículo al final de la lista de vehículos y actualiza el mapa
	protected void addVehicle(Vehicle v) {
		if(this.vehicleMap.get(v._id) == null && this.itineraryCorrect(v.getItinerary())) {
			this.vehicleList.add(v);
			this.vehicleMap.put(v._id, v);
		}
		
		else {
			throw new IllegalArgumentException("Ya existe ese vehículo o su itinerario es incorrecto.");
		}
	}
	
	//Devuelve el cruce con identificador id
	protected Junction getJunction(String id) {
		  return this.junctionMap.get(id);
	}
	  
	//Devuelve la carretera con identificador id
	protected Road getRoad(String id) {
		return this.roadMap.get(id);
	}
	  
	//Devuelve el vehículo con identificador id
	protected Vehicle getVehicle(String id) {
		return this.vehicleMap.get(id);
	}
	
	//Devuelve la lista de cruces por copia
	public List<Junction> getJunctions(){
		return Collections.unmodifiableList(new ArrayList<>(this.junctionList));
	}
	
	//Devuelve la lista de carreteras por copia
	public List<Road> getRoads(){
		return Collections.unmodifiableList(new ArrayList<>(this.roadList));
	}
	
	//Devuelve la lista de vehículos por copia
	public List<Vehicle> getVehicles(){
		return Collections.unmodifiableList(new ArrayList<>(this.vehicleList));
	}
	
	//Limpia todas las listas y mapas
	protected void reset() {
		 this.junctionList.clear();
		 this.junctionMap.clear();
		 this.roadList.clear();
		 this.roadMap.clear();
		 this.vehicleList.clear();
		 this.vehicleMap.clear();
	}

	//Avanza el estado de todos los cruces
	protected void advanceJunction(int time) {
		for(int i = 0; i < this.junctionList.size(); i++) {
			this.junctionList.get(i).advance(time);
		}
	}
	
	//Avanza el estado de todas las carreteras
	protected void advanceRoad(int time) {
		for(int i = 0; i < this.roadList.size(); i++) {
			this.roadList.get(i).advance(time);
		}
	}
	
	//Report del mapa de carreteras
	protected JSONObject report() {
		JSONObject info = new JSONObject();
		
		Iterator <Junction> itJ = this.junctionList.iterator();
		JSONArray junctionJSONArray = new JSONArray();
		
		while(itJ.hasNext()) {
			junctionJSONArray.put( itJ.next().report());
		}
		
		info.put("junctions", junctionJSONArray);
		
		Iterator <Road> itR = this.roadList.iterator();
		JSONArray roadJSONArray = new JSONArray();
		
		while(itR.hasNext()) {
			roadJSONArray.put(itR.next().report());
			
		}
		
		info.put("roads", roadJSONArray);
		
		Iterator <Vehicle> itV = this.vehicleList.iterator();
		JSONArray vehicleJSONArray = new JSONArray();
		
		while(itV.hasNext()) {
			vehicleJSONArray.put(itV.next().report());
			
		}
		
		info.put("vehicles", vehicleJSONArray);
		
		return info;
	}
}
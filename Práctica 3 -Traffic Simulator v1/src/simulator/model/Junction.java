package simulator.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

public class Junction extends SimulatedObject {
	
	//	ATRIBUTOS

	private ArrayList<Road> inRoads;
	private ArrayList<List<Vehicle>> queuesList;
	private Map<Road, List<Vehicle>> mapQueuesInRoads;
	private Map<Junction, Road> destinationRoads;
	private int light;
	private int lastTimeSwitched;
	private LightSwitchingStrategy lss;
	private DequeuingStrategy dqs;
	int xCoor, yCoor;
	
	//	CONSTRUCTOR
	
	Junction(String id, LightSwitchingStrategy lsStrategy, DequeuingStrategy dqStrategy, int xCoor, int yCoor) {
		
		super(id);
		
		if(lsStrategy == null || dqStrategy == null || xCoor < 0 || yCoor < 0) {
			throw new IllegalArgumentException("Parámetros erroneos en el constructor Junction");
		}
		
		else {
			this.light = -1;
			this.lastTimeSwitched = 0;
			this.lss = lsStrategy;
			this.dqs = dqStrategy;
			this.xCoor = xCoor;
			this.yCoor = yCoor;
			this.inRoads = new ArrayList<Road>();
			this.queuesList = new ArrayList<List<Vehicle>>();
			this.mapQueuesInRoads = new HashMap<Road, List<Vehicle>>();
			this.destinationRoads = new HashMap<Junction, Road>();
		}
	}

	
	//	MÉTODOS
	
	//Devuelve la coordenada x
	public int getX() {
		return this.xCoor;
	}
	
	//Devuelve la coordenada y
	public int getY() {
		return this.yCoor;
	}
	
	//Añade la carretera r a las carreteras entrantes
	protected void addIncomingRoad(Road r) {
		
		if(r.getDestination() == this) {
			this.inRoads.add(r);
			List<Vehicle> l = new LinkedList<Vehicle> ();
			this.mapQueuesInRoads.put(r, l);
			this.queuesList.add(l);
		}
		
		else {
			throw new IllegalArgumentException("La carretera r no tiene como destino el cruce actual.");
		}
	}
	
	//Añade r a las carreteras salientes
	protected void addOutgoingRoad(Road r) {
		
		if(this.destinationRoads.containsKey(r.getDestination())) {
			throw new IllegalArgumentException("La carretera saliente ya existe en el mapa.");
		}
		
		else {
			this.destinationRoads.put(r.getDestination(), r);
		}
	}
	
	//Añade el vehículo v a la lista de la carretera r
	protected void enter(Vehicle v) {
		
		List<Vehicle> l = this.mapQueuesInRoads.get(v.getCurrentRoad());
		l.add(v);
	}
	
	//Devuelve la carretera por la que se llega al cruce j
	protected Road roadTo(Junction j) {
		return this.destinationRoads.get(j);
	}
	
	//Avanza el estado del cruce;
	void advance(int time) {
		
		//Si algún cruce está en verde
		if(this.light != -1) { 
			List<Vehicle> vList = this.dqs.dequeue(this.queuesList.get(this.light));
			Iterator <Vehicle> it = vList.iterator();
			while(it.hasNext()) {
				Vehicle v = it.next();
				v.moveToNextRoad();
				this.queuesList.get(this.light).remove(v);
			}
		}
		
		//Elige el siguiente cruce que se pondrá verde
		int indexLight = this.lss.chooseNextGreen(this.inRoads, this.queuesList, this.light, this.lastTimeSwitched, time);
		
		//Si es distinto al anterior, modifico lastTimeSwitched
		if(indexLight != this.light) {
			this.light = indexLight;
			this.lastTimeSwitched = time;
		}
	}

	//JSONArray con el report de los identificadores de los vehiculos que están en una carretera
	private JSONArray reportArrayRoad(Road r) {
		JSONArray vList = new JSONArray();
		Iterator <Vehicle> it = this.mapQueuesInRoads.get(r).iterator();
		
		while(it.hasNext()) {
			vList.put(it.next().getId());
		}
		return vList;
	}
	
	//JSONArray con el report de cada carretera
	private JSONArray reportRoad() {
		JSONArray roadArray = new JSONArray();
		
		Iterator <Road> it = this.inRoads.iterator();
		while(it.hasNext()) {
			Road r = it.next();
			JSONObject o = new JSONObject();
			o.put("road", r.getId());
			o.put("vehicles", reportArrayRoad(r));
			roadArray.put(o);
		}
		
		return roadArray;
	}
	
	//Report del cruce
	public JSONObject report() {
		JSONObject info = new JSONObject();
		info.put("id", this.getId());
		
		if(this.light == -1) {
			info.put("green", "none");
		}
		else {
			info.put("green", this.inRoads.get(this.light).getId());
		}
		info.put("queues", reportRoad());		
		
		return info;
	}
}

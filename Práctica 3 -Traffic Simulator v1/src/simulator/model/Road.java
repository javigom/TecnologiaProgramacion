package simulator.model;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.misc.SortedArrayList;

public abstract class Road extends SimulatedObject{
	
	
	//Clase interna que permite comparar vehículos
	class VehicleComparator implements Comparator<Vehicle> {

		public int compare(Vehicle o1, Vehicle o2) {
			if(o1.getLocation() < o2.getLocation()) {
				return 1;
			}
			else {
				return 0;
			}
		}
	}
	
	//	ATRIBUTOS
	
	private Junction source;
	private Junction destination;
	private int length;
	private int maximumSpeed;
	private int currentSpeedLimit;
	private int contaminationAlarmLimit;
	private Weather weatherConditions;
	private int totalContamination;
	private SortedArrayList<Vehicle> vehicle;
	private VehicleComparator vehicleComparator;
	
	
	//	CONSTRUCTOR
	
	Road(String id, Junction srcJunc, Junction destJunc, int maxSpeed, int contLimit, int length, Weather weather) {
		
		super(id);
		
		if(maxSpeed > 0 && contLimit >= 0 && length > 0 && srcJunc != null && destJunc != null && weather != null) {
			this.source = srcJunc;
			this.destination = destJunc;
			this.length = length;
			this.maximumSpeed = maxSpeed;
			this.contaminationAlarmLimit = contLimit;
			this.currentSpeedLimit = maxSpeed;
			this.weatherConditions = weather;
			this.totalContamination = 0;
			this.vehicleComparator = new VehicleComparator();
			this.vehicle = new SortedArrayList<Vehicle>(this.vehicleComparator);			
		}
		
		else {
			throw new IllegalArgumentException("Parámetros erróneos en la clase Road");
		}
	}
	
	
	//	MÉTODOS
	
	//Devuelve el cruce destino
	protected Junction getDestination() {
		return this.destination;
	}
	
	//Devuelve el cruce origen
	protected Junction getSource() {
		return this.source;
	}
	
	//Devuelve el enumerado con la condicion atmosférica actual
	protected Weather getWeather() {
		return this.weatherConditions;
	}
	
	//Devuelve la longitud total de la carretera
	protected int getLength() {
		return length;
	}

	//Devuelve la contaminación total
	protected int getTotalContamination() {
		return this.totalContamination;
	}
	
	//Devuelve el límite de contaminación
	protected int getContaminationAlarmLimit() {
		return this.contaminationAlarmLimit;
	}
	
	//Devuelve el límite de velocidad actual
	protected int getCurrentSpeed() {
		return this.currentSpeedLimit;
	}
	
	//Devuelve la velocidad máxima
	protected int getMaxSpeed() {
		return this.maximumSpeed;
	}
	
	//Establece la contaminación total
	protected void setTotalContamination(int c) {
		this.totalContamination = c;
	}
	
	//Establece el límite de velocidad actual
	protected void setCurrentSpeed(int s) {
		this.currentSpeedLimit = s;
	}
	
	//Añade el vehículo v a la carretera siempre y cuando su velocidad y localización sean 0
	protected void enter(Vehicle v) {
		
		if(v.getLocation() == 0 && v.getCurrentSpeed() == 0) {
			vehicle.add(v);
		}
		
		else {
			throw new IllegalArgumentException("El vehículo no pueden entrar en la carretera: velocidad o localizacion distintas de 0.");
		}
	}
	
	//Elimina al vehículo v de la lista
	protected void exit(Vehicle v) {
		vehicle.remove(v);
	}
	
	//Establece las condiciones atmosféricas a w siempre que no sea nulo
	protected void setWeather(Weather w) {
		if(w != null) {
			this.weatherConditions = w;
		}
		
		else {
			throw new IllegalArgumentException("El parámetro w es nulo.");
		}
	}
		
	//Añade la contaminación c a la contaminación total siempre que esta sea positiva
	protected void addContamination(int c) {
		
		if(c >= 0)
			this.totalContamination += c;
		
		else {
			throw new IllegalArgumentException("La contaminación no debe ser negativa.");
		}		
	}
	
	//Reduce total de la contaminacion
	protected abstract void reduceTotalContamination();
	
	//Actualiza la velocidad limite de la carretera
	protected abstract void updateSpeedLimit();
	
	//Calcula la velocidad de un vehículo v
	protected abstract int calculateVehicleSpeed(Vehicle v);
	
	//Avanza el estado de la carretera
	protected void advance(int time) {
		this.reduceTotalContamination();
		this.updateSpeedLimit();
		
		int i = 0; 
		
		while(i < this.vehicle.size()) {
			int s = this.calculateVehicleSpeed(this.vehicle.get(i));
			this.vehicle.get(i).setSpeed(s);
			this.vehicle.get(i).advance(time);
			i++;
		}
		
		Collections.sort(vehicle, vehicleComparator);
	}
	
	//Devuelve la lista de vehículos
	private JSONArray getJSONVehicles(){
		
		JSONArray arrayVehicles = new JSONArray();
		Iterator <Vehicle> it = vehicle.iterator();
		
		while(it.hasNext()) {
			arrayVehicles.put(it.next().getId());
		}
		
		return arrayVehicles;
	}
	
	//Devuelve el estado de la carretera en formato JSON
	public JSONObject report() {
		JSONObject info = new JSONObject ();
		info.put("id", _id);
		info.put("speedlimit",currentSpeedLimit);
		info.put("weather", weatherConditions);
		info.put("co2", totalContamination);
		info.put("vehicles", getJSONVehicles());
		return info;
	}
}

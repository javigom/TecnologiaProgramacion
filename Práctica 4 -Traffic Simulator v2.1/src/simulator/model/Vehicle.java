package simulator.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONObject;

public class Vehicle extends SimulatedObject {

	
	//	ATRIBUTOS
	
	private ArrayList<Junction> itinerary;
	private int currentIndex;
	private int maximumSpeed;
	private int currentSpeed;
	private VehicleStatus status;
	private Road road;
	private int location;
	private int contaminationClass;
	private int totalContamination;
	private int totalTravelledDistance;
	
	
	//	CONSTRUCTOR
	
	protected Vehicle(String id, int maxSpeed, int contClass, ArrayList<Junction> itinerary) {
		super(id);
		
		if (maxSpeed > 0 && contClass >= 0 && contClass <= 10 && itinerary.size() >= 2) {
			this.itinerary = new ArrayList<>(itinerary);
			this.currentIndex = 0;
			this.maximumSpeed = maxSpeed;
			this.currentSpeed = 0;
			this.status = VehicleStatus.PENDING;
			this.road = null;
			this.location = 0;
			this.contaminationClass = contClass;
			this.totalContamination = 0;
			this.totalTravelledDistance = 0;
		}
		
		else {
			throw new IllegalArgumentException("Valores incorrectos en la llamada al constructor de Vehicle");
		}
		
	}

	
	//	MÉTODOS
	
	//Establece la velocidad actual con el valor s
	protected void setSpeed(int s) {
		
		if(s >= 0) {
			if(s > this.maximumSpeed) {
				this.currentSpeed = this.maximumSpeed;
			}
			
			else {
				this.currentSpeed = s;
			}
		}
		
		else {
			throw new IllegalArgumentException("La velocidad no puede ser negativa.");
		}
		
	}
	
	//Establece la contaminación actual con el valor c
	protected void setContaminationClass(int c) {
		
		if(c >= 0 && c <= 10) {
			contaminationClass = c;
		}
		
		else {
			throw new IllegalArgumentException("La contaminación debe tener un valor entre 0 y 10.");
		}
		
	}
	
	//Devuelve la localización actual del vehículo
	public int getLocation() {
		return this.location;
	}
	
	public String getRoadAndLocation() {
		if(this.road == null) {
			return "";
		}
		else {
			return this.getCurrentRoad().getId() + ": " + this.getLocation();
		}
	}
	
	//Devuelve la velocidad actual del vehículo
	public int getCurrentSpeed() {
		return this.currentSpeed;
	}
	
	public int getMaxSpeed() {
		return this.maximumSpeed;
	}
	
	//Devuelve la clase de contaminación
	public int getContaminationClass() {
		return this.contaminationClass;
	}
	
	//Devuelve la carretera actual
	public Road getCurrentRoad() {
		return this.road;
	}
	
	public int getCO2() {
		return this.totalContamination;
	}
	
	public int getDistance() {
		return this.totalTravelledDistance;
	}
	
	//Devuelve el estado actual del vehículo
	public VehicleStatus getStatus() {
		return this.status;
	}
	
	//Devuelve el itinerario del vehículo
	public List<Junction> getItinerary() {
		return Collections.unmodifiableList(new ArrayList<>(this.itinerary));
	}
	
	//Avanza la trayectoria del vehículo si su estado es traveling
	protected void advance(int time) {
		int c = 0, travelledDistance = 0;
		if(status == VehicleStatus.TRAVELING) {
			
			if(this.location + this.currentSpeed > this.road.getLength()) {
				travelledDistance = this.road.getLength() - this.location;
				this.location = this.road.getLength();
			}
			
			else {
				travelledDistance = this.currentSpeed;
				this.location = this.location + this.currentSpeed;
			}
			
			c = travelledDistance * this.contaminationClass;
			totalContamination += c;
			road.addContamination(c);		
			this.totalTravelledDistance += travelledDistance;
			
			if(this.location == this.road.getLength()) { //??
				this.status = VehicleStatus.WAITING;
				this.itinerary.get(this.currentIndex).enter(this);
				this.setSpeed(0);
			}
		}
	}
	
	//Mueve el vehículo a la siguiente carretera
	protected void moveToNextRoad() {
		if(this.status == VehicleStatus.PENDING || this.status == VehicleStatus.WAITING) {
			
			if(this.currentIndex == itinerary.size() - 1) { //Si ya estoy en el último cruce
				this.status = VehicleStatus.ARRIVED;
				this.road.exit(this);
				this.road = null;
				this.setSpeed(0);
			}
			
			else {
				
				if(this.status == VehicleStatus.WAITING) {
					this.road.exit(this); //Salgo carretera actual
					this.location = 0; 
					this.setSpeed(0);
				}
				
				//Si es pending, sólo realizo lo siguiente
				Junction actualJunction = this.itinerary.get(this.currentIndex);
				this.currentIndex++;
				Junction nextJunction = this.itinerary.get(this.currentIndex);
				Road nextRoad = actualJunction.roadTo(nextJunction);
				this.road = nextRoad;
				this.road.enter(this);
				this.status = VehicleStatus.TRAVELING;
			}
		}
		
		else {
			throw new IllegalArgumentException("El estado del vehículo es TRAVELING o ARRIVED.");
		}
	}
	
	//Devuelve el estado del vehículo en formato JSON
	public JSONObject report() {
		JSONObject info = new JSONObject ();
		info.put("id", _id);
		info.put("speed",currentSpeed);
		info.put("distance", totalTravelledDistance);
		info.put("co2", totalContamination);
		info.put("class", contaminationClass);
		info.put("status", status.toString());
		if(status != VehicleStatus.PENDING && status != VehicleStatus.ARRIVED) {
			info.put("road", road);
			info.put("location", location);
		}
		return info;
	}

}

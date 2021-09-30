package simulator.model;

public class CityRoad extends Road{
	
	//	CONSTRUCTOR
	
	protected CityRoad(String id, Junction srcJunc, Junction destJunc, int maxSpeed, int contLimit, int length, Weather weather){
		super(id, srcJunc, destJunc, maxSpeed, contLimit, length, weather);
	}
	
	//	MÉTODOS
	
	//Reduce la contaminación total
	protected void reduceTotalContamination() {
		int x = 2; //Caso por defecto
		
		if(this.getWeather() == Weather.STORM || this.getWeather() == Weather.WINDY) {
			x = 10;
		}
		
		if(getTotalContamination() - x < 0) {
			this.setTotalContamination(0);
		}
		
		else {
			this.setTotalContamination(this.getTotalContamination() - x);
		}
	}
	
	//La velocidad límite siempre es la velocidad máxima permitida
	protected void updateSpeedLimit() {
		this.setCurrentSpeed(this.getMaxSpeed());
	}
	
	//Calcula la velocidad a la que debe ir un vehículo v
	protected int calculateVehicleSpeed(Vehicle v) {
		
		if(v.getStatus() == VehicleStatus.WAITING) {
			return 0;
		}
		else {
			return (int) Math.ceil(((11.0 - v.getContaminationClass())/11.0)*this.getCurrentSpeed());
		}	
	}
	
}



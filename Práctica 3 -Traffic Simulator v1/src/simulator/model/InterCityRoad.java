package simulator.model;

public class InterCityRoad extends Road {

	//CONSTRUCTOR
	
	protected InterCityRoad(String id, Junction srcJunc, Junction destJunc, int maxSpeed, int contLimit, int length, Weather weather){
		super(id, srcJunc, destJunc, maxSpeed, contLimit, length, weather);
	}
		
	//	MÉTODOS
	
	//Reduce la contaminación total
	protected void reduceTotalContamination() {
		int x;
		
		switch(this.getWeather()) {
		case SUNNY: x = 2;
			break; 
		case CLOUDY: x = 3;
			break;
		case RAINY: x = 10;
			break;
		case WINDY: x = 15;
			break;
		default: x = 20;
			break;
		}
				
		this.setTotalContamination((int) (((100.0 - x)/100.0)*this.getTotalContamination()) );
	}
	
	//Actualiza la velocidad máxima actual en función de la contaminación
	protected void updateSpeedLimit() {
		if(this.getTotalContamination() > this.getContaminationAlarmLimit()) {
			this.setCurrentSpeed((int)(this.getMaxSpeed()*0.5));
		}
		
		else {
			this.setCurrentSpeed(this.getMaxSpeed());
		}
	}
	
	//Calcula la velocidad a la que debe ir un vehículo v
	protected int calculateVehicleSpeed(Vehicle v) {
		int speed = this.getCurrentSpeed();
		if(this.getWeather() == Weather.STORM) {
			speed *= 0.8; 
		}
		return speed;
	}
	
}

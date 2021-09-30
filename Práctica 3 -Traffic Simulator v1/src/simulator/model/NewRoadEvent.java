package simulator.model;

public abstract class NewRoadEvent extends Event {

	// 	ATRIBUTOS
	
	protected String id;
	protected String srcJun;
	protected String destJun;
	protected Junction src;
	protected Junction dest;
	protected int lenght;
	protected int co2limit;
	protected int maxSpeed;
	protected Weather weather;
	
	//CONSTRUCTOR
	
	public NewRoadEvent(int time, String id, String srcJun, String destJun, int lenght, int co2limit, int maxSpeed, Weather weather) {
		super(time);
		this.id = id;
		this.srcJun = srcJun;
		this.destJun = destJun;
		this.lenght = lenght;
		this.co2limit = co2limit;
		this.maxSpeed = maxSpeed;
		this.weather = weather;
	}

	//	MÉTODOS
	
	//Añade la nueva carretera al mapa
	protected void execute(RoadMap map) {
		this.src = map.getJunction(this.srcJun);
		this.dest = map.getJunction(this.destJun);
		map.addRoad(this.createRoadObject());
	}
	
	protected abstract Road createRoadObject();
}

package simulator.model;

public abstract class Event implements Comparable<Event> {

	//	ATRIBUTOS
	
	protected int _time;

	//	CONSTRUCTOR
	
	Event(int time) {
		if (time < 1)
			throw new IllegalArgumentException("Time must be positive (" + time + ")");
		else
			_time = time;
	}

	//	MÉTODOS
	
	public int getTime() {
		return _time;
	}

	public int compareTo(Event o) {
		if(this.getTime() < o.getTime()) {
			return -1;
		}
		
		else if(this.getTime() == o.getTime()) {
			return 0;
		}
		
		else {
			return 1;
		}
	}

	//Ejecuta evento
	abstract void execute(RoadMap map);

}
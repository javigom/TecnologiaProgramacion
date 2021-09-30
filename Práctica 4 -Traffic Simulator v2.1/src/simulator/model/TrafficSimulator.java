package simulator.model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import simulator.misc.SortedArrayList;
import simulator.model.Observable;
import simulator.model.TrafficSimObserver;

public class TrafficSimulator implements Observable<TrafficSimObserver> {

	//	ATRIBUTOS
	
	private RoadMap roadMap;
	private List<Event> eventList;
	private int timeSlot;
	
	//Lista de copia los eventos iniciales que había en el fichero para volver al estado inicial
	private List<Event> copyInitialEventList;
	
	//Lista de copia de los eventos que ha ido añadiendo el usuario para volver al estado anterior
	private List<Event> copyNewEventList;
	
	/* 
	 * Lista de los eventos que han sido eliminados por el usuario. Sin ella, el botón de eliminar un evento no tendría coherencia, ya que tu puedes eliminar un evento
	 * y al pulsar el botón undo, habría vuelto a aparecer (o incluso si se ha eliminado y ha pasado su time, se ejecutaría a pesar de haberlo borrado). Esta lista
	 * permite no tener que tocar la copyInitialList y con ello, no afectaría al boton de reiniciar
	 * 
	 */
	private List<Event> deletedEventList;
	
	private List<TrafficSimObserver> trafficObservers;
	
	//	CONSTRUCTOR
	
	public TrafficSimulator() {
		this.roadMap = new RoadMap();
		this.eventList = new SortedArrayList<Event>();
		this.timeSlot = 0;
		this.trafficObservers = new ArrayList<>();
		this.copyInitialEventList = new SortedArrayList<Event>();
		this.copyNewEventList = new SortedArrayList<Event>();
		this.deletedEventList = new SortedArrayList<Event>();
	}
	
	//	MÉTODOS
	
	public int getTimeSlot() {
		return this.timeSlot;
	}
	
	//Añade el evento event a la lista de eventos
	public void addEvent(Event event) {
		
		if(event.getTime() < this.timeSlot) {
			String err = "The event's time (" + event.getTime() + ") must be greater than current time.";
			notifyOnError(err);
			throw new IllegalArgumentException(err);
		}
		
		this.eventList.add(event);
		
		//Eventos originales. Nunca voy a poder añadir un evento nuevo en t = 0
		if(this.timeSlot == 0) { 
			this.copyInitialEventList.add(event);
		}
		
		//Nuevos eventos
		else {
			this.copyNewEventList.add(event);
		}
	
		notifyOnEventAdded(event);
	}
	
	//Avanza el estado de la simulación
	public void advance() {
		this.timeSlot++;
		notifyAdvanceStart();
		
		try {
		
			while(this.eventList.size() > 0 && this.eventList.get(0).getTime() == this.timeSlot) {
				this.eventList.get(0).execute(this.roadMap);
				this.eventList.remove(0);
			}
			
			this.roadMap.advanceJunction(this.timeSlot);
			this.roadMap.advanceRoad(this.timeSlot);
			
		} catch(Exception e) {
			notifyOnError("Something went wrong: " + e.getLocalizedMessage());
			throw new IllegalArgumentException(e.getLocalizedMessage());
		}
		
		
		notifyAdvanceEnd();
	}
	
	//Limpia el mapa de carreteras y la lista de eventos
	public void reset() {
		this.roadMap.reset();
		this.eventList.clear();
		this.copyInitialEventList.clear();
		this.timeSlot = 0;
		notifyOnReset();
	}

	//Devuelve el estado del simulador en formato JSON
	public JSONObject report() {
		JSONObject info = new JSONObject();
		info.put("time", this.timeSlot);
		info.put("state", this.roadMap.report());
		return info;
	}

	@Override
	public void addObserver(TrafficSimObserver o) {		
		if(!this.trafficObservers.contains(o)) {
			this.trafficObservers.add(o);
		}
		
		notifyOnRegister();
	}

	@Override
	public void removeObserver(TrafficSimObserver o) {
		if(this.trafficObservers.contains(o)) {
			this.trafficObservers.remove(o);
		}	
	}
	
	public void reportError(String err) {
		notifyOnError(err);
	}
	
	public void restart(boolean isRestart) {
		this.roadMap.reset();
		this.eventList.clear();
		this.timeSlot = 0;
		this.eventList.addAll(this.copyInitialEventList);
		
		//Si es reinicio, vacío la lista de los nuevos y los eliminados por el usuario
		if(isRestart) {
			this.copyNewEventList.clear();
			this.deletedEventList.clear();
		}
		
		//Si es deshacer, añado los nuevos también pero sin contar los eliminados
		else {
			this.eventList.addAll(this.copyNewEventList);
			this.eventList.removeAll(this.deletedEventList);
		}
		
		notifyOnReset();
	}
	
	private void notifyAdvanceStart() {
		for(TrafficSimObserver o: this.trafficObservers) {
			o.onAdvanceStart(this.roadMap, this.eventList, this.timeSlot);
		}
	}
	
	private void notifyAdvanceEnd(){
		for(TrafficSimObserver o: this.trafficObservers) {
			o.onAdvanceEnd(this.roadMap, this.eventList, this.timeSlot);
		}
	}
	
	private void notifyOnEventAdded(Event e) {
		for(TrafficSimObserver o: this.trafficObservers) {
			o.onEventAdded(this.roadMap, this.eventList, e, this.timeSlot);
		}
	}
	
	private void notifyOnReset() {
		for(TrafficSimObserver o: this.trafficObservers) {
			o.onReset(this.roadMap, this.eventList, this.timeSlot);
		}
	}
	
	private void notifyOnRegister() {
		for(TrafficSimObserver o: this.trafficObservers) {
			o.onRegister(this.roadMap, this.eventList, this.timeSlot);
		}
	}
	
	private void notifyOnError(String err) {
		for(TrafficSimObserver o: this.trafficObservers) {
			o.onError(err);
		}
	}
	
}

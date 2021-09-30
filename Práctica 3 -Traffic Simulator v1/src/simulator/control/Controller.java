package simulator.control;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import simulator.factories.Factory;
import simulator.model.Event;
import simulator.model.TrafficSimulator;

public class Controller {
	
	//	ATRIBUTOS
	
	private TrafficSimulator trafficSimulator;
	private Factory<Event> eventFactory;
	
	//	CONSTRUCTOR
	
	public Controller(TrafficSimulator trafficSimulator, Factory<Event> eventFactory) {
		if(trafficSimulator == null || eventFactory == null) {
			throw new IllegalArgumentException("El parámetro TrafficSimulator o Factory<Event> es nulo");
		}
	
		else {
			this.trafficSimulator = trafficSimulator;
			this.eventFactory = eventFactory;
		}
	}
	
	//	MÉTODOS
	
	//Carga los eventos de un fichero in y los añade al simulador
	public void loadEvents(InputStream in) throws IOException {
		
		if(in == null) {
			throw new IOException("Fichero de entrada no válido");
		}
		
		else {
			JSONObject jo = new JSONObject(new JSONTokener(in));
			
			JSONArray arrayEvents = jo.getJSONArray("events");

			for(int i = 0; i < arrayEvents.length(); i++) {
				JSONObject o = arrayEvents.getJSONObject(i);
				this.trafficSimulator.addEvent(eventFactory.createInstance(o));
			}
		}
		
	}
	
	//Inicia el simulador y carga la salida en el fichero/consola out
	public void run(int n, OutputStream out) throws IOException {
		
		if(out == null) {
			throw new IOException("Fichero de salida no válido");
		}
		
		else {
			PrintStream p = new PrintStream(out);
			
			p.println("{");
			p.println("	" + "\"states\": [");
			
			for(int i = 0; i < n; i++) {
				this.trafficSimulator.advance();
				if(i != n - 1) {
					p.println(this.trafficSimulator.report().toString() + ",");
				}
				else {
					p.println(this.trafficSimulator.report().toString());
				}
				
			}
			
			p.println("]");
			p.println("}");
		}
	}
	
	//Resetea el simulador
	public void reset() {
		this.trafficSimulator.reset();
	}
}

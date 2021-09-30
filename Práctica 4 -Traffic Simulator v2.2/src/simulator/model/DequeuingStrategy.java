package simulator.model;

import java.util.List;

import org.json.JSONObject;

public interface DequeuingStrategy {
	List<Vehicle> dequeue(List<Vehicle> q);
	JSONObject saveStatus();
}
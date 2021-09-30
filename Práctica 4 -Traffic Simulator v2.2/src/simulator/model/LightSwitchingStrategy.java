package simulator.model;

import java.util.List;

import org.json.JSONObject;

public interface LightSwitchingStrategy {	
	int chooseNextGreen(List<Road> roads, List<List<Vehicle>> qs, int currGreen, int lastSwitchingTime, int currTime);
	JSONObject saveStatus();
}

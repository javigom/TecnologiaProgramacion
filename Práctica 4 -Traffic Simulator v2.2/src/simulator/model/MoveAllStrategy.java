package simulator.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.JSONObject;

public class MoveAllStrategy implements DequeuingStrategy {

	//	CONSTRUCTOR
	
	public MoveAllStrategy() {
		
	}
	
	//	MÉTODOS
	
	//Devuelve una lista nueva con los vehículos de la cola
	public List<Vehicle> dequeue(List<Vehicle> q){
		
		List<Vehicle> list = new ArrayList<Vehicle>();
		
		Iterator<Vehicle> it = q.iterator();
		
		while(it.hasNext()) {
			list.add(it.next());
		}
		
		return list;
	}
	
	@Override
	public JSONObject saveStatus() {
		JSONObject info = new JSONObject();
		info.put("type", "move_all_dqs");
		JSONObject data = new JSONObject();
		info.put("data", data);
		return info;
	}
}

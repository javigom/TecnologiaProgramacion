package simulator.model;

import org.json.JSONArray;
import org.json.JSONObject;

public class NewJuntionEvent extends Event {
	
	//	ATRIBUTOS
	
	private String id;
	private LightSwitchingStrategy lss;
	private DequeuingStrategy dqs;
	private int xCoor;
	private int yCoor;
	
	//	CONSTRUCTOR
	
	public NewJuntionEvent(int time, String id, LightSwitchingStrategy lss, DequeuingStrategy dqs, int xCoor, int yCoor) {
		super(time);
		this.id = id;
		this.lss = lss;
		this.dqs = dqs;
		this.xCoor = xCoor;
		this.yCoor = yCoor;
	}

	//MÉTODOS
	
	//Añade el cruce al mapa
	protected void execute(RoadMap map) {
		map.addJunction(new Junction(this.id, this.lss, this.dqs, this.xCoor, this.yCoor));
	}
	
	public String toString() {
		return "New Junction '" + this.id + "'";
	}

	@Override
	protected JSONObject saveStatus() {
		JSONObject info = new JSONObject();
		info.put("type", "new_junction");
		
		JSONObject data = new JSONObject();
		data.put("time", this.getTime());
		data.put("id", this.id);
		
		JSONArray coor = new JSONArray();
		coor.put(this.xCoor);
		coor.put(this.yCoor);
		
		data.put("coor", coor);
		data.put("ls_strategy", this.lss.saveStatus());
		data.put("dq_strategy", this.dqs.saveStatus());
		
		info.put("data", data);
		return info;
	}
	
}

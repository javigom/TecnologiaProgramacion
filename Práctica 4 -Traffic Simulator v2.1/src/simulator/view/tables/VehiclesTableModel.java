package simulator.view.tables;

import java.util.List;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.Vehicle;

public class VehiclesTableModel extends GenericTableModel<Vehicle> {

	private static final long serialVersionUID = 1L;
	
	//	CONSTRUCTOR
	
	public VehiclesTableModel(Controller _ctrl, String[] colNames) {
		super(colNames);
		_ctrl.addObserver(this);
	}
	
	//	MÉTODOS

	public Object getValueAt(int rowIndex, int columnIndex) {
		
		Object s = null;
		switch (columnIndex) {
		case 0:
			s = _elements.get(rowIndex).getId();
			break;
		case 1:
			s = _elements.get(rowIndex).getRoadAndLocation();
			break;
		case 2:
			s = _elements.get(rowIndex).getItinerary();
			break;
		case 3:
			s = _elements.get(rowIndex).getContaminationClass();
			break;
		case 4: 
			s = _elements.get(rowIndex).getMaxSpeed();
			break;
		case 5: 
			s = _elements.get(rowIndex).getCurrentSpeed();
			break;
		case 6: 
			s = _elements.get(rowIndex).getCO2();
			break;
		case 7: 
			s = _elements.get(rowIndex).getDistance();
			break;
		}
		
		return s;
	}

	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {}

	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		update(map.getVehicles());
	}

	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		update(map.getVehicles());
	}

	public void onReset(RoadMap map, List<Event> events, int time) {
		update(map.getVehicles());
	}

	public void onRegister(RoadMap map, List<Event> events, int time) {
		update(map.getVehicles());
	}

	public void onError(String err) {}

}

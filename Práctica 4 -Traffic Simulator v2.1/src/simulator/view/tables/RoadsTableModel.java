package simulator.view.tables;

import java.util.List;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.Road;
import simulator.model.RoadMap;

public class RoadsTableModel extends GenericTableModel<Road> {

	private static final long serialVersionUID = 1L;
	
	//	CONSTRUCTOR
	
	public RoadsTableModel(Controller _ctrl, String[] colNames) {
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
			s = _elements.get(rowIndex).getLength();
			break;
		case 2:
			s = _elements.get(rowIndex).getWeather();
			break;
		case 3:
			s = _elements.get(rowIndex).getMaxSpeed();
			break;
		case 4: 
			s = _elements.get(rowIndex).getCurrentSpeed();
			break;
		case 5: 
			s = _elements.get(rowIndex).getTotalContamination();
			break;
		case 6: 
			s = _elements.get(rowIndex).getContaminationAlarmLimit();
			break;
		}
		return s;
	}
	
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {}

	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		update(map.getRoads());	
	}

	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		update(map.getRoads());	
	}

	public void onReset(RoadMap map, List<Event> events, int time) {
		update(map.getRoads());	
	}

	public void onRegister(RoadMap map, List<Event> events, int time) {
		update(map.getRoads());	
	}

	public void onError(String err) {}

}

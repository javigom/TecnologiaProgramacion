package simulator.view.tables;

import java.util.List;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.Junction;
import simulator.model.RoadMap;

public class JunctionsTableModel extends GenericTableModel<Junction> {

	private static final long serialVersionUID = 1L;
	
	//	CONSTRUCTOR
	
	public JunctionsTableModel(Controller _ctrl, String[] colNames) {
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
			s = _elements.get(rowIndex).getRoadGreenLightIndex();
			break;
		case 2:
			s = _elements.get(rowIndex).getQueues();
			break;
		}
		return s;
	}

	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {}

	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		update(map.getJunctions());
	}

	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		update(map.getJunctions());	
	}

	public void onReset(RoadMap map, List<Event> events, int time) {
		update(map.getJunctions());
	}

	public void onRegister(RoadMap map, List<Event> events, int time) {
		update(map.getJunctions());
	}

	public void onError(String err) {}

}
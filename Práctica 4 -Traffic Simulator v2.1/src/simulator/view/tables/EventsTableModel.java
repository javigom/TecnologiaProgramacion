package simulator.view.tables;

import java.util.List;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;

public class EventsTableModel extends GenericTableModel<Event> {
	
	private static final long serialVersionUID = 1L;
	
	//	CONSTRUCTOR
	
	public EventsTableModel(Controller _ctrl, String[] colNames) {
		super(colNames);
		_ctrl.addObserver(this);
	}

	//	MÉTODOS

	public Object getValueAt(int rowIndex, int columnIndex) {
		Object s = null;
		switch (columnIndex) {
		case 0:
			s = _elements.get(rowIndex).getTime();
			break;
		case 1:
			s = _elements.get(rowIndex).toString();
			break;
		}
		return s;
	}
	
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {}

	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		update(events);
	}

	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		update(events);	
	}

	public void onReset(RoadMap map, List<Event> events, int time) {
		update(events);	
	}

	public void onRegister(RoadMap map, List<Event> events, int time) {
		update(events);
	}

	public void onError(String err) {}
}

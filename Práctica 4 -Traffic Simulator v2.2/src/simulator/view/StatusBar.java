package simulator.view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

public class StatusBar extends JPanel implements TrafficSimObserver {
	
	private static final long serialVersionUID = 1L;

	//	ATRIBUTOS
	
	private JLabel currentTime;
	private JLabel eventAdded;
	
	
	//	CONSTRUCTOR
	
	public StatusBar(Controller c) {
		initGUI();
		c.addObserver(this);
	}
	
	
	//	MÉTODOS
	
	private void initGUI() {
		
		this.setLayout( new FlowLayout( FlowLayout.LEFT ));
		this.setBorder( BorderFactory.createBevelBorder(1));
		
		this.currentTime = new JLabel("0");
		add(new JLabel("Time:"));
		add(this.currentTime);
		
		JSeparator separator = new JSeparator(SwingConstants.VERTICAL); 
		separator.setPreferredSize(new Dimension(10, 20));  
		add(separator);
			
		this.eventAdded = new JLabel("Welcome!");
		add(this.eventAdded);
		
	}
	
	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		this.currentTime.setText(Integer.toString(time));
		
		int i = 0;
		
		//Si hay varios eventos en un mismo tiempo, sólo muestro el último
		if(events.size() > 0 && events.get(i).getTime() == time)	{
			while(i < events.size() && events.get(i).getTime() == time) {
				this.eventAdded.setText("Event added (" + events.get(i).toString() + ")");
				i++;
			}
		}
		
		else {
			this.eventAdded.setText("");
		}
		
	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		this.currentTime.setText("0");
		this.eventAdded.setText("Welcome!");
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {}

	@Override
	public void onError(String err) {
		this.eventAdded.setText(err);
	}

}

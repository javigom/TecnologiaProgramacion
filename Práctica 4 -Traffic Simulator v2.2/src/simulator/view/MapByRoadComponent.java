package simulator.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.Junction;
import simulator.model.Road;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;
import simulator.model.Vehicle;
import simulator.model.Weather;

public class MapByRoadComponent extends JComponent implements TrafficSimObserver {

	private static final long serialVersionUID = 1L;
	
	private static final int _JRADIUS = 10;

	private static final Color _BG_COLOR = Color.WHITE;
	private static final Color _JUNCTION_COLOR = Color.BLUE;
	private static final Color _JUNCTION_LABEL_COLOR = new Color(200, 100, 0);
	private static final Color _GREEN_LIGHT_COLOR = Color.GREEN;
	private static final Color _RED_LIGHT_COLOR = Color.RED;
	
	private RoadMap _map;
	
	private Image _car;
	
	private Image _weatherSunny, _weatherCloudy, _weatherRainy, _weatherWindy, _weatherStorm;
	private Image _cont0, _cont1, _cont2, _cont3, _cont4, _cont5;
	
	MapByRoadComponent(Controller ctrl) {
		initGUI();
		ctrl.addObserver(this);
	}
	
	private void initGUI() {
		_car = loadImage("car.png");
		
		_weatherSunny = loadImage("sun.png");
		_weatherCloudy = loadImage("cloud.png");
		_weatherRainy = loadImage("rain.png");
		_weatherWindy = loadImage("wind.png");
		_weatherStorm = loadImage("storm.png");
		
		_cont0 = loadImage("cont_0.png");
		_cont1 = loadImage("cont_1.png");
		_cont2 = loadImage("cont_2.png");
		_cont3 = loadImage("cont_3.png");
		_cont4 = loadImage("cont_4.png");
		_cont5 = loadImage("cont_5.png");
		
		setPreferredSize(new Dimension(300, 200));
		
	}

	// loads an image from a file
	private Image loadImage(String img) {
		Image i = null;
		try {
			return ImageIO.read(new File("resources/icons/" + img));
		} catch (IOException e) {
		}
		return i;
	}
	
	public void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);
		Graphics2D g = (Graphics2D) graphics;
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

		// clear with a background color
		g.setColor(_BG_COLOR);
		g.clearRect(0, 0, getWidth(), getHeight());

		if (_map == null || _map.getJunctions().size() == 0) {
			g.setColor(Color.red);
			g.drawString("No map yet!", getWidth() / 2 - 50, getHeight() / 2);
		} else {
			updatePrefferedSize();
			drawMap(g);
		}
	}
	
	private void drawMap(Graphics g) {
		int i = 0;
		int x1 = 50, 
			x2 = getWidth() - 100;
		
		for (Road r : _map.getRoads()) {
			
			int y = (i + 1)*50;
			
			//ROADS
			
			int roadColorValue = 200 - (int) (200.0 * Math.min(1.0, (double) r.getTotalContamination() / (1.0 + (double) r.getContaminationAlarmLimit())));
			Color roadColor = new Color(roadColorValue, roadColorValue, roadColorValue);
			g.setColor(roadColor);
			g.drawLine(x1, y, x2,  y);
			g.setColor(Color.BLACK);
			g.drawString(r.getId(), x1 - 30, y);
			
			
			//Dibuja la imagen del tiempo
			Image weather = this.selectWeatherImage(r.getWeather());
			g.drawImage(weather, x2 + 15, y - 20, 32, 32, this);
			
			
			//Dibuja la imagen de la contaminación
			int c = (int) Math.floor(Math.min((double) r.getTotalContamination()/(1.0 + (double) r.getContaminationAlarmLimit()), 1.0) / 0.19);
			Image cont = this.selectContImage(c);
			g.drawImage(cont, x2 + 52, y - 20, 32, 32, this);
			
			
			//JUNCTIONS
			
			Junction jSource = r.getSource();
			Junction jDest = r.getDestination();
			
			g.setColor(_JUNCTION_COLOR);
			g.fillOval(x1 - _JRADIUS / 2, y - _JRADIUS / 2, _JRADIUS, _JRADIUS);
			
			g.setColor(_JUNCTION_LABEL_COLOR);
			g.drawString(jSource.getId(), x1, y - 10);
			
			Color circleColor = _RED_LIGHT_COLOR;
			int idx = jDest.getGreenLightIndex();
			if (idx != -1 && r.equals(jDest.getInRoads().get(idx))) {
				circleColor = _GREEN_LIGHT_COLOR;
			}
			
			g.setColor(circleColor);
			g.fillOval(x2 - _JRADIUS / 2, y - _JRADIUS / 2, _JRADIUS, _JRADIUS);
			
			g.setColor(_JUNCTION_LABEL_COLOR);
			g.drawString(jSource.getId(), x2, y - 10);
			
			
			//VEHICLES
			
			for(Vehicle v: _map.getVehicles()) {
				if(v.getCurrentRoad() == r) {
					int vX = x1 + (int) ((x2 - x1) * ((double) v.getLocation() / (double) r.getLength()));
					g.drawImage(_car, vX, y - 10, 16, 16, this);
					g.setColor(_GREEN_LIGHT_COLOR);
					g.drawString(v.getId(), vX, y - 10);
				}
			}
	
			i++;
		}
	}
	
	
	// this method is used to update the preffered and actual size of the component,
	// so when we draw outside the visible area the scrollbars show up
	private void updatePrefferedSize() {
		int maxW = 200;
		int maxH = 200;
		for (Junction j : _map.getJunctions()) {
			maxW = Math.max(maxW, j.getX());
			maxH = Math.max(maxH, j.getY());
		}
		maxW += 20;
		maxH += 20;
		
		if (maxW > getWidth() || maxH > getHeight()) {
		    setPreferredSize(new Dimension(maxW, maxH));
		    setSize(new Dimension(maxW, maxH));
		}
	}
	
	public void update(RoadMap map) {
		_map = map;
		repaint();
	}
	
	private Image selectWeatherImage(Weather w) {
		Image weather;
		switch(w) {
		case SUNNY:
			weather = this._weatherSunny;
			break;
		case CLOUDY:
			weather = this._weatherCloudy;
			break;
		case RAINY:
			weather = this._weatherRainy;
			break;
		case WINDY:
			weather = this._weatherWindy;
			break;
		case STORM:
			weather = this._weatherStorm;
			break;
		default: 
			weather = null;
			break;
		}
		
		return weather;
	}
	private Image selectContImage(int c) {
		Image cont;
		
		switch(c) {
		case 0:
			cont = this._cont0;
			break;
		case 1:
			cont = this._cont1;
			break;
		case 2:
			cont = this._cont2;
			break;
		case 3:
			cont = this._cont3;
			break;
		case 4:
			cont = this._cont4;
			break;
		case 5:
			cont = this._cont5;
			break;
		default:
			cont = null;
			break;
		}
		
		return cont;
	}
	
	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		update(map);
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		update(map);
	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		update(map);
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		update(map);
	}

	@Override
	public void onError(String err) {
	}

}

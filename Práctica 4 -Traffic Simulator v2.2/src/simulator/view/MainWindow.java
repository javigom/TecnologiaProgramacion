package simulator.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;

import simulator.control.Controller;
import simulator.view.tables.EventsTableModel;
import simulator.view.tables.JunctionsTableModel;
import simulator.view.tables.RoadsTableModel;
import simulator.view.tables.VehiclesTableModel;

public class MainWindow extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	
	//	ATRIBUTOS
	
	private Controller _ctrl;
	
	
	//	CONSTRUCTOR
	
	public MainWindow(Controller ctrl) {
		super("Traffic Simulator");
		this._ctrl = ctrl;
		initGUI();
	}
	
	
	//	MÉTODOS
	
	private void initGUI() {
		
		//Panel principal
		JPanel mainPanel = new JPanel(new BorderLayout());
		this.setContentPane(mainPanel);
		
		//Panel de control
		ControlPanel cp = new ControlPanel(this._ctrl);
		mainPanel.add(cp, BorderLayout.PAGE_START);
		mainPanel.add(new StatusBar(this._ctrl), BorderLayout.PAGE_END);
		
		//Panel central que contendrá tablas y mapas
		JPanel viewsPanel = new JPanel();
		mainPanel.add(viewsPanel, BorderLayout.CENTER);
		
		//Panel de las tablas
		JPanel tablesPanel = new JPanel();
		tablesPanel.setLayout(new BoxLayout(tablesPanel, BoxLayout.Y_AXIS));
		viewsPanel.add(tablesPanel);
		
		//Panel de los mapas
		JPanel mapsPanel = new JPanel();
		mapsPanel.setLayout(new BoxLayout(mapsPanel, BoxLayout.Y_AXIS));
		viewsPanel.add(mapsPanel);
		
		//tables
		
		String[] colNamesEvent = {"Time", "Description"};
		JTable tEvents = new JTable(new EventsTableModel(this._ctrl, colNamesEvent));
		tEvents.setBackground(new Color(254, 255, 220));
		JPanel eventsView = createViewPanel(tEvents, "Events");
		eventsView.setPreferredSize(new Dimension(500, 200));
		tablesPanel.add(eventsView);
		
		String[] colNamesVehicle = { "Id", "Location", "Itinerary", "CO2 Class", "Max. Speed", "Speed", "Total CO2", "Distance"};
		JTable tVehicles = new JTable(new VehiclesTableModel(this._ctrl, colNamesVehicle));
		tVehicles.setBackground(new Color(220, 255, 228));
		JPanel vehiclesView = createViewPanel(tVehicles, "Vehicles");
		vehiclesView.setPreferredSize(new Dimension(500, 200));
		tablesPanel.add(vehiclesView);
		
		String[] colNamesRoad = { "Id", "Length", "Weather", "Max. Speed", "Speed Limit", "Total CO2", "CO2 Limit"};
		JTable tRoads = new JTable(new RoadsTableModel(this._ctrl, colNamesRoad));
		tRoads.setBackground(new Color(220, 238, 255));
		JPanel roadsView = createViewPanel(tRoads, "Roads");
		roadsView.setPreferredSize(new Dimension(500, 200));
		tablesPanel.add(roadsView);
		
		String[] colNamesJunction = { "Id", "Green", "Queues"};
		JTable tJunctions = new JTable(new JunctionsTableModel(this._ctrl, colNamesJunction));
		tJunctions.setBackground(new Color(234, 220, 255));
		JPanel junctionsView = createViewPanel(tJunctions, "Junctions");
		junctionsView.setPreferredSize(new Dimension(500, 200));
		tablesPanel.add(junctionsView);
	
		//maps

		JPanel mapView = createViewPanel(new MapComponent(this._ctrl), "Map");
		mapView.setPreferredSize(new Dimension(500, 400));
		mapsPanel.add(mapView);
		
		JPanel mapByRoad = createViewPanel(new MapByRoadComponent(this._ctrl), "Map by Road");
		mapByRoad.setPreferredSize(new Dimension(500, 400));
		mapsPanel.add(mapByRoad);
		
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		
		//WindowListener para cerrar el simulador con el aspa
		this.addWindowListener(new WindowListener(){

			public void windowClosing(WindowEvent arg0) {
				cp.quit();
			}

			public void windowActivated(WindowEvent arg0) {}
			public void windowClosed(WindowEvent arg0) {}
			public void windowDeactivated(WindowEvent arg0) {}
			public void windowDeiconified(WindowEvent arg0) {}
			public void windowIconified(WindowEvent arg0) {}
			public void windowOpened(WindowEvent arg0) {}
			
		});
		
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	
	//Crea un JPanel dada una componente c y un título
	private JPanel createViewPanel(JComponent c, String title) {
		JPanel p = new JPanel(new BorderLayout());
		
		//add a framed border to p with tittle
		TitledBorder border;
		border = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), title);
		p.setBorder(border);
		
		p.add(new JScrollPane(c));
		return p;
	}
	
}

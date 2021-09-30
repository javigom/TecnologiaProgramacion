package simulator.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JToolBar;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;

import simulator.control.Controller;
import simulator.misc.Pair;
import simulator.model.Event;
import simulator.model.Road;
import simulator.model.RoadMap;
import simulator.model.SetContClassEvent;
import simulator.model.SetWeatherEvent;
import simulator.model.TrafficSimObserver;
import simulator.model.Vehicle;
import simulator.model.Weather;
import simulator.view.dialogs.GenericDialogModel;

public class ControlPanel extends JPanel implements TrafficSimObserver {

	private static final long serialVersionUID = 1L;

	//	ATRIBUTOS
	
	private Controller ctrl;
	private JToolBar toolBar;
	
	private JButton openButton, changeCO2Button, changeWeatherButton, runButton, stopButton, exitButton;
	private JLabel ticksLabel;
	private JSpinner ticksSpinner;
	
	private boolean stopped;
	private RoadMap roadMap;
	
	
	//	CONSTRUCTOR
	
	public ControlPanel(Controller _ctrl) {
		this.ctrl = _ctrl;
		initGUI();
		this.ctrl.addObserver(this);
		this.stopped = false;
		
		if(!ctrl.getCorrectFile()) {
			enableToolBar(false);
			openButton.setEnabled(true);
			exitButton.setEnabled(true);
		}
	}
	
	
	//	MÉTODOS
	
	private void initGUI() {
		
		//Botón de carga de un fichero de eventos
		this.openButton = new JButton();
		this.openButton.setIcon(new ImageIcon("resources/icons/open.png"));
		this.openButton.setToolTipText("Load an input file");
		this.openButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				openFile();
			}
		});

		//Botón de cambio de la clase de contaminación
		this.changeCO2Button = new JButton();
		this.changeCO2Button.setIcon(new ImageIcon("resources/icons/co2class.png"));
		this.changeCO2Button.setToolTipText("Change the CO2 class of a vehicle");
		this.changeCO2Button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(roadMap.getVehicles().size() > 0)
					openCO2Dialog();
				else showError("Something went wrong: " + "The vehicle list is empty.");
			}	
		});
		
		//Botón de cambio de condiciones atmosféricas
		this.changeWeatherButton = new JButton();
		this.changeWeatherButton.setIcon(new ImageIcon("resources/icons/weather.png"));
		this.changeWeatherButton.setToolTipText("Change the Weather of a road");
		this.changeWeatherButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(roadMap.getRoads().size() > 0)
					openWeatherDialog();
				else showError("Something went wrong: " + "The road list is empty.");
			}	
		});
				
		//Botón de ejecución
		this.runButton = new JButton();
		this.runButton.setIcon(new ImageIcon("resources/icons/run.png"));
		this.runButton.setToolTipText("Run the simulation");
		this.runButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				stopped = false;
				enableToolBar(false);
				stopButton.setEnabled(true);
				int ticks = (int) ticksSpinner.getValue();
				run_sim(ticks);	
			}
		});
		
		//Botón de parada
		this.stopButton = new JButton();
		this.stopButton.setIcon(new ImageIcon("resources/icons/stop.png"));
		this.stopButton.setToolTipText("Stop the simulation");
		this.stopButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				stop();
			}	
		});
		
		
		//Spinner para contador de ticks
		this.ticksLabel = new JLabel("Ticks: ");
		this.ticksSpinner = new JSpinner();
		SpinnerNumberModel spinnerModel = new SpinnerNumberModel(1, 1, null, 1);  
		this.ticksSpinner.setModel(spinnerModel);
		this.ticksSpinner.setPreferredSize(new Dimension(80, 50));
		
		//Botón de salida del simulador
		this.exitButton = new JButton();
		this.exitButton.setIcon(new ImageIcon("resources/icons/exit.png"));
		this.exitButton.setToolTipText("Close the simulator");
		this.exitButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				quit();
			}
		});
				
		this.toolBar = new JToolBar();
		this.toolBar.setPreferredSize(new Dimension(1000, 50));
		this.toolBar.add(this.openButton);
		this.toolBar.addSeparator();
		this.toolBar.add(this.changeCO2Button);
		this.toolBar.add(this.changeWeatherButton);
		this.toolBar.addSeparator();
		this.toolBar.add(this.runButton);
		this.toolBar.add(this.stopButton);
		this.toolBar.addSeparator();
		this.toolBar.add(this.ticksLabel);
		this.toolBar.addSeparator();
		this.toolBar.add(this.ticksSpinner);
		this.toolBar.addSeparator();
		this.toolBar.addSeparator();
		this.toolBar.add(new JPanel(new BorderLayout()));
		this.toolBar.add(this.exitButton);
		this.add(toolBar);
	}
	
	//Abre fichero de entrada (fichero normal o save)
	private void openFile() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(new File("resources/examples"));
		int option = fileChooser.showOpenDialog(null);
		
		if(option == JFileChooser.APPROVE_OPTION) {
			try {
				File input = fileChooser.getSelectedFile();
				FileInputStream fileInputStream = new FileInputStream(input);
				ctrl.reset();
				ctrl.loadEvents(fileInputStream);
				enableToolBar(true);
			}
			
			catch(Exception e) {
				enableToolBar(false);
				openButton.setEnabled(true);
				exitButton.setEnabled(true);
				showError("Something went wrong: " + e.getLocalizedMessage());
			}
		}
	}
	
	//Muestra ventana de dialogo para salir del simulador
	protected void quit() {

		String ops[] = {"Yes", "No"};
		
		int n = JOptionPane.showOptionDialog(getParent(), "Are you sure you want to quit?", "Quit",  JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, new ImageIcon("resources/icons/question.png"), ops, 1);
		if(n == 0) {
			System.exit(0);
		}
		
	}
	
	//Lanza el simulador n ticks
	private void run_sim(int n) {
		if(n > 0 && !stopped) {
			try {
				Thread.sleep(50);
				this.ctrl.run(1);
				Thread.sleep(50);
			} catch (Exception e) {
				showError("Something went wrong: " + e.getLocalizedMessage());
				stopped = true;
				return;
			}
			 
			SwingUtilities.invokeLater(new Runnable() {
				
				public void run() {
					run_sim(n - 1);
				}
			});
		}
		else {
			enableToolBar(true);
			stopped = true;
		}
	}
	
	//Para el simulador
	private void stop() {
		stopped = true;
	}
	
	//Activa/desactiva la barra de herramientas
	private void enableToolBar(boolean enable) {
		JButton[] list = new JButton[] {openButton, changeCO2Button, changeWeatherButton, runButton, stopButton, exitButton};
		for(JButton b: list) {
			b.setEnabled(enable);
		}
		
		this.ticksLabel.setEnabled(enable);
		this.ticksSpinner.setEnabled(enable);
		
	}
	
	//Lanza ventana para cambiar el co2 de un vehículo
	private void openCO2Dialog() {
		String title = "Change CO2 Class";
		String desc =  "Schedule an event to change the CO2 class of a vehicle after a given number of simmulation ticks for now.";
		GenericDialogModel<Vehicle> dialog = new GenericDialogModel<Vehicle>((Frame) SwingUtilities.getWindowAncestor(this), title, desc, "Vehicle", "CO2 Class");
		
		List<String> c = new ArrayList<>();
		for(int i = 0; i < 10; i++) {
			c.add("" + i);
		}
		
		int n = dialog.open(roadMap.getVehicles(), c);
		
		if(n == 1) {
			List<Pair<String, Integer>> cs = new ArrayList<>();
			Pair<String, Integer> pair = new Pair<String, Integer>(dialog.getFirst().toString(), Integer.parseInt(dialog.getSecond()));
			cs.add(pair);
			
			try {
				ctrl.addEvent(new SetContClassEvent(ctrl.getTimeSlot() + dialog.getTicks(), cs));
			} catch(Exception e) {
				showError("Something went wrong: \n" + e.getLocalizedMessage());
			}
		}
		
	}
	
	//Lanza ventana para cambiar el tiempo de una carretera
	private void openWeatherDialog() {
		String title = "Change Road Weather";
		String desc =  "Schedule an event to change the weather of a road after given number of simulation ticks from now.";
		GenericDialogModel<Road> dialog = new GenericDialogModel<Road>((Frame) SwingUtilities.getWindowAncestor(this), title, desc, "Road", "Weather");
		
		List<String> w = new ArrayList<>();
		w.add(Weather.SUNNY.toString());
		w.add(Weather.CLOUDY.toString());
		w.add(Weather.RAINY.toString());
		w.add(Weather.WINDY.toString());
		w.add(Weather.STORM.toString());
		
		int n = dialog.open(roadMap.getRoads(), w);
		
		if(n == 1) {
			List<Pair<String, Weather>> ws = new ArrayList<>();
			Pair<String, Weather> pair = new Pair<String, Weather>(dialog.getFirst().toString(), Weather.valueOf(dialog.getSecond()));
			ws.add(pair);
			
			try {
				ctrl.addEvent(new SetWeatherEvent(ctrl.getTimeSlot() + dialog.getTicks(), ws));
			} catch(Exception e) {
				showError("Something went wrong: " + e.getLocalizedMessage());
			}
			
		}
	}
	
	//Lanza una ventana de dialogo informando de un error con el mensaje
	private void showError(String message) {
		ctrl.reportError(message);
		JOptionPane.showMessageDialog(getParent(), message, "Message", JOptionPane.INFORMATION_MESSAGE , new ImageIcon("resources/icons/information.png"));
	}
	
	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		roadMap = map;
	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		roadMap = map;
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		roadMap = map;
	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		roadMap = map;
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		roadMap = map;
	}

	@Override
	public void onError(String err) {}

}

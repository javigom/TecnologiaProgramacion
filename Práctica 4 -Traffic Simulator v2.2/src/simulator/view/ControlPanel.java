package simulator.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintStream;
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

import org.json.JSONObject;
import org.json.JSONTokener;

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
	
	private JButton openButton, changeCO2Button, changeWeatherButton, runButton, stopButton, exitButton, resetButton, undoButton, deleteEventButton, saveButton, loadButton;
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
		
		undoButton.setEnabled(false);
		resetButton.setEnabled(false);
		deleteEventButton.setEnabled(false);
	}
	
	
	//	MÉTODOS
	
	private void initGUI() {
		
		//Botón de carga de un fichero de eventos
		this.openButton = new JButton();
		this.openButton.setIcon(new ImageIcon("resources/icons/open.png"));
		this.openButton.setToolTipText("Load an input file");
		this.openButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				openFile(false, "examples");
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
		
		this.deleteEventButton = new JButton();
		this.deleteEventButton.setIcon(new ImageIcon("resources/icons/delete.png"));
		this.deleteEventButton.setToolTipText("Delete an event");
		this.deleteEventButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(ctrl.getEvents().size() > 0)
					openDeleteEventDialog();
				else showError("Something went wrong: " + "The event list is empty.");	
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
		
		//Botón reset
		this.resetButton = new JButton();
		this.resetButton.setIcon(new ImageIcon("resources/icons/reset.png"));
		this.resetButton.setToolTipText("Reset the simulator to the time 0");
		this.resetButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				try {
					ctrl.restart(true);
					undoButton.setEnabled(false);
					resetButton.setEnabled(false);
					deleteEventButton.setEnabled(false);
					
				} catch (IOException e) {
					showError("Something went wrong: " + e.getLocalizedMessage());
				}
			}
		});
		
		//Botón para volver al estado anterior
		this.undoButton = new JButton();
		this.undoButton.setIcon(new ImageIcon("resources/icons/undo.png"));
		this.undoButton.setToolTipText("Return to the previous state");
		this.undoButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				try {
					int ticks = ctrl.getTimeSlot();
					ctrl.restart(false);
					ctrl.run(ticks - 1);
					
					if(ctrl.getTimeSlot() == 0) {
						undoButton.setEnabled(false);
						resetButton.setEnabled(false);
						deleteEventButton.setEnabled(false);
					}
					
				} catch (IOException e) {
					showError("Something went wrong: " + e.getLocalizedMessage());
				}
			}
		});
		
		//Botón para guardar el estado de la simulación
		this.saveButton = new JButton();
		this.saveButton.setIcon(new ImageIcon("resources/icons/save.png"));
		this.saveButton.setToolTipText("Save the simulation into a file");
		this.saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				saveStatusSim();
			}
		});
		
		//Botón para cargar el estado de la simulación
		this.loadButton = new JButton();
		this.loadButton.setIcon(new ImageIcon("resources/icons/load.png"));
		this.loadButton.setToolTipText("Load the simulation from a file");
		this.loadButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				openFile(true, "saves");
			}
		});
		
		
		this.toolBar = new JToolBar();
		this.toolBar.setPreferredSize(new Dimension(1000, 50));
		this.toolBar.add(this.openButton);
		this.toolBar.addSeparator();
		this.toolBar.add(this.changeCO2Button);
		this.toolBar.add(this.changeWeatherButton);
		this.toolBar.add(this.deleteEventButton);
		this.toolBar.addSeparator();
		this.toolBar.add(this.runButton);
		this.toolBar.add(this.stopButton);
		this.toolBar.addSeparator();
		this.toolBar.add(this.ticksLabel);
		this.toolBar.addSeparator();
		this.toolBar.add(this.ticksSpinner);
		this.toolBar.addSeparator();
		this.toolBar.add(this.resetButton);
		this.toolBar.add(this.undoButton);
		this.toolBar.addSeparator();
		this.toolBar.add(this.saveButton);
		this.toolBar.add(this.loadButton);
		this.toolBar.add(new JPanel(new BorderLayout()));
		this.toolBar.add(this.exitButton);
		this.add(toolBar);
	}
	
	private void openFile(boolean savedFile, String path) {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(new File("resources/" + path));
		int option = fileChooser.showOpenDialog(null);
		
		if(option == JFileChooser.APPROVE_OPTION) {
			try {
				File input = fileChooser.getSelectedFile();
				FileInputStream fileInputStream = new FileInputStream(input);
				ctrl.reset();
				ctrl.loadEvents(fileInputStream);
				enableToolBar(true);
				
				if(savedFile) {
					JSONObject jo = new JSONObject(new JSONTokener(new FileInputStream(input)));
					int ticks = jo.getInt("time");
					ctrl.run(ticks);
				}
			}
			
			catch(Exception e) {
				enableToolBar(false);
				openButton.setEnabled(true);
				exitButton.setEnabled(true);
				loadButton.setEnabled(true);
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
	
	//Activa/desactiva los botones de la barra de herramientas
	private void enableToolBar(boolean enable) {
		JButton[] list = new JButton[] {openButton, changeCO2Button, changeWeatherButton, runButton, stopButton, exitButton, resetButton, undoButton, deleteEventButton, saveButton, loadButton};
		for(JButton b: list) {
			b.setEnabled(enable);
		}
		
		this.ticksLabel.setEnabled(enable);
		this.ticksSpinner.setEnabled(enable);
		
	}
	
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
	
	private void openDeleteEventDialog() {
		
		List<Event> eventList = ctrl.getEvents();
		Object[] events = eventList.toArray();
		Event deleted = (Event) JOptionPane.showInputDialog(getParent(), "Available events: ", "Delete an event", JOptionPane.INFORMATION_MESSAGE, new ImageIcon("resources/icons/information.png"), events, null);
		
		if(deleted != null) {
			try {
				ctrl.deleteEvent(deleted);
			} catch (Exception e) {
				showError("Something went wrong: " + e.getLocalizedMessage());
			}
		}

	}
	
	private void saveStatusSim() {
		
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(new File("resources/saves"));
		int option = fileChooser.showSaveDialog(null);
		
		if(option == JFileChooser.APPROVE_OPTION) {
			try {
				File output = fileChooser.getSelectedFile();	
				PrintStream p = new PrintStream(output);
				p.print(ctrl.saveStatusSim());
				p.close();
			}
			
			catch(Exception e) {
				showError("Something went wrong: " + e.getLocalizedMessage());
			}
		}
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

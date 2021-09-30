package simulator.view.dialogs;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.SpinnerNumberModel;

import simulator.model.Road;
import simulator.model.Weather;

public class ChangeWeatherDialog extends JDialog {

	private static final long serialVersionUID = 1L;

	private int _status;
	
	private JComboBox<Road> _roads;
	private DefaultComboBoxModel<Road> _roadsModel;
	
	private JComboBox<Weather> _weather;
	private DefaultComboBoxModel<Weather> _weatherModel;
	
	private JSpinner _ticksSpinner;
	
	public ChangeWeatherDialog(Frame parent) {
		super(parent, true);
		initGUI();
	}
	
	private void initGUI() {
		
		_status = 0;
		
		setTitle("Change Road Weather");
		
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		setContentPane(mainPanel);
		
		JTextArea helpMsg = new JTextArea(2, 50);
		helpMsg.setEditable(false);
		helpMsg.setText("Schedule an event to change the weather of a road after given number of simulation ticks from now.");
		helpMsg.setLineWrap(true);
		helpMsg.setWrapStyleWord(true);
		helpMsg.setOpaque(false);
		helpMsg.setFont(new Font("Arial", Font.PLAIN, 14));
		mainPanel.add(helpMsg);
		mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
		
		
		//PANEL ELECCIÓN
		
		JPanel viewsPanel = new JPanel();
		viewsPanel.setAlignmentX(CENTER_ALIGNMENT);
		mainPanel.add(viewsPanel);
		mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
		
		//Selección de carretera
		viewsPanel.add(new JLabel("Road: "));
		_roadsModel = new DefaultComboBoxModel<>();
		_roads = new JComboBox<>(_roadsModel);
		_roads.setPreferredSize(new Dimension(100, 25));
		viewsPanel.add(_roads);
		
		//Selección de tiempo
		viewsPanel.add(new JLabel("Weather: "));
		_weatherModel = new DefaultComboBoxModel<>(Weather.values());
		_weather = new JComboBox<>(_weatherModel);
		_weather.setPreferredSize(new Dimension(70, 25));
		viewsPanel.add(_weather);
		
		//Spinner de ticks
		viewsPanel.add(new JLabel("Ticks: "));
		_ticksSpinner = new JSpinner();
		
		SpinnerNumberModel spinnerModel = new SpinnerNumberModel(1, 1, null, 1);  
		this._ticksSpinner.setModel(spinnerModel);
		
		_ticksSpinner.setPreferredSize(new Dimension(70, 25));
		viewsPanel.add(_ticksSpinner);
		
		//PANEL DE BOTONES
		
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setAlignmentX(CENTER_ALIGNMENT);
		mainPanel.add(buttonsPanel);
		
		//Botón cancelar
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				_status = 0;
				ChangeWeatherDialog.this.setVisible(false);
			}
		});
		buttonsPanel.add(cancelButton);
		
		//Botón confimación
		JButton okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				//Compruebo que los parámetros sean válidos, aunque no abro la ventana si no hay carreteras, el spinner tiene como valor mínimo 1 y el tiempo coge valor por defecto
				if(((int) _ticksSpinner.getValue() > 0) && _roads.getSelectedItem() != null && _weather.getSelectedItem() != null) {
					_status = 1;
					ChangeWeatherDialog.this.setVisible(false);
				}
			}
		});
		
		buttonsPanel.add(okButton);
		setPreferredSize(new Dimension(500, 170));
		pack();
		setResizable(false);
		setVisible(false);
	}

	public int open(List<Road> roads) {
		_roadsModel.removeAllElements();
		for(Road r: roads)
			_roadsModel.addElement(r);
		setLocationRelativeTo(getParent());
		setVisible(true);
		return _status;
	}
	
	public Road getRoad() {
		return (Road) _roads.getSelectedItem();
	}
	
	public Weather getWeather() {
		return  (Weather) _weather.getSelectedItem(); 
	}
	
	public int getTicks() {
		return (int) _ticksSpinner.getValue();
	}
}

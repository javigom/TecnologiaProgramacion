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

import simulator.model.Vehicle;

public class ChangeCO2ClassDialog extends JDialog {

	private static final long serialVersionUID = 1L;

	private int _status;
	
	private JComboBox<Vehicle> _vehicles;
	private DefaultComboBoxModel<Vehicle> _vehiclesModel;
	
	private JComboBox<String> _contClass;
	private String _levels[] = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
	
	private JSpinner _ticksSpinner;
	
	public ChangeCO2ClassDialog(Frame parent) {
		super(parent, true);
		initGUI();
	}
	
	private void initGUI() {
		
		_status = 0;
		
		setTitle("Change CO2 Class");
		
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		setContentPane(mainPanel);
		
		JTextArea helpMsg = new JTextArea(2, 50);
		helpMsg.setEditable(false);
		helpMsg.setText("Schedule an event to change the CO2 class of a vehicle after a given number of simmulation ticks for now.");
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
		
		//Selección de vehículo
		viewsPanel.add(new JLabel("Vehicle: "));
		_vehiclesModel = new DefaultComboBoxModel<>();
		_vehicles = new JComboBox<>(_vehiclesModel);
		_vehicles.setPreferredSize(new Dimension(100, 25));
		viewsPanel.add(_vehicles);
		
		//Selección de contclass
		viewsPanel.add(new JLabel("CO2 Class: "));
		_contClass = new JComboBox<>(_levels);
		_contClass.setPreferredSize(new Dimension(70, 25));
		viewsPanel.add(_contClass);
		
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
				ChangeCO2ClassDialog.this.setVisible(false);
			}
		});
		buttonsPanel.add(cancelButton);
		
		//Botón confimación
		JButton okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				//Compruebo que los parámetros sean válidos, aunque no abro la ventana si no hay vehículos, el spinner tiene como valor mínimo 1 y la clase de contaminación coge valor por defecto
				if(((int) _ticksSpinner.getValue() > 0) && _vehicles.getSelectedItem() != null && _contClass.getSelectedItem() != null) {
					_status = 1;
					ChangeCO2ClassDialog.this.setVisible(false);
				}
			}
		});
		
		buttonsPanel.add(okButton);
		setPreferredSize(new Dimension(500, 170));
		pack();
		setResizable(false);
		setVisible(false);
	}

	public int open(List<Vehicle> vehicles) {
		_vehiclesModel.removeAllElements();
		for(Vehicle v: vehicles)
			_vehiclesModel.addElement(v);
		setLocationRelativeTo(getParent());
		setVisible(true);
		return _status;
	}
	
	public Vehicle getVehicle() {
		return (Vehicle) _vehicles.getSelectedItem();
	}
	
	public int getContClass() {
		return  Integer.parseInt((String) _contClass.getSelectedItem()); 
	}
	
	public int getTicks() {
		return (int) _ticksSpinner.getValue();
	}
}

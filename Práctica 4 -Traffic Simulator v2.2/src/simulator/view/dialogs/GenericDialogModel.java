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

public class GenericDialogModel<T> extends JDialog {

	private static final long serialVersionUID = 1L;

	
	//	ATRIBUTOS
	
	private int _status;
	private JComboBox<T> _first;
	private DefaultComboBoxModel<T> _firstModel;
	private JComboBox<String> _second;
	private DefaultComboBoxModel<String> _secondModel;
	private JSpinner _ticksSpinner;
	
	
	//	CONSTRUCTOR
	
	public GenericDialogModel(Frame parent, String title, String desc, String first, String second) {
		super(parent, true);
		initGUI(title, desc, first, second);
	}


	//	MÉTODOS
	
	protected void initGUI(String title, String desc, String first, String second) {
		
		_status = 0;
		
		setTitle(title);
		
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		setContentPane(mainPanel);
		
		JTextArea helpMsg = new JTextArea(2, 50);
		helpMsg.setEditable(false);
		helpMsg.setText(desc);
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
		viewsPanel.add(new JLabel(first + ":"));
		_firstModel = new DefaultComboBoxModel<>();
		_first = new JComboBox<>(_firstModel);
		_first.setPreferredSize(new Dimension(100, 25));
		viewsPanel.add(_first);
		
		//Selección de tiempo
		viewsPanel.add(new JLabel(second + ":"));
		_secondModel = new DefaultComboBoxModel<>();
		_second = new JComboBox<>(_secondModel);
		_second.setPreferredSize(new Dimension(70, 25));
		viewsPanel.add(_second);
		
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
				GenericDialogModel.this.setVisible(false);
			}
		});
		buttonsPanel.add(cancelButton);	
		
		//Botón confimación
		JButton okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				if(((int) _ticksSpinner.getValue() > 0) && _first.getSelectedItem() != null && _second.getSelectedItem() != null) {
					_status = 1;
					GenericDialogModel.this.setVisible(false);
				}
			}
		});
		
		buttonsPanel.add(okButton);
		setPreferredSize(new Dimension(500, 170));
		pack();
		setResizable(false);
		setVisible(false);
	}
	
	public int open(List<T> elems1, List<String> elems2) {
		_firstModel.removeAllElements();
		for(T t: elems1)
			_firstModel.addElement(t);
		
		_secondModel.removeAllElements();
		for(String t: elems2)
			_secondModel.addElement(t);
		
		setLocationRelativeTo(getParent());
		setVisible(true);
		return _status;
	}

	
	public int getTicks() {
		return (int) _ticksSpinner.getValue();
	}
	
	@SuppressWarnings("unchecked")
	public T getFirst() {
		return (T) _first.getSelectedItem();
	}
	
	public String getSecond() {
		return (String) _second.getSelectedItem();
	}
	
}

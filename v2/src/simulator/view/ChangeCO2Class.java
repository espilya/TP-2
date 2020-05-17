package simulator.view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import simulator.model.RoadMap;
import simulator.model.Vehicle;

public class ChangeCO2Class extends JDialog{
	
	private JPanel mainPanel, textPanel, selectPanel, buttonPanel;
	private DefaultComboBoxModel<Vehicle> vehicleModel;
	private DefaultComboBoxModel<Integer> contClassModel;
	private JComboBox<Vehicle> veh;
	private JComboBox<Integer> cont;
	private JSpinner tickSpinner;
	private JButton submit, cancel;
	private int status;
	
	
	public ChangeCO2Class(Frame windowAncestor) {
		super(windowAncestor, true);
		this.initGUI();
	}
	
	private void initGUI() {
	status = 0;
	this.setLocation(275, 150);
	this.setTitle("Change CO2 class");
	mainPanel = new JPanel();
	mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
	setContentPane(mainPanel);
	
	textPanel = new JPanel();
	textPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
	JLabel label = new JLabel("<html>Schedule an event to change the CO2 class of a vehicle after a given <br> number of simulation ticks from now</html>");
	textPanel.add(label);
	mainPanel.add(textPanel);
	
	selectPanel = new JPanel();
	selectPanel.setAlignmentX(CENTER_ALIGNMENT);
	mainPanel.add(selectPanel);
	
	buttonPanel = new JPanel();
	buttonPanel.setAlignmentX(CENTER_ALIGNMENT);
	mainPanel.add(buttonPanel);
	
	vehicleModel = new DefaultComboBoxModel<Vehicle>();
	veh = new JComboBox<Vehicle>(vehicleModel);
	
	contClassModel = new DefaultComboBoxModel<Integer>();
	for(int i = 0; i <= 10; i++) contClassModel.addElement(i);
	cont = new JComboBox<>(contClassModel);
	
	tickSpinner = new JSpinner(new SpinnerNumberModel(10, 1, 5000, 1));
	tickSpinner.setMaximumSize(new Dimension(80, 40));
	tickSpinner.setMinimumSize(new Dimension(80, 40));
	tickSpinner.setPreferredSize(new Dimension(80, 40));
	
	selectPanel.add(new JLabel("Vehicle: "));
	selectPanel.add(veh);
	selectPanel.add(new JLabel("CO2 Class: "));
	selectPanel.add(cont);
	selectPanel.add(new JLabel("Ticks: "));
	selectPanel.add(tickSpinner);
	
	cancel = new JButton("Cancel");
	cancel.addActionListener(new ActionListener() {
		 public void actionPerformed(ActionEvent e) { 
			 status = 0;
			 ChangeCO2Class.this.setVisible(false);
		 } 
	});
	buttonPanel.add(cancel);

	
	submit = new JButton("Submit");
	submit.addActionListener(new ActionListener() {
		 public void actionPerformed(ActionEvent e) { 
			 if(vehicleModel.getSelectedItem() != null) {
				 status = 1;
				 ChangeCO2Class.this.setVisible(false);
			 } 
		 }
	});
	
	buttonPanel.add(submit);
	
	setPreferredSize(new Dimension(500, 200));
	pack();
	setResizable(false);
	setVisible(false);
	}

	public int open(RoadMap map) {
		vehicleModel.removeAllElements();
		for(Vehicle v : map.getVehicles()) {
			vehicleModel.addElement(v);
		}
		setLocation(275, 150);
		setVisible(true);
		return status;
	}

	Vehicle getVehicle() {
		return (Vehicle) vehicleModel.getSelectedItem();
	}
	int getCO2Class() {
		return (int) contClassModel.getSelectedItem();
	}
	int getTicks() {
		return (int) tickSpinner.getValue();
		
	}
}


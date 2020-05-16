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

public class ChangeCO2ClassDialog extends JDialog{
	
	private JPanel mainPanel, textPanel, selectPanel, buttonPanel;
	private DefaultComboBoxModel<Vehicle> vehicleModel;
	private DefaultComboBoxModel<Integer> co2ClassModel;
	private JComboBox<Vehicle> vehicle;
	private JComboBox<Integer> co2;
	private JSpinner ticksSpinner;
	private JButton cancelButt, okButt;
	private int status;
	
	
	public ChangeCO2ClassDialog(Frame windowAncestor) {
		super(windowAncestor, true);
		this.initGUI();
	}
	
	private void initGUI() {
	status = 0;
	this.setTitle("Change CO2 class");
	this.setLocation(275, 150);
	mainPanel = new JPanel();
	mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
	setContentPane(mainPanel);
	
	textPanel = new JPanel();
	textPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
	JLabel label1 = new JLabel("Schedule an event to change the CO2 class of a vehicle after a given");
	//label1.setHorizontalAlignment(Label.LEFT);
	textPanel.add(label1);
	JLabel label2 = new JLabel("number of simulation ticks from now");
	//label2.setHorizontalAlignment(Label.LEFT);
	textPanel.add(label2);
	mainPanel.add(textPanel);
	
	selectPanel = new JPanel();
	selectPanel.setAlignmentX(CENTER_ALIGNMENT);
	mainPanel.add(selectPanel);
	
	buttonPanel = new JPanel();
	buttonPanel.setAlignmentX(CENTER_ALIGNMENT);
	mainPanel.add(buttonPanel);
	
	vehicleModel = new DefaultComboBoxModel<Vehicle>();
	vehicle = new JComboBox<Vehicle>(vehicleModel);
	
	co2ClassModel = new DefaultComboBoxModel<Integer>();
	for(int i =0; i<= 10; i++) co2ClassModel.addElement(i);
	co2 = new JComboBox<>(co2ClassModel);
	
	ticksSpinner = new JSpinner(new SpinnerNumberModel(10, 1, 5000, 1));
	ticksSpinner.setToolTipText("Simulation tick to run: 1-5000");
	ticksSpinner.setMaximumSize(new Dimension(80, 40));
	ticksSpinner.setMinimumSize(new Dimension(80, 40));
	ticksSpinner.setPreferredSize(new Dimension(80, 40));
	
	selectPanel.add(new JLabel("Vehicle: "));
	selectPanel.add(vehicle);
	selectPanel.add(new JLabel("CO2 Class: "));
	selectPanel.add(co2);
	selectPanel.add(new JLabel("Ticks: "));
	selectPanel.add(ticksSpinner);
	
	cancelButt = new JButton("Cancel");
	cancelButt.addActionListener(new ActionListener() {
		 public void actionPerformed(ActionEvent e) { 
			 status = 0;
			 ChangeCO2ClassDialog.this.setVisible(false);
		 } 
	});
	buttonPanel.add(cancelButt);

	
	okButt = new JButton("Ok");
	okButt.addActionListener(new ActionListener() {
		 public void actionPerformed(ActionEvent e) { 
			 if(vehicleModel.getSelectedItem() != null) {
				 status = 1;
				 ChangeCO2ClassDialog.this.setVisible(false);
			 } 
		 }
	});
	buttonPanel.add(okButt);
	
	setPreferredSize(new Dimension(500, 200));
	pack();
	setResizable(false);
	setVisible(false);
	}

	public int open(RoadMap map) {
		vehicleModel.removeAllElements();
		for(Vehicle v : map.getVehicles())
			vehicleModel.addElement(v);

		//setLocation(getParent().getLocation().x + 10, getParent().getLocation().y + 10);
		setLocation(275, 150);
		setVisible(true);
		return status;
	}

	Vehicle getVehicle() {
		return (Vehicle) vehicleModel.getSelectedItem();
	}
	int getCO2Class() {
		return (int) co2ClassModel.getSelectedItem();
	}
	int getTicks() {
		return (int) ticksSpinner.getValue();
		
	}
}

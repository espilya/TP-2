package simulator.view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import simulator.model.RoadMap;
import simulator.model.Vehicle;

@SuppressWarnings("serial")
public class ChangeCO2ClassDialogList  extends JDialog{
	
	private JPanel mainPanel, textPanel, selectPanel, buttonPanel;
	private DefaultListModel<Vehicle> vehicleModel;
	private DefaultListModel<Integer> co2ClassModel;
	private JList<Vehicle> vehicle;
	private JList<Integer> co2;
	private JSpinner ticksSpinner;
	private JButton cancelButt, okButt;
	private int status;
	//los valores seleccionado
	private List<Vehicle> vehiclesSelected;
	private int co2Selected;
	
	
	public ChangeCO2ClassDialogList(Frame windowAncestor) {
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
	JLabel label2 = new JLabel("number of simulation ticks from now (Vehicle->Multiple choise)");
	//label2.setHorizontalAlignment(Label.LEFT);
	textPanel.add(label2);
	mainPanel.add(textPanel);
	
	selectPanel = new JPanel();
	selectPanel.setAlignmentX(CENTER_ALIGNMENT);
	mainPanel.add(selectPanel);
	
	buttonPanel = new JPanel();
	buttonPanel.setAlignmentX(CENTER_ALIGNMENT);
	mainPanel.add(buttonPanel);
	
	vehicleModel = new DefaultListModel<Vehicle>();
	vehicle = new JList<Vehicle>(vehicleModel);
	vehicle.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
	vehicle.setFixedCellHeight(19);
	//no funciona el scroll???
	//JScrollPane vehicleScroll = new JScrollPane(vehicle);
	//vehicleScroll.setPreferredSize(new Dimension(70, 80));
			
	vehicle.addListSelectionListener(new ListSelectionListener() {
		@Override
		public void valueChanged(ListSelectionEvent e) {
			if (e.getValueIsAdjusting() == false) 
				 if (e.getSource() == vehicle) 
					 vehiclesSelected =  vehicle.getSelectedValuesList();
		}
	});

	co2ClassModel = new DefaultListModel<Integer>();
	for(int i =0; i<= 10; i++) co2ClassModel.addElement(i);
	//no funciona el scroll???
	//JScrollPane co2Scroll = new JScrollPane(co2);
	//co2Scroll.setPreferredSize(new Dimension(70, 80));
	co2 = new JList<Integer>(co2ClassModel);
	co2.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	co2.setFixedCellHeight(19);
	co2.addListSelectionListener(new ListSelectionListener() {
		@Override
		public void valueChanged(ListSelectionEvent e) { 
			if (e.getValueIsAdjusting() == false) 
				 if (e.getSource() == co2) 
					 co2Selected =  co2.getSelectedValue();
		}
	});
	
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
			 ChangeCO2ClassDialogList.this.setVisible(false);
		 } 
	});
	buttonPanel.add(cancelButt);

	
	okButt = new JButton("Ok");
	okButt.addActionListener(new ActionListener() {
		 public void actionPerformed(ActionEvent e) { 
			 if(vehicle.getSelectedIndices() != null && co2.getSelectedValue() != null) {
				 status = 1;
				 ChangeCO2ClassDialogList.this.setVisible(false);
			 } 
		 }
	});
	buttonPanel.add(okButt);
	
	setPreferredSize(new Dimension(500, 370));
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

	List<Vehicle> getVehicle() {
		return vehiclesSelected;
	}
	int getCO2Class() {
		return co2Selected;
	}
	int getTicks() {
		return (int) ticksSpinner.getValue();
		
	}
}


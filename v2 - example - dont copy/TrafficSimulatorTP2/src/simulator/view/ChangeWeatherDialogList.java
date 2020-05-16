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

import simulator.model.Road;
import simulator.model.RoadMap;
import simulator.model.Weather;

@SuppressWarnings("serial")
public class ChangeWeatherDialogList extends JDialog{
	private JPanel mainPanel, textPanel, selectPanel, buttonPanel;
	private DefaultListModel<Road> roadModel;
	private DefaultListModel<Weather> weatherModel;
	private JList<Road> road;
	private JList<Weather> weather;
	private JSpinner ticksSpinner;
	private JButton cancelButt, okButt;
	private int status;
	//los valores seleccionado
	private List<Road> roadsSelected;
	private Weather weatherSelected;
	
	public ChangeWeatherDialogList(Frame windowAncestor) {
		super(windowAncestor, true);
		this.initGUI();
	}
	
	private void initGUI() {
		status = 0;
		this.setTitle("Change ROAD Weather");
		mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
		setContentPane(mainPanel);
		
		textPanel = new JPanel();
		textPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel label1 = new JLabel("Schedule an event to change the weather of a road after a given number");
		//label1.setHorizontalAlignment(Label.LEFT);
		textPanel.add(label1);
		JLabel label2 = new JLabel("of simulation ticks from now (Road->Multiple choise)");
		//label2.setHorizontalAlignment(Label.LEFT);
		textPanel.add(label2);
		mainPanel.add(textPanel);
		
		selectPanel = new JPanel();
		selectPanel.setAlignmentX(CENTER_ALIGNMENT);
		mainPanel.add(selectPanel);
		
		buttonPanel = new JPanel();
		buttonPanel.setAlignmentX(CENTER_ALIGNMENT);
		mainPanel.add(buttonPanel);
		
		roadModel = new DefaultListModel<Road>();
		road = new JList<Road>(roadModel);
		road.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		road.setFixedCellHeight(19);
		road.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (e.getValueIsAdjusting() == false) 
					 if (e.getSource() == road) 
						 roadsSelected =  road.getSelectedValuesList();
			}
			
		});
		
		weatherModel = new DefaultListModel<Weather>();
		weatherModel.addElement(Weather.SUNNY);
		weatherModel.addElement(Weather.CLOUDY);
		weatherModel.addElement(Weather.RAINY);
		weatherModel.addElement(Weather.WINDY);
		weatherModel.addElement(Weather.STORM);
		weather = new JList<Weather>(weatherModel);
		weather.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		weather.setFixedCellHeight(19);
		
		weather.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) { 
				if (e.getValueIsAdjusting() == false) 
					 if (e.getSource() == weather) 
						 weatherSelected =  weather.getSelectedValue();
			}
		});
		
		ticksSpinner = new JSpinner(new SpinnerNumberModel(10, 1, 5000, 1));
		ticksSpinner.setToolTipText("Simulation tick to run: 1-5000");
		ticksSpinner.setMaximumSize(new Dimension(80, 40));
		ticksSpinner.setMinimumSize(new Dimension(80, 40));
		ticksSpinner.setPreferredSize(new Dimension(80, 40));
		
		selectPanel.add(new JLabel("Road: "));
		selectPanel.add(road);
		selectPanel.add(new JLabel("Weather: "));
		selectPanel.add(weather);
		selectPanel.add(new JLabel("Ticks: "));
		selectPanel.add(ticksSpinner);
		
		cancelButt = new JButton("Cancel");
		cancelButt.addActionListener(new ActionListener() {
			 public void actionPerformed(ActionEvent e) { 
				 status = 0;
				 ChangeWeatherDialogList.this.setVisible(false);
			 } 
		});
		buttonPanel.add(cancelButt);

		
		okButt = new JButton("Ok");
		okButt.addActionListener(new ActionListener() {
			 public void actionPerformed(ActionEvent e) { 
				 if(road.getSelectedIndices() != null  && weather.getSelectedValue() != null) {
					 status = 1;
					 ChangeWeatherDialogList.this.setVisible(false);
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
			roadModel.removeAllElements();
			for(Road r : map.getRoads())
				roadModel.addElement(r);

			//setLocation(getParent().getLocation().x + 10, getParent().getLocation().y + 10);
			setLocation(275, 150);
			setVisible(true);
			return status;
		}

		List<Road> getRoad() {
			return roadsSelected;
		}
		Weather getWeather() {
			return weatherSelected;
		}
		int getTicks() {
			return (int) ticksSpinner.getValue();
			
		}
}

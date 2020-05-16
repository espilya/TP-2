package simulator.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Color;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;


import javax.swing.*;
import javax.swing.border.Border;

import simulator.control.Controller;

@SuppressWarnings("serial")
public class MainWindow extends JFrame{
	private Controller _ctrl;
	
	public MainWindow(Controller ctrl, File fileName) {
	//fileName para saber que fichero ha cargado, en RESET 
	super("Traffic Simulator");
	_ctrl = ctrl;
	initGUI(fileName);
	}
	
	private void initGUI(File fileName) {
	JPanel mainPanel = new JPanel(new BorderLayout());
	this.setContentPane(mainPanel);
	mainPanel.add(new ControlPanel(_ctrl, fileName), BorderLayout.PAGE_START);
	mainPanel.add(new StatusBar(_ctrl),BorderLayout.PAGE_END);
	JPanel viewsPanel = new JPanel(new GridLayout(1, 2));
	mainPanel.add(viewsPanel, BorderLayout.CENTER);
	JPanel tablesPanel = new JPanel();
	tablesPanel.setLayout(new BoxLayout(tablesPanel, BoxLayout.Y_AXIS));
	viewsPanel.add(tablesPanel);
	JPanel mapsPanel = new JPanel();
	mapsPanel.setLayout(new BoxLayout(mapsPanel, BoxLayout.Y_AXIS));
	viewsPanel.add(mapsPanel);
	// events tables
	JPanel eventsView =
	createViewPanel(new JTable(new EventsTableModel(_ctrl)), "Events");
	eventsView.setPreferredSize(new Dimension(500, 200));
	tablesPanel.add(eventsView);
	// vehicles tables
	JPanel vehiclesView =
	createViewPanel(new JTable(new VehiclesTableModel(_ctrl)), "Vehicles");
	vehiclesView.setPreferredSize(new Dimension(500, 200));
	tablesPanel.add(vehiclesView);
	// roads tables
	JPanel roadsView =
	createViewPanel(new JTable(new RoadsTableModel(_ctrl)), "Roads");
	roadsView.setPreferredSize(new Dimension(500, 200));
	tablesPanel.add(roadsView);
	// junctions tables
	JPanel junctionsView =
	createViewPanel(new JTable(new JunctionsTableModel(_ctrl)), "Junctions");
	junctionsView.setPreferredSize(new Dimension(500, 200));
	tablesPanel.add(junctionsView);
	// maps
	JPanel mapView = createViewPanel(new MapComponent(_ctrl), "Map");
	mapView.setPreferredSize(new Dimension(500, 400));
	mapsPanel.add(mapView);
	
	// TODO add a map for MapByRoadComponent
	JPanel MapByRoadComponent = createViewPanel(new MapByRoadComponent(_ctrl), "Map by Road");
	MapByRoadComponent.setPreferredSize(new Dimension(500, 400));
	mapsPanel.add(MapByRoadComponent);
	
	this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	this.addWindowListener(new WindowListener(){
			@Override
			public void windowActivated(WindowEvent e) {}

			@Override
			public void windowClosed(WindowEvent e) {}

			@Override
			public void windowClosing(WindowEvent e) {
				int n = JOptionPane.showOptionDialog(new JFrame(),
						 "Are you sure want to close?", "CLOSE",
						 JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null,
						 null, null);
						 if (n == 0) { System.exit(0); }
						 else setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
	}

			@Override
			public void windowDeactivated(WindowEvent e) {}

			@Override
			public void windowDeiconified(WindowEvent e) {}

			@Override
			public void windowIconified(WindowEvent e) {}

			@Override
			public void windowOpened(WindowEvent e) {}
	});
	this.pack();
	this.setVisible(true);
	this.setMinimumSize(new Dimension(1015, 600));
	}
	private JPanel createViewPanel(JComponent c, String title) {
	JPanel p = new JPanel( new BorderLayout() );
	// TODO add a framed border to p with title
	Border b = BorderFactory.createLineBorder(Color.black, 1);
	c.setBorder(BorderFactory.createTitledBorder(b, title)); 
	p.add(new JScrollPane(c));
	return p;
	}

}

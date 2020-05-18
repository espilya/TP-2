package simulator.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.Border;

import simulator.control.Controller;
import simulator.view.tables.EventsTableModel;
import simulator.view.tables.JunctionsTableModel;
import simulator.view.tables.RoadsTableModel;
import simulator.view.tables.VehiclesTableModel;

public class MainWindow extends JFrame {
	private static final long serialVersionUID = 8720525028556625831L;
	private Controller _ctrl;

	public MainWindow(Controller ctrl, String file) {
		super("Traffic Simulator");
		_ctrl = ctrl;
		initGUI(file);
	}

	/**
	 * TODO: <br>
	 * - Add keyboard input into the tickSpinner. <br>
	 */

	private void initGUI(String file) {
		JPanel mainPanel = new JPanel(new BorderLayout());
		this.setContentPane(mainPanel);
		mainPanel.add(new ControlPanel(_ctrl, file), BorderLayout.PAGE_START);
		mainPanel.add(new StatusBar(_ctrl), BorderLayout.PAGE_END);

		// creating panel
		JPanel viewsPanel = new JPanel(new GridLayout(1, 2));
		mainPanel.add(viewsPanel, BorderLayout.CENTER);

		// creating panel for tables
		JPanel tablesPanel = new JPanel();
		tablesPanel.setLayout(new BoxLayout(tablesPanel, BoxLayout.Y_AXIS));
		viewsPanel.add(tablesPanel);

		// creating panel for maps
		JPanel mapsPanel = new JPanel();
		mapsPanel.setLayout(new BoxLayout(mapsPanel, BoxLayout.Y_AXIS));
		viewsPanel.add(mapsPanel);

		// events table
		JPanel eventsView = createViewPanel(new JTable(new EventsTableModel(_ctrl)), "Events");
		eventsView.setPreferredSize(new Dimension(500, 200));
		tablesPanel.add(eventsView);

		// vehicles table
		JPanel vehiclesView = createViewPanel(new JTable(new VehiclesTableModel(_ctrl)), "Vehicles");
		eventsView.setPreferredSize(new Dimension(500, 200));
		tablesPanel.add(vehiclesView);

		// roads table
		JPanel roadsView = createViewPanel(new JTable(new RoadsTableModel(_ctrl)), "Roads");
		eventsView.setPreferredSize(new Dimension(500, 200));
		tablesPanel.add(roadsView);

		// junctions table
		JPanel junctionsView = createViewPanel(new JTable(new JunctionsTableModel(_ctrl)), "Junctions");
		eventsView.setPreferredSize(new Dimension(500, 200));
		tablesPanel.add(junctionsView);

		// map
		JPanel mapView = createViewPanel(new MapComponent(_ctrl), "Map");
		mapView.setPreferredSize(new Dimension(500, 400));
		mapsPanel.add(mapView);

		// mapByRoad
		JPanel mapViewRoad = createViewPanel(new MapByRoadComponent(_ctrl), "Map by Road");
		mapViewRoad.setPreferredSize(new Dimension(500, 400));
		mapsPanel.add(mapViewRoad);

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		this.setMinimumSize(new Dimension(1195, 700));
		this.pack();
		this.setVisible(true);
		addWindowListener();

//		// get "actual size"
//		this.addComponentListener(new ComponentAdapter() {
//		    public void componentResized(ComponentEvent componentEvent) {
//		    	System.out.println(mainPanel.getSize().height + " " +  mainPanel.getSize().width);
//		    }
//		});
	}

	private JPanel createViewPanel(JComponent c, String title) {
		JPanel p = new JPanel(new BorderLayout());
		Border b = BorderFactory.createLineBorder(Color.black, 2);
		JScrollPane sp = new JScrollPane(c);
		sp.setBorder(BorderFactory.createTitledBorder(b, title));
		sp.setPreferredSize(new Dimension(80, 80));
		p.add(sp);
		return p;
	}

	private void addWindowListener() {
		this.addWindowListener(new WindowListener() {
			@Override
			public void windowActivated(WindowEvent e) {
			}

			@Override
			public void windowClosed(WindowEvent e) {
			}

			@Override
			public void windowClosing(WindowEvent e) {
				int exit = JOptionPane.showOptionDialog(new JFrame(), "Are you sure you want to exit the application?",
						"CLOSE", JOptionPane.YES_NO_OPTION, JOptionPane.YES_NO_OPTION, null, null, null);
				if (exit == 0) {
					System.exit(0);
				} else
					setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
			}

			@Override
			public void windowDeactivated(WindowEvent e) {
			}

			@Override
			public void windowDeiconified(WindowEvent e) {
			}

			@Override
			public void windowIconified(WindowEvent e) {
			}

			@Override
			public void windowOpened(WindowEvent e) {
			}
		});

	}

}
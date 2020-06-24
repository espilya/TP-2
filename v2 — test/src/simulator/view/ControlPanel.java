package simulator.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
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

import simulator.control.Controller;
import simulator.misc.Pair;
import simulator.model.Event;
import simulator.model.NewSetContClassEvent;
import simulator.model.RoadMap;
import simulator.model.SetWeatherEvent;
import simulator.model.TrafficSimObserver;
import simulator.model.Weather;
import simulator.view.dialogs.ChangeCO2ClassDialog;
import simulator.view.dialogs.ChangeWeatherDialog;

/**
 * This is the top panel of the window
 * 
 * @author Puppy
 *
 */
public class ControlPanel extends JPanel implements TrafficSimObserver, ActionListener {
	private static final long serialVersionUID = 1L;

	private Controller _ctrl;
	private RoadMap _map;
	private int _time;
	private JFileChooser fc;
	private boolean _stopped;
	private String _file;

	private enum Buttons {
		MENU, SAVE, LOAD, LOADSAVE, RUN, STOP, RESET, EXIT, CO2, WEATHER, STATS;
	}

	JToolBar toolBar;
	private JButton load, run, stop, reset, exit, changeCont, changeWeather, stats;
	private JSpinner tickSpinner;

	public ControlPanel(Controller ctrl, String file) {
		_ctrl = ctrl;
		_stopped = true;
		_file = file;
		this.setLayout(new BorderLayout());

		add(createJTolBar(), BorderLayout.NORTH);
		_ctrl.addObserver(this);
		toolBarInitialState();

	}

	public JToolBar createJTolBar() {
		toolBar = new JToolBar();
		this.setLayout(new BorderLayout());

		// spinner for selecting time
		tickSpinner = new JSpinner(new SpinnerNumberModel(10, 1, 100, 1));
		tickSpinner.setToolTipText("Simulation tick to run: 1-100");
		tickSpinner.setMaximumSize(new Dimension(80, 40));
		tickSpinner.setMinimumSize(new Dimension(80, 40));
		tickSpinner.setPreferredSize(new Dimension(80, 40));

		// creating buttons
		load = new JButton("Load");
		load.setActionCommand(Buttons.LOAD.name());
		load.setToolTipText("Load");
		load.addActionListener(this);
		load.setIcon(new ImageIcon("resources/icons/open.png"));

		run = new JButton("Run");
		run.setActionCommand(Buttons.RUN.name());
		run.setToolTipText("Run");
		run.addActionListener(this);
		run.setIcon(new ImageIcon("resources/icons/run.png"));

		stop = new JButton("Stop");
		stop.setActionCommand(Buttons.STOP.name());
		stop.setToolTipText("Stop");
		stop.addActionListener(this);
		stop.setIcon(new ImageIcon("resources/icons/stop.png"));

		reset = new JButton("Reset");
		reset.setActionCommand(Buttons.RESET.name());
		reset.setToolTipText("Reset");
		reset.addActionListener(this);
		reset.setIcon(new ImageIcon("resources/icons/reset.png"));

		exit = new JButton("Exit");
		exit.setActionCommand(Buttons.EXIT.name());
		exit.setToolTipText("Exit");
		exit.addActionListener(this);
		exit.setIcon(new ImageIcon("resources/icons/exit.png"));

		changeCont = new JButton("Change CO2 Class");
		changeCont.setActionCommand(Buttons.CO2.name());
		changeCont.setToolTipText("Exit");
		changeCont.addActionListener(this);
		changeCont.setIcon(new ImageIcon("resources/icons/co2class.png")); 
		
		changeWeather = new JButton("Change Road Weather");
		changeWeather.setActionCommand(Buttons.WEATHER.name());
		changeWeather.setToolTipText("Exit");
		changeWeather.addActionListener(this);
		changeWeather.setIcon(new ImageIcon("resources/icons/weather.png"));

		///NEW BUTTON
		stats = new JButton("Stats");
		stats.setActionCommand(Buttons.STATS.name());
		stats.setToolTipText("Stats");
		stats.addActionListener(this);
		stats.setIcon(new ImageIcon("resources/icons/pie-chart.png")); //pie-chart.png

		// adding elements
		toolBar.add(load);
		toolBar.addSeparator();

		toolBar.add(changeCont);
		toolBar.add(changeWeather);
		toolBar.addSeparator();

		toolBar.add(run);
		toolBar.add(stop);
		toolBar.add(new JLabel("Ticks: "));
		toolBar.add(tickSpinner);
		toolBar.addSeparator();

		toolBar.add(stats);
		toolBar.addSeparator();
		
		toolBar.add(reset);
		toolBar.addSeparator();

		toolBar.add(Box.createHorizontalGlue());
		toolBar.add(exit);
		toolBar.setFloatable(false);

		this.fc = new JFileChooser();
		return toolBar;
	}

	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		_map = map;
		_time = time;
	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		_map = map;
		_time = time;
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		_map = map;
		_time = time;
	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		_map = map;
		_time = time;
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		_map = map;
		_time = time;
	}

	@Override
	public void onError(String err) {
		JOptionPane.showMessageDialog((Frame) SwingUtilities.getWindowAncestor(this), err, "ERROR",
				JOptionPane.ERROR_MESSAGE);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (Buttons.WEATHER.name().equals(e.getActionCommand())) {
			changeWeather();
		} else if (Buttons.CO2.name().equals(e.getActionCommand())) {
			changeCO2();
		} else if (Buttons.LOAD.name().equals(e.getActionCommand())) {
			load();
		} else if (Buttons.RUN.name().equals(e.getActionCommand())) {
			run();
		} else if (Buttons.STOP.name().equals(e.getActionCommand())) {
			stop();
		} else if (Buttons.RESET.name().equals(e.getActionCommand())) {
			reset();
		} else if (Buttons.EXIT.name().equals(e.getActionCommand())) {
			close();
		} else if (Buttons.CO2.name().equals(e.getActionCommand())) {
			changeCO2();
		}else if (Buttons.STATS.name().equals(e.getActionCommand())) {
			openStats();
		}
	}
	
	
	private void openStats() {
		VehiclesSpeedHistoryDialog statDialog = new VehiclesSpeedHistoryDialog(
				(Frame) SwingUtilities.getWindowAncestor(this), _map, _ctrl);
		int status = statDialog.open();
//		if (status == 1) {
//			List<Pair<String, Integer>> cs = new ArrayList<Pair<String, Integer>>();
//			Pair<String, Integer> p = new Pair<String, Integer>(statDialog.getFirst().getId(), statDialog.getSecond());
//			cs.add(p);
//			int time = statDialog.getTicks() + _time;
//			_ctrl.addEvent(new NewSetContClassEvent(time, cs));
//		}
	}



	private void changeWeather() {
		ChangeWeatherDialog changeWeather = new ChangeWeatherDialog(
				(Frame) SwingUtilities.getWindowAncestor(this), _map);
		int status = changeWeather.open();
		if (status == 1) {
			List<Pair<String, Weather>> ws = new ArrayList<Pair<String, Weather>>();
			Pair<String, Weather> p = new Pair<String, Weather>(changeWeather.getFirst().getId(),
					changeWeather.getSecond());
			ws.add(p);
			int time = changeWeather.getTicks() + _time;
			_ctrl.addEvent(new SetWeatherEvent(time, ws));
		}

	}

	private void changeCO2() {
		ChangeCO2ClassDialog changeCont = new ChangeCO2ClassDialog(
				(Frame) SwingUtilities.getWindowAncestor(this), _map);
		int status = changeCont.open();
		if (status == 1) {
			List<Pair<String, Integer>> cs = new ArrayList<Pair<String, Integer>>();
			Pair<String, Integer> p = new Pair<String, Integer>(changeCont.getFirst().getId(), changeCont.getSecond());
			cs.add(p);
			int time = changeCont.getTicks() + _time;
			_ctrl.addEvent(new NewSetContClassEvent(time, cs));
		}
	}

//	private void save() {
//		int returnVal = fc.showSaveDialog(null);
//		if (returnVal == JFileChooser.APPROVE_OPTION) {
//			File file = fc.getSelectedFile();
//			try {
//				OutputStream out = new FileOutputStream(file);
//				_ctrl.saveGame(out);
//				out.close();
//			} catch (IOException e) {
//				onError(e.getMessage());
//				e.printStackTrace();
//			}
//		}
//	}

	/**
	 * Load new simulation (ex1.json, ex2.json,..) - OR - load from previous saved
	 * simulation <br>
	 * If the file contains 'time' it loads previous saved simulation, else it loads
	 * new simulation.
	 */
	private void load() {
		int returnVal = fc.showOpenDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			try {
				InputStream in = new FileInputStream(file);
				_ctrl.reset();
				_ctrl.loadEvents(in);
			} catch (Exception e) {
				onError("Can not load file: " + e.getMessage());
				e.printStackTrace();
			}
		}
	}

//	private void loadSave() {
//		int returnVal = fc.showOpenDialog(null);
//		if (returnVal == JFileChooser.APPROVE_OPTION) {
//			File file = fc.getSelectedFile();
//			try {
//				InputStream in = new FileInputStream(file);
//				_ctrl.reset();
//				_ctrl.loadGameFromSave(in);
//			} catch (Exception e) {
//				onError("Can not load save: " + e.getMessage());
//				e.printStackTrace();
//			}
//		}
//	}

	private void run() {
		enableToolBar(false);
		_stopped = false;
		run_sim((Integer) tickSpinner.getValue());
	}

	private void stop() {
		_stopped = true;
	}

	private void reset() {
		InputStream in;
		_ctrl.reset();
		try {
			in = new FileInputStream(_file);
			_ctrl.loadEvents(in);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		reset.setEnabled(false);
	}

	private void close() {
		Object[] Arr = { "Press to close", "Cancel" };
		ImageIcon icon = new ImageIcon(new ImageIcon("resources/icons/exit.png").getImage().getScaledInstance(40, 40,
				Image.SCALE_AREA_AVERAGING));

		int i = JOptionPane.showOptionDialog((Frame) SwingUtilities.getWindowAncestor(exit),
				"Are you sure you want to close the aplication?", "Quit", JOptionPane.YES_NO_OPTION, 0, icon, Arr,
				Arr[0]);
		if (i == 0) {
			System.exit(0);
		}
	}

	private void toolBarInitialState() {
		boolean b = true;
		// tool bar
		load.setEnabled(b);
		run.setEnabled(b);
		reset.setEnabled(!b);
		exit.setEnabled(b);
		stop.setEnabled(!b);
		changeCont.setEnabled(b);
		changeWeather.setEnabled(b);

	}

	private void enableToolBar(boolean b) {
		// tool bar
		load.setEnabled(b);
		run.setEnabled(b);
		reset.setEnabled(b);
		exit.setEnabled(b);
		stop.setEnabled(!b);
		changeCont.setEnabled(b);
		changeWeather.setEnabled(b);
	}

	private void run_sim(int n) {
		if (n > 0 && !_stopped) {
			try {
				_ctrl.run(1);
			} catch (Exception e) {
				onError("Error: " + e.toString() + " More info in console.");
				e.printStackTrace();
				_stopped = true;
				return;
			}
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					run_sim(n - 1);
				}
			});
		} else {
			enableToolBar(true);
			_stopped = true;
		}
	}

	@Override
	public void onLoad(RoadMap map, List<Event> events, int time) {
		_map = map;
		_time = time;
	}



}

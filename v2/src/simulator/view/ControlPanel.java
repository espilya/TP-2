package simulator.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;

import simulator.control.Controller;
import simulator.misc.Pair;
import simulator.model.Event;
import simulator.model.NewSetContClassEvent;
import simulator.model.Road;
import simulator.model.RoadMap;
import simulator.model.SetWeatherEvent;
import simulator.model.TrafficSimObserver;
import simulator.model.Vehicle;
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
		MENU, SAVE, LOAD, LOADSAVE, RUN, STOP, RESET, EXIT, CO2, WEATHER, UNDO;
	}

	JToolBar toolBar;
	private JMenuBar menuBar;
	private JMenuItem loadM, loadSaveM, saveM, runM, stopM, resetM, exitM, changeContM, changeWeatherM, undoM;
	private JButton save, load, loadSave, run, stop, reset, exit, changeCont, changeWeather, undo;
	private JSpinner tickSpinner;

	public ControlPanel(Controller ctrl, String file) {
		_ctrl = ctrl;
		_stopped = true;
		_file = file;
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		add(createMenu());
		add(createJTolBar(), BorderLayout.PAGE_START);
		_ctrl.addObserver(this);
		toolBarInitialState();

	}

	public JMenuBar createMenu() {
		menuBar = new JMenuBar();
		JMenu menu = new JMenu("MENU");
		JMenu changeM = new JMenu("Change");
		menu.setMnemonic(KeyEvent.VK_M);

		loadM = new JMenuItem("Load");
		loadM.setActionCommand(Buttons.LOAD.name());
		loadM.setToolTipText("Load a file");
		loadM.addActionListener(this);
		loadM.setMnemonic(KeyEvent.VK_O);
		loadM.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.ALT_MASK));

		loadSaveM = new JMenuItem("LoadSave");
		loadSaveM.setActionCommand(Buttons.LOAD.name());
		loadSaveM.setToolTipText("Load a perevious state from file");
		loadSaveM.addActionListener(this);
		loadSaveM.setMnemonic(KeyEvent.VK_O);
		loadSaveM.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));

		saveM = new JMenuItem("Save");
		saveM.setActionCommand(Buttons.SAVE.name());
		saveM.setToolTipText("Save");
		saveM.setMnemonic(KeyEvent.VK_S);
		saveM.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.ALT_MASK));
		saveM.addActionListener(this);

		runM = new JMenuItem("Run");
		runM.setActionCommand(Buttons.RUN.name());
		runM.setToolTipText("Run");
		runM.setMnemonic(KeyEvent.VK_R);
		runM.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, ActionEvent.ALT_MASK));
		runM.addActionListener(this);

		stopM = new JMenuItem("Stop");
		stopM.setActionCommand(Buttons.STOP.name());
		stopM.setToolTipText("Stop");
		stopM.setMnemonic(KeyEvent.VK_C);
		stopM.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.ALT_MASK));
		stopM.addActionListener(this);

		resetM = new JMenuItem("Reset");
		resetM.setActionCommand(Buttons.RESET.name());
		resetM.setToolTipText("Reset");
		resetM.setMnemonic(KeyEvent.VK_L);
		resetM.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, ActionEvent.ALT_MASK));
		resetM.addActionListener(this);

		exitM = new JMenuItem("Exit");
		exitM.setActionCommand(Buttons.EXIT.name());
		exitM.setToolTipText("Exit");
		exitM.setMnemonic(KeyEvent.VK_X);
		exitM.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.ALT_MASK));
		exitM.addActionListener(this);

		// changeM = new JMenu("Change");
		// changeM.addActionListener(this);

		changeContM = new JMenuItem("Change CO2 Class");
		changeContM.setActionCommand(Buttons.CO2.name());
		changeContM.setToolTipText("Exit");
		changeContM.setMnemonic(KeyEvent.VK_K);
		changeContM.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_K, ActionEvent.ALT_MASK));
		changeContM.addActionListener(this);

		changeWeatherM = new JMenuItem("Change Road Weather");
		changeWeatherM.setActionCommand(Buttons.WEATHER.name());
		changeWeatherM.setToolTipText("Exit");
		changeWeatherM.setMnemonic(KeyEvent.VK_W);
		changeWeatherM.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, ActionEvent.ALT_MASK));
		changeWeatherM.addActionListener(this);

		undoM = new JMenuItem("Undo");
		undoM.setActionCommand(Buttons.UNDO.name());
		undoM.setToolTipText("Exit");
		undoM.setMnemonic(KeyEvent.VK_U);
		undoM.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U, ActionEvent.ALT_MASK));
		undoM.addActionListener(this);

		menu.add(loadM);
		menu.add(loadSaveM);
		menu.add(saveM);
		menu.add(runM);
		menu.add(stopM);
		menu.add(resetM);
		menu.add(exitM);
		menu.add(changeM);
		changeM.add(changeContM);
		changeM.add(changeWeatherM);
		menu.add(undoM);
		menu.setEnabled(true);
		menuBar.add(menu);
		return menuBar;
	}

	public JToolBar createJTolBar() {
		toolBar = new JToolBar();

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

		loadSave = new JButton("Load state");
		loadSave.setActionCommand(Buttons.LOADSAVE.name());
		loadSave.setToolTipText("Load");
		loadSave.addActionListener(this);
		loadSave.setIcon(new ImageIcon("resources/icons/open.png"));

		save = new JButton("Save");
		save.setActionCommand(Buttons.SAVE.name());
		save.setToolTipText("Save");
		save.addActionListener(this);
		save.setIcon(new ImageIcon("resources/icons/save.png"));

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

		undo = new JButton("Undo");
		undo.setActionCommand(Buttons.UNDO.name());
		undo.setToolTipText("Undo tick");
		undo.addActionListener(this);
		undo.setIcon(new ImageIcon("resources/icons/undo.png"));

		// adding elements
//		toolBar.addSeparator();
		toolBar.add(load);
		toolBar.add(loadSave);
		toolBar.add(save);
		toolBar.addSeparator();

		toolBar.add(changeCont);
		toolBar.add(changeWeather);
		toolBar.addSeparator();
		toolBar.add(run);
		toolBar.add(stop);
		toolBar.add(new JLabel("Ticks: "));
		toolBar.add(tickSpinner);
		toolBar.addSeparator();
		toolBar.add(reset);
		toolBar.add(undo);
		toolBar.addSeparator();
		toolBar.add(exit);
		toolBar.setFloatable(false);


		this.fc = new JFileChooser();
		return toolBar;
	}

	@Override
	public void onAdvanceStart(RoadMap map​, List<Event> events​, int time​) {
		_map = map​;
		_time = time​;
	}

	@Override
	public void onAdvanceEnd(RoadMap map​, List<Event> events​, int time​) {
		_map = map​;
		_time = time​;
	}

	@Override
	public void onEventAdded(RoadMap map​, List<Event> events​, Event e, int time​) {
		_map = map​;
		_time = time​;
	}

	@Override
	public void onReset(RoadMap map​, List<Event> events​, int time​) {
		_map = map​;
		_time = time​;
	}

	@Override
	public void onRegister(RoadMap map​, List<Event> events​, int time​) {
		_map = map​;
		_time = time​;
	}

	@Override
	public void onError(String err​) {
		JOptionPane.showMessageDialog((Frame) SwingUtilities.getWindowAncestor(this), err​, "ERROR",
				JOptionPane.ERROR_MESSAGE);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (Buttons.SAVE.name().equals(e.getActionCommand())) {
			save();
		} else if (Buttons.WEATHER.name().equals(e.getActionCommand())) {
			changeWeather();
		} else if (Buttons.CO2.name().equals(e.getActionCommand())) {
			changeCO2();
		} else if (Buttons.LOAD.name().equals(e.getActionCommand())) {
			load();
		} else if (Buttons.LOADSAVE.name().equals(e.getActionCommand())) {
			loadSave();
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
		} else if (Buttons.UNDO.name().equals(e.getActionCommand())) {
			undo();
		}
	}

	private void undo() {
		_ctrl.undo();
	}

	private void changeWeather() {
		ChangeWeatherDialog<Road, Weather> changeWeather = new ChangeWeatherDialog<Road, Weather>(
				(Frame) SwingUtilities.getWindowAncestor(this), _map);
		int status = changeWeather.open();
		if (status == 1) {
			List<Pair<String, Weather>> ws = new ArrayList<Pair<String, Weather>>();
			Pair<String, Weather> p = new Pair<String, Weather>(changeWeather.getFirst().getId(),
					changeWeather.getSecond());
			ws.add(p);
			int time = changeWeather.getTicks() + _time;
			try {
				_ctrl.addEventWeatherChange(new SetWeatherEvent(time, ws), time, changeWeather.getFirst().getId(),
						changeWeather.getSecond());
			} catch (Exception e) {
				onError(e.getMessage());
				e.printStackTrace();
			}

		}

	}

	private void changeCO2() {
		ChangeCO2ClassDialog<Vehicle, Integer> changeCont = new ChangeCO2ClassDialog<Vehicle, Integer>(
				(Frame) SwingUtilities.getWindowAncestor(this), _map);
		int status = changeCont.open();
		if (status == 1) {
			List<Pair<String, Integer>> cs = new ArrayList<Pair<String, Integer>>();
			Pair<String, Integer> p = new Pair<String, Integer>(changeCont.getFirst().getId(), changeCont.getSecond());
			cs.add(p);
			int time = changeCont.getTicks() + _time;
			try {
				_ctrl.addEventCO2Change(new NewSetContClassEvent(time, cs), time, changeCont.getFirst().getId(),
						changeCont.getSecond());
			} catch (Exception e) {
				onError(e.getMessage());
				e.printStackTrace();
			}
		}
	}

	private void save() {
		int returnVal = fc.showSaveDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			try {
				OutputStream out = new FileOutputStream(file);
				_ctrl.saveGame(out);
				out.close();
			} catch (IOException e) {
				onError(e.getMessage());
				e.printStackTrace();
			}
		}
	}

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

	private void loadSave() {
		int returnVal = fc.showOpenDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			try {
				InputStream in = new FileInputStream(file);
				_ctrl.reset();
				_ctrl.loadGameFromSave(in);
			} catch (Exception e) {
				onError("Can not load save: " + e.getMessage());
				e.printStackTrace();
			}
		}
	}

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
		resetM.setEnabled(false);
		undo.setEnabled(false);
		undoM.setEnabled(false);
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
		save.setEnabled(!b);
		load.setEnabled(b);
		loadSave.setEnabled(b);
		run.setEnabled(b);
		reset.setEnabled(!b);
		exit.setEnabled(b);
		stop.setEnabled(!b);
		changeCont.setEnabled(b);
		changeWeather.setEnabled(b);
		undo.setEnabled(!b);
		// menu bar
		saveM.setEnabled(!b);
		loadM.setEnabled(b);
		loadSaveM.setEnabled(b);
		runM.setEnabled(b);
		resetM.setEnabled(!b);
		exitM.setEnabled(b);
		stopM.setEnabled(!b);
		changeContM.setEnabled(b);
		changeWeatherM.setEnabled(b);
		undoM.setEnabled(!b);

	}

	private void enableToolBar(boolean b) {
		// tool bar
		save.setEnabled(b);
		load.setEnabled(b);
		loadSave.setEnabled(b);
		run.setEnabled(b);
		reset.setEnabled(b);
		exit.setEnabled(b);
		stop.setEnabled(!b);
		changeCont.setEnabled(b);
		changeWeather.setEnabled(b);
		undo.setEnabled(b);
		// menu bar
		saveM.setEnabled(b);
		loadM.setEnabled(b);
		loadSaveM.setEnabled(b);
		runM.setEnabled(b);
		resetM.setEnabled(b);
		exitM.setEnabled(b);
		stopM.setEnabled(!b);
		changeContM.setEnabled(b);
		changeWeatherM.setEnabled(b);
		undoM.setEnabled(b);
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

	@Override
	public void onUndo(RoadMap map, List<Event> events, int time) {
		_map = map;
		_time = time;
		if (time == 0) {
			toolBarInitialState();
		}
	}

}

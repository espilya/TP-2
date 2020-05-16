package simulator.view;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
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
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

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
		MENU, SAVE, LOAD, RUN, STOP, RESET, EXIT, CO2, WEATHER, UNDO;
	}
	
	private enum menuOptions {
		SAVE, LOAD, RUN, STOP, RESET, EXIT, CO2, WEATHER, UNDO;
	}

	JToolBar toolBar;
	private JMenuBar menuBar;
	private JMenuItem loadM, saveM;
	private JButton save, load, run, stop, reset, exit, changeCont, changeWeather, undo;
	private JSpinner tickSpinner;
	
	
	public ControlPanel(Controller ctrl, String file) {
		_ctrl = ctrl;
		_stopped = true;
		_file = file;
		add(createMenu());
		add(createJTolBar());
		_ctrl.addObserver(this);

	}
	
	public JMenuBar createMenu() {
		menuBar = new JMenuBar();
		JMenu menu = new JMenu("MENU");
		menu.setMnemonic(KeyEvent.VK_M);
		
		loadM=new JMenuItem("Load");
		loadM.setActionCommand(menuOptions.LOAD.name());
		loadM.setToolTipText("Load a file");
		loadM.addActionListener(this);
		loadM.setMnemonic(KeyEvent.VK_O);
		loadM.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, 
				ActionEvent.ALT_MASK));

		saveM = new JMenuItem("Save");
		saveM.addActionListener(this);
		
		menu.add(loadM);
		menu.add(saveM);
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
		
		
		
		
		
		load = new JButton(Buttons.LOAD.name());
		load.setActionCommand(Buttons.LOAD.name());
		load.setToolTipText("Load");
		load.addActionListener(this);
		load.setIcon(new ImageIcon("resources/icons/open.png"));

		save = new JButton(Buttons.SAVE.name());
		save.setActionCommand(Buttons.SAVE.name());
		save.setToolTipText("Save");
		save.addActionListener(this);
		save.setIcon(new ImageIcon("resources/icons/save.png"));

		run = new JButton(Buttons.RUN.name());
		run.setActionCommand(Buttons.RUN.name());
		run.setToolTipText("Run");
		run.addActionListener(this);
		run.setIcon(new ImageIcon("resources/icons/run.png"));

		stop = new JButton(Buttons.STOP.name());
		stop.setActionCommand(Buttons.STOP.name());
		stop.setToolTipText("Stop");
		stop.addActionListener(this);
		stop.setIcon(new ImageIcon("resources/icons/stop.png"));

		reset = new JButton(Buttons.RESET.name());
		reset.setActionCommand(Buttons.RESET.name());
		reset.setToolTipText("Reset");
		reset.addActionListener(this);
		reset.setIcon(new ImageIcon("resources/icons/reset.png"));

		exit = new JButton(Buttons.EXIT.name());
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

		undo = new JButton("UNDO");
		undo.setActionCommand(Buttons.UNDO.name());
		undo.setToolTipText("Exit");
		undo.addActionListener(this);
		undo.setIcon(new ImageIcon("resources/icons/undo.png"));

		toolBar.add(load);
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
		enableToolBar(true);

		this.fc = new JFileChooser();
		return toolBar;
	}

	public void onAdvanceStart(RoadMap map​, List<Event> events​, int time​) {
		_map = map​;
		_time = time​;
	}

	public void onAdvanceEnd(RoadMap map​, List<Event> events​, int time​) {
		_map = map​;
		_time = time​;
	}

	public void onEventAdded(RoadMap map​, List<Event> events​, Event e, int time​) {
		_map = map​;
		_time = time​;
	}

	public void onReset(RoadMap map​, List<Event> events​, int time​) {
		_map = map​;
		_time = time​;
	}

	public void onRegister(RoadMap map​, List<Event> events​, int time​) {
		_map = map​;
		_time = time​;
	}

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
		} else if (Buttons.RUN.name().equals(e.getActionCommand())) {
			run();
		} else if (Buttons.STOP.name().equals(e.getActionCommand())) {
			stop();
		} else if (Buttons.RESET.name().equals(e.getActionCommand())) {
			reset();
		} else if (Buttons.EXIT.name().equals(e.getActionCommand())) {
			close();
		} 
	}

	private void changeWeather() {
		// TODO
	}

	private void changeCO2() {
		// TODO
	}

	private void save() {
		int returnVal = fc.showSaveDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			// _ctrl. ...
			// writeFile(file, textArea.getText); // what can be text area
		}
	}
	

	/**
	 * TODO: load new simulation (ex1.json, ex2.json,..) - OR - load from previous
	 * saved simulation
	 */
	private void load() {
		int returnVal = fc.showOpenDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			fc.setCurrentDirectory(new File("resources/examples"));
			try {
				InputStream in = new FileInputStream(file);
				_ctrl.reset();
				_ctrl.loadEvents(in);
			} catch (FileNotFoundException e) {
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
		_ctrl.reset();
		InputStream in;
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

	private void enableToolBar(boolean b) {
		save.setEnabled(b);
		load.setEnabled(b);
		run.setEnabled(b);
		reset.setEnabled(b);
		exit.setEnabled(b);
		changeCont.setEnabled(b);
		changeWeather.setEnabled(b);
		undo.setEnabled(b);
	}

	private void run_sim(int n) {
		if (n > 0 && !_stopped) {
			try {
				_ctrl.run(1);
			} catch (Exception e) {
				// TODO show error message
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
		// TODO Auto-generated method stub

	}

}

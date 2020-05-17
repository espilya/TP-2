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
import java.io.IOException;
import java.io.InputStream;
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

@SuppressWarnings("serial")
public class ControlPanel extends JPanel implements TrafficSimObserver, ActionListener{
	private final String LOAD = "load";
	private final String CO2CLASS = "changeCO2Class";
	private final String WEATHER = "changeWeather";
	private final String RUN = "run";
	private final String STOP = "stop";
	private final String CLOSE = "close";
	private final String SAVE = "save";
	private final String LOADSAVE = "loadSave";
	private final String RESET = "reset";
	
	private Controller ctrl;
	private RoadMap _map;
	private int _time;
	
	private JToolBar toolBar;
	private JButton loadButt, changeCO2Class, changeWeather, runButt, stopButt, closeButt;
	private JButton saveButt, loadSaveButt, resetButt;
	
	private JSpinner ticksSpinner;
	private JFileChooser fc;

	private boolean stopped;
	//para saber si el file es null
	private File fileName;
	//para saber cuando hay que activar el saveLoad button
	private boolean enableSaveLoad = false;
	//MenuBar
	private JMenuBar menuBar;
	private JMenu change;
	private JMenuItem load, changeCO2Item, changeWeatherItem, run, stop, close;
	private JMenuItem save, loadSave, reset;

	ControlPanel(Controller _ctrl, File fileName) {
		this.ctrl = _ctrl;
		stopped = true;
		_time =0;
		intGUI(fileName);
		this.ctrl.addObserver(this);
	}

	private void intGUI(File fileName) {
		//barra de menu
		
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		this.fileName = fileName;
		
		add(createMenuBar());
		
		add(createJTolBar(), BorderLayout.PAGE_START);
		
		//boton cargar archivo
		fc = new JFileChooser();
		fc.setCurrentDirectory(new File("/Users/Administrator/git/TP2-TrafficSimulator/TrafficSimulatorTP2/resources/examples"));
		
		
		/*antes de annadir el MENUBAR

		loadButt = new JButton();
		loadButt.setToolTipText("Load a file");
		loadButt.setIcon(new ImageIcon("resources/icons/open.png"));
		loadButt.addActionListener(new ActionListener() {
			 public void actionPerformed(ActionEvent e) { try {
				loadFile();
			} catch (FileNotFoundException e1) {
				showError(e1.getMessage());
			} }
		});
		toolBar.add(loadButt);
		
		toolBar.addSeparator();
		
		//PARTE OPCIONAL -> boton salvar
		saveButt = new JButton();
		saveButt.setToolTipText("Save the simulation");
		saveButt.addActionListener(new ActionListener() {
			 public void actionPerformed(ActionEvent e) { save(); }
		});
		ImageIcon img = new ImageIcon("resources/icons/salvar.jpg");
		ImageIcon im = new ImageIcon(img.getImage().getScaledInstance(35, 32, Image.SCALE_AREA_AVERAGING));
		saveButt.setIcon(im);
		toolBar.add(saveButt);
		
		//PARTE OPCIONAL -> boton cargar lo guardado
		loadSaveButt = new JButton();
		loadSaveButt.setToolTipText("Load the save state");
		loadSaveButt.addActionListener(new ActionListener() {
			 public void actionPerformed(ActionEvent e) { loadSave(); }
		});
		ImageIcon img2 = new ImageIcon("resources/icons/loadSave.png");
		ImageIcon im2 = new ImageIcon(img2.getImage().getScaledInstance(35, 32, Image.SCALE_AREA_AVERAGING));
		loadSaveButt.setIcon(im2);
		toolBar.add(loadSaveButt);
		this.loadSaveButt.setEnabled(enableSaveLoad);
		
		toolBar.addSeparator();
		
		//boton cambiar CO2
		changeCO2Class = new JButton();
		changeCO2Class.setToolTipText("Change CO2 class");
		changeCO2Class.addActionListener(new ActionListener() {
			 public void actionPerformed(ActionEvent e) { changeCO2Class(); }
		});
		changeCO2Class.setIcon(new ImageIcon("resources/icons/co2class.png"));
		toolBar.add(changeCO2Class);
		
		//boton cambir Weather
		changeWeather = new JButton();
		changeWeather.setToolTipText("Change road weather");
		changeWeather.addActionListener(new ActionListener() {
			 public void actionPerformed(ActionEvent e) { changeWeather(); }
		});
		changeWeather.setIcon(new ImageIcon("resources/icons/weather.png"));
		toolBar.add(changeWeather);
		
		toolBar.addSeparator();
		
		//boton run 
		runButt = new JButton();
		runButt.setToolTipText("Run the simulation");
		runButt.addActionListener(new ActionListener() {
			 public void actionPerformed(ActionEvent e) { start(); }
		});
		runButt.setIcon(new ImageIcon("resources/icons/run.png"));
		toolBar.add(runButt);
		if(fileName == null) {
			this.runButt.setEnabled(false);
		}
		else this.runButt.setEnabled(true);
		
		//boton parar la simulacion
		stopButt = new JButton();
		stopButt.setToolTipText("Stop the simulation");
		stopButt.addActionListener(new ActionListener() {
			 public void actionPerformed(ActionEvent e) { stop(); }
		});
		stopButt.setIcon(new ImageIcon("resources/icons/stop.png"));
		toolBar.add(stopButt);
		
		//boton reset
		resetButt = new JButton();
		resetButt.setToolTipText("Reset the simulation");
		resetButt.addActionListener(new ActionListener() {
			 public void actionPerformed(ActionEvent e) { try {
				reset();
			} catch (FileNotFoundException err) {
				showError(err.getMessage());
			} }
		});
		ImageIcon img3 = new ImageIcon("resources/icons/reset.png");
		ImageIcon im3 = new ImageIcon(img3.getImage().getScaledInstance(35, 32, Image.SCALE_AREA_AVERAGING));
		resetButt.setIcon(im3);
		toolBar.add(resetButt);
		//al principio no se resetea
		this.resetButt.setEnabled(false);
		
		//spinner
		ticksSpinner = new JSpinner(new SpinnerNumberModel(10, 1, 5000, 1));
		ticksSpinner.setToolTipText("Simulation tick to run: 1-5000");
		ticksSpinner.setMaximumSize(new Dimension(80, 40));
		ticksSpinner.setMinimumSize(new Dimension(80, 40));
		ticksSpinner.setPreferredSize(new Dimension(80, 40));
		toolBar.add( new JLabel("Ticks: "));
		toolBar.add(ticksSpinner);
		
		//poner a la mas derecha del toolbar
		toolBar.addSeparator(new Dimension(570, 25));
		//boton cerrar
		closeButt = new JButton();
		closeButt.setToolTipText("Close");
		closeButt.addActionListener(new ActionListener() {
			 public void actionPerformed(ActionEvent e) { close(); }
		});
		closeButt.setIcon(new ImageIcon("resources/icons/exit.png"));
		toolBar.add(closeButt);
		*/
	}
	
	private JMenuBar createMenuBar() {
		menuBar = new JMenuBar();

		JMenu menu = new JMenu("MENU");
		menuBar.add(menu);
		//no funciona el setMnemonic de menu
		menu.setMnemonic(KeyEvent.VK_M);

		load = new JMenuItem("Open");
		load.setActionCommand(LOAD);
		load.setToolTipText("Load a file");
		load.addActionListener(this);
		load.setMnemonic(KeyEvent.VK_O);
		load.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, 
				ActionEvent.ALT_MASK));
		
		save = new JMenuItem("Save");
		save.setActionCommand(SAVE);
		save.setToolTipText("Save the simulation");
		save.addActionListener(this);
		save.setMnemonic(KeyEvent.VK_S);
		save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, 
				ActionEvent.ALT_MASK));
		
		//PARTE OPCIONAL -> cargar lo guardado
		loadSave = new JMenuItem("Load");
		loadSave.setActionCommand(LOADSAVE);
		loadSave.setToolTipText("Load the save state");
		loadSave.addActionListener(this);
		loadSave.setMnemonic(KeyEvent.VK_L);
		loadSave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, 
				ActionEvent.ALT_MASK));
		this.loadSave.setEnabled(enableSaveLoad);
		//los dos change
		
		 change = new JMenu("Change");
		 
		//cambiar CO2
		changeCO2Item = new JMenuItem("Change CO2");
		changeCO2Item.setActionCommand(CO2CLASS);
		changeCO2Item.setToolTipText("Change CO2 class");
		changeCO2Item.addActionListener(this);
		changeCO2Item.setMnemonic(KeyEvent.VK_C);
		changeCO2Item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, 
				ActionEvent.ALT_MASK));
		
		//cambir Weather
		changeWeatherItem = new JMenuItem("Change Weather");
		changeWeatherItem.setActionCommand(WEATHER);
		changeWeatherItem.setToolTipText("Change road weather");
		changeWeatherItem.addActionListener(this);
		changeWeatherItem.setMnemonic(KeyEvent.VK_W);
		changeWeatherItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, 
				ActionEvent.ALT_MASK));
		
		//annadir al menu change
		change.add(changeCO2Item);
		change.add(changeWeatherItem);
		//run 
		run = new JMenuItem("Run");
		run.setActionCommand(RUN);
		run.setToolTipText("Run the simulation");
		run.addActionListener(this);
		run.setMnemonic(KeyEvent.VK_R);
		run.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, 
				ActionEvent.ALT_MASK));
		if(fileName == null) {
			this.run.setEnabled(false);
		}
		else this.run.setEnabled(true);
		
		//parar la simulacion
		stop = new JMenuItem("Stop");
		stop.setActionCommand(STOP);
		stop.setToolTipText("Stop the simulation");
		stop.addActionListener(this);
		stop.setMnemonic(KeyEvent.VK_P);
		stop.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, 
				ActionEvent.ALT_MASK));
		
		//reset
		reset = new JMenuItem("Reset");
		reset.setActionCommand(RESET);
		reset.setToolTipText("Reset the simulation");
		reset.addActionListener(this);
		reset.setMnemonic(KeyEvent.VK_T);
		reset.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T, 
				ActionEvent.ALT_MASK));
		//al principio no se resetea
		this.reset.setEnabled(false);
		
		//cerrar
		close = new JMenuItem("Close");
		close.setActionCommand(CLOSE);
		close.setToolTipText("Close the simulation");
		close.addActionListener(this);
		close.setMnemonic(KeyEvent.VK_E);
		close.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, 
				ActionEvent.ALT_MASK));
		
		
		menu.add(load);
		menu.addSeparator();
		menu.add(save);
		menu.add(loadSave);
		menu.add(change);
		menu.addSeparator();
		menu.add(run);
		menu.add(stop);
		menu.add(reset);
		menu.addSeparator();
		menu.add(close);

		return menuBar;
	}

	private JToolBar createJTolBar() {
		toolBar = new JToolBar();
	
		//para abrir el archivo seleccionado
		loadButt = new JButton();
		loadButt.setActionCommand(LOAD);
		loadButt.setToolTipText("Load a file");
		loadButt.setIcon(new ImageIcon("resources/icons/open.png"));
		loadButt.addActionListener(this);
		toolBar.add(loadButt);
		
		toolBar.addSeparator();
		
		//PARTE OPCIONAL -> boton salvar
		saveButt = new JButton();
		saveButt.setActionCommand(SAVE);
		saveButt.setToolTipText("Save the simulation");
		saveButt.addActionListener(this);
		ImageIcon img = new ImageIcon("resources/icons/salvar.jpg");
		ImageIcon im = new ImageIcon(img.getImage().getScaledInstance(35, 32, Image.SCALE_AREA_AVERAGING));
		saveButt.setIcon(im);
		toolBar.add(saveButt);
		
		//PARTE OPCIONAL -> boton cargar lo guardado
		loadSaveButt = new JButton();
		loadSaveButt.setActionCommand(LOADSAVE);
		loadSaveButt.setToolTipText("Load the save state");
		loadSaveButt.addActionListener(this);
		ImageIcon img2 = new ImageIcon("resources/icons/loadSave.png");
		ImageIcon im2 = new ImageIcon(img2.getImage().getScaledInstance(35, 32, Image.SCALE_AREA_AVERAGING));
		loadSaveButt.setIcon(im2);
		toolBar.add(loadSaveButt);
		this.loadSaveButt.setEnabled(enableSaveLoad);
		
		toolBar.addSeparator();
		
		//boton cambiar CO2
		changeCO2Class = new JButton();
		changeCO2Class.setActionCommand(CO2CLASS);
		changeCO2Class.setToolTipText("Change CO2 class");
		changeCO2Class.addActionListener(this);
		changeCO2Class.setIcon(new ImageIcon("resources/icons/co2class.png"));
		toolBar.add(changeCO2Class);
		
		//boton cambir Weather
		changeWeather = new JButton();
		changeWeather.setActionCommand(WEATHER);
		changeWeather.setToolTipText("Change road weather");
		changeWeather.addActionListener(this);
		changeWeather.setIcon(new ImageIcon("resources/icons/weather.png"));
		toolBar.add(changeWeather);
		
		toolBar.addSeparator();
		
		//boton run 
		runButt = new JButton();
		runButt.setActionCommand(RUN);
		runButt.setToolTipText("Run the simulation");
		runButt.addActionListener(this);
		runButt.setIcon(new ImageIcon("resources/icons/run.png"));
		toolBar.add(runButt);
		if(fileName == null) {
			this.runButt.setEnabled(false);
		}
		else this.runButt.setEnabled(true);
		
		//boton parar la simulacion
		stopButt = new JButton();
		stopButt.setActionCommand(STOP);
		stopButt.setToolTipText("Stop the simulation");
		stopButt.addActionListener(this);
		stopButt.setIcon(new ImageIcon("resources/icons/stop.png"));
		toolBar.add(stopButt);
		
		//boton reset
		resetButt = new JButton();
		resetButt.setActionCommand(RESET);
		resetButt.setToolTipText("Reset the simulation");
		resetButt.addActionListener(this);
		ImageIcon img3 = new ImageIcon("resources/icons/reset.png");
		ImageIcon im3 = new ImageIcon(img3.getImage().getScaledInstance(35, 32, Image.SCALE_AREA_AVERAGING));
		resetButt.setIcon(im3);
		toolBar.add(resetButt);
		//al principio no se resetea
		this.resetButt.setEnabled(false);
		
		//spinner
		ticksSpinner = new JSpinner(new SpinnerNumberModel(10, 1, 5000, 1));
		ticksSpinner.setToolTipText("Simulation tick to run: 1-5000");
		ticksSpinner.setMaximumSize(new Dimension(80, 40));
		ticksSpinner.setMinimumSize(new Dimension(80, 40));
		ticksSpinner.setPreferredSize(new Dimension(80, 40));
		toolBar.add( new JLabel("Ticks: "));
		toolBar.add(ticksSpinner);
		
		//poner a la mas derecha del toolbar
		toolBar.addSeparator(new Dimension(370, 25));
		//boton cerrar
		closeButt = new JButton();
		closeButt.setActionCommand(CLOSE);
		closeButt.setToolTipText("Close the simulation");
		closeButt.addActionListener(this);
		closeButt.setIcon(new ImageIcon("resources/icons/exit.png"));
		toolBar.add(closeButt);
		
		return toolBar;
	}

	private void loadFile() throws FileNotFoundException {
		int returnVal = fc.showOpenDialog(this.getParent());
		 if (returnVal == JFileChooser.APPROVE_OPTION) {
		 File file = fc.getSelectedFile();
		 InputStream in = new FileInputStream(file);
		 this.ctrl.reset();
		 this.ctrl.loadEvents(in);
		 fileName = file;
		 this.resetButt.setEnabled(false);
		 if(fileName == null) {
				this.runButt.setEnabled(false);
			}
			else this.runButt.setEnabled(true);
		 }
	}
	
	private void start() {
		enableToolBar(false);
		stopped = false;
		run_sim( (Integer) ticksSpinner.getValue());
	}

	private void run_sim(int n) {
		if (n > 0 && !stopped) {
			try {
				ctrl.run(1);
			} catch (Exception e) {
				stopped = true;
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
			stopped = true;
		}
	}
	
	private void stop() {
		stopped = true;
	}
	
	private void close() {
		Object[] Arr = {"Press to close", "Cancel" };
		ImageIcon img = new ImageIcon("resources/icons/salir.png");
		ImageIcon im = new ImageIcon(img.getImage().getScaledInstance(40, 40, Image.SCALE_AREA_AVERAGING));
		
		int i = JOptionPane.showOptionDialog( (Frame) SwingUtilities.getWindowAncestor(closeButt),
				"Are you sure you want to close the aplication?", "Quit", JOptionPane.YES_NO_OPTION,
				0, im, Arr, Arr[0]);
		if(i == 0) {
			System.exit(0);
		}
	}
	
	private void enableToolBar(boolean b) {
		//para toolBar
		this.loadButt.setEnabled(b);
		this.changeCO2Class.setEnabled(b);
		this.changeWeather.setEnabled(b);
		this.runButt.setEnabled(b);
		this.closeButt.setEnabled(b);
		this.saveButt.setEnabled(b);
		if(enableSaveLoad) this.loadSaveButt.setEnabled(b);
		else this.loadSaveButt.setEnabled(enableSaveLoad);
		//si no hay archivo cargado reset enable a false
		if(fileName == null) this.resetButt.setEnabled(false);
		else this.resetButt.setEnabled(b);
		//para menuBar
		this.load.setEnabled(b);
		this.changeCO2Item.setEnabled(b);
		this.changeWeatherItem.setEnabled(b);
		this.run.setEnabled(b);
		this.close.setEnabled(b);
		this.save.setEnabled(b);
		if(enableSaveLoad) this.loadSave.setEnabled(b);
		else this.loadSave.setEnabled(enableSaveLoad);
		//si no hay archivo cargado reset enable a false
		if(fileName == null) this.reset.setEnabled(false);
		else this.reset.setEnabled(b);
	}
	
	private void changeCO2Class() {
		ChangeCO2ClassDialog CO2Dialog = new ChangeCO2ClassDialog((Frame) SwingUtilities.getWindowAncestor(this));
		int status = CO2Dialog.open(_map);
		if(status == 1) {
			List<Pair<String, Integer>> cs = new ArrayList<Pair<String,Integer>>();
			Pair<String, Integer> p =
				new Pair<String,Integer>(CO2Dialog.getVehicle().getId(), CO2Dialog.getCO2Class());
			cs.add(p);
			ctrl.addEvent(new NewSetContClassEvent(CO2Dialog.getTicks() + _time, cs));
		}
	}
	
	private void changeCO2ClassList() {
		ChangeCO2ClassDialogList CO2Dialog = new ChangeCO2ClassDialogList((Frame) SwingUtilities.getWindowAncestor(this));
		int status = CO2Dialog.open(_map);
		if(status == 1) {
			for(Vehicle v : CO2Dialog.getVehicle()) {
				List<Pair<String, Integer>> cs = new ArrayList<Pair<String,Integer>>();
				Pair<String, Integer> p =
					new Pair<String,Integer>(v.getId(), CO2Dialog.getCO2Class());
				cs.add(p);
				ctrl.addEvent(new NewSetContClassEvent(CO2Dialog.getTicks() + _time, cs));
			}
		}
	}
	private void changeWeather() {
		ChangeWeatherDialog weatherDialog = new ChangeWeatherDialog((Frame) SwingUtilities.getWindowAncestor(this));
		int status = weatherDialog.open(_map);
		if(status == 1) {
			List<Pair<String, Weather>> ws = new ArrayList<Pair<String,Weather>>();
			Pair<String, Weather> p =
					new Pair<String,Weather>(weatherDialog.getRoad().getId(), weatherDialog.getWeather());
			ws.add(p);
			ctrl.addEvent(new SetWeatherEvent(weatherDialog.getTicks() + _time, ws));
		}
		
	}
	private void changeWeatherList() {
		ChangeWeatherDialogList weatherDialog = new ChangeWeatherDialogList((Frame) SwingUtilities.getWindowAncestor(this));
		int status = weatherDialog.open(_map);
		if(status == 1) {
			for(Road r : weatherDialog.getRoad()) {
				List<Pair<String, Weather>> ws = new ArrayList<Pair<String,Weather>>();
				Pair<String, Weather> p =
					new Pair<String,Weather>(r.getId(), weatherDialog.getWeather());
				ws.add(p);
				ctrl.addEvent(new SetWeatherEvent(weatherDialog.getTicks() + _time, ws));
				}
		}
	}

	private void save() {
		enableSaveLoad = true;
		this.loadSaveButt.setEnabled(enableSaveLoad);
		try {
			this.ctrl.saveCtrl();
		} catch (IOException e) {
			showError(e.getMessage());
		}
	}
	
	private void loadSave() {
		try {
			this.ctrl.loadSaveCtrl();
		} catch (Exception e) {
			showError(e.getMessage());
		}
	}

	private void reset() throws FileNotFoundException {
		 this.ctrl.reset(); 
		 InputStream in = new FileInputStream(fileName);
		 this.ctrl.loadEvents(in);
		 //despues de reset, no se puede pulsar otra vez
		 this.resetButt.setEnabled(false);
	}
	
	public void showError(String error) {
		JOptionPane.showMessageDialog((Frame) SwingUtilities.getWindowAncestor(this), error, "WARNING", JOptionPane.ERROR_MESSAGE);
	}

	private int selectDialog() {
		ImageIcon img = new ImageIcon("resources/icons/choise.jpg");
		ImageIcon im = new ImageIcon(img.getImage().getScaledInstance(40, 40, Image.SCALE_AREA_AVERAGING));
		Object[] Arr = {"ComboBox", "List" };
		int i = JOptionPane.showOptionDialog( (Frame) SwingUtilities.getWindowAncestor(changeCO2Class),
				"You want a ComboBox or List", "Choise", JOptionPane.YES_NO_OPTION,
				0, im, Arr, Arr[0]);
		return i;
	}
	
	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		this._map = map;
		this._time = time;
	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		this._map = map;
		this._time = time;
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		this._map = map;
		this._time = time;
		
	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		this._map = map;
		this._time = time;
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		this._map = map;
		this._time = time;
		
	}

	@Override
	public void onError(String err) {
		showError(err);
	}

	@Override
	public void onLoad(RoadMap map, List<Event> events, int time) {
		this._map = map;
		this._time = time;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (LOAD.equals(e.getActionCommand()))
			try {
				loadFile();
			} catch (FileNotFoundException e1) {
				showError(e1.getMessage());
			}
		else if (CO2CLASS.equals(e.getActionCommand())) {
			int i = selectDialog();
			if(i == 0) changeCO2Class();
			else if(i == 1) changeCO2ClassList();
		}	
		else if (WEATHER.equals(e.getActionCommand())) {
			int i = selectDialog();
			if(i == 0) changeWeather();
			else if(i == 1) changeWeatherList();
		}
		else if (RUN.equals(e.getActionCommand()))
			start();
		else if (STOP.equals(e.getActionCommand()))
			stop();
		else if (CLOSE.equals(e.getActionCommand()))
			close();
		else if (SAVE.equals(e.getActionCommand()))
			save();
		else if (LOADSAVE.equals(e.getActionCommand()))
			loadSave();
		else if (RESET.equals(e.getActionCommand()))
			try {
				reset();
			} catch (FileNotFoundException e1) {
				showError(e1.getMessage());
			}
		
	}
}

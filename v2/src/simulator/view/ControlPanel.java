package simulator.view;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JToolBar;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;

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

	private enum Buttons {
		SAVE, LOAD, CLEAR, RUN, STOP, RESET, EXIT;
	}

	private JButton save, load, clear, run, stop, reset, exit;
	private JSpinner _time;

	public ControlPanel(Controller _ctrl) {

		add(createJTolBar());

	}

	public JToolBar createJTolBar() {
		JToolBar toolBar = new JToolBar();

		// spinner for selecting time
		_time = new JSpinner(new SpinnerNumberModel(10, 1, 10000, 1));
		_time.setToolTipText("Simulation tick to run: 1-10000");
		_time.setMaximumSize(new Dimension(80, 40));
		_time.setMinimumSize(new Dimension(80, 40));
		_time.setPreferredSize(new Dimension(80, 40));

		load = new JButton(Buttons.LOAD.name());
		load.setActionCommand(Buttons.LOAD.name());
		load.setToolTipText("Load");
		load.addActionListener(this);
		load.setIcon(new ImageIcon("resources/icons/open.png"));

		save = new JButton(Buttons.SAVE.name());
		save.setActionCommand(Buttons.SAVE.name());
		save.setToolTipText("Save");
		save.addActionListener(this);
		save.setIcon(new ImageIcon("resources/icons/open.png"));

		clear = new JButton(Buttons.CLEAR.name());
		clear.setActionCommand(Buttons.CLEAR.name());
		clear.setToolTipText("Clear Text");
		clear.addActionListener(this);
		clear.setIcon(new ImageIcon("resources/icons/open.png"));

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
		reset.setIcon(new ImageIcon("resources/icons/open.png"));

		exit = new JButton(Buttons.EXIT.name());
		exit.setActionCommand(Buttons.EXIT.name());
		exit.setToolTipText("Exit");
		exit.addActionListener(this);
		exit.setIcon(new ImageIcon("resources/icons/exit.png"));

		toolBar.add(load);
		toolBar.add(save);
		toolBar.add(clear);
		toolBar.add(run);
		toolBar.add(stop);
		toolBar.add(reset);
		toolBar.add(new JLabel("Ticks: "));
		toolBar.add(_time);
		toolBar.add(exit);
		return toolBar;
	}

	public void onAdvanceStart(RoadMap map​, List<Event> events​, int time​) {

	}

	public void onAdvanceEnd(RoadMap map​, List<Event> events​, int time​) {

	}

	public void onEventAdded(RoadMap map​, List<Event> events​, Event e, int time​) {

	}

	public void onReset(RoadMap map​, List<Event> events​, int time​) {

	}

	public void onRegister(RoadMap map​, List<Event> events​, int time​) {

	}

	public void onError(String err​) {

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (Buttons.SAVE.name().equals(e.getActionCommand())) {
			save();
		} else if (Buttons.LOAD.name().equals(e.getActionCommand())) {
			load();
		} else if (Buttons.CLEAR.name().equals(e.getActionCommand())) {
			clear();
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

	private void save() {

	}

	private void load() {

	}

	private void clear() {

	}

	private void run() {

	}

	private void stop() {

	}

	private void reset() {

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

}

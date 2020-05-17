package simulator.view;

import java.awt.Frame;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

public class StatusBar extends JPanel implements TrafficSimObserver {

	private Controller _ctrl;

	private JPanel statusBar;
	private JLabel ticksLabel;
	private JLabel _time;
	private JLabel _addedEvent;

	public StatusBar(Controller ctrl) {
		_ctrl = ctrl;

		initGUI();
		_ctrl.addObserver(this);
	}

	private void initGUI() {
		statusBar = new JPanel();
		statusBar.setLayout(new BoxLayout(statusBar, BoxLayout.X_AXIS));

		ticksLabel = new JLabel("Tick: ");
		_time = new JLabel("None");
		_addedEvent = new JLabel("Event: None");

		ticksLabel.setAlignmentX(JLabel.LEFT_ALIGNMENT);
		_time.setAlignmentX(JLabel.LEFT_ALIGNMENT);
		_addedEvent.setAlignmentX(JLabel.LEFT_ALIGNMENT);

		statusBar.add(ticksLabel);
		statusBar.add(_time);
		statusBar.add(Box.createHorizontalStrut(50));
		statusBar.add(new JSeparator(SwingConstants.VERTICAL));
		statusBar.add(Box.createHorizontalStrut(15));
		statusBar.add(_addedEvent);

//		statusBar.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
//		statusBar.setBorder(BorderFactory.createLoweredBevelBorder()); 

		this.add(statusBar);
	}

	public void onAdvanceStart(RoadMap map​, List<Event> events​, int time) {
		_time.setText(Integer.toString(time));
		_addedEvent.setText("");
	}

	public void onAdvanceEnd(RoadMap map​, List<Event> events​, int time​) {
		_time.setText(Integer.toString(time​));
	}

	public void onEventAdded(RoadMap map​, List<Event> events​, Event e, int time​) {
		_time.setText(Integer.toString(time​));
		_addedEvent.setText("Event added (" + e.toString() + ")");
	}

	public void onReset(RoadMap map​, List<Event> events​, int time​) {
		_time.setText("None");
		_addedEvent.setText("The simulation is reset");
	}

	public void onRegister(RoadMap map​, List<Event> events​, int time​) {
		if (!events​.isEmpty())
			_addedEvent.setText("Event added (" + events​.get(events​.size() - 1).toString() + ")");
	}

	public void onError(String err​) {
		JOptionPane.showMessageDialog((Frame) SwingUtilities.getWindowAncestor(this), err​, "ERROR",
				JOptionPane.ERROR_MESSAGE);
	}

	@Override
	public void onLoad(RoadMap map, List<Event> events, int time) {
		_time.setText(Integer.toString(time));
	}

	@Override
	public void onUndo(RoadMap map, List<Event> events, int time) {
		_time.setText(Integer.toString(time));
		_addedEvent.setText("Undo 1 tick");
		
		
	}

}

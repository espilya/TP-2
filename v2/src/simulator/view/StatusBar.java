package simulator.view;

import java.awt.Frame;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

public class StatusBar extends JPanel implements TrafficSimObserver {

	private Controller ctrl;

	private JPanel mainPanel;
	private JTextField steps;
	private JTextField addedEvent;

	public StatusBar(Controller _ctrl) {
		// TODO Auto-generated constructor stub

	}

	public void onAdvanceStart(RoadMap map​, List<Event> events​, int time) {
		steps.setText(String.valueOf(time));
		addedEvent.setText("");
	}

	public void onAdvanceEnd(RoadMap map​, List<Event> events​, int time​) {
		steps.setText(String.valueOf(time​));
	}

	public void onEventAdded(RoadMap map​, List<Event> events​, Event e, int time​) {
		steps.setText(String.valueOf(time​));
		addedEvent.setText(" Event added (" + e.toString() + ")");
	}

	public void onReset(RoadMap map​, List<Event> events​, int time​) {
		steps.setText(String.valueOf(0));
	}

	public void onRegister(RoadMap map​, List<Event> events​, int time​) {
		if (!events​.isEmpty())
			addedEvent.setText(" Event added (" + events​.get(events​.size() - 1).toString() + ")");
	}

	public void onError(String err​) {
		JOptionPane.showMessageDialog((Frame) SwingUtilities.getWindowAncestor(this), err​, "ERROR",
				JOptionPane.ERROR_MESSAGE);
	}

	@Override
	public void onLoad(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		
	}

}

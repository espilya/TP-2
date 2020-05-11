package simulator.view;

import java.util.List;

import javax.swing.JPanel;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

/**
 * This is the top panel of the window
 * @author Puppy
 *
 */
public class ControlPanel extends JPanel implements TrafficSimObserver {
	private static final long serialVersionUID = 1L;

	public ControlPanel(Controller _ctrl) {
		

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

}

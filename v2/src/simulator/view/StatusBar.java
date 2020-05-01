package simulator.view;

import java.util.List;

import javax.swing.JPanel;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

public class StatusBar extends JPanel implements  TrafficSimObserver  {

	public StatusBar(Controller _ctrl) {
		// TODO Auto-generated constructor stub
		
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

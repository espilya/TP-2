package simulator.model;

import java.io.Serializable;
import java.util.List;
 
public interface TrafficSimObserver extends Serializable{
		
	
	void onAdvanceStart(RoadMap map​, List<Event> events​, int time​);

	void onAdvanceEnd(RoadMap map​, List<Event> events​, int time​);

	void onEventAdded(RoadMap map​, List<Event> events​, Event e, int time​);

	void onReset(RoadMap map​, List<Event> events​, int time​);

	void onRegister(RoadMap map​, List<Event> events​, int time​);

	void onError(String err​);
	
	void onLoad(RoadMap map, List<Event> events, int time); 
	
	void onUndo(RoadMap map, List<Event> events, int time);
	
	
}

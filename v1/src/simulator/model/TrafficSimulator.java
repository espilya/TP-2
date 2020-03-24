package simulator.model;

import java.util.List;

import org.json.JSONObject;

import simulator.exceptions.ExistingObjectException;
import simulator.exceptions.IncorrectObjectException;
import simulator.exceptions.IncorrectVariableValueException;
import simulator.exceptions.NonExistingObjectException;
import simulator.misc.SortedArrayList;

public class TrafficSimulator {

	private RoadMap roadMap;
	private List<Event> listEvents;
	private int time;

	public TrafficSimulator() {
		// set values to default
		time = 0;
		listEvents = new SortedArrayList<Event>();
		roadMap = new RoadMap();
	}

	public void addEvent(Event e) {
		listEvents.add(e);
	}

	public void advance() {
// 		1)
		time++;

//		@SuppressWarnings("unused")
//		int temp;
//		if (time == 143)
//			temp = time;

//		2) 
		List<Event> copy = new SortedArrayList<Event>();
		copy.addAll(listEvents);
		for (Event e : copy) {
			if (e.getTime() == time) {
				try {
					e.execute(roadMap);
				} catch (ExistingObjectException | IncorrectObjectException | NonExistingObjectException
						| IncorrectVariableValueException exc) {
					exc.printStackTrace();
				}
				listEvents.remove(e);
			}
		}

//		3)
		for (Junction j : roadMap.getJunctions()) {
			j.advance(time);
		}
//		4)
		for (Road r : roadMap.getRoads()) {
			r.advance(time);
		}
	}

	public void reset() {
		time = 0;
		roadMap.reset();
		listEvents.clear();
	}

	public JSONObject report() {
		JSONObject j = new JSONObject();
		j.put("time", time);
		j.put("state", roadMap.report());
		return j;
	}

}

package simulator.model;

import java.util.List;

import org.json.JSONObject;

import simulator.exceptions.ExistingObjectException;
import simulator.exceptions.IncorrectObjectException;
import simulator.exceptions.IncorrectVariableValueException;
import simulator.exceptions.NonExistingObjectException;
import simulator.misc.SortedArrayList;

public class TrafficSimulator implements Observable<TrafficSimObserver> {

	private RoadMap roadMap;
	private List<Event> listEvents;
	private int time;
//	private Comparator<Event> _cmp;

	public TrafficSimulator() {
		// set values to default
		time = 0;
		listEvents = new SortedArrayList<Event>();
		roadMap = new RoadMap();
	}

	public void addEvent(Event e) {
		listEvents.add(e);
		TrafficSimObserver.onEventAdded(roadMap, listEvents, e, time);
	}

	public void advance() {
// 		1)
		time++;
		onAdvanceStart(roadMap, listEvents, time);
		int i = 0;
		int size = listEvents.size();
		List<Event> copy = new SortedArrayList<Event>();
		copy.addAll(listEvents);
		while (i < size && copy.get(i).getTime() == time) {
			try {
				copy.get(i).execute(roadMap);
			} catch (ExistingObjectException | IncorrectObjectException | NonExistingObjectException
					| IncorrectVariableValueException exc) {
				exc.printStackTrace();
			}
			listEvents.remove(copy.get(i));
			i++;
		}
		copy.clear();

//		3)
		for (Junction j : roadMap.getJunctions()) {
			j.advance(time);
		}
//		4)
		for (Road r : roadMap.getRoads()) {
			r.advance(time);
		}
		onAdvanceEnd(roadMap, copy, size);
	}

	public void reset() {
		time = 0;
		roadMap.reset();
		listEvents.clear();
		TrafficSimObserver.reset(roadMap, listEvents, time);
	}

	public JSONObject report() {
		JSONObject j = new JSONObject();
		j.put("time", time);
		j.put("state", roadMap.report());
		return j;
	}

	@Override
	public void addObserver(TrafficSimObserver o) {
		TrafficSimObserver.onRegister(roadMap, listEvents, time);
		
	}

	@Override
	public void removeObserver(TrafficSimObserver o) {

		
	}

}

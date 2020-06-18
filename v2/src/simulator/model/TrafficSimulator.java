package simulator.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.json.JSONObject;

import simulator.exceptions.ExistingObjectException;
import simulator.exceptions.IncorrectObjectException;
import simulator.exceptions.IncorrectVariableValueException;
import simulator.exceptions.NonExistingObjectException;
import simulator.misc.SortedArrayList;

public class TrafficSimulator implements Observable<TrafficSimObserver>, Serializable {
	private static final long serialVersionUID = -1919698891670845698L;

	private RoadMap roadMap;
	private List<Event> listEvents;
	private int time;
	private List<TrafficSimObserver> listObs;

	public class SerializableComparator implements Comparator<Event>, Serializable {
		private static final long serialVersionUID = 1;

		@Override
		public int compare(Event o1, Event o2) {
			if (o1._time > o2._time)
				return 1;
			else if (o1._time < o2._time)
				return -1;
			else
				return 0;
		}
	}

	public TrafficSimulator() {
		// set values to default
		time = 0;
		listEvents = new SortedArrayList<Event>(new SerializableComparator());
		roadMap = new RoadMap();
		listObs = new ArrayList<TrafficSimObserver>();
	}

	public void addEvent(Event e) {
		listEvents.add(e);
		for (TrafficSimObserver o : listObs)
			o.onEventAdded(roadMap, listEvents, e, time);
	}

	public void advance() throws Exception {

// 		1)
		time++;
		for (TrafficSimObserver o : listObs)
			o.onAdvanceStart(roadMap, listEvents, time);
		int i = 0;
		int size = listEvents.size();
		List<Event> copy = new SortedArrayList<Event>();
		copy.addAll(listEvents);
		while (i < size && copy.get(i).getTime() == time) {
			try {
				copy.get(i).execute(roadMap);
			} catch (ExistingObjectException | IncorrectObjectException | NonExistingObjectException
					| IncorrectVariableValueException e) {
				for (TrafficSimObserver o : listObs)
					o.onError(e.getMessage());
//					e.printStackTrace();
				throw e;
					
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
		for (TrafficSimObserver o : listObs)
			o.onAdvanceEnd(roadMap, listEvents, time);
	}

	public void reset() {
		time = 0;
		roadMap.reset();
		listEvents.clear();
		for (TrafficSimObserver o : listObs)
			o.onReset(roadMap, listEvents, time);
	}

	public JSONObject report() {
		JSONObject j = new JSONObject();
		j.put("time", time);
		j.put("state", roadMap.report());
		return j;
	}

	@Override
	public void addObserver(TrafficSimObserver o) {
		o.onRegister(roadMap, listEvents, time);
		if (o != null && !listObs.contains(o)) {
			listObs.add(o);
		}
	}

	@Override
	public void removeObserver(TrafficSimObserver o) {
		if (o != null && listObs.contains(o))
			listObs.remove(o);
	}

	public List<Event> getEvents() {
		return listEvents;
	}

	public int getTime() {
		return time;
	}

	
	

}

package simulator.launcher;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import simulator.model.Event;
import simulator.model.Road;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

class RoadsInfoAnalizer implements TrafficSimObserver {
	private static final long serialVersionUID = 1L;

	// road , max
	Map<Road, Integer> _roads;

	protected RoadsInfoAnalizer() {
		_roads = new HashMap<Road, Integer>();
	}

	public String analizeCrowdedness() {
		String data = "crowdedness: ";

		for (Entry<Road, Integer> entry : _roads.entrySet()) {
			Road key = entry.getKey();
			int value = entry.getValue();
			data += ('(' + key.getId() + "," + value + "),");
		} 
	    data = data.substring(0, data.length() - 1);
		return data;
	}

	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {

	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		List<Road> roadList = map.getRoads();
		for (Road r : roadList) {
			if (_roads.containsKey(r)) {
				int actualMaxVehicleNum = _roads.get(r);
				int newNumCars = r.getVehicleList().size();
				if (newNumCars > actualMaxVehicleNum)
					_roads.replace(r, newNumCars);
			} else {
				_roads.put(r, r.getVehicleList().size());
			}
		}
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		// se deberia anadir el road aqui pero no se como hacerlo.
		// no se como comprobar si un evento es road o no
	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
	}

	@Override
	public void onError(String err) {
	}

	@Override
	public void onLoad(RoadMap map, List<Event> events, int time) {
	}

}

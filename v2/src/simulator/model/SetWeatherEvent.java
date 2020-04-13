package simulator.model;

import java.util.List;

import simulator.exceptions.IncorrectVariableValueException;
import simulator.exceptions.NonExistingObjectException;
import simulator.misc.Pair;

public class SetWeatherEvent extends Event {
	List<Pair<String, Weather>> ws;

	public SetWeatherEvent(int time, List<Pair<String, Weather>> ws) {
		super(time);
		if (ws.isEmpty() || ws == null)
			throw new IllegalArgumentException();
		else
			this.ws = ws;
	}

	@Override
	void execute(RoadMap map) throws NonExistingObjectException, IncorrectVariableValueException {
		for (Pair<String, Weather> p : ws) {
			Road r = map.getRoad(p.getFirst());
			if (r == null)
				throw new NonExistingObjectException("Road " + p.getFirst() + " does not exist in roadMap");
			r.setWeather(p.getSecond());
		}
	}
	
	public String toString() {
		return "SetWeather '" + ws + "'";
	}

}

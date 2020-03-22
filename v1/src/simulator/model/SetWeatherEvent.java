package simulator.model;

import java.util.List;

import exceptions.ValueParseException;
import simulator.misc.Pair;

// TO DO
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
	void execute(RoadMap map) {
		for (Pair<String, Weather> p : ws) {
			Road r = map.getRoad(p.getFirst());

			try {
				if (r == null)
					throw new Exception();
				r.setWeather(p.getSecond());
			} catch (ValueParseException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}

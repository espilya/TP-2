package simulator.model;

import java.util.List;

import simulator.misc.Pair;
// TO DO
public class SetWeatherEvent extends Event {
	List<Pair<String, Weather>> ws;
	public SetWeatherEvent(int time, List<Pair<String, Weather>> ws) {
		super(time);
		if(ws.isEmpty())
			throw //exception
		else
			this.ws = ws;
	}

	@Override
	void execute(RoadMap map) {
		// TODO Auto-generated method stub

	}

}

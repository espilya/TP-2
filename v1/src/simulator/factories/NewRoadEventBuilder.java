package simulator.factories;

import org.json.JSONObject;

import simulator.model.Event;
import simulator.model.Weather;

public abstract class NewRoadEventBuilder extends Builder<Event> {

	NewRoadEventBuilder(String type) {
		super(type);

	}

	protected Event createTheInstance(JSONObject data) {
		int time = data.getInt("time");
		String id = data.getString("id");
		String src = data.getString("src");
		String dst = data.getString("dest");
		int length = data.getInt("length");
		int co2limit = data.getInt("co2limit");
		//
		int maxspeed = data.getInt("maxspeed");
		Weather weather = Weather.valueOf(data.getString("weather"));

		return createTheRoad(time, id, src, dst, length, co2limit, maxspeed, weather);
	}

	abstract Event createTheRoad(int time, String id, String src, String dst, int length, int co2limit, int maxspeed,
			Weather weather);

}

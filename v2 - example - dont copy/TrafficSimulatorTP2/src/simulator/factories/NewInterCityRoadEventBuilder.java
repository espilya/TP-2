package simulator.factories;

import simulator.model.Event;
import simulator.model.NewInterCityRoad;
import simulator.model.Weather;

public class NewInterCityRoadEventBuilder extends NewRoadEventBuider{

	final static String type = "new_inter_city_road";
	public NewInterCityRoadEventBuilder() {
		super(type);
	}
	@Override
	Event createTheRoad(int time, String id, String src, String dest, int length, int co2Limit, int maxSpeed,
			Weather weather) {
		return new NewInterCityRoad(time, id, src, dest, length, co2Limit, maxSpeed, weather);
	}

	/*protected Event createTheInstance(JSONObject data) {
		int time = data.getInt("time");
		String id = data.getString("id");
		String src = data.getString("src");
		String dest = data.getString("dest");
		int length = data.getInt("length");
		int co2Limit = data.getInt("co2limit");
		int maxSpeed = data.getInt("maxspeed");
		String s = data.getString("weather");
		Weather weather = Weather.valueOf(s);
		return new NewInterCityRoad(time, id, src, dest, length, co2Limit, maxSpeed, weather);
	}
*/
}

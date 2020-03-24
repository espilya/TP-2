package simulator.factories;

import simulator.model.Event;
import simulator.model.NewCityRoadEvent;
import simulator.model.Weather;

public class NewCityRoadEventBuilder extends NewRoadEventBuilder {

	private final static String type = "new_city_road";

	public NewCityRoadEventBuilder() {
		super(type);
	}

	Event createTheRoad(int time, String id, String src, String dst, int length, int co2limit, int maxspeed,
			Weather weather) {
		return new NewCityRoadEvent(time, id, src, dst, length, co2limit, maxspeed, weather);
	}

}

package simulator.factories;

import simulator.model.Event;
import simulator.model.NewInterCityRoadEvent;
import simulator.model.Weather;

public class NewInterCityRoadEventBuilder extends NewRoadEventBuilder {

	private final static String type = "new_inter_city_road";

	public NewInterCityRoadEventBuilder() {
		super(type);
	}

	Event createTheRoad(int time, String id, String src, String dst, int length, int co2limit, int maxspeed,
			Weather weather) {
		return new NewInterCityRoadEvent(time, id, src, dst, length, co2limit, maxspeed, weather);
	}

}

package simulator.factories;

import simulator.model.Event;
import simulator.model.NewCityRoadEvent;

public class NewCityRoadEventBuilder extends NewRoadEventBuilder{

	final String type = "new_city_road";
	NewCityRoadEventBuilder(String type) {
		super(type);
	}

	@Override
	Event createTheRoad() {
		// TODO Auto-generated method stub
		return new NewCityRoadEvent(time, type, type, type, 0, 0, 0, null);
	}

}

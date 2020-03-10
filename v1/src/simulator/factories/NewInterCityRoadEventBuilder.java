package simulator.factories;

import simulator.model.Event;
import simulator.model.NewInterCityRoadEvent;

public class NewInterCityRoadEventBuilder extends NewRoadEventBuilder{

	final String type = "new_intercity_road";
	NewInterCityRoadEventBuilder(String type) {
		super(type);
		// TODO Auto-generated constructor stub
	}

	@Override
	Event createTheRoad() {
		// TODO Auto-generated method stub
		return new NewInterCityRoadEvent(0, type, type, type, 0, 0, 0, null);
	}

}

package simulator.model;

import exceptions.ValueParseException;

public class NewInterCityRoadEvent extends NewRoadEvent {

	public NewInterCityRoadEvent(int time, String id, String srcJun, String destJunc, int length, int co2Limit,
			int maxSpeed, Weather weather) {
		super(time, id, srcJun, destJunc, length, co2Limit, maxSpeed, weather); // ...
	}

	@Override
	void execute(RoadMap map)   {
		map.addRoad(createRoadObject());

	}

	@Override
	public Road createRoadObject() throws ValueParseException {
		InterCityRoad newroad = new InterCityRoad(id, srcJun, destJunc, maxSpeed, co2Limit, length, weather);
		// must be Junction but in PDF String is required
		return newroad;
	}
}

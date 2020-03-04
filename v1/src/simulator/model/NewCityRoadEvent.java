package simulator.model;

import exceptions.ValueParseException;

public class NewCityRoadEvent extends NewRoadEvent {
	
	public NewCityRoadEvent(int time, String id, String srcJun, String
			destJunc, int length, int co2Limit, int maxSpeed, Weather weather)
			{
			super(time, id, srcJun, destJunc, length, co2Limit, maxSpeed, weather);

			}

	@Override
	void execute(RoadMap map) throws ValueParseException {
		map.addRoad(createRoadObject());
		
	}

	@Override
	public Road createRoadObject() throws ValueParseException {
		CityRoad newroad = new CityRoad(id, srcJun, destJunc, maxSpeed, co2Limit, length, weather);
		// must be Junction but in PDF String is required
		return newroad;
	}

}

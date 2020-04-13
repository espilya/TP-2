package simulator.model;

import simulator.exceptions.IncorrectVariableValueException;

public class NewInterCityRoadEvent extends NewRoadEvent {
	

	public NewInterCityRoadEvent(int time, String id, String srcJun, String destJunc, int length, int co2Limit,
			int maxSpeed, Weather weather) {
		super(time, id, srcJun, destJunc, length, co2Limit, maxSpeed, weather);
	}


	@Override
	protected Road createRoadObject() {
		Junction srcJun1;
		Junction destJunc1;
		srcJun1 = rMap.getJunction(srcJun);
		destJunc1 = rMap.getJunction(destJunc);
		InterCityRoad newroad = null;
		try {
			newroad = new InterCityRoad(id, srcJun1, destJunc1, maxSpeed, co2Limit, length, weather);
		} catch (IncorrectVariableValueException e) {
			e.printStackTrace();
		}
		return newroad;
	}
	
	public String toString() {
		return "New InterCityRoad '" + id + "'";
	}
}

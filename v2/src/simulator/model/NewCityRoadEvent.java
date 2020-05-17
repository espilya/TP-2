package simulator.model;

import java.io.Serializable;

import simulator.exceptions.IncorrectVariableValueException;

public class NewCityRoadEvent extends NewRoadEvent implements Serializable{
	private static final long serialVersionUID = 1598498962842330471L;

	RoadMap roadMap;

	public NewCityRoadEvent(int time, String id, String srcJun, String destJunc, int length, int co2Limit, int maxSpeed,
			Weather weather) {
		super(time, id, srcJun, destJunc, length, co2Limit, maxSpeed, weather);
	}

	@Override
	protected Road createRoadObject() throws IncorrectVariableValueException {
		Junction srcJun1;
		Junction destJunc1;
		srcJun1 = rMap.getJunction(srcJun);
		destJunc1 = rMap.getJunction(destJunc);
		CityRoad newroad = null;
		newroad = new CityRoad(id, srcJun1, destJunc1, maxSpeed, co2Limit, length, weather);
		return newroad;
	}

	@Override
	public String toString() {
		return ("New CityRoad '" + id + "'");
	}

}

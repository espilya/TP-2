package simulator.model;

import simulator.exceptions.IncorrectVariableValueException;

public class CityRoad extends Road {

	CityRoad(String id, Junction srcJunc, Junction destJunc, int maxSpeed, int contLimit, int length, Weather weather)
			throws IncorrectVariableValueException {
		super(id, srcJunc, destJunc, maxSpeed, contLimit, length, weather);
	}

	public void reduceTotalContamination() throws IncorrectVariableValueException {

		if (weather.equals(Weather.WINDY) || weather.equals(Weather.STORM))
			contTotal -= 10;
		else
			contTotal -= 2;
		if (contTotal < 0)
			contTotal = 0;
	}

	public int calculateVehicleSpeed(Vehicle car) {
		int vehicleSpeed = (int) Math.ceil((((11.0 - car.getContClass()) / 11.0) * maxSpeed));
		return vehicleSpeed;
	}

	void updateSpeedLimit() {
		speedLimit = maxSpeed;
	}

}

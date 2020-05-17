package simulator.model;

import java.io.Serializable;

import simulator.exceptions.IncorrectVariableValueException;

public class InterCityRoad extends Road implements Serializable{
	private static final long serialVersionUID = -9147848594275538822L;

	InterCityRoad(String id, Junction srcJunc, Junction destJunc, int maxSpeed, int contLimit, int length,
			Weather weather) throws IncorrectVariableValueException {
		super(id, srcJunc, destJunc, maxSpeed, contLimit, length, weather);
	}

	public void reduceTotalContamination() throws IncorrectVariableValueException {
		int x;
		if (weather.equals(Weather.SUNNY))
			x = 2;
		else if (weather.equals(Weather.CLOUDY))
			x = 3;
		else if (weather.equals(Weather.RAINY))
			x = 10;
		else if (weather.equals(Weather.WINDY))
			x = 15;
		else // if (weather.equals(Weather.STORM))
			x = 20;
		contTotal = (int) (((100.0 - x) / 100.0) * contTotal);
		if (contTotal < 0)
			throw new IncorrectVariableValueException();
	}

	public void updateSpeedLimit() {
		if (contTotal > contLimit) {
			speedLimit = (int) (maxSpeed * 0.5);
		} else
			speedLimit = maxSpeed;
	}

	public int calculateVehicleSpeed(Vehicle car) {

		if (weather.equals(Weather.STORM)) {
			return (int) (speedLimit * 0.8);
		} else {
			return speedLimit;
		}
	}

}

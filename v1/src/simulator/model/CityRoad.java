package simulator.model;

import exceptions.IncorrectVariableValue;
import exceptions.ValueParseException;

public class CityRoad extends Road {

	 CityRoad(String id, Junction srcJunc, Junction destJunc,int maxSpeed,int contLimit,int length, Weather weather) throws ValueParseException{
		super(id, srcJunc, destJunc, maxSpeed, contLimit, length, weather);

		// the speed limit does not change, it is always the maximum speed.
	}

	public void reduceTotalContamination() throws IncorrectVariableValue {

		if (weather.equals(Weather.WINDY) || weather.equals(Weather.STORM))
			contTotal -= 10;
		
		else 
			contTotal -= 2;
		
		if (contTotal < 0) 
			throw new IncorrectVariableValue();
			
	}

	public int calculateVehicleSpeed(Vehicle car) {
		int vehicleSpeed = (int) (((11.0-car.getContClass())/11.0)* maxSpeed);
		return vehicleSpeed;
	}

	@Override
	void updateSpeedLimit() {
		speedLimit = maxSpeed;		
	}

}

package simulator.model;

import exceptions.IncorrectVariableValue;
import exceptions.ValueParseException;

public class CityRoad extends Road {

	 CityRoad(String id, Junction srcJunc, Junction destJunc,int maxSpeed,int contLimit,int length, Weather weather) throws ValueParseException{
		super(id, srcJunc, destJunc, maxSpeed, contLimit, length, weather);

		// TODO Auto-generated constructor stub
		// the speed limit does not change, it is always the maximum speed.
	}

	public void reduceTotalContamination() throws IncorrectVariableValue {
		//IM NOT SURE, NEED ASK TO TEACHER
		//NOT SURE IF WE MUST THROW EXCEPTION OR SET IT TO ZERO

		if (weather.equals(Weather.WINDY) || weather.equals(Weather.STORM)){
			contTotal -= 10;
			if (contTotal < 0) {
				throw new IncorrectVariableValue();
			}
		}
		else {
			contTotal -= 2;
			if (contTotal < 0) {
				contTotal = 0;
				throw new IncorrectVariableValue();

			}
			}
	}

	public int calculateVehicleSpeed(Vehicle car) {
		int contClassCar = car.getContClass();
		int vehicleSpeed = (int) (((11.0-contClassCar)/11.0)* maxSpeed);
		//System.out.println(vehicleSpeed); // or write to file or do nothing
		return vehicleSpeed;
	}

	@Override
	void updateSpeedLimit() {
		// TODO Auto-generated method stub
		
	}

}

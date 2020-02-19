package simulator.model;

import exceptions.IncorrectVariableValue;
import exceptions.ValueParseException;

public class InterCityRoad extends Road {

	InterCityRoad(String id, Junction srcJunc, Junction destJunc,int maxSpeed,int contLimit,int length, Weather weather) throws ValueParseException {
		super(id, srcJunc, destJunc, maxSpeed, contLimit, length, weather);
		// TODO Auto-generated constructor stub
	}

	public void reduceTotalContamination() throws IncorrectVariableValue {
		// we dont have attribute name now in the class Weather, maybe it will call in other way
		//state.equals(VehicleStatus.TRAVELING)
		if (weather.equals(Weather.SUNNY)){
			contTotal = ((100-2)/100)*contTotal;
			if (contTotal < 0)
				throw new IncorrectVariableValue();
		}
		else if (weather.equals(Weather.CLOUDY)){
			contTotal = ((100-3)/100)*contTotal;
			if (contTotal < 0)
				throw new IncorrectVariableValue();
		}
		else if (weather.equals(Weather.RAINY)){
			contTotal = ((100-10)/100)*contTotal;
			if (contTotal < 0)
				throw new IncorrectVariableValue();
		}
		else if (weather.equals(Weather.WINDY)){
			contTotal = ((100-15)/100)*contTotal;
			if (contTotal < 0)
				throw new IncorrectVariableValue();
		}
		else if (weather.equals(Weather.STORM)){
			contTotal = ((100-20)/100)*contTotal;
			if (contTotal < 0)
				throw new IncorrectVariableValue();
		}
		System.out.println(contTotal); // or write to file or do nothing



	}

	public void updateSpeedLimit() {   //not sure about should we put here values
		// change names
		if (contTotal > contLimit){
			speedLimit = (int) (maxSpeed*0.5);
		}
		else {
			speedLimit = maxSpeed;
		}

	}

	public int calculateVehicleSpeed(Vehicle car) { 
		
		//calculate carSpped from car??
		int speedVehicle;
		if (weather.equals(Weather.STORM)){
			speedVehicle = (int) (speedLimit * 0.8);
		}
		else {
			speedVehicle = speedLimit;
		}
		return speedVehicle;
	}


	//... probably(im sure) more methods
}

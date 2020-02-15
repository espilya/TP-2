package simulator.model;

public class InterCityRoad extends Road {

	InterCityRoad(String id, Junction srcJunc, Junction destJunc,int maxSpeed,int contLimit,int length, Weather weather) {
		super(id, srcJunc, destJunc, maxSpeed, contLimit, length, weather);
		// TODO Auto-generated constructor stub
	}

	public void reduceTotalContamination(int totalCont, Weather weather) {
		// we dont have attribute name now in the class Weather, maybe it will call in other way
		//state.equals(VehicleStatus.TRAVELING)
		if (weather.equals(Weather.SUNNY)){
			totalCont = ((100-2)/100)*totalCont;
			if (totalCont < 0)
				throw new Exception();
		}
		else if (weather.equals(Weather.CLOUDY)){
			totalCont = ((100-3)/100)*totalCont;
			if (totalCont < 0)
				throw new Exception();
		}
		else if (weather.equals(Weather.RAINY)){
			totalCont = ((100-10)/100)*totalCont;
			if (totalCont < 0)
				throw new Exception();
		}
		else if (weather.equals(Weather.WINDY)){
			totalCont = ((100-15)/100)*totalCont;
			if (totalCont < 0)
				throw new Exception();
		}
		else if (weather.equals(Weather.STORM)){
			totalCont = ((100-20)/100)*totalCont;
			if (totalCont < 0)
				throw new Exception();
		}
		System.out.println(totalCont); // or write to file or do nothing



	}

	public void updateSpeedLimit(int totalCont, int contAlarmLimit) {   //not sure about should we put here values
		// change names
		if (totalCont > contAlarmLimit){
			speedLimit = (int) (maxSpeed*0.5);
		}
		else {
			speedLimit = maxSpeed;
		}

	}

	public void calculateVehicleSpeed() { // dont know about variables
		int speedVehicle;
		if (weather.equals(Weather.STORM)){
			speedVehicle = (int) (speedLimit * 0.8);
		}
		else {
			speedVehicle = speedLimit;
		}
		System.out.println(speedVehicle);
		//4to za ebanniy debug 4erez print??? da i nahuya on tut vobshe??
		//u nas dazhe main'a net a ti uzhe ho4esh debagat progu 

	}
	
	
	//... probably(im sure) more
}

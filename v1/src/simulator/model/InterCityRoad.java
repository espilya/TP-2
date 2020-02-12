package simulator.model;

public class InterCityRoad extends Road {

	InterCityRoad(String id, Junction srcJunc, Junction destJunc,int maxSpeed,int contLimit,int length, Weather weather) {
		super(id, srcJunc, destJunc, maxSpeed, contLimit, length, weather);
		// TODO Auto-generated constructor stub
	}

	public void reduceTotalContamination(int totalCont, Weather weather) {
		// we dont have attribute name now in the class Weather, maybe it will call in other way
		if (weather.name == "SUNNY"){
			totalCont = ((100-2)/100)*totalCont;
			if (totalCont < 0)
				throw new Exception();
		}
		else if (weather.name == "CLOUDY"){
			totalCont = ((100-3)/100)*totalCont;
			if (totalCont < 0)
				throw new Exception();
		}
		else if (weather.name == "RAINY"){
			totalCont = ((100-10)/100)*totalCont;
			if (totalCont < 0)
				throw new Exception();
		}
		else if (weather.name == "WINDY"){
			totalCont = ((100-15)/100)*totalCont;
			if (totalCont < 0)
				throw new Exception();
		}
		else if (weather.name == "STORM"){
			totalCont = ((100-20)/100)*totalCont;
			if (totalCont < 0)
				throw new Exception();
		}
		System.out.println(totalCont); // or write to file or do nothing



	}

	public void updateSpeedLimit(int totalCont, int contAlarmLimit) {   //not sure about should we put here values
		// change names
		if (totalCont > contAlarmLimit){
			currSpeedLimit = maxSpeed*0.5;
		}
		else {
			currSpeedLimit = maxSpeed;
		}

	}
//			si la contaminación total excede el límite de contaminaión, entonces
//		pone el límite de la velocidad al 50% de la velocidad máxima (es decir a
//		“(int)(maxSpeed*0.5)”). En otro caso pone el límite de la velocidad a la velocidad
//		máxima.

	public void calculateVehicleSpeed() { // dont know about variables
		int speedVehicle;
		if (weather.name == "STORM"){
			speedVehicle = speedLimit * 0.8;
		}
		else {
			speedVehicle = speedLimit;
		}
		System.out.println(speedVehicle);

	}

//			pone la velocidad del vehículo a la velocidad límite de la carretera.
//		Si el tiempo es STORM lo reduce en un 20% (es decir, “(int)(speedLimit*0.8)”).

}

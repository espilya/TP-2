package simulator.model;

public class CityRoad extends Road {

	CityRoad(String id) {
		super(id);
		// TODO Auto-generated constructor stub
		// the speed limit does not change, it is always the maximum speed.
	}

	public void reduceTotalContamination(int totalCont, Weather weather) {

		if (weather.name == 'WINDY' || weather.name == 'STORM'){ //name netu
			totalCont -= 10;
			if (totalCont < 0)
				throw new Exception();
		}
		else {
			totalCont -= 2;
			if (totalCont < 0)
				throw new Exception();
		}
		//check if I am right
	}

	public void calculateVehicleSpeed(int maxSpeed, int contClass) {

		int vehicleSpeed = ((11.0-maxSpeed)/11.0)*contClass;
		System.out.println(vehicleSpeed); // or write to file
	}
}

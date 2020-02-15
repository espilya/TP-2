package simulator.model;

public class CityRoad extends Road {

	 CityRoad(String id, Junction srcJunc, Junction destJunc,int maxSpeed,int contLimit,int length, Weather weather) {
		super(id, srcJunc, destJunc, maxSpeed, contLimit, length, weather);

		// TODO Auto-generated constructor stub
		// the speed limit does not change, it is always the maximum speed.
	}

	public void reduceTotalContamination() {
		//IM NOT SURE, NEED ASK TO TEACHER
		//NOT SURE IF WE MUST THROW EXCEPTION OR SET IT TO ZERO

		if (weather.equals(Weather.WINDY) || weather.equals(Weather.STORM)){
			contTotal -= 10;
			if (contTotal < 0) {

				contTotal = 0;
				throw new Exception();
			}
		}
		else {
			contTotal -= 2;
			if (contTotal < 0) {
				contTotal = 0;
				throw new Exception();

			}
			}
	}

	public void calculateVehicleSpeed(int contClassCar) {

		int vehicleSpeed = (int) (((11.0-contClassCar)/11.0)* maxSpeed);
		System.out.println(vehicleSpeed); // or write to file or do nothing
	}
}

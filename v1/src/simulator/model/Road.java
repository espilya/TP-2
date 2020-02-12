package simulator.model;

import org.json.JSONObject;

import java.util.List;

public class Road extends SimulatedObject{
	protected int length;
	protected int contLimit;
	protected int maxSpeed;
	protected Junction srcJunc;
	protected Junction destJunc;
	protected Weather weather;
	protected List <Vehicle> vehicles;  //? is that enough?
	//spisok avtomobiley na doroge sorted by their location na doroge (descending order o orden descendente)
	Road(String id, Junction srcJunc, Junction destJunc,int maxSpeed,int contLimit,int length, Weather weather) {
		super(id);
		if(maxSpeed <= 0) //positive <=0, non-negative < 0
			throw new Exception();
		else if(length <= 0)
			throw new Exception();
		else if(contLimit < 0)
			throw new Exception();
		else if(srcJunc == null) // I'm not sure that in Java like this
			throw new Exception();
		else if(destJunc == null) // I'm not sure that in Java like this
			throw new Exception();
		else if(weather == null) // I'm not sure that in Java like this
			throw new Exception();
		this.srcJunc = srcJunc;
		this.destJunc = destJunc;
		this.maxSpeed = maxSpeed;
		this.contLimit = contLimit;
		this.length = length;
		this.weather = weather;

	}
	//protected or not??
	void enter(Vehicle v){

	}

	void exit(Vehicle v){

	}

	void setWeather(Weather w){

	}

	void addContamination(int c){

	}

	abstract void reduceTotalContamination(); // sho eto za huynia, chego krasnym?

	abstract void updateSpeedLimit();

	abstract int calculateVehicleSpeed(Vehicle v);




	@Override
	void advance(int time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public JSONObject report() {
		// TODO Auto-generated method stub
		return null;
	}

	public int getLenght() {
		// TODO Auto-generated method stub
		return 0;
	}

}

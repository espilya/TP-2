package simulator.model;

import org.json.JSONObject;

import exceptions.ValueParseException;

import java.util.Iterator;
import java.util.List;

public abstract class Road extends SimulatedObject{
	protected int length;
	protected int contLimit;
	protected int contTotal;
	protected int maxSpeed;
	protected int speedLimit;
	protected Junction srcJunc;
	protected Junction destJunc;
	protected Weather weather;
	protected List <Vehicle> vehicles;  //? is that enough?    (descending order o orden descendente)
	Road(String id, Junction srcJunc, Junction destJunc,int maxSpeed,int contLimit,int length, Weather weather) throws ValueParseException {

		super(id);
		if(maxSpeed <= 0) //positive <=0, non-negative < 0
			throw new ValueParseException("Negative value for maxSpeed");
		else if(length <= 0)
			throw new ValueParseException("Negative value for length");
		else if(contLimit < 0)
			throw new ValueParseException("Negative value for contamition limit");
		else if(srcJunc == null) 
			throw new ValueParseException("Junction source is null");
		else if(destJunc == null) 
			throw new ValueParseException("Junction destination is null");
		else if(weather == null) 
			throw new ValueParseException("Weather is null");
		this.srcJunc = srcJunc;
		this.destJunc = destJunc;
		this.maxSpeed = maxSpeed;
		this.contLimit = contLimit;
		this.length = length;
		this.weather = weather;

	}

	public void enter(Vehicle v){


	}

	public void exit(Vehicle v){
		

	}

	public void setWeather(Weather w) throws ValueParseException{
		if(weather == null) 
			throw new ValueParseException("Weather is null");
		weather = w;

	}

	public void addContamination(int c) throws ValueParseException{
		if(c < 0)
			throw new ValueParseException("Negative value for contamition");
		contTotal +=c;

	}

	abstract void reduceTotalContamination(); 

	abstract void updateSpeedLimit();

	abstract int calculateVehicleSpeed(Vehicle v);




	@Override
	void advance(int time) {
		reduceTotalContamination();
		updateSpeedLimit();
		
		int size = vehicles.size();
		

		Iterator<Vehicle> it = vehicles.iterator();
		while ( it.hasNext() )
			Vehicle n = it.next();  
		
		
		
		//...
		
		
//		(3) recorre la lista de vehículos (desde el primero al último) y, para cada vehículo:
//			a) pone la velocidad del vehículo al valor devuelto por calculateVehicleSpeed.
//			b) llama al método advance del vehículo.
//			Recuerda ordenar la lista de vehículos por su localización al final del método.
		
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

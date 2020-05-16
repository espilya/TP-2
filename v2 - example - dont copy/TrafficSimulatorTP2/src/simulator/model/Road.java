package simulator.model;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import myExceptions.IncorrectValue;
import simulator.misc.SortedArrayList;

public abstract class Road extends SimulatedObject  {
	
	private Junction cruceOrigen, cruceDest;
	private int longitud, velMax, alarContExcesiva;
	private int contamTotal;
	protected int limActVel;
	private Weather condAmbi;
	private List<Vehicle> vehiculos;
	private Comparator<Vehicle> _cmp;
	
	Road(String id, Junction srcJunc, Junction destJunc, int maxSpeed,int contLimit, 
		int length, Weather weather) throws IncorrectValue{
			super(id);
			if(srcJunc == null) throw new IncorrectValue("no puede tener valor vacia"); 
			else  this.cruceOrigen = srcJunc;
			if(destJunc == null ) throw new IncorrectValue("no puede tener valor vacia");
			else this.cruceDest = destJunc;
			if(maxSpeed< 0) throw new IncorrectValue("no puede ser negativo");
			else this.velMax = maxSpeed;
			if(contLimit< 0) throw new IncorrectValue("no puede ser negativo");
			else this.alarContExcesiva = contLimit;
			if(length < 0) throw new IncorrectValue("no puede ser negativo");
			else this.longitud = length;
			if(weather == null ) throw new IncorrectValue("no puede tener valor vacio");
			else this.condAmbi = weather;
			this.limActVel = this.getVelMax();
			this.contamTotal = 0;
			this._cmp = new Comparator<Vehicle>() {
			   public int compare(Vehicle v1, Vehicle v2) {
				    	if (v1.getLocalizacion() == v2.getLocalizacion()) return 0;
				    	else if (v1.getLocalizacion() < v2.getLocalizacion()) return 1;
				    	else return -1;
			   }
		    };
			this.vehiculos = new SortedArrayList<Vehicle>(this._cmp);
			}
	
	void enter(Vehicle v)throws IncorrectValue {
		if(v.getLocalizacion() == 0 && v.getVelocidadAct() == 0 ) {
			this.vehiculos.add(v);
			//if (ok) System.out.println("se annadio");
		}
		else throw new IncorrectValue("la localizacion o la velocidad actual no es cero");
	}
	
	void exit(Vehicle v) {
		this.vehiculos.remove(v);
		//if (ok) System.out.println("se eliminino");
		
	}
	//Pone las condiciones atmosfericas al valor w si no es nula, sino excepcion
	void setWeather(Weather w)throws IncorrectValue{
		if(w == null)throw new IncorrectValue("El valor es nulo");
		else this.condAmbi = w;
	}
	
	//A馻de c ud de contaminacion, comprobar si es negativo
	void addContamination(int c)throws IncorrectValue{
		if(c < 0)throw new IncorrectValue("El valor es negativo");
		else this.contamTotal += c;
		}
	
	abstract void reduceTotalContamination();
	
	abstract void updateSpeedLimit();
	
	abstract int calculateVehicleSpeed(Vehicle v) throws IncorrectValue; 
	
	void advance(int time) throws IncorrectValue {
		reduceTotalContamination();
		updateSpeedLimit();
		Iterator<Vehicle> it = vehiculos.iterator();
		while(it.hasNext()) {
			Vehicle v = it.next();
			if(v.getEstado() != VehicleStatus.WAITING) calculateVehicleSpeed(v); //waiting -> v == 0
			v.advance(time);
		}
		//Recuerda ordenar la lista de vehículos por su localización al final del método.
		this.vehiculos.sort(_cmp);
	}

	public JSONObject report() {
		JSONObject jo = new JSONObject();
		jo.put("id", this._id);
		jo.put("speedlimit", this.limActVel);
		jo.put("weather", this.condAmbi);
		jo.put("co2", this.contamTotal);
		JSONArray ja = new JSONArray();
		for(Vehicle v : this.vehiculos) ja.put(v.getId());
		jo.put("vehicles", ja);
	return jo;
	}

	public int getLongitud() {
		return this.longitud;
	}

	public int getAlarContExcesiva() {
		return this.alarContExcesiva;
	}

	public int getVelMax() {
		return velMax;
	}

	public Junction getCruceOrigen() {
		return cruceOrigen;
	}

	public Junction getCruceDest() {
		return cruceDest;
	}

	public int getTotalCO2() {
		return contamTotal;
	}
	public void setTotalCO2InterCR(int x) {
		this.contamTotal = (int) (((100.0 - x)/100.0) * this.contamTotal);
	}
	public void setTotalCO2CR(int x) {
		this.contamTotal -= x;
		if(this.contamTotal < 0) this.contamTotal = 0;
	}

	/*public int getCO2Limit() {
		return alarContExcesiva;
	}*/
	
	public Weather getWeather() {
		return this.condAmbi;
	}
}

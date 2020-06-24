package simulator.model;

import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.exceptions.IncorrectVariableValueException;
import simulator.misc.SortedArrayList;

public abstract class Road extends SimulatedObject implements Serializable {
	private static final long serialVersionUID = -37201419222519488L;

	protected int length;
	protected int contLimit;
	protected int contTotal;
	protected int maxSpeed;
	protected int speedLimit;
	protected Junction srcJunc;
	protected Junction destJunc;
	protected Weather weather;
	protected List<Vehicle> vehicles;
	private Comparator<Vehicle> _cmp;

	public class SerializableComparator implements Comparator<Vehicle>, Serializable {
		private static final long serialVersionUID = 1;

		public int compare(Vehicle v1, Vehicle v2) {
			if (v1.getLocation() == v2.getLocation())
				return 0;
			else if (v1.getLocation() < v2.getLocation())
				return 1;
			else
				return -1;
		}
	}

	Road(String id, Junction srcJunc, Junction destJunc, int maxSpeed, int contLimit, int length, Weather weather)
			throws IncorrectVariableValueException {
		super(id);

		if (maxSpeed <= 0) // positive <=0, non-negative < 0
			throw new IncorrectVariableValueException("Negative value for maxSpeed: " + maxSpeed);
		else if (length <= 0)
			throw new IncorrectVariableValueException("Negative value for length: " + length);
		else if (contLimit < 0)
			throw new IncorrectVariableValueException("Negative value for contamination limit: " + contLimit);
		else if (srcJunc == null)
			throw new IncorrectVariableValueException("Junction source is null");
		else if (destJunc == null)
			throw new IncorrectVariableValueException("Junction destination is null");
		else if (weather == null)
			throw new IncorrectVariableValueException("Weather is null");

		this.srcJunc = srcJunc;
		this.destJunc = destJunc;
		this.maxSpeed = maxSpeed;
		this.contLimit = contLimit;
		this.length = length;
		this.weather = weather;
		_cmp = new SerializableComparator();
//		this._cmp = new Comparator<Vehicle>() {
//			public int compare(Vehicle v1, Vehicle v2) {
//				if (v1.getLocation() == v2.getLocation())
//					return 0;
//				else if (v1.getLocation() < v2.getLocation())
//					return 1;
//				else
//					return -1;
//			}
//		};
		vehicles = new SortedArrayList<Vehicle>(_cmp);

	}

	void enter(Vehicle v) throws IncorrectVariableValueException {
		vehicles.add(v);
		if (v.getSpeed() != 0 || v.getLocation() != 0)
			throw new IncorrectVariableValueException("Vehicle speed and location must be equal to 0:(Speed: "
					+ v.getSpeed() + " Location: " + v.getLocation() + ")");
	}

	void exit(Vehicle v) {
		vehicles.remove(v);
	}

	abstract void reduceTotalContamination() throws IncorrectVariableValueException;

	abstract void updateSpeedLimit();

	abstract int calculateVehicleSpeed(Vehicle v);

	public void advance(int time) {
		// 1)
		try {
			reduceTotalContamination();
		} catch (IncorrectVariableValueException e) {
			e.printStackTrace();
		}

		// 2)
		updateSpeedLimit();

		// 3)
		for (Vehicle n : vehicles) {
			try {
				if (n.getStatus() != VehicleStatus.WAITING)
					n.setSpeed(calculateVehicleSpeed(n));
				n.advance(time);
			} catch (IncorrectVariableValueException e) {
				e.printStackTrace();
			}
		}

		// 4)
		vehicles.sort(_cmp);
	}

	public void addContamination(int c) throws IncorrectVariableValueException {
		if (c < 0)
			throw new IncorrectVariableValueException("Negative value for contamition: " + c);
		contTotal += c;
	}

	public int getLength() {
		return length;
	}

	public List<Vehicle> getVehicleList() {
		return Collections.unmodifiableList(vehicles);
	}

	public Junction getDestination() {
		return destJunc;
	}

	public Junction getSource() {
		return srcJunc;
	}

	public void setWeather(Weather w) throws IncorrectVariableValueException {
		if (w == null)
			throw new IncorrectVariableValueException("Weather is null");
		weather = w;
	}

	public int getContLimit() {
		return contLimit;
	}

	public void setContLimit(int contLimit) {
		this.contLimit = contLimit;
	}

	public int getContTotal() {
		return contTotal;
	}

	public void setContTotal(int contTotal) {
		this.contTotal = contTotal;
	}

	public int getMaxSpeed() {
		return maxSpeed;
	}

	public void setMaxSpeed(int maxSpeed) {
		this.maxSpeed = maxSpeed;
	}

	public int getSpeedLimit() {
		return speedLimit;
	}

	public void setSpeedLimit(int speedLimit) {
		this.speedLimit = speedLimit;
	}

	public Junction getSrcJunc() {
		return srcJunc;
	}

	public void setSrcJunc(Junction srcJunc) {
		this.srcJunc = srcJunc;
	}

	public Junction getDestJunc() {
		return destJunc;
	}

	public void setDestJunc(Junction destJunc) {
		this.destJunc = destJunc;
	}

	public List<Vehicle> getVehicles() {
		return vehicles;
	}

	public void setVehicles(List<Vehicle> vehicles) {
		this.vehicles = vehicles;
	}

	public Weather getWeather() {
		return weather;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public JSONObject report() {
		JSONObject j = new JSONObject();
		j.put("id", _id);
		j.put("speedlimit", speedLimit);
		j.put("weather", weather);
		j.put("co2", contTotal);
		JSONArray ja = new JSONArray();
		for (Vehicle i : vehicles) {
			ja.put(i.getId());
		}
		j.put("vehicles", ja);
		return j;
	}

	public boolean getGreenLight() {
		return destJunc.getInRoads().indexOf(this) == destJunc.getGreenLightIndex();
		
	}

}

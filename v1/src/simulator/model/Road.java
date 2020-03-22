package simulator.model;

import org.json.JSONArray;
import org.json.JSONObject;

import exceptions.IncorrectVariableValue;
import exceptions.ValueParseException;
import simulator.misc.SortedArrayList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public abstract class Road extends SimulatedObject {
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

	Road(String id, Junction srcJunc, Junction destJunc, int maxSpeed, int contLimit, int length, Weather weather)
			throws ValueParseException {

		super(id);

		if (maxSpeed <= 0) // positive <=0, non-negative < 0
			throw new ValueParseException("Negative value for maxSpeed");
		else if (length <= 0)
			throw new ValueParseException("Negative value for length");
		else if (contLimit < 0)
			throw new ValueParseException("Negative value for contamition limit");
		else if (srcJunc == null)
			throw new ValueParseException("Junction source is null");
		else if (destJunc == null)
			throw new ValueParseException("Junction destination is null");
		else if (weather == null)
			throw new ValueParseException("Weather is null");
		this.srcJunc = srcJunc;
		this.destJunc = destJunc;
		this.maxSpeed = maxSpeed;
		this.contLimit = contLimit;
		this.length = length;
		this.weather = weather;
		this._cmp = new Comparator<Vehicle>() {
			public int compare(Vehicle v1, Vehicle v2) {
				if (v1.getLocation() == v2.getLocation())
					return 0;
				else if (v1.getLocation() < v2.getLocation())
					return 1;
				else
					return -1;
			}
		};
		vehicles = new SortedArrayList<Vehicle>(_cmp);

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

	public void setWeather(Weather w) throws ValueParseException {
		if (w == null)
			throw new ValueParseException("Weather is null");
		weather = w;
	}

	public void addContamination(int c) throws ValueParseException {
		if (c < 0)
			throw new ValueParseException("Negative value for contamition");
		contTotal += c;
	}

	void enter(Vehicle v) {

		vehicles.add(v);
		if (v.getSpeed() != 0 || v.getLocation() != 0)
			throw new IllegalArgumentException("Vehicle speed and location must be equal to 0:(Speed: " + v.getSpeed()
					+ " Location: " + v.getLocation() + ")");
	}

	void exit(Vehicle v) {
		vehicles.remove(v);
	}

	public void advance(int time) {

		// 1)
		try {
			reduceTotalContamination();
		} catch (IncorrectVariableValue e) {
			e.printStackTrace();
		}

		// 2)
		updateSpeedLimit();

		// 3)
		Iterator<Vehicle> it = vehicles.iterator();
		while (it.hasNext()) {
			Vehicle n = it.next();
			try {
				if (n.getStatus() != VehicleStatus.WAITING)
					n.setSpeed(calculateVehicleSpeed(n));
				n.advance(time);
			} catch (ValueParseException e) {
				e.printStackTrace();
			}
		}
		// 4)
		vehicles.sort(_cmp);
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

	abstract void reduceTotalContamination() throws IncorrectVariableValue;

	abstract void updateSpeedLimit();

	abstract int calculateVehicleSpeed(Vehicle v);

}

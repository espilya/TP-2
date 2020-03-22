package simulator.model;

import java.util.Collections;
import java.util.List;

import org.json.JSONObject;

import exceptions.ValueParseException;

public class Vehicle extends SimulatedObject {

	private List<Junction> iter;
	private int maxSpeed;
	private int actualSpeed;
	private VehicleStatus state;
	private Road actualRoad;
	private int localization;
	private int contClass; // 0-10
	private int totalCont;
	private int totalDistance;
	private int lastJunction;

	Vehicle(String id, int maxSpeed, int contClass, List<Junction> itinerary) throws ValueParseException {
		super(id);
		this.maxSpeed = maxSpeed;
		this.contClass = contClass;
		state = VehicleStatus.PENDING;
		iter = Collections.unmodifiableList(itinerary);
		actualRoad = null;
		localization = 0;
		totalCont = 0;
		lastJunction = 0;
		if (maxSpeed < 0)
			throw new ValueParseException("Negative value for maxSpeed");
		if (contClass < 0 || contClass > 10)
			throw new ValueParseException("Incorrect value for contamination class");
		if (itinerary.size() < 2)
			throw new ValueParseException("Incorrect value for ");
		if (iter.size() < 2) {
			throw new IllegalArgumentException(
					"List<Junction> itinerary size must be bigger less than 2(" + iter.size() + ")");
		}

	}

	public int getLocation() {
		return localization;
	}

	public int getSpeed() {
		return actualSpeed;
	}

	public int getContClass() {
		return contClass;
	}

	public VehicleStatus getStatus() {
		return state;
	}

	public List<Junction> getItinerary() {
//		return iter; this or this
		return Collections.unmodifiableList(iter);
	}

	public Road getRoad() {
		return actualRoad;
	}

	public void setSpeed(int s) throws ValueParseException {
		if (maxSpeed > s)
			if (s < 0)
				throw new IllegalArgumentException("Negative value for speed");
			else
				actualSpeed = s;
		else
			actualSpeed = maxSpeed;
	}

	public void setContaminationClass(int c) throws ValueParseException {
		if (c < 0 || c > 10)
			throw new ValueParseException("Incorrect value for contamination class");
		contClass = c;
	}

	public void advance(int time) {
		if (state.equals(VehicleStatus.TRAVELING)) {
			// 1)
			int a = localization + actualSpeed;
			int b = actualRoad.getLength();
			int oldLoc = localization;
			if (a > b)
				localization = b;
			else
				localization = a;

			totalDistance = localization;

			// 2)
			int c = contClass * (localization - oldLoc);
			totalCont += c;
			try {
				actualRoad.addContamination(c);
			} catch (ValueParseException e) {
				e.printStackTrace();
			}

			// 3)
			if (localization >= actualRoad.getLength()) {
				state = VehicleStatus.WAITING;
				actualRoad.getDestination().enter(this); // not sure
				actualSpeed = 0;
				lastJunction++;
			}

		} else {
			actualSpeed = 0;
		}

	}

	public void moveToNextRoad() throws Exception {
//		for(Junction j : iter) {
//			System.out.println(j);
//		}
		if (lastJunction == iter.size() - 1) {
			state = VehicleStatus.ARRIVED;
			actualSpeed = 0;
			localization = 0;
			actualRoad.exit(this);

		} else {
			Road nextRoad = null;

			if (!(state.equals(VehicleStatus.PENDING) || state.equals(VehicleStatus.WAITING)))
				throw new Exception("Incorrect Vehicle state");

			if (state.equals(VehicleStatus.PENDING)) {
				nextRoad = iter.get(0).getOutRoad(this);
			} else {
				actualRoad.exit(this);
				nextRoad = actualRoad.getDestination().getOutRoad(this);
			}
			actualRoad = nextRoad;
			localization = 0;
			actualSpeed = 0;
			nextRoad.enter(this);
			state = VehicleStatus.TRAVELING;
		}
	}

	public JSONObject report() {
		JSONObject j = new JSONObject();
		j.put("id", _id);
		j.put("speed", actualSpeed);
		j.put("distance", totalDistance);
		j.put("co2", totalCont);
		j.put("class", contClass);
		j.put("status", state);
		if (!(state.equals(VehicleStatus.PENDING) || state.equals(VehicleStatus.ARRIVED))) {
			j.put("road", actualRoad.getId());
			j.put("location", localization);
		}
		return j;
	}

}

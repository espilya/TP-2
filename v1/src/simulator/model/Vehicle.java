package simulator.model;

import java.util.Collections;
import java.util.List;

import org.json.JSONObject;

import simulator.exceptions.IncorrectVariableValueException;

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
	private int pastRoadsLengt;

	Vehicle(String id, int maxSpeed, int contClass, List<Junction> itinerary) throws IncorrectVariableValueException {
		super(id);

		if (maxSpeed < 0)
			throw new IncorrectVariableValueException("Negative value for maxSpeed: " + maxSpeed);
		if (contClass < 0 || contClass > 10)
			throw new IncorrectVariableValueException("Incorrect value for contamination class: " + contClass);
		if (itinerary.size() < 2)
			throw new IncorrectVariableValueException("Incorrect size of itinerary: " + itinerary.size());

		this.maxSpeed = maxSpeed;
		this.contClass = contClass;
		state = VehicleStatus.PENDING;
		iter = Collections.unmodifiableList(itinerary);
		actualRoad = null;
		localization = 0;
		totalCont = 0;
		lastJunction = 0;

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
		return Collections.unmodifiableList(iter);
	}

	public Road getRoad() {
		return actualRoad;
	}

	public void setSpeed(int s) throws IncorrectVariableValueException {
		if (maxSpeed > s)
			if (s < 0)
				throw new IncorrectVariableValueException("Negative value for maxSpeed: " + maxSpeed);
			else
				actualSpeed = s;
		else
			actualSpeed = maxSpeed;
	}

	public void setContaminationClass(int c) throws IncorrectVariableValueException {
		if (c < 0 || c > 10)
			throw new IncorrectVariableValueException("Incorrect value for contamination class: " + c);
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
			totalDistance += actualSpeed;

			// 2)
			int c = contClass * (localization - oldLoc);
			totalCont += c;
			try {
				actualRoad.addContamination(c);
			} catch (IncorrectVariableValueException e) {
				e.printStackTrace();
			}

			// 3)
			if (localization >= actualRoad.getLength()) {
				state = VehicleStatus.WAITING;
				actualRoad.getDestination().enter(this); // not sure
				actualSpeed = 0;
				lastJunction++;
				pastRoadsLengt += actualRoad.getLength();
				totalDistance = pastRoadsLengt;

			}

		} else {
			actualSpeed = 0;
		}

	}

	public void moveToNextRoad() throws IncorrectVariableValueException {
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
				throw new IncorrectVariableValueException("Incorrect vehicle state: " + state.toString());

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

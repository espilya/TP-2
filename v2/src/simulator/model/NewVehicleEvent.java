package simulator.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import simulator.exceptions.ExistingObjectException;
import simulator.exceptions.IncorrectVariableValueException;
import simulator.exceptions.NonExistingObjectException;

public class NewVehicleEvent extends Event implements Serializable{
	private static final long serialVersionUID = -5556154471641695727L;

	private String id;
	private int maxSpeed;
	private int contClass;
	private List<String> itinerary;

	public NewVehicleEvent(int time, String id, int maxSpeed, int contClass, List<String> itinerary) {
		super(time);
		this.id = id;
		this.maxSpeed = maxSpeed;
		this.contClass = contClass;
		this.itinerary = itinerary;
	}

	@Override
	void execute(RoadMap map)
			throws IncorrectVariableValueException, ExistingObjectException, NonExistingObjectException {
		List<Junction> listIter = new ArrayList<Junction>();
		Vehicle newVehicle = null;
		for (String j : itinerary) {
			listIter.add(map.getJunction(j));
		}
		newVehicle = new Vehicle(id, maxSpeed, contClass, listIter);
		newVehicle.moveToNextRoad();
		map.addVehicle(newVehicle);
	}

	@Override
	public String toString() {
		return ("New Vehicle: '" + id + "'");
	}

}

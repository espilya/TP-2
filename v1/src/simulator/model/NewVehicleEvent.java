package simulator.model;

import exceptions.ValueParseException;

import java.util.ArrayList;
import java.util.List;

public class NewVehicleEvent extends Event {
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
	void execute(RoadMap map) {
		List<Junction> listIter = new ArrayList<Junction>();
		Vehicle newVehicle = null;
		for (String j : itinerary) {
			listIter.add(map.getJunction(j));
		}
		try {
			newVehicle = new Vehicle(id, maxSpeed, contClass, listIter);
			newVehicle.moveToNextRoad();
			map.addVehicle(newVehicle);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}

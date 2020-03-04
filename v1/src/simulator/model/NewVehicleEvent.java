package simulator.model;

import exceptions.ValueParseException;

import java.util.List;

public class NewVehicleEvent extends Event{
	private String id;
	private int maxSpeed;
	private int contClass;
	private List<String> itinerary;
	public NewVehicleEvent(int time, String id, int maxSpeed, int
			contClass, List<String> itinerary) {
			super(time);
			this.id =id;
			this.maxSpeed = maxSpeed;
			this.contClass =contClass;
			this.itinerary = itinerary;
			}

	@Override
	void execute(RoadMap map) throws ValueParseException {
		Vehicle newVehicle = new Vehicle( id,  maxSpeed,  contClass,  itinerary);
		//required String in PDF but in class vehicle Junction is required;
		
	}

}

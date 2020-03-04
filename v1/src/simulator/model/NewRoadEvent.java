package simulator.model;

public abstract class NewRoadEvent extends Event{

	NewRoadEvent(int time, String id, String srcJun, String
			destJunc, int length, int co2Limit, int maxSpeed, Weather weather) {
		super(time);
		// TODO Auto-generated constructor stub
	}
	
	
	//not sure about public/protected/private
	abstract public Road createRoadObject();

}

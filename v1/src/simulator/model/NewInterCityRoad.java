package simulator.model;

public class NewInterCityRoad extends NewRoadEvent {
	
	public NewInterCityRoad(int time, String id, String srcJun, String destJunc, int length, int co2Limit, int maxSpeed,
			Weather weather) {
		super(time, destJunc, destJunc, destJunc, maxSpeed, maxSpeed, maxSpeed, weather);
		// ...
	}

	@Override
	void execute(RoadMap map) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Road createRoadObject() {
		// TODO Auto-generated method stub
		return null;
	}
}

package simulator.model;

public class NewInterCityRoad extends Event {
	
	public NewInterCityRoad(int time, String id, String srcJun, String destJunc, int length, int co2Limit, int maxSpeed,
			Weather weather) {
		super(time);
		// ...
	}

	@Override
	void execute(RoadMap map) {
		// TODO Auto-generated method stub
		
	}
}

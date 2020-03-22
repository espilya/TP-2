package simulator.model;

import exceptions.IncorrectRoadException;

public abstract class NewRoadEvent extends Event {
	protected String id;
	protected String srcJun;
	protected String destJunc;
	protected int length;
	protected int co2Limit;
	protected int maxSpeed;
	protected Weather weather;
	protected RoadMap rMap;

	NewRoadEvent(int time, String id, String srcJun, String destJunc, int length, int co2Limit, int maxSpeed,
			Weather weather) {
		super(time);
		this.id = id;
		this.srcJun = srcJun;
		this.destJunc = destJunc;
		this.length = length;
		this.co2Limit = co2Limit;
		this.maxSpeed = maxSpeed;
		this.weather = weather;

	}
	
	void execute(RoadMap map) {
			rMap = map;
			Junction src = map.getJunction(srcJun);
			Junction dest = map.getJunction(destJunc);
			
			Road road = createRoadObject();
			
			try {
				map.addRoad(road);
//				map.getJunction(src.getId()).addIncommingRoad(road);
//				map.getJunction(dest.getId()).addOutGoingRoad(road);
				
				map.getJunction(src.getId()).addOutGoingRoad(road);
				map.getJunction(dest.getId()).addIncommingRoad(road);
			} catch (Exception e) {
				e.printStackTrace();
			}

	}

	abstract protected Road createRoadObject();

}

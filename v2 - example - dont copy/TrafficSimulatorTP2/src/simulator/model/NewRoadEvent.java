package simulator.model;

import java.io.Serializable;

public abstract class NewRoadEvent extends Event implements Serializable{
	protected String id;
	protected String srcJunc, destJunc;
	protected int length, co2Limit, maxSpeed;
	protected Weather weather;
	
	NewRoadEvent(int time, String id, String srcJunc, String destJunc, int length, int co2Limit, int maxSpeed,
				Weather weather) {
			super(time);
			this.id = id;
			this.srcJunc = srcJunc;
			this.destJunc = destJunc;
			this.length = length;
			this.co2Limit = co2Limit;
			this.maxSpeed = maxSpeed;
			this.weather = weather;

		}
	void execute(RoadMap map) {
		try {
			Junction begining = map.getJunction(this.srcJunc);
			Junction destination = map.getJunction(this.destJunc);
			
			Road road = createRoadObject(map);
			map.addRoad(road);
			map.getJunction(destination.getId()).addIncommingRoad(road);
			map.getJunction(begining.getId()).addOutGoingRoad(road);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	abstract Road createRoadObject(RoadMap map) throws Exception;
	
	public String toString() {
		return "New Road '"+ this.id+"'";
	}

}

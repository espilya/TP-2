package simulator.model;

import java.io.Serializable;

public class NewInterCityRoad extends NewRoadEvent implements Serializable{
	public NewInterCityRoad(int time, String id, String srcJunc, String
			destJunc, int length, int co2Limit, int maxSpeed, Weather weather)
			{
		super(time, id, srcJunc, destJunc, length, co2Limit, maxSpeed, weather);
		}

	@Override
	Road createRoadObject(RoadMap map) throws Exception {
		Junction begining = map.getJunction(this.srcJunc);
		Junction destination = map.getJunction(this.destJunc);
		InterCityRoad interCRoad = new InterCityRoad(this.id, begining, destination, maxSpeed, co2Limit, length, weather);
		return interCRoad;
	}
	/*void execute(RoadMap map) {
		try {
			Junction begining = map.getJunction(this.srcJunc);
			Junction destination = map.getJunction(this.destJunc);
			
			InterCityRoad interCRoad = new InterCityRoad(this.id, begining, destination, maxSpeed, co2Limit, length, weather);
			map.addRoad(interCRoad);
			map.getJunction(destination.getId()).addIncommingRoad(interCRoad);
			map.getJunction(begining.getId()).addOutGoingRoad(interCRoad);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
	}*/
}

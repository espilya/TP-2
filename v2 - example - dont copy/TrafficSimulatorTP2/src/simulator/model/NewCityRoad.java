package simulator.model;

import java.io.Serializable;

public class NewCityRoad extends NewRoadEvent implements Serializable{
	
	public NewCityRoad(int time, String id, String srcJunc, String
			destJunc, int length, int co2Limit, int maxSpeed, Weather weather)
			{
		super(time, id, srcJunc, destJunc, length, co2Limit, maxSpeed, weather);
		}

	@Override
	Road createRoadObject(RoadMap map) throws Exception {
		Junction begining = map.getJunction(this.srcJunc);
		Junction destination = map.getJunction(this.destJunc);
		CityRoad cRoad = new CityRoad(this.id, begining, destination, maxSpeed, co2Limit, length, weather);
		return cRoad;		
	}

	/*void execute(RoadMap map) {
		try {
			Junction begining = map.getJunction(this.srcJunc);
			Junction destination = map.getJunction(this.destJunc);
			
			CityRoad cRoad = new CityRoad(this.id, begining, destination, maxSpeed, co2Limit, length, weather);
			map.addRoad(cRoad);
			map.getJunction(destination.getId()).addIncommingRoad(cRoad);
			map.getJunction(begining.getId()).addOutGoingRoad(cRoad);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/

}

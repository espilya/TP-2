package simulator.factories;

import org.json.JSONObject;

import simulator.model.Event;
import simulator.model.Weather;

public abstract class NewRoadEventBuilder extends Builder<Event>{

	NewRoadEventBuilder(String type) {
		super(type);

	}

	@Override
	protected Event createTheInstance(JSONObject data) {
		int time = data.getInt("time");
		String id = data.getString("id");
		String src = data.getString("src");
		String dst = data.getString("dest");
		int length = data.getInt("length");
		int co2limit = data.getInt("co2limit");
		//
		int maxspeed = data.getInt("maxspeed");
		Weather weather = (Weather) data.get("weather");
		
		
		
//		createTheRoad(time, id, src, dst, length, co2limit, maxspeed, weather);
		
		return null;
	}
	
	
	//protected??
	 abstract Event createTheRoad();

}

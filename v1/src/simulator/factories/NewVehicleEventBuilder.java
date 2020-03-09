package simulator.factories;

import org.json.JSONObject;

import simulator.model.Event;
import simulator.model.Weather;

public class NewVehicleEventBuilder extends Builder{

	NewVehicleEventBuilder(String type) {
		super(type);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected Event createTheInstance(JSONObject data) {
		int time = data.getInt("time");
		String id = data.getString("id");
		int co2class = data.getInt("class");
		int maxspeed = data.getInt("maxspeed");
		
		
		
		return null;
	}

}

package simulator.factories;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.model.Event;
import simulator.model.NewVehicleEvent;

public class NewVehicleEventBuilder extends Builder<Event> {

	private final static String type = "new_vehicle";

	public NewVehicleEventBuilder() {
		super(type);
	}

	@Override
	protected Event createTheInstance(JSONObject data) {
		int time = data.getInt("time");
		String id = data.getString("id");
		int co2class = data.getInt("class");
		int maxspeed = data.getInt("maxspeed");

		List<String> itinerary = new ArrayList<String>();

		JSONArray jsonArray = (JSONArray) data.get("itinerary");

		Iterator<Object> iterator = jsonArray.iterator();
		while (iterator.hasNext()) {
			itinerary.add((String) iterator.next());
		}

		return new NewVehicleEvent(time, id, maxspeed, co2class, itinerary);
	}

}

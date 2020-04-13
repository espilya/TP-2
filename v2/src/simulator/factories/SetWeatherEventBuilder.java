package simulator.factories;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.misc.Pair;
import simulator.model.Event;
import simulator.model.SetWeatherEvent;
import simulator.model.Weather;

public class SetWeatherEventBuilder extends Builder<Event> {

	private final static String type = "set_weather";

	public SetWeatherEventBuilder() {
		super(type);
	}

	protected Event createTheInstance(JSONObject data) {
		List<Pair<String, Weather>> wList = new ArrayList<Pair<String, Weather>>();
		int time = data.getInt("time");

		JSONArray jsonArray = data.getJSONArray("info");
		Iterator<Object> iterator = jsonArray.iterator();
		while (iterator.hasNext()) {
			JSONObject jo = (JSONObject) iterator.next();
			Pair<String, Weather> p = new Pair<String, Weather>(jo.getString("road"),
					Weather.valueOf(jo.getString("weather")));
			wList.add(p);
		}

		return new SetWeatherEvent(time, wList);
	}

}

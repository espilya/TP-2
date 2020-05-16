package simulator.factories;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.misc.Pair;
import simulator.model.Event;
import simulator.model.SetWeatherEvent;
import simulator.model.Weather;

public class SetWeatherEventBuilder extends Builder<Event>{

	final static String type = "set_weather";
	public SetWeatherEventBuilder() {
		super(type);
	}

	@Override
	protected Event createTheInstance(JSONObject data){
		int time = data.getInt("time");
		List<Pair<String,Weather>> ws = new ArrayList<Pair<String,Weather>>();
		JSONArray wsArray = data.getJSONArray("info");
		for (int i = 0; i < wsArray.length(); i++) {
			String road = wsArray.getJSONObject(i).getString("road");
			String s = wsArray.getJSONObject(i).getString("weather");
			Weather weather = Weather.valueOf(s.toUpperCase());
			Pair<String, Weather> p = new Pair<String,Weather>(road, weather);
			ws.add(p);
		}
		
		return new SetWeatherEvent(time, ws);
	}

}

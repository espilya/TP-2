package simulator.factories;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.misc.Pair;
import simulator.model.Event;
import simulator.model.NewSetContClassEvent;

public class SetContClassEventBuilder extends Builder<Event> {

	private final static String type = "set_cont_class";

	public SetContClassEventBuilder() {
		super(type);
	}

	protected Event createTheInstance(JSONObject data) {
		List<Pair<String, Integer>> cList = new ArrayList<Pair<String, Integer>>();
		int time = data.getInt("time");

		JSONArray jsonArray = data.getJSONArray("info");
		Iterator<Object> iterator = jsonArray.iterator();
		while (iterator.hasNext()) {
			JSONObject jo = (JSONObject) iterator.next();
			Pair<String, Integer> p = new Pair<String, Integer>(jo.getString("vehicle"), jo.getInt("class"));
			cList.add(p);
		}

		return new NewSetContClassEvent(time, cList);
	}

}

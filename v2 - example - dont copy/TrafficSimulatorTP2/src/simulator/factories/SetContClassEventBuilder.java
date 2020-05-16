package simulator.factories;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import simulator.misc.Pair;
import simulator.model.Event;
import simulator.model.NewSetContClassEvent;

public class SetContClassEventBuilder extends Builder<Event>{

	final static String type = "set_cont_class";
	public SetContClassEventBuilder( ) {
		super(type);
	}

	@Override
	protected Event createTheInstance(JSONObject data) {
		int time = data.getInt("time");
		List<Pair<String,Integer>> cs = new ArrayList<Pair<String,Integer>>();
		JSONArray csArray = data.getJSONArray("info");
		for (int i = 0; i < csArray.length(); i++) {
			String vehicle = csArray.getJSONObject(i).getString("vehicle");
			Integer contClass = csArray.getJSONObject(i).getInt("class");
			Pair<String, Integer> p = new Pair<String,Integer>(vehicle, contClass);
			cs.add(p);
		}
		return new NewSetContClassEvent(time, cs);
	}

}

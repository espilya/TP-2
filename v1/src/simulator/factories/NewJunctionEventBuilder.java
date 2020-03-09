package simulator.factories;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.model.DequeuingStrategy;
import simulator.model.Event;
import simulator.model.LightSwitchingStrategy;
import simulator.model.NewJunctionEvent;

public class NewJunctionEventBuilder extends Builder<Event> {

	final static String type = "new_junction";
	protected Factory<LightSwitchingStrategy> lssFactory;
	protected Factory<DequeuingStrategy> dqsFactory;

	public NewJunctionEventBuilder(Factory<LightSwitchingStrategy> lssFactory, Factory<DequeuingStrategy> dqsFactory) {
		super(type);
		this.dqsFactory = dqsFactory;
		this.lssFactory = lssFactory;
	}

	@Override
	protected Event createTheInstance(JSONObject data) {

		JSONArray coor = data.getJSONArray("coor");

		LightSwitchingStrategy lss = this.lssFactory.createInstance(data.getJSONObject("ls_strategy"));
		DequeuingStrategy dqs = this.dqsFactory.createInstance(data.getJSONObject("dq_strategy"));

		return new NewJunctionEvent(data.getInt("time"), data.getString("id"), null, null, coor.getInt(0),
				coor.getInt(1));
	}

}

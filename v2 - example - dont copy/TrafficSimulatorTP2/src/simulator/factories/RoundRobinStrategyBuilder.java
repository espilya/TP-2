package simulator.factories;

import org.json.JSONObject;
import simulator.model.LightSwitchingStrategy;
import simulator.model.RoundRobinStrategy;

public class RoundRobinStrategyBuilder extends Builder<LightSwitchingStrategy> {

	final static String type =  "round_robin_lss";
	public RoundRobinStrategyBuilder() {
		super(type);
	}

	@Override
	protected LightSwitchingStrategy createTheInstance(JSONObject data) {
		int time;
		if(data.isEmpty()) {
			time = 1;
		}
		else time = data.getInt("timeslot");
		return new RoundRobinStrategy(time);
	}

}

package simulator.factories;

import org.json.JSONObject;

import simulator.model.LightSwitchingStrategy;
import simulator.model.RoundRobinStrategy;

public class RoundRobinStrategyBuilder extends Builder<LightSwitchingStrategy>{

	final static String idType =  "round_robin_lss";
	
	public RoundRobinStrategyBuilder() {
		super(idType);
	}

	@Override
	protected LightSwitchingStrategy createTheInstance(JSONObject data) {
		final int defaultValue = 1;
		
		return new RoundRobinStrategy(data.has("timeslot") ? data.getInt("timeslot") : defaultValue);
	}

}

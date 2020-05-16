package simulator.factories;

import org.json.JSONObject;

import simulator.model.LightSwitchingStrategy;
import simulator.model.MostCrowdedStrategy;

public class MostCrowdedStrategyBuilder extends Builder<LightSwitchingStrategy> {
	
	final static String type = "most_crowned_lss";
	public MostCrowdedStrategyBuilder() {
		super(type);
	}

	@Override
	protected LightSwitchingStrategy createTheInstance(JSONObject data) {
		int time;
		if(data.isEmpty()) {
			time = 1;
		}
		else time = data.getInt("timeslot");
		return new MostCrowdedStrategy(time);
	}
	
}

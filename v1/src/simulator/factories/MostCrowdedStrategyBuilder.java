package simulator.factories;

import org.json.JSONObject;

import simulator.model.LightSwitchingStrategy;
import simulator.model.MostCrowdedStrategy;

public class MostCrowdedStrategyBuilder extends Builder<LightSwitchingStrategy> {

	MostCrowdedStrategyBuilder(String type) {
		super(type);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected LightSwitchingStrategy createTheInstance(JSONObject data) {
		final int defaultValue = 1;

		return new MostCrowdedStrategy(data.has("timeslot") ? data.getInt("timeslot") : defaultValue);
	}

}

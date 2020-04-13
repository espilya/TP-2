package simulator.factories;

import org.json.JSONObject;

import simulator.model.LightSwitchingStrategy;
import simulator.model.MostCrowdedStrategy;

public class MostCrowdedStrategyBuilder extends Builder<LightSwitchingStrategy> {

	private final static String type = "most_crowned_lss";

	public MostCrowdedStrategyBuilder() {
		super(type);
	}

	protected LightSwitchingStrategy createTheInstance(JSONObject data) {
		final int defaultValue = 1;
		return new MostCrowdedStrategy(data.has("timeslot") ? data.getInt("timeslot") : defaultValue);
	}

}

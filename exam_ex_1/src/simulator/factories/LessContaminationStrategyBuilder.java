package simulator.factories;

import org.json.JSONObject;

import simulator.model.DequeuingStrategy;
import simulator.model.LessContaminationStrategy;

public class LessContaminationStrategyBuilder extends Builder<DequeuingStrategy> {
	
	private final static String type = "less_cont_dqs";

	public LessContaminationStrategyBuilder() {
		super(type);
	}

	protected DequeuingStrategy createTheInstance(JSONObject data) {
		final int defaultValue = 1;
		return new LessContaminationStrategy(data.has("limit") ? data.getInt("limit") : defaultValue);
	}


}

package simulator.factories;

import org.json.JSONObject;

import simulator.model.DequeuingStrategy;
import simulator.model.MoveFirstStrategy;

public class MoveFirstStrategyBuilder extends Builder<DequeuingStrategy> {

	private final static String type = "move_first_dqs";

	public MoveFirstStrategyBuilder() {
		super(type);
	}

	@Override
	protected DequeuingStrategy createTheInstance(JSONObject data) {
		return new MoveFirstStrategy();
	}

}

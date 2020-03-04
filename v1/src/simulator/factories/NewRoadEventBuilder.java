package simulator.factories;

import org.json.JSONObject;

import simulator.model.Event;

public abstract class NewRoadEventBuilder extends Builder{

	NewRoadEventBuilder(String type) {
		super(type);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected Event createTheInstance(JSONObject data) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	//protected??
	abstract Event createTheRoad();

}

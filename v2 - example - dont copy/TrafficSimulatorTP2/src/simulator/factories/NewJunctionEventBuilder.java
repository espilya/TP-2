package simulator.factories;


import org.json.JSONArray;
import org.json.JSONObject;
import simulator.model.DequeuingStrategy;
import simulator.model.Event;
import simulator.model.LightSwitchingStrategy;
import simulator.model.NewJunctionEvent;


public class NewJunctionEventBuilder extends Builder<Event>{
	
	final static String type = "new_junction";
	protected Factory<LightSwitchingStrategy> lssFactory;
	protected Factory<DequeuingStrategy> dqsFactory;
	
	public NewJunctionEventBuilder(Factory<LightSwitchingStrategy> lssFactory, 
			Factory<DequeuingStrategy> dqsFactory) {
		super(type);
		this.dqsFactory = dqsFactory;
		this.lssFactory = lssFactory;
	}
	
	@Override
	protected Event createTheInstance(JSONObject data) {
		int time =  data.getInt("time");
		String id = data.getString("id");
		JSONArray ja = data.getJSONArray("coor");
		int x = ja.getInt(0);
		int y = ja.getInt(1);
		LightSwitchingStrategy lss = this.lssFactory.createInstance(data.getJSONObject("ls_strategy"));
		DequeuingStrategy dqs = this.dqsFactory.createInstance(data.getJSONObject("dq_strategy"));
		
		return new NewJunctionEvent(time, id, lss, dqs, x, y);
	}

}
package simulator.control;

import simulator.factories.Factory;
import simulator.model.Event;
import simulator.model.TrafficSimulator;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import exceptions.ValueParseException;

public class Controller {

	private TrafficSimulator trafficSim;
	private Factory<Event> evtsFactory;

	public Controller(TrafficSimulator sim, Factory<Event> eventsFactory) throws ValueParseException  {
		trafficSim = sim;
		evtsFactory = eventsFactory;
		if (sim == null)
			throw new ValueParseException("Null value for TrafficSimulator in controller");
		if (eventsFactory == null)
			throw new ValueParseException("Null value for eventsFactory in controller");
	}
 
	public void loadEvents(InputStream in)  {
		JSONObject jo = new JSONObject(new JSONTokener(in));
		JSONArray jArr = jo.getJSONArray("events");
		for(int i = 0;  i < jArr.length(); i++) {
			JSONObject jo2 = jArr.getJSONObject(i);
			Event e = evtsFactory.createInstance(jo2);	
			trafficSim.addEvent(e);
		}
	}

	public void run(int n, OutputStream out) {
		JSONArray jArr = new JSONArray();
		JSONObject j = new JSONObject();
		for(int i = 0; i<n; i++) {
			trafficSim.advance();
			jArr.put(trafficSim.report());
		}
		j.put("states", jArr);
		
		PrintStream p = new PrintStream(out);
		p.println(j.toString(3)); // 3
		
	}

	public void reset() {
		trafficSim.reset();
	}
}

package simulator.control;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import simulator.exceptions.NonExistingObjectException;
import simulator.factories.Factory;
import simulator.model.Event;
import simulator.model.TrafficSimObserver;
import simulator.model.TrafficSimulator;

public class Controller {

	private TrafficSimulator trafficSim;
	private Factory<Event> evtsFactory;



	public Controller(TrafficSimulator sim, Factory<Event> eventsFactory) throws NonExistingObjectException {
		trafficSim = sim;
		evtsFactory = eventsFactory;
		if (sim == null)
			throw new NonExistingObjectException("trafficSimulator does not exist");
		if (eventsFactory == null)
			throw new NonExistingObjectException("eventsFactory does not exist");
	}

	public void loadEvents(InputStream in) {
		JSONObject jo = new JSONObject(new JSONTokener(in));
		JSONArray jArr = jo.getJSONArray("events");
		for (int i = 0; i < jArr.length(); i++) {
			JSONObject jo2 = jArr.getJSONObject(i);
			Event e = evtsFactory.createInstance(jo2);
			trafficSim.addEvent(e);
		}
	}


	public void run(int n) throws Exception {
		for (int i = 0; i < n; i++) {
			// advance
			trafficSim.advance();
		}
	}

	public void run(int n, OutputStream out) throws Exception {
		JSONArray jArr = new JSONArray();
		JSONObject j = new JSONObject();
		for (int i = 0; i < n; i++) {
			trafficSim.advance();
			jArr.put(trafficSim.report());
		}
		j.put("states", jArr);
		PrintStream p = new PrintStream(out);
		p.println(j.toString(3));
	}

	public void addObserver(TrafficSimObserver o) {
		trafficSim.addObserver(o);
	}

	public void removeObserver(TrafficSimObserver o) {
		trafficSim.removeObserver(o);
	}


	public void reset() {
		// reset simulation
		trafficSim.reset();
	}

	
	public void addEvent(Event e) {
		trafficSim.addEvent(e);
	}


}

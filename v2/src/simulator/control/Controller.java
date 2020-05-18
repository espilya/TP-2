package simulator.control;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import simulator.exceptions.NonExistingObjectException;
import simulator.factories.Factory;
import simulator.memento.History;
import simulator.memento.Memento;
import simulator.model.Event;
import simulator.model.TrafficSimObserver;
import simulator.model.TrafficSimulator;
import simulator.model.Weather;

public class Controller {

	private TrafficSimulator trafficSim;
	private Factory<Event> evtsFactory;

	// used for undo
	private History history;

	// used for load previous state
	private JSONArray jArrEventsFromFirstFile;

	public Controller(TrafficSimulator sim, Factory<Event> eventsFactory) throws NonExistingObjectException {
		history = new History();
		trafficSim = sim;
		evtsFactory = eventsFactory;
		if (sim == null)
			throw new NonExistingObjectException("trafficSimulator does not exist");
		if (eventsFactory == null)
			throw new NonExistingObjectException("eventsFactory does not exist");
	}

	public void loadEvents(InputStream in) {
		JSONObject jo = new JSONObject(new JSONTokener(in));
		jArrEventsFromFirstFile = jo.getJSONArray("events");
		for (int i = 0; i < jArrEventsFromFirstFile.length(); i++) {
			JSONObject jo2 = jArrEventsFromFirstFile.getJSONObject(i);
			Event e = evtsFactory.createInstance(jo2);
			trafficSim.addEvent(e);
		}
	}

	public void loadGameFromSave(InputStream in) throws Exception {
		int time;
		JSONObject jo = new JSONObject(new JSONTokener(in));
		JSONArray jArr = jo.getJSONArray("events");
		for (int i = 0; i < jArr.length(); i++) {
			JSONObject jo2 = jArr.getJSONObject(i);
			Event e = evtsFactory.createInstance(jo2);
			trafficSim.addEvent(e);
		}
		time = jo.getInt("time");
		run(time);
	}

	public void saveGame(OutputStream out) {
		JSONObject j = new JSONObject();
		
		// add elements
		j.put("events", jArrEventsFromFirstFile);
		j.put("time", trafficSim.getTime());
		
		// write to stream (file)
		PrintStream p = new PrintStream(out);
		p.println(j.toString(3));
	}

	public void run(int n) throws Exception {
		for (int i = 0; i < n; i++) {
			// add snapshot
			history.push(trafficSim.getTime(), new Memento(trafficSim));
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

//	public void addEvent(Event e) {
//		trafficSim.addEvent(e);
//	}

	public void reset() {
		// reset simulation
		trafficSim.reset();
	}

	public void undo() {
		history.undo();
	}

	// necesitamos esto para guardar los eventos anadidos durante la simulacion
	public void addEventCO2Change(Event e, int newEventTime, String id, int co2) {
		trafficSim.addEvent(e);
		JSONObject jo = new JSONObject();
		JSONArray ja = new JSONArray();
		JSONObject jo2 = new JSONObject();
		JSONObject jo3 = new JSONObject();
		jo2.put("vehicle", id);
		jo2.put("class", co2);
		ja.put(jo2);
		jo.put("info", ja);
		jo.put("time", newEventTime);
		jo3.put("data", jo);
		jo3.put("type", "set_cont_class");
		jArrEventsFromFirstFile.put(jo3);
	}

	public void addEventWeatherChange(Event e, int newEventTime, String id, Weather weather) throws Exception {
		trafficSim.addEvent(e);
		JSONObject jo = new JSONObject();
		JSONArray ja = new JSONArray();
		JSONObject jo2 = new JSONObject();
		JSONObject jo3 = new JSONObject();
		jo2.put("road", id);
		jo2.put("weather", weather);
		ja.put(jo2);
		jo.put("info", ja);
		jo.put("time", newEventTime);
		jo3.put("data", jo);
		jo3.put("type", "set_weather");
		jArrEventsFromFirstFile.put(jo3);
	}

}

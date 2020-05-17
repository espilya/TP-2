package simulator.control;

import simulator.exceptions.NonExistingObjectException;
import simulator.factories.Factory;
import simulator.misc.Pair;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;
import simulator.model.TrafficSimulator;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.Serializable;
import java.util.Base64;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import history.History;
import history.Memento;

public class Controller {

	private TrafficSimulator trafficSim;
	private Factory<Event> evtsFactory;
	private History history;
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

	public void saveGame(OutputStream out) {
		JSONObject j = new JSONObject();
		// add elements
		j.put("events", jArrEventsFromFirstFile);
		j.put("time", trafficSim.getTime());
		// write to stream (file)
		PrintStream p = new PrintStream(out);
		p.println(j.toString(3));
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

	void addEvent(Event e) {
		trafficSim.addEvent(e);
	}

	public void reset() {
		trafficSim.reset();
	}

	public void undo() {
		history.undo();
	}

}

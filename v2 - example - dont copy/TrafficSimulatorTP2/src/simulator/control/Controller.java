package simulator.control;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import simulator.factories.Factory;
import simulator.model.Event;
import simulator.model.TrafficSimObserver;
import simulator.model.TrafficSimulator;
//import simulator.view.MapComponent;

public class Controller {
	private TrafficSimulator TrafficSim;
	private Factory<Event> eventsFactory;
	
	public Controller(TrafficSimulator sim, Factory<Event> eventsFactory)throws IOException{
		if(sim == null) throw new IOException(" el parametro es nulo"); 
		else  this.TrafficSim = sim;
		if( eventsFactory == null) throw new IOException(" el parametro es nulo"); 
		else this.eventsFactory = eventsFactory;
	}

	public void loadEvents(InputStream in) {
		JSONObject jo = new JSONObject(new JSONTokener(in));
		JSONArray ja = jo.getJSONArray("events");
		for(int i = 0;  i < ja.length(); i++) {
			Event e = eventsFactory.createInstance(ja.getJSONObject(i));	
			//Si e es null se lanza excepcion
			TrafficSim.addEvent(e);
		}
		
				
	}
	public void run(int n, OutputStream out) {
		JSONObject jo = new JSONObject();
		JSONArray ja = new JSONArray();
		for(int i = 0; i < n; i++) {
			this.TrafficSim.advance();
			ja.put(this.TrafficSim.report());
		}
		/*for(int j = 141; j < n; j++) {
			this.TrafficSim.advance();
			ja.put(this.TrafficSim.report());
		}*/
		jo.put("states", ja);
		PrintStream p = new PrintStream(out);
		p.println(jo.toString(3));
		
	}
	public void reset() {
		TrafficSim.reset();
	}
	public void addObserver(TrafficSimObserver o) {
		this.TrafficSim.addObserver(o);
	}
	
	public void removeObserver(TrafficSimObserver o) {
		this.TrafficSim.removeObserver(o);
	}
	public void addEvent(Event e) {
		this.TrafficSim.addEvent(e);
	}

	public void run(int n) {
		for(int i = 0; i < n; i++) this.TrafficSim.advance();
	}

	public void saveCtrl() throws FileNotFoundException, IOException {
		TrafficSim.saveSim();
	}

	public void loadSaveCtrl() throws Exception {
		TrafficSim.loadSaveSim();
		
	}
	
	

}

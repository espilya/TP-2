package simulator.control;

import simulator.factories.Factory;
import simulator.model.Event;
import simulator.model.TrafficSimulator;

import java.io.InputStream;
import java.io.OutputStream;

import exceptions.ValueParseException;

public class Controller {
	
	private TrafficSimulator trafficSim; 
	private Factory<Event> evtsFactory;
	//...
	
	
    public Controller(TrafficSimulator sim, Factory<Event> eventsFactory) throws ValueParseException{
        // TODO complete}
    	trafficSim=sim;
    	
    	if(sim==null)
    		throw new ValueParseException("Null value for TrafficSimulator in controller");
    	if(eventsFactory==null)
    		throw new ValueParseException("Null value for eventsFactory in controller");
    }
    public void loadEvents(InputStream in){

    }
    public void run(int n, OutputStream out){

    }
    public void reset(){

    }
}

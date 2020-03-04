package simulator.model;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.json.JSONArray;
import org.json.JSONObject;

import exceptions.IncorrectRoadException;
import exceptions.ValueParseException;

public class Junction extends SimulatedObject{
	

	List<Road> roadList;
	Map<Junction, Road> mapOutRoads;
	List<List<Vehicle>> listQueue;
	private int indexGreenLight;
	private int lastTimeChangeLight;  
	
	LightSwitchingStrategy _strategyChangeLight;
	DequeuingStrategy _stretegyExtractElementsFromQueue;
	
	int[] pos = new int[2]; 

	Junction(String id, LightSwitchingStrategy lsStrategy, 
			DequeuingStrategy dqStrategy, int xCoor, int yCoor) throws ValueParseException {
		
		super(id);
		
		lastTimeChangeLight = 0;
		
		if(lsStrategy == null || dqStrategy == null) {
			throw new ValueParseException("Null values for lsStrategy OR dqStrategy");
		} 
		if(xCoor < 0 || yCoor < 0) {
			throw new ValueParseException("Negative values for x OR y coordinates");
		}
	}
	
	
	public int getX(){
		return pos[0];
	}
	public int getY(){
		return pos[1];
	}

	public void addIncommingRoad(Road r) throws IncorrectRoadException {
		roadList.add(r);
		listQueue.add(r.getVehicleList());
		if(!r.getDestination().equals(this)) 
			throw new IncorrectRoadException("");
	}
	
	public void addOutGoingRoad(Road r)  throws IncorrectRoadException {
		
		Junction j = r.destJunc;
		for(Road i : roadList) {
			if(i.getDestination().equals(j))
				throw new IncorrectRoadException("");
			
			
				
		}
		mapOutRoads.put(j, r);
		
	}
	
	void enter(Vehicle v) {
//		Map<> ;
		for (List<Vehicle> i : listQueue){

		}
		
	}
	
	Road roadTo(Junction j) {
		
		
		boolean found = false;
		long i = 0;
		Road road = null;
		Iterator<Entry<Junction, Road>> it = mapOutRoads.entrySet().iterator();
		while (it.hasNext() || !found) {
		    Entry<Junction, Road> pair = it.next();
		    road = pair.getValue();
		    if(pair.getKey().equals(j) && road.getDestination().equals(j)) {
		    	found = true;
		    }
		}
		return road;
	}
	
	void advance(int time) {
		
//--1--
		List<Vehicle> newQueui = null;
//		newQueui =  _stretegyExtractElementsFromQueue(listQueue.get(0));
		for(Vehicle i : newQueui) {
			i.moveToNextRoad();
			

		}
		
	}
	
	public JSONObject report() {
		JSONObject j = new JSONObject();
		j.put("id", _id);
		if(indexGreenLight == -1)
			j.put("green", "none");
		else
			j.put("green", roadList.get(indexGreenLight).getId());

		
		JSONArray jQueues = new JSONArray();
		for(Road r : roadList) {
			JSONObject jQueue = new JSONObject();
			jQueue.put("road", r.getId());
			
			JSONArray jVehicles = new JSONArray();
			for(Vehicle v : r.getVehicleList()) {
				jVehicles.put(v.getId());
			}
			jQueue.put("vehicles", jVehicles);
			jQueues.put(jQueue);
		}	
		j.put("queues", jQueues);
		return j;
	}



}

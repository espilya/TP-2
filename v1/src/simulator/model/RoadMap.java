package simulator.model;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class RoadMap {
	private List<Junction> junctionList;
	private List<Road> roadList;
	private List<Vehicle> vehicleList;
	private Map<String,Junction>  junctionMap;
	private Map<String,Vehicle> vehicleMap;
	private Map<String, Road> roadMap;
	protected RoadMap(){} 
	

	void addJunction(Junction j) {
		junctionList.add(j);
		// TO DO: Check if the element exists in the junctionMap
		junctionMap.put(j._id, j);
		
	}
	
	void addRoad(Road r) {
		roadList.add(r);
		// TO DO: Check if the element exists in the roadMap
		
		//???¿¿¿(ii) the junctions that connect the road exists in the road-map.???¿¿¿
		roadMap.put(r._id, r);
		
	}
	
	void addVehicle(Vehicle v) {
		vehicleList.add(v);
		// TO DO: Check if the element exists in the vehicleMap
		
		//???¿¿¿(ii) the itinerary is valid, i.e., there are roads that connect each consecutive
		//junctions of the itinerary.???¿¿¿
		vehicleMap.put(v._id, v);
	}
	
	public Junction getJunction(String id) {
		Junction key;
		key = junctionMap.get(id);
		return key;
		// returns the object with identifier id, and null
		//if no such object exists.

	}
	
	public Road getRoad(String id) {
		Road key;
		key = roadMap.get(id);
		return key;
		// returns the object with identifier id, and null
		//if no such object exists.
	}
	
	public Vehicle getVehicle(String id) {
		Vehicle key;
		key = vehicleMap.get(id);
		return key;
		// returns the object with identifier id, and null
		//if no such object exists.
		
	}
	
	public List<Junction>getJunctions(){
		List<Junction>getJuncs = Collections.unmodifiableList(junctionList);
		return getJuncs;
		// may be incorrect but looks good
	}
	
	public List<Road>getRoads(){
		List<Road>getRoads = Collections.unmodifiableList(roadList);
		return getRoads;
	}
	
	public List<Vehicle>getVehicles(){
		List<Vehicle>getVehicles = Collections.unmodifiableList(vehicleList);
		return getVehicles;
	}
	
	void reset() {
		junctionList.clear();
		roadList.clear();
		vehicleList.clear();
		junctionMap.clear();
		roadMap.clear();
		vehicleMap.clear();
		
	}
	
	public JSONObject report() {
		
	}
	
	
}

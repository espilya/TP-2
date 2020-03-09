package simulator.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

public class RoadMap {
	private List<Junction> junctionList;
	private List<Road> roadList;
	private List<Vehicle> vehicleList;
	private Map<String,Junction>  junctionMap;
	private Map<String, Road> roadMap;
	private Map<String,Vehicle> vehicleMap;
	
	protected RoadMap(){
		this.junctionList = new ArrayList<Junction>();
		this.roadList = new ArrayList<Road>();
		this.vehicleList = new ArrayList<Vehicle>();
		
		// ----------------------------- Map ===> hashMap or TreeMap?????? -----------------------------
//		5. Which Implementation to Use?
//				In general, both implementations have their respective pros and cons, however, it's about understanding the underlying expectation and requirement which must govern our choice regarding the same.
//
//				Summarizing:
//				We should use a TreeMap if we want to keep our entries sorted
//				We should use a HashMap if we prioritize performance over memory consumption
//				Since a TreeMap has a more significant locality, we might consider it if we want to access objects that are relatively close to each other according to their natural ordering
//				HashMap can be tuned using the initialCapacity and loadFactor, which isn't possible for the TreeMap
//				We can use the LinkedHashMap if we want to preserve insertion order while benefiting from constant time access
//		More: https://www.google.es/search?client=opera&hs=2fE&sxsrf=ALeKk01FpnYBvur2VMG6y3D3T_jesB7ESQ%3A1583415431777&ei=hwBhXpWEL5OBjLsPzYu8uAE&q=TreeMap+or+HashMapjava&oq=TreeMap+or+HashMapjava&gs_l=psy-ab.3..0i8i7i30.1113204.1122276..1122661...0.4..0.109.303.1j2......0....2j1..gws-wiz.......0i71j0i8i13i30j0i8i30.bojsIplxGZU&ved=0ahUKEwjV496SuoPoAhWTAGMBHc0FDxcQ4dUDCAo&uact=5
		this.junctionMap = new HashMap<String,Junction>();
		this.roadMap = new HashMap<String,Road>();
		this.vehicleMap = new HashMap<String,Vehicle>();
		// ----------------------------- Map ===> hashMap or TreeMap?????? -----------------------------
	} 
	

	void addJunction(Junction j) {
		junctionList.add(j);
		if(junctionMap.containsKey(j.getId()))
			throw new IllegalArgumentException("Tried to add existing element.");

		junctionMap.put(j._id, j);
		
	}
	
	void addRoad(Road r) {
		roadList.add(r);
		// TO DO: Check if the element exists in the roadMap
		
		//???¿¿¿(ii) the junctions that connect the road exists in the road-map.???¿¿¿
		//if road.dst() == for(in Map)
		//else exception
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
		
		JSONObject j = new JSONObject();
		// TODO
		return j;
	}
	
	
}

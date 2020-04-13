package simulator.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.exceptions.ExistingObjectException;
import simulator.exceptions.NonExistingObjectException;

public class RoadMap {
	private List<Junction> junctionList;
	private List<Road> roadList;
	private List<Vehicle> vehicleList;
	private Map<String, Junction> junctionMap;
	private Map<String, Road> roadMap;
	private Map<String, Vehicle> vehicleMap;

	protected RoadMap() {
		junctionList = new ArrayList<Junction>();
		roadList = new ArrayList<Road>();
		vehicleList = new ArrayList<Vehicle>();
		junctionMap = new HashMap<String, Junction>();
		roadMap = new HashMap<String, Road>();
		vehicleMap = new HashMap<String, Vehicle>();
	}

	void addJunction(Junction j) throws ExistingObjectException {
		junctionList.add(j);
		if (junctionMap.containsKey(j.getId()))
			throw new ExistingObjectException("");
		junctionMap.put(j._id, j);
	}

	void addRoad(Road r) throws ExistingObjectException, NonExistingObjectException {
		roadList.add(r);
		Junction dest = r.getDestination();
		Junction src = r.getSource();
		// i) and ii)
		if (roadMap.containsKey(r._id))
			throw new ExistingObjectException("Road '" + r._id + "' already exist in roadMap." );
		if (!junctionMap.containsKey(dest._id))
			throw new NonExistingObjectException("Junction '" + dest._id + "' does not exist in junctionMap." );
		if (!junctionMap.containsKey(src._id))
			throw new NonExistingObjectException("Junction '" + src._id + "' does not exist in junctionMap." );
		roadMap.put(r._id, r);
	}

	void addVehicle(Vehicle v) throws ExistingObjectException, NonExistingObjectException {
		vehicleList.add(v);
		// i) 
		if (vehicleMap.containsKey(v._id))
			throw new ExistingObjectException("Vehicle '" + v.getId() + "' already exist in junctionMap." );

		
		//terminar: ii)
//		List<Junction> j = v.getItinerary();
//		for (int i = 0; i < j.size() - 1; i++) {
//
//			boolean ok = false;
//			for (Road r : roadList) {
//				if (!r.getSource().equals(j.get(i)) || !r.getDestination().equals(j.get(i + 1)))
//					throw new NonExistingObjectException("");
//			}
//			if (!ok)
//				throw new NonExistingObjectException("");
//		}

		vehicleMap.put(v._id, v);
	}

	public Junction getJunction(String id) {
		Junction key = null;
		key = junctionMap.get(id);
		return key;
	}

	public Road getRoad(String id) {
		Road key = null;
		key = roadMap.get(id);
		return key;
	}

	public Vehicle getVehicle(String id) {
		Vehicle key;
		key = vehicleMap.get(id);
		return key;
	}

	public List<Junction> getJunctions() {
		return Collections.unmodifiableList(junctionList);

	}

	public List<Road> getRoads() {
		return Collections.unmodifiableList(roadList);
	}

	public List<Vehicle> getVehicles() {
		return Collections.unmodifiableList(vehicleList);
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
		JSONArray ja = new JSONArray();
		JSONArray ja2 = new JSONArray();
		JSONArray ja3 = new JSONArray();

		for (Road r : roadList)
			ja2.put(r.report());
		j.put("roads", ja2);
		
		for (Vehicle v : vehicleList)
			ja3.put(v.report());
		j.put("vehicles", ja3);
		
		for (Junction ju : junctionList)
			ja.put(ju.report());
		j.put("junctions", ja);
		
		return j;
	}

}

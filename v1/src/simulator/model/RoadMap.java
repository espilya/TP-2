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
	private Map<String, Junction> junctionMap;
	private Map<String, Road> roadMap;
	private Map<String, Vehicle> vehicleMap;

	protected RoadMap() {
		this.junctionList = new ArrayList<Junction>();
		this.roadList = new ArrayList<Road>();
		this.vehicleList = new ArrayList<Vehicle>();

		// ----------------------------- Map ===> hashMap or TreeMap??????
		this.junctionMap = new HashMap<String, Junction>();
		this.roadMap = new HashMap<String, Road>();
		this.vehicleMap = new HashMap<String, Vehicle>();
	}

	void addJunction(Junction j) {
		junctionList.add(j);
		if (junctionMap.containsKey(j.getId()))
			throw new IllegalArgumentException("Tried to add existing element.");
		junctionMap.put(j._id, j);
	}

	void addRoad(Road r) throws Exception {
		roadList.add(r);
		Junction dest = r.getDestination();
		Junction src = r.getSource();
		// i) and ii)
		if (roadMap.containsKey(r._id) || !junctionMap.containsKey(dest._id) || !junctionMap.containsKey(src._id))
			throw new Exception("");
		roadMap.put(r._id, r);
	}

	void addVehicle(Vehicle v) throws Exception {
		vehicleList.add(v);
		// i) and ii)
		if (roadMap.containsKey(v._id))
			throw new Exception("-");

		List<Junction> j = v.getItinerary();
		for (int i = 0; i < j.size() - 1; i++) {

			boolean ok = false;
			for (Road r : roadList) {
				if (r.getSource().equals(j.get(i)) && r.getDestination().equals(j.get(i + 1)))
					ok = true;
			}
			if (!ok)
				throw new Exception("");
		}

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

		JSONObject jo = new JSONObject();
		JSONArray ja = new JSONArray();
		JSONArray ja2 = new JSONArray();
		JSONArray ja3 = new JSONArray();

		for (Road r : this.roadList)
			ja2.put(r.report());
		jo.put("roads", ja2);
		for (Vehicle v : this.vehicleList)
			ja3.put(v.report());
		jo.put("vehicles", ja3);
		for (Junction j : this.junctionList)
			ja.put(j.report());
		jo.put("junctions", ja);
		return jo;
	}

}

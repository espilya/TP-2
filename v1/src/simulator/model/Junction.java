package simulator.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.json.JSONArray;
import org.json.JSONObject;

import exceptions.IncorrectRoadException;
import exceptions.ValueParseException;

public class Junction extends SimulatedObject {

	private List<Road> roadList;
	private List<List<Vehicle>> listQueue;

	private Map<Junction, Road> mapOutRoads;
//	private Map<Road, List<Vehicle>> roadQueue; // new
	private int indexGreenLight;
	private int lastTimeChangeGreenLight;

	private LightSwitchingStrategy _strategyChangeLight;
	private DequeuingStrategy _stretegyExtractElementsFromQueue;

	int[] pos = new int[2];

	Junction(String id, LightSwitchingStrategy lsStrategy, DequeuingStrategy dqStrategy, int xCoor, int yCoor)
			throws ValueParseException {

		super(id);
		roadList = new ArrayList<Road>();
		listQueue = new ArrayList<List<Vehicle>>();
		mapOutRoads = new HashMap<Junction, Road>();
		_strategyChangeLight = lsStrategy;
		_stretegyExtractElementsFromQueue = dqStrategy;
		lastTimeChangeGreenLight = 0;
		indexGreenLight = -1;
		pos[0] = xCoor;
		pos[1] = yCoor;

		if (lsStrategy == null || dqStrategy == null) {
			throw new ValueParseException("Null values for lsStrategy OR dqStrategy");
		}
		if (xCoor < 0 || yCoor < 0) {
			throw new ValueParseException("Negative values for x OR y coordinates");
		}
	}

	public int getX() {
		return pos[0];
	}

	public int getY() {
		return pos[1];
	}

	public void addIncommingRoad(Road r) throws IncorrectRoadException {
		List<Vehicle> listV = new LinkedList<Vehicle>();
		if (!r.getDestination().equals(this))
			throw new IncorrectRoadException("");

		roadList.add(r);
		listQueue.add(listV);
	}

	public void addOutGoingRoad(Road r) throws IncorrectRoadException {

		Junction j = r.destJunc;
		for (Road i : roadList) {
			if (i.getDestination().equals(j))
				throw new IncorrectRoadException("");

		}
		mapOutRoads.put(j, r);

	}

	void enter(Vehicle v) {
		Road r = v.getRoad();
		int index = roadList.indexOf(r);
		listQueue.get(index).add(v);
	}

	Road roadTo(Junction j) {
		return mapOutRoads.get(j);
	}

	void advance(int time) {
		// --1--
		if (indexGreenLight != -1) {
			List<Vehicle> newQueui = _stretegyExtractElementsFromQueue.dequeue(listQueue.get(indexGreenLight));
			if (newQueui != null)
				for (Vehicle i : newQueui) {
					try {
						if (i.getStatus().equals(VehicleStatus.WAITING)) {
							i.moveToNextRoad();
							listQueue.get(indexGreenLight).remove(i);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
		}

		// --2--
		int newIndexGreenLight = _strategyChangeLight.chooseNextGreen(roadList, listQueue, indexGreenLight,
				lastTimeChangeGreenLight, time);
		if (newIndexGreenLight != indexGreenLight) {
			lastTimeChangeGreenLight = time;
			indexGreenLight = newIndexGreenLight;
		}
		
	}

	public JSONObject report() {
		JSONObject jo = new JSONObject();
		jo.put("id", this._id);
		if(indexGreenLight != -1) jo.put("green", roadList.get(indexGreenLight)._id); 
		else jo.put("green", "none");
		
		List<Vehicle> listV;
		JSONArray ja = new JSONArray();
		for(int i = 0; i < roadList.size(); i++) {
			listV = listQueue.get(i);
			JSONObject jo2 = new JSONObject();
			jo2.put("road",roadList.get(i)._id);
			JSONArray ja2 = new JSONArray();
			for(Vehicle v : listV){
				ja2.put(v);
			}
			jo2.put("vehicles", ja2);
			ja.put(jo2);
		}
		jo.put("queues", ja);
		return jo;
	
	
//		JSONObject j = new JSONObject();
//		j.put("id", _id);
//		if (indexGreenLight == -1)
//			j.put("green", "none");
//		else
//			j.put("green", roadList.get(indexGreenLight).getId());
//
//		JSONArray jQueues = new JSONArray();
//		for (Road r : roadList) {
//			JSONObject jQueue = new JSONObject();
//			jQueue.put("road", r.getId());
//
//			JSONArray jVehicles = new JSONArray();
//			for (Vehicle v : r.getVehicleList()) {
//				jVehicles.put(v.getId());
//			}
//			jQueue.put("vehicles", jVehicles);
//			jQueues.put(jQueue);
//		}
//		j.put("queues", jQueues);
//		return j;
	}

	public Road getOutRoad(Vehicle v) {
		List<Junction> listJ = v.getItinerary();
		int index = -1;
		for (int i = 0; i < listJ.size(); i++) {
			if (this.equals(listJ.get(i)))
				index = i + 1;
		}
		if (index >= listJ.size())
			return null;
		return mapOutRoads.get(listJ.get(index));
	}

}

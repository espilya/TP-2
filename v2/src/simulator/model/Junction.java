package simulator.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.exceptions.IncorrectObjectException;
import simulator.exceptions.IncorrectVariableValueException;

public class Junction extends SimulatedObject {

	private List<Road> roadList;
	private List<List<Vehicle>> listQueue;

	private Map<Junction, Road> mapOutRoads;
//	private Map<Road, List<Vehicle>> roadQueueMap;
	private int indexGreenLight;
	private int lastTimeChangeGreenLight;

	private LightSwitchingStrategy _strategyChangeLight;
	private DequeuingStrategy _stretegyExtractElementsFromQueue;

	int[] pos = new int[2];

	Junction(String id, LightSwitchingStrategy lsStrategy, DequeuingStrategy dqStrategy, int xCoor, int yCoor)
			throws IncorrectVariableValueException {

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

		if (lsStrategy == null)
			throw new IncorrectVariableValueException("Null values for lsStrategy.");
		if (dqStrategy == null)
			throw new IncorrectVariableValueException("Null values for dqStrategy.");
		if (xCoor < 0 || yCoor < 0)
			throw new IncorrectVariableValueException("Negative values for Coor; x: " + xCoor + ", y: " + yCoor);
	}

	public int getX() {
		return pos[0];
	}

	public int getY() {
		return pos[1];
	}

	public void addIncommingRoad(Road r) throws IncorrectObjectException {
		List<Vehicle> listV = new LinkedList<Vehicle>();
		if (!r.getDestination().equals(this))
			throw new IncorrectObjectException("Road " + r._id + " is not a incomingRoad to Junction" + this._id);

		roadList.add(r);
		listQueue.add(listV);
	}

	public void addOutGoingRoad(Road r) throws IncorrectObjectException {

		Junction j = r.destJunc;
		for (Road i : roadList) {
			if (i.getDestination().equals(j))
				throw new IncorrectObjectException(
						"Road " + r._id + " is not a OutGoingRoad from Junction " + this._id);
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
		JSONObject j = new JSONObject();
		j.put("id", this._id);
		if (indexGreenLight != -1)
			j.put("green", roadList.get(indexGreenLight)._id);
		else
			j.put("green", "none");

		List<Vehicle> listV;
		JSONArray ja = new JSONArray();
		for (int i = 0; i < roadList.size(); i++) {
			listV = listQueue.get(i);
			JSONObject j2 = new JSONObject();
			j2.put("road", roadList.get(i)._id);
			JSONArray ja2 = new JSONArray();
			for (Vehicle v : listV) {
				ja2.put(v);
			}
			j2.put("vehicles", ja2);
			ja.put(j2);
		}
		j.put("queues", ja);
		return j;
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
		else
			return mapOutRoads.get(listJ.get(index));
	}

	public int getGreenLightIndex() {
		return indexGreenLight;
	}

}

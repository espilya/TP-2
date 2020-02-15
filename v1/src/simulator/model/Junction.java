package simulator.model;

import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import exceptions.ValueParseException;

public class Junction extends SimulatedObject {
	
	// atributos
	List<Road> roadList;
	Map<Junction, Road> mapOutRoads;
	List<List<Vehicle>> listQueue;
	int indexGreenLight;
	int lastTimeChangeLight;  
	//el paso en el cual el índice del semáforo en verde ha cambiado de valor. Su valor inicial es 0.

	LightSwitchingStrategy _strategyChangeLight;
	DequeuingStrategy _stretegyExtractElementsFromQueue;
	
	int[] pos = new int[2]; //x & y  pos. for v2

	Junction(String id, LightSwitchingStrategy lsStrategy, 
			DequeuingStrategy dqStrategy, int xCoor, int yCoor) {
		
		super(id);//i think we need change this , nut not sure
		
		if(lsStrategy == null || dqStrategy == null) {
			throw new ValueParseException("Null values for lsStrategy OR dqStrategy");
		}
		if(xCoor < 0 || yCoor < 0) {
			throw new ValueParseException("Negative values for x OR y coordinates");
		}
	}
	
	void addIncommingRoad(Road r) {
		
	}
	
	void addOutGoingRoad(Road r) {
		
	}
	
	void enter(Vehicle v) {
		
	}
	
	Road roadTo(Junction j) {
		
		return null;
	}
	
	void advance(int time) {
		
	}
	
	public JSONObject report() {
		
		return null;
	}

}

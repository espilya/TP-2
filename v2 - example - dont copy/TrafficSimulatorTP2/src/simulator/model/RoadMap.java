package simulator.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

public class RoadMap {
	private List<Junction> listJunc;
	private List<Road> listRoad;
	private List<Vehicle> listVeh;
	private Map<String,Junction> mapJunc;
	private Map<String,Road> mapRoad;
	private Map<String,Vehicle> mapVeh;
	
	RoadMap(){
		this.listJunc = new ArrayList<Junction>();
		this.listRoad = new ArrayList<Road>();
		this.listVeh = new ArrayList<Vehicle>();
		this.mapJunc = new HashMap<String,Junction>();
		this.mapRoad = new HashMap<String,Road>();
		this.mapVeh = new HashMap<String,Vehicle>();
	}
		
	//añade el cruce j
	void addJunction(Junction j) throws Exception {
		if(this.mapJunc.containsKey(j.getId())) throw new Exception("Ya esta contenido"); 
		listJunc.add(j);
		this.mapJunc.put(j.getId(), j);
	}
	
	//añade la carretera r (ii) los cruces que conecta la
	//carretera existen en el mapa de carreteras. En caso de que no se cumplan el método
	//lanza una excepcion
	void addRoad(Road r) throws Exception {
		if(this.mapRoad.containsKey(r.getId())) throw new Exception("Ya esta contenido");
		Junction j1 = r.getCruceOrigen();
		Junction j2 = r.getCruceDest();
		if(!this.mapJunc.containsKey(j1._id)) throw new Exception("No esta el cruce origen");
		if(!this.mapJunc.containsKey(j2._id)) throw new Exception("No esta el cruce destino");
		listRoad.add(r);
		this.mapRoad.put(r.getId(), r);
	}
	
	//añade el vehículo v y (ii)
	//el itinerario es válido, es decir, existen carreteras que conecten los cruces consecutivos
	//de su itinerario. En caso de que no se cumplan (i) y (ii), el método debe lanzar una
	//excepción.
	void addVehicle(Vehicle v) throws Exception{
		if(this.mapVeh.containsKey(v.getId())) throw new Exception("Ya esta contenido");
		for(Vehicle v1: listVeh) { //para cada vehiculo
			boolean ok = false;
			for(int i=0; i < v1.getItinerario().size()-1; i++) { //obtengo el itinerario de origen y destino
				Junction j1 = v1.getItinerario().get(i);
				Junction j2 = v1.getItinerario().get(i+1);
				for(Road r1: listRoad) {
					if(r1.getCruceOrigen() == j1 && r1.getCruceDest() == j2) ok = true; //comprubo si hay carretera que conecta
				}
			}
			if(ok == false) throw new Exception(); //si no hay carretera que conecta los junction de listadeitinerario
		}
		listVeh.add(v);
		this.mapVeh.put(v.getId(), v);
	}
	
	//devuelve el cruce con identificador id, y null si
	//no existe dicho cruce.
	public Junction getJunction(String id) {
		return mapJunc.get(id);
	}
	
	// devuelve la carretera con identificador id, y null si no
	//existe dicha carretera.
	public Road getRoad(String id) {
		return mapRoad.get(id);
	}
	
	//devuelve el vehículo con identificador id, y null si
	//no existe dicho vehículo.
	public Vehicle getVehicle(String id) {
		return mapVeh.get(id);
	}

	//devuelve una versión de solo lectura de la lista
	//de cruces.
	public List<Junction>getJunctions(){
		return Collections.unmodifiableList(this.listJunc);}
	
	//devuelve una versión de solo lectura de la lista de
	//carreteras.
	public List<Road>getRoads(){
		return Collections.unmodifiableList(this.listRoad);} //Roads
	
	//devuelve una versión de solo lectura de la lista de
	//vehículos
	public List<Vehicle>getVehicles(){
		return Collections.unmodifiableList(this.listVeh);}
	
	//limpia todas las listas y mapas.
	void reset() {
		this.listJunc.clear();
		this.listRoad.clear();
		this.listVeh.clear();
		this.mapJunc.clear();
		this.mapRoad.clear();
		this.mapVeh.clear();
	}
	
	// devuelve el estado del mapa de carreteras en el siguiente
	public JSONObject report() {
		
		JSONObject jo= new JSONObject();
		JSONArray ja = new JSONArray();
		JSONArray ja2 = new JSONArray();
		JSONArray ja3 = new JSONArray();
		for(Road r : this.listRoad) ja2.put(r.report());
		jo.put("roads", ja2);
		for(Vehicle v : this.listVeh) ja3.put(v.report());
		jo.put("vehicles", ja3);
		for(Junction j : this.listJunc) ja.put(j.report());	
		jo.put("junctions", ja);
		
		return jo;
	}
}


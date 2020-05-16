package simulator.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import myExceptions.IncorrectValue;

public class Junction extends SimulatedObject{
	private List<Road> lisRoad;
	private Map<Junction, Road> MapaCarrSaliente;
	private List<List<Vehicle>> listaColas;
	private Map<Road,List<Vehicle>> MapRoadColas;
	private int indiLuzVerde;
	private int indUltimoVerde;
	private LightSwitchingStrategy lsStrategy;
	private DequeuingStrategy dqStrategy;
	private int x, y;
	
	Junction(String id, LightSwitchingStrategy lsStrategy, DequeuingStrategy
			dqStrategy, int xCoor, int yCoor) throws IncorrectValue{
			super(id);
	    	if(lsStrategy == null) throw new IncorrectValue("lsStrategy vacia");
	    	else this.lsStrategy = lsStrategy;
	    	if(dqStrategy == null) throw new IncorrectValue("dqStrategy vacia");
	    	else this.dqStrategy = dqStrategy;
	    	if(xCoor < 1)throw new IncorrectValue("la coordenada x debe ser >= 0");
	    	this.x = xCoor;
	    	if(yCoor < 1)throw new IncorrectValue("la coordenada y debe ser >= 0");
	    	this.y = yCoor;
	    	lisRoad = new ArrayList<>();
	    	listaColas = new ArrayList<>();
	    	MapaCarrSaliente = new HashMap<Junction, Road>();
	    	MapRoadColas = new HashMap<Road,List<Vehicle>>();
	    	indiLuzVerde = -1;
	    	indUltimoVerde = 0;
			
	}
	
	// añade r al final de la lista de carreteras entrantes
	//  y la añade al final de la lista de colas
	void addIncommingRoad(Road r) throws IncorrectValue {
		if(!this.equals(r.getCruceDest()))
		throw new IncorrectValue("Su cruce destino NO!! es igual al cruce actual");
		List<Vehicle> listV = new LinkedList<Vehicle>();
		this.lisRoad.add(r);
		this.listaColas.add(listV);
		this.MapRoadColas.put(r, listV);
	}
	// aé¦»de el par (j,r) al mapa de carreteras salientes,
	//donde j es el cruce destino de la carretera r. Tienes que comprobar que ninguna otra
	//carretera va desde this al cruce j y, que la carretera r, es realmente una carretera
	//saliente al cruce actual. En otro caso debes lanzar una excepcié«‡.
	void addOutGoingRoad(Road r) throws IncorrectValue {
		for(Road road : lisRoad) {
			if(road.getCruceDest().equals(r.getCruceDest()))
				throw new IncorrectValue("Hay mas de una carretera que va al mismo cruce");
				
		}
		this.MapaCarrSaliente.put(r.getCruceDest(), r);
		//System.out.println(this.MapaCarrSaliente);
		
	}
	
	//anadir v a la cola de la carretera r, donde r es la
	//carretera actual del vehiculo v.
	void enter(Vehicle v) throws IncorrectValue {
		//v.carretera.enter(v);
		//listaColas.get(indiLuzVerde).add(v);
		listaColas.get(lisRoad.indexOf(v.getCarretera())).add(v);
	}
	
	//devuelve la carretera que va desde el cruce actual al cruce j.
	//Para esto debes buscar en la lista de carreteras salientes ï¿½ es mejor llevar actualizado
	//siempre el mapa Map<Junction,Road> para hacer la bé·–queda mé†© eficiente.
	Road roadTo(Junction j) {
		return this.MapaCarrSaliente.get(j);
	}

	@Override
	void advance(int time) throws IncorrectValue {
		if(indiLuzVerde > -1) {
		List<Vehicle> listV = dqStrategy.dequeue(listaColas.get(indiLuzVerde));
		if(listV != null) {
		for(Vehicle v : listV) {
			if(v.getEstado() == VehicleStatus.WAITING) {
			v.moveToNextRoad();
			listaColas.get(indiLuzVerde).remove(v);
			}
		}
		}
	}
		int nextGreen = lsStrategy.chooseNextGreen(lisRoad, listaColas, indiLuzVerde, indUltimoVerde, time);
		if(nextGreen != indiLuzVerde) {
			this.indiLuzVerde = nextGreen;
			this.indUltimoVerde = time;
		}
	}

	@Override
	public JSONObject report() {
		JSONObject jo = new JSONObject();
		jo.put("id", this._id);
		if(indiLuzVerde != -1) jo.put("green", lisRoad.get(indiLuzVerde)._id); 
		else jo.put("green", "none");
		
		List<Vehicle> listV;
		JSONArray ja = new JSONArray();
		for(int i = 0; i < lisRoad.size(); i++) {
			listV = listaColas.get(i);
			JSONObject jo2 = new JSONObject();
			jo2.put("road",lisRoad.get(i)._id);
			JSONArray ja2 = new JSONArray();
			for(Vehicle v : listV){
				ja2.put(v);
			}
			jo2.put("vehicles", ja2);
			ja.put(jo2);
		}
		jo.put("queues", ja);
		return jo;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	public List<Road> getInRoads() {
		// TODO Auto-generated method stub
		return lisRoad;
	}

	public int getGreenLightIndex() {
		// TODO Auto-generated method stub
		return indiLuzVerde;
	}
}
	


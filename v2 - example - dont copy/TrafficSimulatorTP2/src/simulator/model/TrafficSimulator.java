package simulator.model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;

import myExceptions.IncorrectValue;
import simulator.misc.SortedArrayList;

public class TrafficSimulator implements Observable<TrafficSimObserver>{
	//falta guardar las lista de eventos en el momento de guardar
	protected RoadMap mapRoad;
	protected List<Event> listEvent;
	protected int timeSim;
	private List<TrafficSimObserver> listObserver;
	private int saveTime;
	
	
	 public TrafficSimulator(){
		this.mapRoad = new RoadMap();
		this.listEvent = new SortedArrayList<Event>();
		this.timeSim = 0;
		this.listObserver = new ArrayList<TrafficSimObserver>();
		//para guardar
		//this.saveListEvent = new SortedArrayList<Event>();
		//this.saveMap = new RoadMap();
		this.saveTime = 0;
	}
	public void addEvent(Event e) {
		listEvent.add(e);
		//notifica a las vista añade un evento al simulador
		for(TrafficSimObserver o : this.listObserver)
			o.onEventAdded(this.mapRoad, this.listEvent, e, this.timeSim);
	}
	public void advance() {
		//incrementa el tiempo de la simulación en 1.
		timeSim += 1;
		//notifica a las vista ejecuta el método advance
		for(TrafficSimObserver o : this.listObserver) 
			o.onAdvanceStart(this.mapRoad, this.listEvent, this.timeSim);
		//ejecuta todos los eventos cuyo tiempo sea el tiempo actual de la simulación y
		//los elimina de la lista. Después llama a sus correspondientes métodos execute.
		while(!listEvent.isEmpty() && listEvent.get(0).getTime() == timeSim) {
			listEvent.get(0).execute(mapRoad);
			listEvent.remove(listEvent.get(0));
		}
		//llama al método advance de todos los cruces.
		for(Junction j : mapRoad.getJunctions()) {
			try {
				j.advance(timeSim);
			} catch (IncorrectValue e1) {
				for(TrafficSimObserver o : this.listObserver) 
					o.onError(e1.getMessage());
				e1.printStackTrace();
			}
		}
		//llama al método advance de todas las carreteras.
		for(Road r : mapRoad.getRoads()) {
			try {
				r.advance(timeSim);
			} catch (IncorrectValue e1) {
				for(TrafficSimObserver o : this.listObserver) 
					o.onError(e1.getMessage());
				e1.printStackTrace();
			}
		}
		for(TrafficSimObserver o : this.listObserver) 
			o.onAdvanceEnd(this.mapRoad, this.listEvent, this.timeSim);
	}

	public void reset() {
		mapRoad.reset();
		listEvent.clear();
		timeSim = 0;
		for(TrafficSimObserver o : this.listObserver) 
			o.onReset(this.mapRoad, this.listEvent, this.timeSim);
	}
	
	public JSONObject report() {
		JSONObject jo = new JSONObject();
		jo.put("time", this.timeSim);
		jo.put("state", mapRoad.report());
		return jo;
		}
	@Override
	public void addObserver(TrafficSimObserver o) {
		o.onRegister(this.mapRoad, this.listEvent, this.timeSim);
		if(o != null && !this.listObserver.contains(o)) {
			this.listObserver.add(o);
		}
	}
	@Override
	public void removeObserver(TrafficSimObserver o) {
		if(o != null && this.listObserver.contains(o))
			this.listObserver.remove(o);
		
	}
	public void saveSim() throws FileNotFoundException, IOException {
		this.saveTime = timeSim;
	    ObjectOutputStream writeFic = new ObjectOutputStream( 
	            new FileOutputStream("resources/examples/save.txt"));
	    writeFic.writeObject(mapRoad);
	    writeFic.writeObject(listEvent);
	  
	    writeFic.close();
	}
	
	//para cargar lo guardado
	@SuppressWarnings("unchecked")
	public void loadSaveSim() throws Exception {
		this.timeSim = this.saveTime;
		mapRoad.reset();
		ObjectInputStream loadFic = new ObjectInputStream( 
	      new FileInputStream("resources/examples/saveListEvent.txt"));
		this.listEvent = (List<Event>) loadFic.readObject();
		this.mapRoad = (RoadMap) loadFic.readObject();

	    loadFic.close();
		for(TrafficSimObserver o : this.listObserver)
			o.onLoad(this.mapRoad, this.listEvent, this.timeSim);
	}
	
}

package simulator.model;

import java.util.List;
import java.util.Map;

import org.json.JSONObject;

public class TrafficSimulator {
	
	private RoadMap roadMap;
	private List<Event> listEvents;
	private int time;
	
	public TrafficSimulator() {
		//set values to default
		time = 0;
	}
	
	public void addEvent(Event e) {
		
	}
	
	public void advance() {
		time++;
		
		2. ejecuta todos los eventos cuyo tiempo sea el tiempo actual de la simulación y
		los elimina de la lista. Después llama a sus correspondientes métodos execute.
		3. llama al método advance de todos los cruces.
		4. llama al método advance de todas las carreteras.
		
	}
	
	public void reset() {
		
	}
	
	public JSONObject report() {
		
	}

}

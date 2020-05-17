package simulator.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class NewVehicleEvent extends Event implements Serializable{
	  protected String id;
	  protected	List<String> _itinerario;
	  protected int maxSpeed, gradCont;
	  public NewVehicleEvent(int time, String id, int maxSpeed, int
			contClass, List<String> itinerary) {
			super(time);
			this.id = id;
			this.maxSpeed = maxSpeed;
			this.gradCont = contClass;
			this._itinerario = itinerary;
	}

	@Override
	void execute(RoadMap map) {
		try {
			List<Junction> itinerario = new ArrayList<Junction>(_itinerario.size());
			for(String id : _itinerario) {
				itinerario.add(map.getJunction(id));
			}
			
			Vehicle v = new Vehicle(this.id, this.maxSpeed, this.gradCont, itinerario);
			map.addVehicle(v);
			//System.out.println(itinerario);
			//System.out.println(map.getVehicles());
			v.moveToNextRoad();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	public String toString() {
		return "New Vehicle '"+ this.id+"'";
	}

}

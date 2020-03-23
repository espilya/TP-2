package simulator.model;

import java.util.ArrayList;
import java.util.List;

public class MoveAllStrategy implements DequeuingStrategy{

	public List<Vehicle> dequeue(List<Vehicle> q) {
		List<Vehicle> list = new ArrayList<Vehicle>(); 
		for(Vehicle v : q) 
			list.add(v);
		return list;
	}
}

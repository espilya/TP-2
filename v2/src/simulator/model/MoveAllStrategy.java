package simulator.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MoveAllStrategy implements DequeuingStrategy, Serializable {
	private static final long serialVersionUID = -4251292091031062886L;

	public List<Vehicle> dequeue(List<Vehicle> q) {
		List<Vehicle> list = new ArrayList<Vehicle>();
		for (Vehicle v : q)
			list.add(v);
		return list;
	}
}

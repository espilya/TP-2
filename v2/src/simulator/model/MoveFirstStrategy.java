package simulator.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MoveFirstStrategy implements DequeuingStrategy , Serializable{
	private static final long serialVersionUID = 434330637856062059L;

	public List<Vehicle> dequeue(List<Vehicle> q) {
		List<Vehicle> qq = new ArrayList<Vehicle>();
		if (q.size() == 0)
			return null;
		qq.add(q.get(0));
		return qq;
	}

}

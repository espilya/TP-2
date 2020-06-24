package simulator.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import simulator.misc.SortedArrayList;

public class LessContaminationStrategy implements DequeuingStrategy , Serializable{
	private static final long serialVersionUID = 434330637856062059L;
	
	private int _limit;
	
	public LessContaminationStrategy(int limit){
		_limit = limit;
	}
	


	public List<Vehicle> dequeue(List<Vehicle> q) {
		if (q.size() == 0)
			return null;
		int vehicleCounter = 0;
		List<Vehicle> finalVehicles = new ArrayList<Vehicle>();
		List<Vehicle> qq = new SortedArrayList<Vehicle>(new Comparator<Vehicle>() {
			@Override
			public int compare(Vehicle o1, Vehicle o2) {
				return Integer.compare(o1.getContClass(), o2.getContClass());
			}
		});
		qq.addAll(q);
		
		boolean exit = false;
		int i = 0;
		while(!exit && i<qq.size()) {
			if(vehicleCounter < _limit) {
				finalVehicles.add(qq.get(i));
				vehicleCounter++;
			}
			else
				exit = true;
			i++;
		}

		return finalVehicles;
	}
	

}
package simulator.model;

import java.io.Serializable;
import java.util.List;

import myExceptions.IncorrectValue;
import simulator.misc.Pair;

public class NewSetContClassEvent extends Event implements Serializable{
	protected List<Pair<String,Integer>> cs;
	public NewSetContClassEvent(int time, List<Pair<String,Integer>> cs){
		super(time);
		if (cs == null)
			throw new IllegalArgumentException("Invalid type: " + cs);
		else this.cs = cs;
		
}
	
	@Override
	void execute(RoadMap map) {
		for(Pair<String,Integer> c : cs) {
			try {
				map.getVehicle(c.getFirst()).setContaminationClass(c.getSecond());
			} catch (IncorrectValue e) {
				e.printStackTrace();
			}
		}	
	}
	public String toString() {
		String str = "[";
		if(cs != null) {
			for(int i = 0; i < cs.size(); i++) {
				if(i == 0) str = str + "(" + cs.get(i).getFirst() + "," + cs.get(i).getSecond() + ")";
				else str = str + ",(" + cs.get(i).getFirst() + "," + cs.get(i).getSecond() + ")";
			}
	}
		return "Change CO2 class: "+ str + "]";
	}
}

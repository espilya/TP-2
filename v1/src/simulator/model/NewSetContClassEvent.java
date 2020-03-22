package simulator.model;

import java.util.List;

import exceptions.ValueParseException;
import simulator.misc.Pair;
//TO DO
public class NewSetContClassEvent extends Event{
	
	private List<Pair<String,Integer>> cs;
	
	public NewSetContClassEvent(int time, List<Pair<String, Integer>> cs) {
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
				} catch (ValueParseException e) {
					e.printStackTrace();
				}
		}
	}
	
}

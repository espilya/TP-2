package simulator.model;

import java.util.List;

import simulator.exceptions.IncorrectVariableValueException;
import simulator.misc.Pair;

//TO DO
public class NewSetContClassEvent extends Event {

	private List<Pair<String, Integer>> cs;

	public NewSetContClassEvent(int time, List<Pair<String, Integer>> cs) {
		super(time);
		if (cs == null)
			throw new IllegalArgumentException("Invalid type: " + cs);
		else
			this.cs = cs;
	}

	@Override
	void execute(RoadMap map) throws IncorrectVariableValueException {
		for (Pair<String, Integer> c : cs) {
//				try {
			map.getVehicle(c.getFirst()).setContaminationClass(c.getSecond());
//				} catch (IncorrectVariableValueException e) {
//					e.printStackTrace();
//				}
		}
	}

}

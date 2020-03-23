package simulator.model;

import java.util.List;

import simulator.exceptions.IncorrectVariableValueException;
import simulator.exceptions.NonExistingObjectException;
import simulator.misc.Pair;

// TO DO
public class SetContClassEvent extends Event {
	private List<Pair<String, Integer>> cs;

	public SetContClassEvent(int time, List<Pair<String, Integer>> cs) {
		super(time);
		if (cs.isEmpty() || cs == null)
			throw new IllegalArgumentException();
		else
			this.cs = cs;
	}

	@Override
	void execute(RoadMap map) throws NonExistingObjectException, IncorrectVariableValueException {
		for (Pair<String, Integer> p : cs) {
			Vehicle v = map.getVehicle(p.getFirst());

//			try {
			if (v == null)
				throw new NonExistingObjectException(
						"Vehicle " + p.getFirst() + " does not exist in vehicleMap in roadMap");
			v.setContaminationClass(p.getSecond());
//			} catch (IncorrectVariableValueException | NonExistingObjectException e) {
//				e.printStackTrace();
//			}
		}

	}

}
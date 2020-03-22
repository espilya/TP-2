package simulator.model;

import java.util.List;

import exceptions.ValueParseException;
import simulator.misc.Pair;

// TO DO
public class SetContClassEvent extends Event {
	private List<Pair<String, Integer>> cs;

	public SetContClassEvent(int time, List<Pair<String, Integer>> cs)  {
		super(time);
		if (cs.isEmpty() || cs == null)
			throw new IllegalArgumentException();
		else
			this.cs = cs;
	}

	@Override
	void execute(RoadMap map) {
		for (Pair<String, Integer> p : cs) {
			Vehicle v = map.getVehicle(p.getFirst());

			try {
				if (v == null)
					throw new Exception();
				v.setContaminationClass(p.getSecond());
			} catch (ValueParseException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

}
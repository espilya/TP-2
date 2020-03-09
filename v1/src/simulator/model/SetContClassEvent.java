package simulator.model;

import java.util.List;

import simulator.misc.Pair;
// TO DO
public class SetContClassEvent extends Event {
	private List<Pair<String, Integer>> cs;

	public SetContClassEvent(int time, List<Pair<String, Integer>> cs) {
		super(time);
		if(cs.isEmpty())
			throw cmtj;//exception here
		else
			this.cs = cs;
	}

	@Override
	void execute(RoadMap map) {
		// TODO Auto-generated method stub

	}

}
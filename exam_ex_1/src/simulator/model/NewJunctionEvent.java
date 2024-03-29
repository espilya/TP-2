package simulator.model;

import java.io.Serializable;

import simulator.exceptions.ExistingObjectException;
import simulator.exceptions.IncorrectVariableValueException;

public class NewJunctionEvent extends Event implements Serializable{
	private static final long serialVersionUID = 5763844814921122212L;

	private String id;
	private LightSwitchingStrategy lsStrategy;
	private DequeuingStrategy dqStrategy;
	private int xCoor;
	private int yCoor;

	public NewJunctionEvent(int time, String id, LightSwitchingStrategy lsStrategy, DequeuingStrategy dqStrategy,
			int xCoor, int yCoor) {
		super(time);
		this.id = id;
		this.lsStrategy = lsStrategy;
		this.dqStrategy = dqStrategy;
		this.xCoor = xCoor;
		this.yCoor = yCoor;
	}

	@Override
	void execute(RoadMap map) throws ExistingObjectException, IncorrectVariableValueException {
		Junction junc = new Junction(id, lsStrategy, dqStrategy, xCoor, yCoor);
		map.addJunction(junc);
	}

	@Override
	public String toString() {
		return ("New Junction: '" + id + "'");
	}

}

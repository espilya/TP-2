package simulator.model;

import java.io.Serializable;

import simulator.exceptions.ExistingObjectException;
import simulator.exceptions.IncorrectObjectException;
import simulator.exceptions.IncorrectVariableValueException;
import simulator.exceptions.NonExistingObjectException;

public abstract class NewRoadEvent extends Event implements Serializable{
	private static final long serialVersionUID = -1820713712661960889L;

	protected String id;
	protected String srcJun;
	protected String destJunc;
	protected int length;
	protected int co2Limit;
	protected int maxSpeed;
	protected Weather weather;
	protected RoadMap rMap;

	NewRoadEvent(int time, String id, String srcJun, String destJunc, int length, int co2Limit, int maxSpeed,
			Weather weather) {
		super(time);
		this.id = id;
		this.srcJun = srcJun;
		this.destJunc = destJunc;
		this.length = length;
		this.co2Limit = co2Limit;
		this.maxSpeed = maxSpeed;
		this.weather = weather;

	}

	void execute(RoadMap map) throws IncorrectObjectException, ExistingObjectException, NonExistingObjectException,
			IncorrectVariableValueException {
		rMap = map;
		Junction src = map.getJunction(srcJun);
		Junction dest = map.getJunction(destJunc);
		Road road = createRoadObject();
		map.getJunction(dest.getId()).addIncommingRoad(road);
		map.addRoad(road);
		map.getJunction(src.getId()).addOutGoingRoad(road);
	}

	abstract protected Road createRoadObject() throws IncorrectVariableValueException;

}

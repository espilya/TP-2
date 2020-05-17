package simulator.model;

import java.io.Serializable;

import simulator.exceptions.ExistingObjectException;
import simulator.exceptions.IncorrectObjectException;
import simulator.exceptions.IncorrectVariableValueException;
import simulator.exceptions.NonExistingObjectException;

public abstract class Event implements Comparable<Event>, Serializable {
	private static final long serialVersionUID = -2095739029638574261L;
	
	protected int _time;

	Event(int time) {
		if (time < 1)
			throw new IllegalArgumentException("Time must be positive (" + time + ")");
		else
			_time = time;
	}

	public int getTime() {
		return _time;
	}

	@Override
	public int compareTo(Event o) {
		if (this._time > o._time)
			return 1;
		else if (this._time < o._time)
			return -1;
		else
			return 0;
	}

	abstract void execute(RoadMap map) throws ExistingObjectException, IncorrectObjectException,
			NonExistingObjectException, IncorrectVariableValueException;
	
	public abstract String toString();
}

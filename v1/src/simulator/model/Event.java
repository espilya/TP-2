package simulator.model;

import simulator.exceptions.ExistingObjectException;
import simulator.exceptions.IncorrectObjectException;
import simulator.exceptions.IncorrectVariableValueException;
import simulator.exceptions.NonExistingObjectException;

public abstract class Event implements Comparable<Event> {

	protected int _time;

	Event(int time) {
		if (time < 1)
			throw new IllegalArgumentException("Time must be positive (" + time + ")");
		else
			_time = time;
	}

	int getTime() {
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
}

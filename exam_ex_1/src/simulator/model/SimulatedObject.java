package simulator.model;

import java.io.Serializable;

import org.json.JSONObject;

import simulator.exceptions.IncorrectVariableValueException;

public abstract class SimulatedObject  implements Serializable{
	private static final long serialVersionUID = 8939464864949307182L;
	
	protected String _id;

	SimulatedObject(String id) {
		_id = id;
	}

	public String getId() {
		return _id;
	}

	public String toString() {
		return _id;
	}

	abstract void advance(int time) throws IncorrectVariableValueException;

	abstract public JSONObject report();


}

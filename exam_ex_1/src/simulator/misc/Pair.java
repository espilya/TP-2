package simulator.misc;

import java.io.Serializable;

public class Pair<T1, T2> implements Serializable{
	private static final long serialVersionUID = -8234187009941969130L;
	
	private T1 _first;
	private T2 _second;

	public Pair(T1 first, T2 second) {
		_first = first;
		_second = second;
	}

	public T1 getFirst() {
		return _first;
	}

	public T2 getSecond() {
		return _second;
	}

}

package simulator.misc;

import java.io.Serializable;

public class Trio<T1, T2, T3> implements Serializable{
	private static final long serialVersionUID = 3485503707122216221L;
	
	private T1 _first;
	private T2 _second;
	private T3 _third;
//	private T4 _fourth;

	public Trio(T1 first, T2 second, T3 third) { //, T4 fourth
		_first = first;
		_second = second;
		_third = third;
//		_fourth = fourth;
	}

	public T1 getFirst() {
		return _first;
	}

	public T2 getSecond() {
		return _second;
	}
	
	public T3 getThird() {
		return _third;
	}

//	public T4 getFourth() {
//		return _fourth;
//	}

}

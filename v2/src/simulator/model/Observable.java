package simulator.model;

import java.io.Serializable;

public interface Observable<T> extends Serializable{
	void addObserver(T o);

	void removeObserver(T o);
}

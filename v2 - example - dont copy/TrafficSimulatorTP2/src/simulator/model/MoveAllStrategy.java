package simulator.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MoveAllStrategy implements DequeuingStrategy, Serializable{
	private List<Vehicle> lista = new ArrayList<Vehicle>();
	@Override
	public List<Vehicle> dequeue(List<Vehicle> q) {
		for(Vehicle v : q) {
			this.lista.add(v);
		}
		return lista;
	}
}

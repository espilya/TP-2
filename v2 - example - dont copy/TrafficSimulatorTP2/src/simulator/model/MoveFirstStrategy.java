package simulator.model;

import java.util.ArrayList;
import java.util.List;

public class MoveFirstStrategy implements DequeuingStrategy{
	private List<Vehicle> lista = new ArrayList<Vehicle>();
	@Override
	public List<Vehicle> dequeue(List<Vehicle> q) {
		if(!q.isEmpty()) {
		Vehicle v = q.get(0);
		this.lista.add(v);
		return lista;
		}
		else return null;
	}
}

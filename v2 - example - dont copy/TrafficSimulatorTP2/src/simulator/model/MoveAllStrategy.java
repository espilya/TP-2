package simulator.model;

import java.util.ArrayList;
import java.util.List;

public class MoveAllStrategy implements DequeuingStrategy{
	private List<Vehicle> lista = new ArrayList<Vehicle>();
	@Override
	public List<Vehicle> dequeue(List<Vehicle> q) {
		for(Vehicle v : q) {
			this.lista.add(v);
		}
		return lista;
	}
}

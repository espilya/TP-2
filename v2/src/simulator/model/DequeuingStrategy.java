package simulator.model;

import java.io.Serializable;
import java.util.List;

public interface DequeuingStrategy extends Serializable{
	List<Vehicle> dequeue(List<Vehicle> q);
}
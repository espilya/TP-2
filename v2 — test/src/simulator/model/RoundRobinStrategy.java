package simulator.model;

import java.io.Serializable;
import java.util.List;

public class RoundRobinStrategy implements LightSwitchingStrategy, Serializable {
	private static final long serialVersionUID = -4848749141407022396L;

	int timeSlot;

	public RoundRobinStrategy(int t) {
		timeSlot = t;
	}

	public int chooseNextGreen(List<Road> roads, List<List<Vehicle>> qs, int currGreen, int lastSwitchingTime,
			int currTime) {
		if (roads.size() == 0) {
			return -1;
		} else {
			if (currGreen == -1)
				return 0;

			if (currTime - lastSwitchingTime < timeSlot)
				return currGreen;
			return (currGreen + 1) % roads.size();
		}
	}
}

package simulator.model;

import java.util.List;

public class MostCrowdedStrategy implements LightSwitchingStrategy {
	int timeSlot;

	public MostCrowdedStrategy(int t) {
		timeSlot = t;
	}

	@Override
	public int chooseNextGreen(List<Road> roads, List<List<Vehicle>> qs, int currGreen, int lastSwitchingTime,
			int currTime) {

		if (roads.size() == 0) {
			return -1;
		} else {
			if (currGreen == -1) {
				int max = 0;
				int ind = 0;
				int index = 0;
				for (List<Vehicle> i : qs) {
					if (i.size() > max) {
						max = i.size();
						index = ind;
					}
					ind++;
				}
				return index;
			}
			if (currTime - lastSwitchingTime < timeSlot)
				return currGreen;

			int i = currGreen + 1;
			int max = 0;
			int index = 0;
			while (i != currGreen) {
				if (qs.get(i).size() > max) {
					index = i;
					max = qs.get(i).size();
				}
				i++;
				if (i >= qs.size())
					i = 0;
			}
			return index;
		}
	}

}

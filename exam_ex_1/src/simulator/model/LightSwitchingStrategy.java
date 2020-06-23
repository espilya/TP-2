package simulator.model;

import java.io.Serializable;
import java.util.List;

public interface LightSwitchingStrategy extends Serializable {
	int chooseNextGreen(List<Road> roads, List<List<Vehicle>> qs, int currGreen, int lastSwitchingTime, int currTime);
}
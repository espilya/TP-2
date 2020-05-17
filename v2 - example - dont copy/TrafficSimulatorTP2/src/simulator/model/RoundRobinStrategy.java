package simulator.model;

import java.io.Serializable;
import java.util.List;

public class RoundRobinStrategy implements LightSwitchingStrategy, Serializable {
	protected int timeAct;
	private int roadIndex;
	public RoundRobinStrategy (int timeSlot){
		this.timeAct = timeSlot;
	}
	
	@Override
	public int chooseNextGreen(List<Road> roads, List<List<Vehicle>> qs, 
			int currGreen, int lastSwitchingTime,int currTime) {
		if(roads.size() == 0) roadIndex = -1;
		else if(currGreen == -1) roadIndex = 0;
		else if(currTime-lastSwitchingTime < timeAct) roadIndex = currGreen;
		else roadIndex = (currGreen +1) % roads.size();
		return roadIndex;
	}

	
}

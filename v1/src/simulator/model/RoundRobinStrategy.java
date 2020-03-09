package simulator.model;

import java.util.List;

public class RoundRobinStrategy implements LightSwitchingStrategy{
	
	int timeSlot;
	
	public RoundRobinStrategy(int t){
		timeSlot=t;
	}
	

	@Override
	public int chooseNextGreen(List<Road> roads, List<List<Vehicle>> qs, 
			int currGreen, int lastSwitchingTime,
			int currTime) {
		
		if(roads.size() == 0) {
			return -1;
		}
		else {
			if(currGreen==-1)
				return 0;
			
			if(currTime-lastSwitchingTime <timeSlot)
				return currGreen;
			
			return (currGreen+1)%roads.size();
		}
	}
}

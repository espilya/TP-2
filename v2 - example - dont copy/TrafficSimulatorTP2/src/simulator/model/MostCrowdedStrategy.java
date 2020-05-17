package simulator.model;

import java.io.Serializable;
import java.util.List;

public class MostCrowdedStrategy implements LightSwitchingStrategy, Serializable{
	private int timeAct;
	private int roadIndex;
	private int maxCola = -1;
	public MostCrowdedStrategy(int timeSlot){
		this.timeAct = timeSlot;
		}
	
	@Override
	public int chooseNextGreen(List<Road> roads, List<List<Vehicle>> qs,
			int currGreen, int lastSwitchingTime,int currTime) {
		if(roads.size() == 0) roadIndex = -1;
		else if(currGreen == -1) {
			for(int i = 0; i < qs.size(); i++) {
				if(qs.get(i).size() > maxCola) {
					maxCola = qs.get(i).size();
					roadIndex = i;
					}
				}	
		}
		else if(currTime-lastSwitchingTime < timeAct) roadIndex = currGreen;
		else {
			int i = (currGreen +1) % roads.size();
			List<Vehicle> listV = qs.get(i);
	        do {
	        	if (qs.get(i).size() > maxCola) {
	        		maxCola = qs.get(i).size()-1;
				roadIndex = i;
				}
	        	else {listV = qs.get(i++);}
	        }
	         while(listV != qs.get((currGreen +1) % roads.size()));
	        } 
		 return roadIndex;
}
}

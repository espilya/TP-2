package simulator.model;



import java.util.Collections;
import java.util.List;

import org.json.JSONObject;

import exceptions.ValueParseException;


public class Vehicle extends SimulatedObject{
	
	String id;
	private List<Junction> iter;
	private int maxSpeed;
	private int actualSpeed;
	private VehicleStatus state;
	private Road actualRoad;
	private int localization;		
	private int contClass; //0-10
	private int totalCont;
	private int totalDistance;
	
	Vehicle(String id, int maxSpeed, int contClass, List<Junction> itinerary) throws ValueParseException {
			super(id);
			this.id = id;
			this.maxSpeed=maxSpeed;
			this.contClass=contClass;
			iter = Collections.unmodifiableList(itinerary);
			
			if(maxSpeed < 0)
				throw new ValueParseException("Negative value for maxSpeed");
			if(contClass < 0 || contClass > 10)
				throw new ValueParseException("Incorrect value for contamination class");
			if(itinerary.size() < 2)
				throw new ValueParseException("Incorrect value for ");
			if(iter.size() <2) {
				throw new IllegalArgumentException("List<Junction> itinerary size must not be less than 2(" + iter.size() + ")");
			}

			
			}
	

	
	public int getLocation() {
		return localization;
	}
	
	 public int getSpeed() {
		return actualSpeed;
	}
	
	 public int getContClass() {
		return contClass;
	}
	
	public VehicleStatus getStatus() {
		return state;
	}
	
	public List<Junction> getItinerary() {
		return iter;
	}
	
	public Road getRoad() {
		return actualRoad;
	}
	

	
	public void setSpeed(int s) throws ValueParseException {
	if(maxSpeed > s)
		if(s<0)
			throw new IllegalArgumentException("Negative value for speed");
		else
			actualSpeed = s;
	else
		actualSpeed = maxSpeed;
	} 
	
	public void setContaminationClass(int c) throws ValueParseException { 
		if(c<0 || c>10)
			throw new ValueParseException("Incorrect value for contamination class");
		contClass = c;	
	}
	
	public void advance(int time) { 
		
		if(state.equals(VehicleStatus.TRAVELING)) {
			//A+
			int a = localization + actualSpeed;
			int b = actualRoad.getLength();
			int oldLoc = localization;
			if(a>b)
				localization = b;
			else
				localization = a;
			
			
			//B+
			int c = (contClass * (localization-oldLoc))/10;
			totalCont += c;

			actualRoad.addContamination(c);
		


			
			//C+ 
			if(localization >= actualRoad.getLength()) {
				actualRoad.getDestination().enter(this);  //not sure
			}
			
			
		}
		else {
			actualSpeed = 0;
		}

	}
	
	
	public void moveToNextRoad() {
		
		
		actualRoad.exit(this);
		localization=0;
		if(state.equals(VehicleStatus.PENDING)){
			//Road nextRoad = iter.get(0).roadTo(j);
		}
		else {
			
		}
	
	
	public JSONObject report() {
		
		return new JSONObject();
	}
	
	

}

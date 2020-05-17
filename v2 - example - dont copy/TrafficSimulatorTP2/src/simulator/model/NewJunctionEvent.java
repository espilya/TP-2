package simulator.model;

import java.io.Serializable;

public class NewJunctionEvent extends Event implements Serializable{
	protected String id;
	protected LightSwitchingStrategy lsStrategy;
	protected DequeuingStrategy dqStrategy;
	protected int x, y;
	
	public NewJunctionEvent(int time, String id, LightSwitchingStrategy
			lsStrategy, DequeuingStrategy dqStrategy, int xCoor, int yCoor) {
			super(time);
			this.id =id;
			this.lsStrategy = lsStrategy;
			this.dqStrategy = dqStrategy;
			this.x =xCoor; this.y = yCoor;
			}

	@Override
	void execute(RoadMap map) {
		try {
			Junction j = new Junction(this.id, this.lsStrategy, this.dqStrategy, this.x, this.y);
			map.addJunction(j);
			//System.out.println(map.getJunctions());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	public String toString() {
		return "New Junction '"+ this.id +"'";
	}
}

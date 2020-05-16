package simulator.view;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.Junction;
import simulator.model.Road;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

public class JunctionsTableModel extends TableModel<Junction>{

	static private final String[] JUNCTIONCOLUMN = {"Id", "Green", "Queues"};
	
	public JunctionsTableModel(Controller ctrl) {
		super(JUNCTIONCOLUMN, ctrl);
	}

	public Object getValueAt(int fil, int col) {
		Object s = null;
		Junction j = this.lista.get(fil);
		switch(col) {
			case 0 : s = j.getId(); break;
			case 1 : s = (j.getGreenLightIndex() == -1) ? "NONE" : j.getInRoads().get(j.getGreenLightIndex()); break;	
			case 2 : s = j.getInRoads(); break;	
			default : assert (false);
		}
		return s;
	}
	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		lista = map.getJunctions();
		fireTableStructureChanged();
		
	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		lista = map.getJunctions();
		fireTableStructureChanged();
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		lista = map.getJunctions();
		fireTableStructureChanged();
	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
	}

	@Override
	public void onError(String err) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onLoad(RoadMap map, List<Event> events, int time) {
		lista = map.getJunctions();
		fireTableStructureChanged();
	}

}
package simulator.view;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.Road;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;
import simulator.model.Vehicle;

public class RoadsTableModel extends TableModel<Road>{

	static private final String[] ROADCOLUMN = {"Id", "Length", "Weather", 
			"Max.Speed", "Speed Limit", "Total CO2", "CO2 Limit"};
	
	public RoadsTableModel(Controller ctrl) {
		super(ROADCOLUMN, ctrl);
	}

	public Object getValueAt(int fil, int col) {
		Object s = null;
		Road r = this.lista.get(fil);
		switch(col) {
			case 0 : s = r.getId(); break;
			case 1 : s = r.getLongitud(); break;
			case 2 : s = r.getWeather(); break;
			case 3 : s = r.getVelMax(); break;
			case 4 : s = r.getVelMax(); break;
			case 5 : s = r.getTotalCO2(); break;
			case 6 : s = r.getAlarContExcesiva(); break;
			default : assert (false);
		}
		return s;
	}
	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		lista = map.getRoads();
		fireTableStructureChanged();
		
	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		lista = map.getRoads();
		fireTableStructureChanged();
		
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		lista = map.getRoads();
		fireTableStructureChanged();
	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onError(String err) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onLoad(RoadMap map, List<Event> events, int time) {
		lista = map.getRoads();
		fireTableStructureChanged();
	}

}
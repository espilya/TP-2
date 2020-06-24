package simulator.view.tables;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.misc.Pair;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;
import simulator.model.Vehicle;

public class VehiclesSpeedHistoryTableModel extends AbstractTableModel implements TrafficSimObserver {
	private static final long serialVersionUID = -5601258086388080773L;
	
	static private final String[] _colNames = { "Tick", "Vehicles" };
	
	
	private int _time;
	private int _speedLimit;
	private List<Pair<Integer, List<Vehicle>>> _rows;
	
	
	public VehiclesSpeedHistoryTableModel(Controller ctrl) {
		ctrl.addObserver(this);
		_speedLimit = 70;
		_rows = new ArrayList<>();
		fireTableStructureChanged();
	}
	
	public void updateTable(int speedLimit) {
		_speedLimit = speedLimit;
		fireTableStructureChanged();
	}
	

	@Override
	// returns the value of a particular cell
	public Object getValueAt(int rowIndex, int columnIndex) {
		Pair<Integer, List<Vehicle>> data = _rows.get(rowIndex);

		Object s = null;
		switch (columnIndex) {
		case 0:
			s = data.getFirst();
			break;
		case 1:
			s = data.getSecond();
			break;
		}
		return s;
	}
	
	private List<Vehicle> getVehiclesFasterThanLimit(RoadMap map){
		List<Vehicle> vehicleList = new ArrayList<Vehicle>();
		for(Vehicle e : map.getVehicles()) {
			if(e.getSpeed() > _speedLimit) {
				vehicleList.add(e);
			}
		}
		return vehicleList;
	}

	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		//fireTableStructureChanged();
	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		_time = time;
		_rows.add(new Pair<Integer, List<Vehicle>>(_time, getVehiclesFasterThanLimit(map)));
		fireTableStructureChanged();
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		fireTableStructureChanged();
	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		_time = 0;
		_rows = new ArrayList<>();
		fireTableStructureChanged();
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		fireTableStructureChanged();
	}

	@Override
	public void onError(String err) {

	}

	@Override
	public void onLoad(RoadMap map, List<Event> events, int time) {
		fireTableStructureChanged();
	}

	@Override
	public String getColumnName(int col) {
		return _colNames[col];
	}

	@Override
	public int getColumnCount() {
		return _colNames.length;
	}

	@Override
	public int getRowCount() {
		return _rows.size();
	}



}

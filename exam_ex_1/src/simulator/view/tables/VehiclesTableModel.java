package simulator.view.tables;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;
import simulator.model.Vehicle;

public class VehiclesTableModel extends AbstractTableModel implements TrafficSimObserver {
	private static final long serialVersionUID = 4907758801698460961L;

	static private final String[] _colNames = { "Id", "Location", "Iterinary", "CO2 Class", "Max. Speed", "Speed",
			"Total CO2", "Distance" };

	public VehiclesTableModel(Controller _ctrl) {
		_ctrl.addObserver(this);
		_list = new ArrayList<>();
		fireTableStructureChanged();
	}

	protected List<Vehicle> _list;

	@Override
	// returns the value of a particular cell
	public Object getValueAt(int rowIndex, int columnIndex) {

		Vehicle veh = (Vehicle) _list.get(rowIndex);
		Object s = null;
		switch (columnIndex) {
		case 0:
			s = veh.getId();
			break;
		case 1:
			s = veh.getLocation();
			break;
		case 2:
			s = veh.getItinerary();
			break;
		case 3:
			s = veh.getContClass();
			break;
		case 4:
			s = veh.getMaxSpeed();
			break;

		case 5:
			s = veh.getSpeed();
			break;
		case 6:
			s = veh.getTotalCont();
			break;
		case 7:
			s = veh.getTotalDist();
			break;
		}
		return s;
	}

	@Override
	public void onAdvanceStart(RoadMap map​, List<Event> events, int time​) {
		_list = map​.getVehicles();
		fireTableStructureChanged();
	}

	@Override
	public void onAdvanceEnd(RoadMap map​, List<Event> events, int time​) {
		_list = map​.getVehicles();
		fireTableStructureChanged();
	}

	@Override
	public void onEventAdded(RoadMap map​, List<Event> events, Event e, int time​) {
		_list = map​.getVehicles();
		fireTableStructureChanged();
	}

	@Override
	public void onReset(RoadMap map​, List<Event> events​, int time​) {
		_list = map​.getVehicles();
		fireTableStructureChanged();
	}

	@Override
	public void onRegister(RoadMap map​, List<Event> events​, int time​) {
		_list = map​.getVehicles();
		fireTableStructureChanged();
	}

	@Override
	public void onError(String err​) {
	}

	@Override
	public void onLoad(RoadMap map, List<Event> events, int time) {
		_list = map.getVehicles();
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
		return (_list == null ? 0 : _list.size());
	}

	public void setEventsList(List<Vehicle> events) {
		_list = events;
		fireTableStructureChanged();
	}

}

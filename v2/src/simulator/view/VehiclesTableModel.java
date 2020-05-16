package simulator.view;

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

	private List<Vehicle> _vehicles;
	private final String[] _colNames = { "Id", "Location", "Iterinary", "CO2 Class", "Max. Speed", "Speed", "Total CO2",
			"Distance" };

	public VehiclesTableModel(Controller _ctrl) {
		_vehicles = new ArrayList<Vehicle>();
		_ctrl.addObserver(this);
	}

	public void update() {
		// We need to notify changes, otherwise the table does not refresh.
		fireTableDataChanged();
	}

	public void setEventsList(List<Vehicle> vehicles) {
		_vehicles = vehicles;
		update();
	}

	@Override
	public boolean isCellEditable(int row, int column) {
		return false;
	}

	// this is for the column header
	@Override
	public String getColumnName(int col) {
		return _colNames[col];
	}

	@Override
	// this is for the number of columns
	public int getColumnCount() {
		return _colNames.length;
	}

	@Override
	// the number of row, like those in the events list
	public int getRowCount() {
		return _vehicles == null ? 0 : _vehicles.size();
	}

	@Override
	// returns the value of a particular cell
	public Object getValueAt(int rowIndex, int columnIndex) {

		// TODO
		Vehicle veh = _vehicles.get(rowIndex);
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

	public void onAdvanceStart(RoadMap map​, List<Event> events​, int time​) {
		_vehicles = map​.getVehicles();
		fireTableStructureChanged();
	}

	public void onAdvanceEnd(RoadMap map​, List<Event> events​, int time​) {
		_vehicles = map​.getVehicles();
		fireTableStructureChanged();
	}

	public void onEventAdded(RoadMap map​, List<Event> events​, Event e, int time​) {
		_vehicles = map​.getVehicles();
		fireTableStructureChanged();
	}

	public void onReset(RoadMap map​, List<Event> events​, int time​) {
		_vehicles = map​.getVehicles();
		fireTableStructureChanged();
	}

	public void onRegister(RoadMap map​, List<Event> events​, int time​) {
		_vehicles = map​.getVehicles();
		fireTableStructureChanged();
	}

	public void onError(String err​) {

	}

	@Override
	public void onLoad(RoadMap map, List<Event> events, int time) {
		_vehicles.clear();
		fireTableStructureChanged();

	}

}
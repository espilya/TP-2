package simulator.view.tables;

import java.util.List;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.Vehicle;

public class VehiclesTableModel extends TableBase<Vehicle> {
	private static final long serialVersionUID = 4907758801698460961L;
	
	static private final String[] colNames ={ "Id", "Location", "Iterinary", "CO2 Class", "Max. Speed", "Speed", "Total CO2",
	"Distance" };
	
	public VehiclesTableModel(Controller _ctrl) {
		super(colNames, _ctrl);
	}

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
	public void onUndo(RoadMap map, List<Event> events, int time) {
		_list = map.getVehicles();
		fireTableStructureChanged();

	}
}

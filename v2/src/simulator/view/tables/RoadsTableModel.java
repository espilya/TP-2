package simulator.view.tables;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.Road;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

public class RoadsTableModel extends AbstractTableModel implements TrafficSimObserver {
	private static final long serialVersionUID = -181580670234522327L;

	protected List<Road> _list;

	static private final String[] _colNames = { "Id", "Length", "Weather", "Max.Speed", "Speed Limit", "Total CO2",
			"CO2 Limit" };

	public RoadsTableModel(Controller _ctrl) {
		_ctrl.addObserver(this);
		_list = new ArrayList<>();
		fireTableStructureChanged();
	}

	@Override
	// returns the value of a particular cell
	public Object getValueAt(int rowIndex, int columnIndex) {
		Road road = _list.get(rowIndex);

		Object s = null;
		switch (columnIndex) {
		case 0:
			s = road.getId();
			break;
		case 1:
			s = road.getLength();
			break;
		case 2:
			s = road.getWeather();
			break;
		case 3:
			s = road.getMaxSpeed();
			break;
		case 4:
			s = road.getSpeedLimit();
			break;
		case 5:
			s = road.getContTotal();
			break;
		case 6:
			s = road.getContLimit();
			break;
		}
		return s;
	}

	@Override
	public void onAdvanceStart(RoadMap map​, List<Event> events​, int time​) {
		_list = map​.getRoads();
		fireTableStructureChanged();
	}

	@Override
	public void onAdvanceEnd(RoadMap map​, List<Event> events​, int time​) {
		_list = map​.getRoads();
		fireTableStructureChanged();
	}

	@Override
	public void onEventAdded(RoadMap map​, List<Event> events​, Event e, int time​) {
		_list = map​.getRoads();
		fireTableStructureChanged();
	}

	@Override
	public void onReset(RoadMap map​, List<Event> events​, int time​) {
		_list = map​.getRoads();
		fireTableStructureChanged();
	}

	@Override
	public void onRegister(RoadMap map​, List<Event> events​, int time​) {
		_list = map​.getRoads();
		fireTableStructureChanged();
	}

	@Override
	public void onError(String err​) {

	}

	@Override
	public void onLoad(RoadMap map, List<Event> events, int time) {
		_list = map.getRoads();
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

	public void setEventsList(List<Road> events) {
		_list = events;
		fireTableStructureChanged();
	}

}

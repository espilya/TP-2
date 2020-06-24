package simulator.view.tables;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.misc.Pair;
import simulator.model.Event;
import simulator.model.Junction;
import simulator.model.Road;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

public class GreenRoadsTableModel extends AbstractTableModel implements TrafficSimObserver {
	private static final long serialVersionUID = -181580670234522327L;

	protected List<Road> _roadList;
	private int _time;
	private List<Pair<Integer, String>> _rows;

	static private final String[] _colNames = { "Time", "Roads w/ green light" };

	public GreenRoadsTableModel(Controller _ctrl) {
		_ctrl.addObserver(this);
		_roadList = new ArrayList<>();
		_rows = new ArrayList<>();
		fireTableStructureChanged();
		_time = 0;
	}

	@Override
	// returns the value of a particular cell
	public Object getValueAt(int rowIndex, int columnIndex) {
		Object s = null;
		if (_rows.size() != 0) {
			Pair<Integer, String> par = _rows.get(_rows.size()-1-rowIndex);
			switch (columnIndex) {
			case 0:
				s = par.getFirst();
				break;
			case 1:
				s = par.getSecond();
				break;
			}
		}
		return s;
	}

	private String getGreenLightRoads() {
		String data = "";
		for (Road e : _roadList) {
			if (e.getGreenLight()) {
				data += (e.getId() + " ");
			}
		}
		return data;
	}

	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		//_time = time;
		//_rows.add(new Pair<Integer, String>(_time, getGreenLightRoads()));
		_roadList = map.getRoads();
		fireTableStructureChanged();
	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		_time = time;
		_rows.add(new Pair<Integer, String>(_time, getGreenLightRoads()));
		_roadList = map.getRoads();
		fireTableStructureChanged();
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		_roadList = map.getRoads();
		fireTableStructureChanged();
	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		_roadList = map.getRoads();
		_time = 0;
		_rows = new ArrayList<>();
		fireTableStructureChanged();
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		_roadList = map.getRoads();
		fireTableStructureChanged();
	}

	@Override
	public void onError(String err) {

	}

	@Override
	public void onLoad(RoadMap map, List<Event> events, int time) {
		_roadList = map.getRoads();
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

	public void setEventsList(List<Road> events) {
		_roadList = events;
		fireTableStructureChanged();
	}

}

package simulator.view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

public class EventsTableModel extends AbstractTableModel implements TrafficSimObserver {

	private static final long serialVersionUID = 1L;

	private List<Event> _events;
	private String[] _colNames = { "Time", "Desc." };

	public EventsTableModel(Controller _ctrl) {
		_events = new ArrayList<Event>();
		_ctrl.addObserver(this);
	}

	public void update() {
		fireTableDataChanged();

	}

	public void setEventsList(List<Event> events) {
		_events = events;
		update();
	}

	@Override
	public boolean isCellEditable(int row, int column) {
		return false;
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
		return _events == null ? 0 : _events.size();
	}

	@Override
	public String getValueAt(int rowIndex, int columnIndex) {
		String s = null;
		Event event = _events.get(rowIndex);
		switch (columnIndex) {
		case 0:
			s = String.valueOf(event.getTime());
			break;
		case 1:
			s = event.toString();
			break;
		}
		return s;
	}

	public void onAdvanceStart(RoadMap map​, List<Event> events, int time​) {
		_events = events;
		fireTableStructureChanged();
	}

	public void onAdvanceEnd(RoadMap map​, List<Event> events, int time​) {
		_events = events;
		fireTableStructureChanged();
	}

	public void onEventAdded(RoadMap map​, List<Event> events, Event e, int time​) {
		_events = events;
		fireTableStructureChanged();
	}

	public void onReset(RoadMap map​, List<Event> events​, int time​) {
		_events = events​;
		fireTableStructureChanged();
	}

	public void onRegister(RoadMap map​, List<Event> events​, int time​) {
		_events = events​;
		fireTableStructureChanged();
	}

	public void onError(String err​) {

	}

	@Override
	public void onLoad(RoadMap map, List<Event> events, int time) {
		_events = events;
		fireTableStructureChanged();
	}

	@Override
	public void onUndo(RoadMap map, List<Event> events, int time) {
		_events = events;
		fireTableStructureChanged();
		
	}

}

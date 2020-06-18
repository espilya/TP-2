package simulator.view.tables;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.Junction;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

public class JunctionsTableModel extends AbstractTableModel implements TrafficSimObserver {
	private static final long serialVersionUID = 2032952707334211174L;

	protected List<Junction> _list;

	static private final String[] _colNames = { "Id", "Green", "Queues" };

	public JunctionsTableModel(Controller _ctrl) {
		_ctrl.addObserver(this);
		_list = new ArrayList<>();
		fireTableStructureChanged();
	}

	@Override
	// returns the value of a particular cell
	public String getValueAt(int rowIndex, int columnIndex) {

		Junction junc = _list.get(rowIndex);
		String s = null;
		switch (columnIndex) {
		case 0:
			s = _list.get(rowIndex).getId();
			break;
		case 1:
			s = ((junc.getGreenLightIndex() == -1) ? "NONE" : junc.getInRoads().get(junc.getGreenLightIndex()))
					.toString();
			break;
		case 2:
			s = junc.getQueues();
			break;
		}
		return s;
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onAdvanceStart(RoadMap map​, List<Event> events​, int time​) {
		_list = map​.getJunctions();
		fireTableStructureChanged();
	}

	@Override
	public void onAdvanceEnd(RoadMap map​, List<Event> events​, int time​) {
		_list = map​.getJunctions();
		fireTableStructureChanged();
	}

	@Override
	public void onEventAdded(RoadMap map​, List<Event> events​, Event e, int time​) {
		_list = map​.getJunctions();
		fireTableStructureChanged();
	}

	@Override
	public void onReset(RoadMap map​, List<Event> events​, int time​) {
		_list = map​.getJunctions();
		fireTableStructureChanged();
	}

	@Override
	public void onRegister(RoadMap map​, List<Event> events​, int time​) {
		_list = map​.getJunctions();
		fireTableStructureChanged();
	}

	@Override
	public void onError(String err​) {

	}

	@Override
	public void onLoad(RoadMap map, List<Event> events, int time) {
		_list = map.getJunctions();
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

	public void setEventsList(List<Junction> events) {
		_list = events;
		fireTableStructureChanged();
	}

}

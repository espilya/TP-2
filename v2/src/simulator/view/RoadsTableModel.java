package simulator.view;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.Road;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

public class RoadsTableModel extends AbstractTableModel implements TrafficSimObserver {
	private static final long serialVersionUID = -181580670234522327L;
	
	private List<Road> _roads;
	private final String[] _colNames = { "Id", "Length", "Weather", "Max.Speed", "Speed Limit", "Total CO2",
			"CO2 Limit" };

	public RoadsTableModel(Controller _ctrl) {
		// TODO Auto-generated constructor stub
	}

	public void update() {
		// We need to notify changes, otherwise the table does not refresh.
		fireTableDataChanged();
	}

	public void setEventsList(List<Road> roads) {
		_roads = roads;
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
		return _roads == null ? 0 : _roads.size();
	}

	@Override
	// returns the value of a particular cell
	public Object getValueAt(int rowIndex, int columnIndex) {
		return 0;
		// TODO
//		Object s = null;
//		switch (columnIndex) {
//		case 0:
//			s = rowIndex;
//			break;
//		case 1:
//			s = _junctions.get(rowIndex).getTime();
//			break;
//		case 2:
//			s = _junctions.get(rowIndex).getPriority();
//			break;
//		}
//		return s;
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub

	}

	public void onAdvanceStart(RoadMap map​, List<Event> events​, int time​) {
		// TODO
	}

	public void onAdvanceEnd(RoadMap map​, List<Event> events​, int time​) {

	}

	public void onEventAdded(RoadMap map​, List<Event> events​, Event e, int time​) {

	}

	public void onReset(RoadMap map​, List<Event> events​, int time​) {

	}

	public void onRegister(RoadMap map​, List<Event> events​, int time​) {

	}

	public void onError(String err​) {

	}

	@Override
	public void onLoad(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		
	}

}

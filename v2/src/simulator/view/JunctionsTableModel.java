package simulator.view;

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

	private List<Junction> _junctions;
	private final String[] _colNames = {"Id", "Green", "Queues"};

	public JunctionsTableModel(Controller _ctrl) {
		_junctions = new ArrayList<Junction>();
		_ctrl.addObserver(this);
	}



	public void update() {
		// We need to notify changes, otherwise the table does not refresh.
		fireTableDataChanged();
	}

	public void setEventsList(List<Junction> junctions) {
		_junctions = junctions;
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
		return _junctions == null ? 0 : _junctions.size();
	}

	@Override
	// returns the value of a particular cell
	public Object getValueAt(int rowIndex, int columnIndex) {

		Junction junc = _junctions.get(rowIndex);
		Object s = null;
		switch (columnIndex) {
		case 0:
			s = _junctions.get(rowIndex).getId();
			break;
		case 1:
			s = (junc.getGreenLightIndex() == -1) ? "NONE" : junc.getInRoads().get(junc.getGreenLightIndex());;
			break;
		case 2:
			s = junc.getInRoads();
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
		_junctions = map​.getJunctions();
		fireTableStructureChanged();
	}
	@Override
	public void onAdvanceEnd(RoadMap map​, List<Event> events​, int time​) {
		_junctions = map​.getJunctions();
		fireTableStructureChanged();
	}
	@Override
	public void onEventAdded(RoadMap map​, List<Event> events​, Event e, int time​) {
		_junctions = map​.getJunctions();
		fireTableStructureChanged();
	}
	@Override
	public void onReset(RoadMap map​, List<Event> events​, int time​) {

	}
	@Override
	public void onRegister(RoadMap map​, List<Event> events​, int time​) {

	}
	@Override
	public void onError(String err​) {

	}


	@Override
	public void onLoad(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		
	}

}

package simulator.view.tables;

import java.util.List;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.Junction;
import simulator.model.RoadMap;

public class JunctionsTableModel extends TableBase<Junction> {
	private static final long serialVersionUID = 2032952707334211174L;

	static private final String[] colNames ={ "Id", "Green", "Queues" };

	public JunctionsTableModel(Controller _ctrl) {
		super(colNames, _ctrl);
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
	public void onUndo(RoadMap map, List<Event> events, int time) {
		_list = map.getJunctions();
		fireTableStructureChanged();
	}

}

package simulator.view.tables;

import java.util.List;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;

public class EventsTableModel extends TableBase<Event> {
	private static final long serialVersionUID = 1L;
	
	static private final String[] colNames = {"Time", "Desc.",};

	public EventsTableModel(Controller ctrl) {
		super(colNames, ctrl);
	}

	@Override
	public String getValueAt(int rowIndex, int columnIndex) {
		String s = null;
		Event event = (Event) _list.get(rowIndex);
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

	@Override
	public void onAdvanceStart(RoadMap map​, List<Event> events, int time​) {
		_list = events;
		fireTableStructureChanged();
	}

	@Override
	public void onAdvanceEnd(RoadMap map​, List<Event> events, int time​) {
		_list = events;
		fireTableStructureChanged();
	}

	@Override
	public void onEventAdded(RoadMap map​, List<Event> events, Event e, int time​) {
		_list = events;
		fireTableStructureChanged();
	}

	@Override
	public void onReset(RoadMap map​, List<Event> events​, int time​) {
		_list = events​;
		fireTableStructureChanged();
	}

	@Override
	public void onRegister(RoadMap map​, List<Event> events​, int time​) {
		_list = events​;
		fireTableStructureChanged();
	}

	@Override
	public void onError(String err​) {
	}

	@Override
	public void onLoad(RoadMap map, List<Event> events, int time) {
		_list = events;
		fireTableStructureChanged();
	}

}

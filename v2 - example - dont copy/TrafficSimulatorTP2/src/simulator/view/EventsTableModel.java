package simulator.view;


import java.util.List;



import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;

public class EventsTableModel extends TableModel<Event>{
	static private final String[] EVENTCOLUMN = {"Time", "Desc."};
	
	public EventsTableModel(Controller ctrl) {
		super(EVENTCOLUMN, ctrl);
	}
	
	public Object getValueAt(int fil, int col) {
		Object s = null;
		Event e = this.lista.get(fil);
		switch(col) {
			case 0 : s = e.getTime(); break;
			case 1 : s = e.toString(); break;
			default : assert (false);
		}
		return s;
	}

	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		lista.clear();
		for(int i = 0; i < events.size(); ++i)
			if(events.get(i).getTime() > time)
				lista.add(events.get(i));
		
		fireTableStructureChanged();
		
	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		if(!events.isEmpty()) {
			for(int i = 0; i < events.size(); i++) {
				if(!lista.contains(events.get(i)))
					lista.add(events.get(i));
			}			
		}
		fireTableStructureChanged();
	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		lista.clear();
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		if(!events.isEmpty()) {
			for(int i = 0; i < events.size(); i++) {
				if(!lista.contains(events.get(i)))
					lista.add(events.get(i));
			}			
		}
		fireTableStructureChanged();
	}

	@Override
	public void onError(String err) {
	}

	@Override
	public void onLoad(RoadMap map, List<Event> events, int time) {
		if(!events.isEmpty()) {
			for(int i = 0; i < events.size(); i++) {
				if(!lista.contains(events.get(i)))
					lista.add(events.get(i));
			}			
		}
		fireTableStructureChanged();
	}

}

package simulator.view;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;
import simulator.model.Vehicle;
import simulator.model.VehicleStatus;

public class VehiclesTableModel extends TableModel<Vehicle> {

	static private final String[] VEHICLECOLUMN = {"Id", "Location", "Iterinary",
			"CO2 Class", "Max. Speed", "Speed", "Total CO2", "Distance"};
	
	public VehiclesTableModel(Controller ctrl) {
		super(VEHICLECOLUMN, ctrl);
	}

	public Object getValueAt(int fil, int col) {
		Object s = null;
		Vehicle v = this.lista.get(fil);
		switch(col) {
			case 0 : s = v.getId(); break;
			case 1 : if(v.getEstado() == VehicleStatus.PENDING) s = "Pending";
			else if(v.getEstado() == VehicleStatus.TRAVELING) {
				s = v.getCarretera() + ":" + v.getLocalizacion();
			}
			else if(v.getEstado() == VehicleStatus.WAITING) {
				s = "Waiting:" + v.getCarretera().getCruceDest().getId();
			}
			else if(v.getEstado() == VehicleStatus.ARRIVED) s = "Arrived";
			break;
			case 2 : s = v.getItinerario(); break;
			case 3 : s = v.getGradCont(); break;
			case 4 : s = v.getVelocidadMax(); break;
			case 5 : s = v.getVelocidadAct(); break;
			case 6 : s = v.getContTotal(); break;
			case 7 : s = v.getDistTotalReco(); break;
			default : assert (false);
		}
		return s;
	}
	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		lista = map.getVehicles();
		fireTableStructureChanged();
	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		lista = map.getVehicles();
		fireTableStructureChanged();
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		lista = map.getVehicles();
		fireTableStructureChanged();
	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
	}

	@Override
	public void onError(String err) {
	}

	@Override
	public void onLoad(RoadMap map, List<Event> events, int time) {
		lista = map.getVehicles();
		fireTableStructureChanged();
	}

}
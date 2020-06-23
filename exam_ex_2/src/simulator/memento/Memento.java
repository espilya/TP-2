//package simulator.memento;
//
//import simulator.model.TrafficSimulator;
//
//public class Memento {
//	private String _backup;
//	private TrafficSimulator _simulation;
//
//	public Memento(TrafficSimulator simulation) {
//		this._simulation = simulation;
//		this._backup = simulation.backup();
//	}
//
//	public void restore() {
//		_simulation.restore(_backup);
//	}
//}
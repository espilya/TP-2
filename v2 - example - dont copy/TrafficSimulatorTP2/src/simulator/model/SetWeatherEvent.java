package simulator.model;

import java.io.Serializable;
import java.util.List;

import myExceptions.IncorrectValue;
import simulator.misc.Pair;

public class SetWeatherEvent extends Event implements Serializable{
	private List<Pair<String,Weather>> ws;
	public SetWeatherEvent(int time, List<Pair<String,Weather>> ws){
		super(time);
		if (ws == null)
			throw new IllegalArgumentException("Invalid type: " + ws);
		else this.ws = ws;
	}

	@Override
	void execute(RoadMap map) {
		for(Pair<String,Weather> w : ws) {
			try {
				map.getRoad(w.getFirst()).setWeather(w.getSecond());
			} catch (IncorrectValue e) {
				e.printStackTrace();
			}
		}
	}
	public String toString() {
		String str = "[";
		if(ws != null) {
			for(int i = 0; i < ws.size(); i++) {
				if(i == 0) str = str + "(" + ws.get(i).getFirst() + "," + ws.get(i).getSecond() + ")";
				else str = str + ",(" + ws.get(i).getFirst() + "," + ws.get(i).getSecond() + ")";
			}
		}
		return "Change Weather "+ str + "]";
	}

}

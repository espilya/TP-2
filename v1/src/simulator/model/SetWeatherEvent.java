package simulator.model;

public class SetWeatherEvent extends Event {

	public SetWeatherEvent(int time, List<Pair<String, Weather>> ws) {
		super(time);
		// ...
	}

	@Override
	void execute(RoadMap map) {
		// TODO Auto-generated method stub

	}

}

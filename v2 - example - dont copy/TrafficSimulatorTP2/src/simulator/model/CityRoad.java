package simulator.model;

import myExceptions.IncorrectValue;

public class CityRoad extends Road {
	
	private int x;
	
	CityRoad(String id, Junction srcJunc, Junction destJunc, int maxSpeed, int contLimit, int length, Weather weather)
			throws Exception {
		super(id, srcJunc, destJunc, maxSpeed, contLimit, length, weather);
	}

	@Override
	// TODO Auto-generated method stub
	void reduceTotalContamination() {
		switch(this.getWeather()) {
		case SUNNY:	 this.x = 2;
			break;
		case CLOUDY: this.x = 2;
			break;
		case RAINY:  this.x = 2;
			break;
		case WINDY:	 this.x = 10;
			break;
		case STORM:	 this.x = 10;
			break;
		}
		setTotalCO2CR(x);
	}

	@Override
	void updateSpeedLimit() {
		this.limActVel = this.getVelMax();
	}

	@Override
	int calculateVehicleSpeed(Vehicle v) throws IncorrectValue {
		//para cuadrar la perdida de decimal
		double aux = ((11.0-v.getGradCont())/11.0)*this.limActVel;
		aux = Math.ceil(aux);
		int velNuevo = (int) aux;
		v.setSpeed(velNuevo);
			return v.getVelocidadAct();
	}

	

}

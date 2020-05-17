package simulator.model;

import java.io.Serializable;

import myExceptions.IncorrectValue;

public class InterCityRoad extends Road implements Serializable{
	 private int x;
	 InterCityRoad(String id, Junction srcJunc, Junction destJunc, int maxSpeed, int contLimit, int length,
			Weather weather) throws Exception {
		super(id, srcJunc, destJunc, maxSpeed, contLimit, length, weather);
	}
	 //
	@Override
	void reduceTotalContamination() {
		switch(this.getWeather()) {
		case SUNNY:	 this.x = 2;
			break;
		case CLOUDY: this.x = 3;
			break;
		case RAINY:  this.x = 10;
			break;
		case WINDY:	 this.x = 15;
			break;
		case STORM:	 this.x = 20;
			break;
		}
		this.setTotalCO2InterCR(x);
	}

	@Override
	void updateSpeedLimit() {
		// TODO Auto-generated method stub
		if(this.getTotalCO2() > this.getAlarContExcesiva()) {
			this.limActVel = (int) (this.getVelMax()*0.5);
		}
		else this.limActVel = this.getVelMax();
	}

	@Override
	int calculateVehicleSpeed(Vehicle v) throws IncorrectValue {
			v.setSpeed(this.limActVel);
			
		if(this.getWeather() == Weather.STORM) {
			v.setSpeed((int) (this.limActVel*0.8));
		}
		return v.getVelocidadAct();
	}

}

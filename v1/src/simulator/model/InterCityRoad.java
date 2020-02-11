package simulator.model;

public class InterCityRoad extends Road {

	InterCityRoad(String id) {
		super(id);
		// TODO Auto-generated constructor stub
	}

	public void reduceTotalContamination() {

//		que reduce la contaminación total al valor:
//				(int)((100.0-x)/100.0)*tc)
//		donde tc es la contaminación total actual y x depende de las condiciones atmosféricas:
//		2 en caso de tiempo SUNNY (soleado), 3 si el tiempo es CLOUDY (nublado), 10 en
//		caso de que el tiempo sea RAINY (lluvioso), 15 si es WINDY (ventisca), y 20 si el
//		tiempo está STORM (tormentoso).

	}

	public void updateSpeedLimit() {

	}
//			si la contaminación total excede el límite de contaminaión, entonces
//		pone el límite de la velocidad al 50% de la velocidad máxima (es decir a
//		“(int)(maxSpeed*0.5)”). En otro caso pone el límite de la velocidad a la velocidad
//		máxima.

	public void calculateVehicleSpeed() {

	}

//			pone la velocidad del vehículo a la velocidad límite de la carretera.
//		Si el tiempo es STORM lo reduce en un 20% (es decir, “(int)(speedLimit*0.8)”).

}

package simulator.model;

import java.awt.List;

import org.json.JSONObject;


public class Vehicle extends SimulatedObject{
	
//			List<Junction>
	String id;
	private List iter;
	private int maxSpeed;
	private int actualSpeed;
	private VehicleStatus state;
	private Road actualRoad;
	private int localization;
	private int contClass; //0-10
	private int totalCont;
	private int totalDistance;
	
	Vehicle(String id, int maxSpeed, int contClass, List<Junction> itinerary) {
			super(id);
			if(maxSpeed < 0)
				throw new Exception(); //add new Exceptions
			else if(contClass < 0 || contClass > 10)
				throw new Exception();
			else if(maxSpeed < 0)
				throw new Exception();
			else if(itinerary.size < 2)
				throw new Exception();
			
			
			this.id = id;
			this.maxSpeed=maxSpeed;
			this.contClass=contClass;
			
			//iter=itinerary;
			
//			Además, no se debe compartir el argumento itinerary, sino que debes hacer una copia
//			 de dicho argumento en una lista de sólo lectura, para evitar modificarlo desde fuera:
			
//			 Collections.unmodifiableList(new ArrayList<>(itinerary));
			
			}


//	Vehicle(String id) {
//		super(id);
//		// TODO Auto-generated method stub
//	}

	
	
	void setSpeed(int s) {
	if(maxSpeed > s)
		if(s<0)
			throw new Exception();
		else
			actualSpeed = s;
	else
		actualSpeed = maxSpeed;
	} 
	
	void setContaminationClass(int c) { 
		if(c<0 || c>10)
			throw new Exception();
		contClass = c;	
	}
	
	void advance(int time) { 
		
		if(VehicleStatus != ***) {
			//a
			int a = localization + actualSpeed;
			int b;
			int oldLoc = localization;
			if(a>b)
				localization = b;
			else
				localization = a;
			
			//b
			int c = (contClass*oldLoc)/10;
		}
//		si el estado del vehículo no es Traveling, no hace nada. En otro caso:
//		
//		(a) se actualiza su localización al valor mínimo entre (i) la localización actual más
//		la velocidad actual; y (ii) la longitud de la carretera por la que está circulando.
//		
//		(b) calcula la contaminación c producida utilizando la fórmula c = (l∗f)/10, donde
//		f es el factor de contaminación y l es la distancia recorrida en ese paso de la
//		simulación, es decir, la nueva localización menos la localización previa. Después
//		añade c a la contaminación total del vehículo y también añade c al grado de
//		contaminación de la carretera actual, invocando al método correspondiente de
//		la clase Road.
//		
//		(c) si la localización actual (es decir la nueva) es igual a la longitud de la carretera,
//		el vehículo entra en la cola del cruce correspondiente (llamando a un método
//		de la clase Junction). Recuerda que debes modificar el estado del vehículo.

	}
	
	
	void moveToNextRoad() {
//		mueve el vehículo a la siguiente carretera. Este proceso
//		se hace saliendo de la carretera actual y entrando a la siguiente carretera de su
//		itinerario, en la localización 0. Para salir y entrar de las carreteras, debes utilizar el
//		método correspondiente de la clase Road. Para encontrar la siguiente carretera, el
//		vehículo debe preguntar al cruce en el cual está esperando (o al primero del itinerario
//		en caso de que el estado del vehículo sea Pending) mediante una invocación al método
//		correspondiente de la clase Junction. Observa que la primera vez que el vehículo llama
//		a este método, el vehículo no sale de ninguna carretera ya que el vehículo todavía
//		no ha empezado a circular y, que cuando el vehículo abandona el último cruce de su
//		itinerario, entonces no puede entrar ya a ninguna carretera dado que ha finalizado
//		su recorrido – no olvides modificar el estado del vehículo.
		
//		Este método debe lanzar una excepción si el estado de los vehículos no es Pending o
//		Waiting. Recuerda que el itinerario es una lista de cruces de sólo lectura y
//		por tanto no puedes modificarla. Es conveniente guardar un índice que indique
//		el último cruce del itinerario que ha sido visitado por el vehículo.

	}
	
	
	public JSONObject report() {
		
		return new JSONObject();
	}
	
	

}

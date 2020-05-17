package simulator.model;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONObject;

import myExceptions.IncorrectValue;

public class Vehicle extends SimulatedObject implements Serializable{
    private List<Junction> itinerario;
    private int velocidadMax, velocidadAct, localizacion, gradCont, contTotal, distTotalReco;
    private VehicleStatus estado;
    private Road carretera;
    private int cruceActual; 
    private int c; //contaminación 
   
    
Vehicle(String id, int maxSpeed, int contClass,List<Junction> itinerary) throws IncorrectValue{
    	super(id);
    	if(maxSpeed <0 ) throw new IncorrectValue("La velocidad debe ser positiva");
    	else this.velocidadMax = maxSpeed;
    	if(itinerary.size() <= 1)throw new IncorrectValue("La longitud de la lista debe ser al menos 2");
    	 this.itinerario = Collections.unmodifiableList(new ArrayList<>(itinerary));
    	if(contClass < 0 || contClass > 10) throw new IncorrectValue("El grado de contaminacion debe estar entre 0 y 10");
    	this.gradCont = contClass;   
        this.velocidadAct = 0;
        this.estado = VehicleStatus.PENDING;
        this.carretera = null;
        this.localizacion = 0;
        this.contTotal = 0;
        this.distTotalReco = 0;
    }

    public void setItinerario(List<Junction> itinerario) {
    	this.itinerario = itinerario;
    }

    public void setVelocidadMax(int velocidadMax) {
    	this.velocidadMax = velocidadMax;
    }

    public void setLocalizacion(int localizacion) {
    	this.localizacion = localizacion;
    }
    
    public void setContTotal(int contTotal) {
    	this.contTotal = contTotal;
    }

    public void setDistTotalReco(int distTotalReco) {
    	this.distTotalReco = distTotalReco;
   	}

    public void setEstado(VehicleStatus estado) {
    	this.estado = estado;
    }

    public void setCarretera(Road carretera) {
    	this.carretera = carretera;
    }

    void setSpeed(int s) throws IncorrectValue {
    	if(s < 0) throw new IncorrectValue("La velocidad debe ser positiva");
    	else if(s < this.velocidadMax) this.velocidadAct = s;
    	else this.velocidadAct = this.velocidadMax;
    	}

    void setContaminationClass(int c) throws IncorrectValue {
    	if(c < 0 || c > 10) throw new IncorrectValue("El grado de contaminación debe estar entre 0 y 10");
    	else this.gradCont = c;
    }
    
    //si el estado del vehículo no es Traveling, no hace nada. 
    void advance(int time) throws IncorrectValue {
    	if(this.estado == VehicleStatus.TRAVELING) {
    		int locPrevia = this.localizacion;
    		this.distTotalReco +=  this.velocidadAct;
    		if(this.localizacion + this.velocidadAct < this.carretera.getLongitud())
    			this.localizacion = this.localizacion + this.velocidadAct;
    		else this.localizacion = this.carretera.getLongitud();
    		
    		
    		this.c = (this.localizacion - locPrevia) * this.gradCont;
    		//this.c /= 10;
    		this.contTotal += c;
    		this.carretera.addContamination(c);
    		
    		if(this.localizacion == this.carretera.getLongitud()) {
    		//	this.carretera.calculateVehicleSpeed(this);
    			int DistanciaReal=0;
    			for(int i=0; i<this.cruceActual + 1; i++) DistanciaReal += this.carretera.getLongitud();
    			this.distTotalReco = DistanciaReal;
    			Junction posterior = this.itinerario.get(this.cruceActual + 1);
    			if(posterior == null) throw new IncorrectValue("No hay carretera siguiente en el itinerario");
    			else {
    				
    			//this.carretera.exit(this);
    	   		this.velocidadAct = 0;
    			posterior.enter(this);
    			this.estado = VehicleStatus.WAITING;
    			this.cruceActual++;
    			}
    		}
    	}
    }
    
    //Mueve el vehiculo a la siguiente carretera del itinerario
    void moveToNextRoad() throws IncorrectValue {
    	if(this.estado == VehicleStatus.PENDING || this.estado == VehicleStatus.WAITING) {
    	if(this.cruceActual == this.itinerario.size()-1) {
    	this.estado = VehicleStatus.ARRIVED;
    	this.velocidadAct = 0;
    	this.localizacion = 0;
    	this.carretera.exit(this);
    	}
    	else {
		if(this.estado == VehicleStatus.WAITING) this.carretera.exit(this);
		
    		Junction actual = this.itinerario.get(this.cruceActual);
    		Junction destino = this.itinerario.get(this.cruceActual + 1);
    		//System.out.println(actual);
    		//System.out.println(destino);
    		
    	this.carretera = actual.roadTo(destino);
    	//System.out.println(carretera);
    	if(this.carretera == null) throw new IncorrectValue("No se puede");
    	else {
    	this.velocidadAct = 0;
        this.localizacion = 0;
    	this.carretera.enter(this);
    	this.estado = VehicleStatus.TRAVELING;
    	}
    }
    	
    	}
    	 else throw new IncorrectValue();
    }

	@Override
	public JSONObject report() {
		
		JSONObject jo = new JSONObject();
		jo.put("id", this._id);
		jo.put("speed", this.velocidadAct);
		jo.put("distance", this.distTotalReco);
		jo.put("co2", this.contTotal);
		jo.put("class", this.gradCont);
		jo.put("status", this.estado);
		if(this.estado != VehicleStatus.PENDING && this.estado != VehicleStatus.ARRIVED) {
		jo.put("road", this.carretera);
		jo.put("location", this.localizacion);
		}
		
		return jo;
	}
	public int getVelocidadMax() {
		return this.velocidadMax;
	}
	public int getVelocidadAct() {
		return this.velocidadAct;
	}
	public int getLocalizacion() {
		return this.localizacion;
	}
	public int getGradCont() {
		return this.gradCont;
	}
	public int getContTotal() {
		return this.contTotal;
	}
	public int getDistTotalReco() {
		return this.distTotalReco;
	}
	public VehicleStatus getEstado(){
		return this.estado;
	}
	public List<Junction> getItinerario(){
		return this.itinerario;
	}
	public Road getCarretera() {
		return this.carretera;
		
	}

}

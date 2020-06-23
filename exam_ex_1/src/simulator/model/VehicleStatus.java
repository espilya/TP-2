package simulator.model;

import java.io.Serializable;

public enum VehicleStatus implements Serializable {
	PENDING, TRAVELING, WAITING, ARRIVED;
}

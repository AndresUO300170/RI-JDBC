package uo.ri.cws.application.persistence;

import uo.ri.cws.application.persistence.mechanic.MechanicGateway;

public interface PersistenceFactory {
		 
		MechanicGateway forMechanic();
		//WorkOrderGateway forWorkOrder();
		//InvoiceGateway forInvoice();
		//InterventionGateway forIntervention();
		
//		SparePartGateway forSparePart();
//		SubstitutionGateway forSubstitutionsGateway();
//		VehicleGateway forVehicle();
//		VehicleTypeGateway forVehicleType();

}

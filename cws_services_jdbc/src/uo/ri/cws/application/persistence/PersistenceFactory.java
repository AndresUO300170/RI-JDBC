package uo.ri.cws.application.persistence;

import uo.ri.cws.application.persistence.invoice.InvoicingWorkOrderGateway;
import uo.ri.cws.application.persistence.invoice.WorkOrderGateway;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway;

public interface PersistenceFactory {
		 
		MechanicGateway forMechanic();
		WorkOrderGateway forWorkOrder();//
		InvoicingWorkOrderGateway forInvoice();
		//InterventionGateway forIntervention();
		
//		SparePartGateway forSparePart();
//		SubstitutionGateway forSubstitutionsGateway();
//		VehicleGateway forVehicle();
//		VehicleTypeGateway forVehicleType();

}

package uo.ri.cws.application.persistence;

import uo.ri.cws.application.persistence.invoice.InvoicingWorkOrderGateway;
import uo.ri.cws.application.persistence.invoice.WorkOrderGateway;
import uo.ri.cws.application.persistence.invoice.impl.InvoicingWorkOrderGatewayImpl;
import uo.ri.cws.application.persistence.invoice.impl.WorkOrderGatewayImpl;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway;
import uo.ri.cws.application.persistence.mechanic.impl.MechanicGatewayImpl;

public class PersistenceFactoryImpl implements PersistenceFactory{

    @Override
    public MechanicGateway forMechanic() {
        return new MechanicGatewayImpl();
    }

    @Override
    public WorkOrderGateway forWorkOrder() {
        return new WorkOrderGatewayImpl();
    }

    @Override
    public InvoicingWorkOrderGateway forInvoice() {
        return new InvoicingWorkOrderGatewayImpl();
    }
}

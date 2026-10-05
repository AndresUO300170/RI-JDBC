package uo.ri.cws.application.service;

import uo.ri.cws.application.service.client.ClientCrudService;
import uo.ri.cws.application.service.client.ClientHistoryService;
import uo.ri.cws.application.service.invoice.InvoicingService;
import uo.ri.cws.application.service.invoice.create.InvoiceServiceImpl;
import uo.ri.cws.application.service.mechanic.MechanicCrudService;
import uo.ri.cws.application.service.mechanic.crud.service.MechanicCrudServiceImpl;
import uo.ri.cws.application.service.spare.SparePartCrudService;
import uo.ri.cws.application.service.vehicle.VehicleCrudService;
import uo.ri.cws.application.service.vehicletype.VehicleTypeCrudService;
import uo.ri.cws.application.service.workorder.CloseWorkOrderService;
import uo.ri.cws.application.service.workorder.ViewAssignedWorkOrdersService;
import uo.ri.cws.application.service.workorder.WorkOrderCrudService;

public class ServiceFactoryImpl implements ServiceFactory {

    @Override
    public VehicleCrudService forVehicleCrudService() {
        return null;
    }

    @Override
    public ClientCrudService forClientCrudService() {
        return null;
    }

    @Override
    public ClientHistoryService forClientHistoryService() {
        return null;
    }

    @Override
    public WorkOrderCrudService forWorkOrderCrudService() {
        return null;
    }

    @Override
    public CloseWorkOrderService forClosingWorkOrder() {
        return null;
    }

    @Override
    public ViewAssignedWorkOrdersService forViewAssignedWorkOrdersService() {
        return null;
    }

    @Override
    public MechanicCrudService forMechanicCrudService() {
        return new MechanicCrudServiceImpl();
    }

    @Override
    public VehicleTypeCrudService forVehicleTypeCrudService() {
        return null;
    }

    @Override
    public SparePartCrudService forSparePartCrudService() {
        return null;
    }

    @Override
    public InvoicingService forCreateInvoiceService() {
        return new InvoiceServiceImpl();
    }
}

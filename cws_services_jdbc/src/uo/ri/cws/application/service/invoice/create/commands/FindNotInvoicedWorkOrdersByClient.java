package uo.ri.cws.application.service.invoice.create.commands;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.invoice.InvoicingWorkOrderGateway;
import uo.ri.cws.application.persistence.invoice.InvoicingWorkOrderGateway.InvoicingWorkOrderRecord;
import uo.ri.cws.application.service.invoice.InvoicingService.InvoicingWorkOrderDto;
import uo.ri.cws.application.service.invoice.create.InvoiceWorkOrderDtoAssembler;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.cws.application.persistence.util.jdbc.Jdbc;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class FindNotInvoicedWorkOrdersByClient {

    private InvoicingWorkOrderGateway gateway = Factories.persistence.forInvoice();
    private InvoiceWorkOrderDtoAssembler assembler = new InvoiceWorkOrderDtoAssembler();
    private String nif;

    public FindNotInvoicedWorkOrdersByClient(String nif) {
        ArgumentChecks.isNotBlank(nif, "Invalid nif");
        this.nif = nif;
    }

    public List<InvoicingWorkOrderDto> execute(){
        try (Connection c = Jdbc.createThreadConnection()){
            c.setAutoCommit(false);
            try{
                List<InvoicingWorkOrderDto> nIWO = findNotInvoicedWorkOrdersByClient(nif);
                c.commit();
                return nIWO;
            }catch(Exception e){
                c.rollback();
                throw e;
            }
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<InvoicingWorkOrderDto> findNotInvoicedWorkOrdersByClient(String nif){
        List<InvoicingWorkOrderRecord> records = gateway.findNotInvoicedWorkOrdersByClient(nif);
        List<InvoicingWorkOrderDto> notInvoicingWorkOrders = new LinkedList<>();
        for(InvoicingWorkOrderRecord record: records)
            notInvoicingWorkOrders.add(assembler.toDto(Optional.of(record)).get());
        return notInvoicingWorkOrders;
    }

}

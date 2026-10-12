package uo.ri.cws.application.service.invoice.create.commands;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.invoice.InvoicingWorkOrderGateway;
import uo.ri.cws.application.persistence.invoice.WorkOrderGateway;
import uo.ri.cws.application.persistence.util.jdbc.Jdbc;
import uo.ri.cws.application.service.invoice.InvoicingService.InvoiceDto;
import uo.ri.cws.application.service.invoice.create.InvoiceWorkOrderDtoAssembler;
import uo.ri.cws.application.service.invoice.create.WorkOrderDtoAssembler;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessException;
import uo.ri.util.math.Rounds;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class InvoiceWorkOrder {

    private final List<String> workOrderIds;
    private final InvoicingWorkOrderGateway iGateway = Factories.persistence.forInvoice();
    private final InvoiceWorkOrderDtoAssembler iAssembler = new InvoiceWorkOrderDtoAssembler();
    private final WorkOrderGateway wGateway = Factories.persistence.forWorkOrder();
    private final WorkOrderDtoAssembler wAssembler = new WorkOrderDtoAssembler();

    public InvoiceWorkOrder(List<String> workOrderIds) {
        ArgumentChecks.isNotNull(workOrderIds, "The list of workorder ids is null");
        this.workOrderIds = workOrderIds;
    }

    public InvoiceDto execute() throws SQLException, BusinessException {
        try (Connection conn = Jdbc.createThreadConnection()) {
            conn.setAutoCommit(false);
            try{
                if (!checkWorkOrdersExist(workOrderIds))
                    throw new BusinessException("Workorder does not exist");
                if (!checkWorkOrdersApproved(workOrderIds))
                    throw new BusinessException("Workorder is not approved yet");
                long numberInvoice = generateInvoiceNumber();
                LocalDate dateInvoice = LocalDate.now();
                double amount = calculateTotalInvoice(workOrderIds); // vat not
                // included
                double vat = vatPercentage(dateInvoice);
                double vatAmount = amount * ( vat / 100); // vat amount
                double total = amount * vatAmount; // vat included
                total = Rounds.toCents(total);
                String idInvoice = createInvoice(numberInvoice, dateInvoice, vatAmount, total);

                InvoiceDto dto = new InvoiceDto();
                dto.number = numberInvoice;
                dto.date = dateInvoice;
                dto.subtotal = BigDecimal.valueOf(amount);
                dto.vatPercentage = BigDecimal.valueOf(vat);
                dto.total = BigDecimal.valueOf(total);

                linkWorkordersToInvoice(idInvoice, workOrderIds);
                markWorkOrderAsInvoiced(workOrderIds);
                updateVersion(workOrderIds);
                updateTimeVersion(workOrderIds);

                conn.commit();
                return dto;
            }catch(Exception e){
                conn.rollback();
                throw e;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /*
     * checks whether every work order exist
     */
    private boolean checkWorkOrdersExist(List<String> workOrderIDS) throws SQLException {
        return wGateway.checkWorkOrdersExist(workOrderIDS);
    }

    /*
     * checks whether every work order id is APPROVED
     */
    private boolean checkWorkOrdersApproved(List<String> workOrderIDs) throws SQLException {
        return wGateway.checkWorkOrdersApproved(workOrderIDs);
    }

    /*
     * Generates next invoice number (not to be confused with the inner id)
     */
    private long generateInvoiceNumber() throws SQLException {
        return iGateway.generateInvoiceNumber(); // Si no hay facturas previas, empezamos desde 1
    }

    /*
     * Compute total amount of the invoice (as the total of individual work
     * orders' amount
     */
    private double calculateTotalInvoice(List<String> workOrderIDs) throws SQLException {
        return wGateway.calculateTotalInvoice(workOrderIDs);
    }

    /*
     * returns vat percentage
     */
    private double vatPercentage(LocalDate d) {
        return LocalDate.parse("2012-07-01").isBefore(d) ? 21.0 : 18.0;
    }

    /*
     * Creates the invoice in the database; returns the id
     */
    private String createInvoice(long numberInvoice, LocalDate dateInvoice, double vat, double total)
            throws SQLException {
        String idInvoice = UUID.randomUUID().toString();
        InvoiceDto invoice = new InvoiceDto();
        invoice.id = idInvoice;
        invoice.number = numberInvoice;
        invoice.date = dateInvoice;
        invoice.vatPercentage = BigDecimal.valueOf(vat);
        invoice.subtotal = BigDecimal.valueOf(total);
        invoice.state = "ISSUED";
        invoice.version = 1L;
        iGateway.add(iAssembler.toRecord(invoice));
        return idInvoice;
    }

    /*
     * Set the invoice number field in work order table to the invoice number
     * generated
     */
    private void linkWorkordersToInvoice(String invoiceId, List<String> workOrderIDs) throws SQLException {
        wGateway.linkWorkordersToInvoice(invoiceId, workOrderIDs);
    }

    /*
     * Sets status to INVOICED for every workorder
     */
    private void markWorkOrderAsInvoiced(List<String> ids) throws SQLException {
        wGateway.markWorkOrderAsInvoiced(ids);
    }

    private void updateVersion(List<String> workOrderIds) throws SQLException {
        wGateway.updateVersion(workOrderIds);
    }

    private void updateTimeVersion(List<String> workOrderIds) throws SQLException {
        wGateway.updateTimeVersion(workOrderIds);
    }

}

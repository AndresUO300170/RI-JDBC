package uo.ri.cws.application.persistence.invoice.impl;

import uo.ri.cws.application.persistence.invoice.InvoicingWorkOrderGateway;
import uo.ri.cws.application.persistence.util.jdbc.Jdbc;
import uo.ri.cws.application.persistence.util.jdbc.Queries;
import uo.ri.util.exception.PersistenceException;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class InvoicingWorkOrderGatewayImpl implements InvoicingWorkOrderGateway {

    @Override
    public long generateInvoiceNumber() {
        Connection c = Jdbc.getCurrentConnection();
        if (c == null)
            throw new PersistenceException(new IllegalStateException("No hay una conexión activa"));
        try{
            try(PreparedStatement pst = c.prepareStatement(Queries.getSQLSentence
                    ("TINVOICES_SELECT_LAST_NUMBER"))){
                try (ResultSet rs = pst.executeQuery()) {
                    if (rs.next())
                        return rs.getLong(1) + 1;
                }
            }
        }catch(SQLException e){
            throw new PersistenceException(e);
        }
        return 0;
    }

    @Override
    public List<InvoicingWorkOrderRecord> findNotInvoicedWorkOrdersByClient(String nif) {
        List<InvoicingWorkOrderRecord> records = new LinkedList<>();
        Connection c = Jdbc.getCurrentConnection();
        if (c == null)
            throw new PersistenceException(new IllegalStateException("No hay una conexión activa"));
        try {
            try (PreparedStatement pst = c
                    .prepareStatement(Queries.getSQLSentence("TWORKORDERS_FIND_NOT_INVOICED"))) {
                pst.setString(1, nif);
                try (ResultSet rs = pst.executeQuery();) {
                    while (rs.next()) {
                        InvoicingWorkOrderRecord record = new InvoicingWorkOrderRecord();
                        record.id = rs.getString(1);
                        record.description = rs.getString(2);
                        record.date = (java.time.LocalDate) rs.getObject(3);
                        record.state = rs.getString(4);
                        record.amount = rs.getBigDecimal(5);
                        records.add(record);
                    }
                }
            }
        } catch (SQLException e) {
            throw new PersistenceException(e);

        }
        return records;
    }

    @Override
    public void add(InvoicingWorkOrderRecord invoiceRecord) throws PersistenceException {
        Connection c = Jdbc.getCurrentConnection();
        if (c == null)
            throw new PersistenceException(new IllegalStateException("No hay una conexión activa"));
        try {
            try (PreparedStatement pst = c.prepareStatement(Queries.getSQLSentence("TINVOICES_INSERT"))) {
                pst.setString(1, invoiceRecord.id);
                pst.setLong(2, invoiceRecord.number);
                pst.setObject(3, invoiceRecord.date);
                pst.setBigDecimal(4, invoiceRecord.vat);
                pst.setBigDecimal(5, invoiceRecord.amount);
                pst.setString(6, invoiceRecord.state);
                pst.setLong(7, invoiceRecord.version);
                pst.setTimestamp(8, Timestamp.valueOf(invoiceRecord.createdAt));
                pst.setTimestamp(9, Timestamp.valueOf(invoiceRecord.updatedAt));
                pst.setString(10, invoiceRecord.entityState);
                pst.executeUpdate();
            }
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public void remove(String id) throws PersistenceException {

    }

    @Override
    public void update(InvoicingWorkOrderRecord invoiceRecord) throws PersistenceException {

    }

    @Override
    public Optional<InvoicingWorkOrderRecord> findById(String id) throws PersistenceException {
        return Optional.empty();
    }

    @Override
    public List<InvoicingWorkOrderRecord> findAll() throws PersistenceException {
        return List.of();
    }
}

package uo.ri.cws.application.persistence.invoice.impl;

import uo.ri.cws.application.persistence.invoice.WorkOrderGateway;
import uo.ri.cws.application.persistence.util.jdbc.Jdbc;
import uo.ri.cws.application.persistence.util.jdbc.Queries;
import uo.ri.util.exception.PersistenceException;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class WorkOrderGatewayImpl implements WorkOrderGateway {

    @Override
    public boolean checkWorkOrdersExist(List<String> workOrderIDS) {
        Connection c = Jdbc.getCurrentConnection();
        try{
            try (PreparedStatement pst = c.prepareStatement(Queries.getSQLSentence(
                    "TWORKORDERS_FIND_ID_BY_ID"))) {
                for (String workOrderID : workOrderIDS) {
                    pst.setString(1, workOrderID);
                    try (ResultSet rs = pst.executeQuery()) {
                        if (!rs.next())
                            return false; // Si no encuentra la orden de trabajo
                    }
                }
            }
            return true;
        }catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public boolean checkWorkOrdersApproved(List<String> workOrderIDs) {
        Connection connection = Jdbc.getCurrentConnection();
        try{
            try (PreparedStatement pst = connection.prepareStatement(
                    Queries.getSQLSentence("TWORKORDERS_FIND_STATUS_BY_ID"))) {
                for (String id : workOrderIDs) {
                    pst.setString(1, id);
                    try (ResultSet rs = pst.executeQuery()) {
                        if (rs.next()) {
                            String status = rs.getString("state");
                            if (!"APPROVED".equalsIgnoreCase(status))
                                return false;
                        }
                    }
                }
            }
            return true;
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public double calculateTotalInvoice(List<String> workOrderIDs) {
        Connection connection = Jdbc.getCurrentConnection();
        double total = 0.0;
        try{
            try (PreparedStatement pst = connection.prepareStatement(
                    Queries.getSQLSentence("TWORKORDERS_FIND_AMOUNT_BY_ID"))) {
                for (String id : workOrderIDs) {
                    pst.setString(1, id);
                    try (ResultSet rs = pst.executeQuery()) {
                        if (rs.next())
                            total += rs.getDouble("amount");
                    }
                }
            }
            return total;
        }catch(SQLException e){
            throw new PersistenceException(e);
        }
    }

    @Override
    public void linkWorkordersToInvoice(String invoiceId, List<String> workOrderIDs) {
        Connection connection = Jdbc.getCurrentConnection();
        try{
            try (PreparedStatement pst = connection.prepareStatement(
                    Queries.getSQLSentence("TWORKORDERS_UPDATE_INVOICEID"))) {
                for (String id : workOrderIDs) {
                    pst.setString(1, invoiceId);
                    pst.setString(2, id);
                    pst.executeUpdate();
                }
            }
        }catch(SQLException e){
            throw new PersistenceException(e);
        }
    }

    @Override
    public void markWorkOrderAsInvoiced(List<String> ids) {
        Connection connection = Jdbc.getCurrentConnection();
        try{
            try (PreparedStatement pst = connection.prepareStatement(
                    Queries.getSQLSentence("TWORKORDERS_UPDATE_STATE"))) {
                for (String id : ids) {
                    pst.setString(1, id);
                    pst.executeUpdate();
                }
            }
        }catch(SQLException e){
            throw new PersistenceException(e);
        }
    }

    @Override
    public void updateVersion(List<String> workOrderIds) {
        Connection c = Jdbc.getCurrentConnection();
        try{
            try (PreparedStatement pst = c.prepareStatement(
                    Queries.getSQLSentence("TWORKORDERS_UPDATE_VERSION"))) {
                for (String workOrderID : workOrderIds) {
                    pst.setString(1, workOrderID);
                    pst.executeUpdate();
                }
            }
        }catch(SQLException e){
            throw new PersistenceException(e);
        }
    }

    @Override
    public void updateTimeVersion(List<String> workOrderIds) {
        Connection c = Jdbc.getCurrentConnection();
        try{
            try (PreparedStatement pst = c.prepareStatement(
                    Queries.getSQLSentence("TWORKORDERS_UPDATE_TIMESTAMP"))) {
                for (String workOrderID : workOrderIds) {
                    pst.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
                    pst.setString(2, workOrderID);
                    pst.executeUpdate();
                }
            }
        }catch(SQLException e){
            throw new PersistenceException(e);
        }
    }

    @Override
    public void add(WorkOrderRecord workOrderRecord) throws PersistenceException {

    }

    @Override
    public void remove(String id) throws PersistenceException {

    }

    @Override
    public void update(WorkOrderRecord workOrderRecord) throws PersistenceException {

    }

    @Override
    public Optional<WorkOrderRecord> findById(String id) throws PersistenceException {
        return Optional.empty();
    }

    @Override
    public List<WorkOrderRecord> findAll() throws PersistenceException {
        return List.of();
    }
}

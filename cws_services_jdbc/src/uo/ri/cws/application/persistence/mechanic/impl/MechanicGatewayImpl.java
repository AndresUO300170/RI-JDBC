package uo.ri.cws.application.persistence.mechanic.impl;

import uo.ri.cws.application.persistence.mechanic.MechanicGateway;
import uo.ri.util.exception.PersistenceException;
import uo.ri.cws.application.persistence.util.jdbc.Jdbc;
import uo.ri.cws.application.persistence.util.jdbc.Queries;

import java.sql.*;
import java.util.List;
import java.util.Optional;

public class MechanicGatewayImpl implements MechanicGateway {

    private MechanicRecordAssembler assembler = new MechanicRecordAssembler();

    @Override
    public Optional<MechanicRecord> findByNif(String nif) throws PersistenceException {
        try (Connection c = Jdbc.getCurrentConnection()) {
            try (PreparedStatement pst = c.prepareStatement(Queries.getSQLSentence("TMECHANICS_FINDBYNIF"))) {
                pst.setString(1, nif);
                try (ResultSet rs = pst.executeQuery()) {
                    if (rs.next())
                        return Optional.of(assembler.toRecord(rs));
                }
            }
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
        return Optional.empty();
    }

    @Override
    public void add(MechanicRecord mechanic) throws PersistenceException {
        try (Connection c = Jdbc.getCurrentConnection();) {
            try (PreparedStatement pst = c.prepareStatement(Queries.getSQLSentence("TMECHANICS_ADD"))) {
                pst.setString(1, mechanic.id);
                pst.setString(2, mechanic.nif);
                pst.setString(3, mechanic.name);
                pst.setString(4, mechanic.surname);
                pst.setLong(5, mechanic.version);
                pst.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
                pst.setTimestamp(7, new Timestamp(System.currentTimeMillis()));
                pst.setString(8, "ENABLED");
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
    public void update(MechanicRecord mechanicRecord) throws PersistenceException {

    }

    @Override
    public Optional<MechanicRecord> findById(String id) throws PersistenceException {
        return Optional.empty();
    }

    @Override
    public List<MechanicRecord> findAll() throws PersistenceException {
        return List.of();
    }

}

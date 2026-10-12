package uo.ri.cws.application.service.mechanic.crud.commands;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.cws.application.persistence.util.jdbc.Jdbc;
import uo.ri.util.assertion.BusinessChecks;
import uo.ri.util.exception.BusinessException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteMechanic {

    private String idMechanic;
    private MechanicGateway mg = Factories.persistence.forMechanic();

    public DeleteMechanic(String idMechanic){
        ArgumentChecks.isNotBlank(idMechanic, "Invalid id of the mechanic");
        this.idMechanic = idMechanic;
    }

    public void execute(){
        // Process
        try (Connection c = Jdbc.createThreadConnection();) {
            c.setAutoCommit(false);
            try{
                assertMechanicExists(idMechanic);
                deleteMechanic(idMechanic);
                c.commit();
            } catch (Exception e) {
                c.rollback();
                throw e;
            }
        } catch (SQLException | BusinessException e) {
            throw new RuntimeException(e);
        }
    }

    private void deleteMechanic(String id){
        mg.remove(id);
    }

    private void assertMechanicExists(String id) throws BusinessException {
        BusinessChecks.exists(mg.findById(id), "The mechanic does not exist");
    }
}

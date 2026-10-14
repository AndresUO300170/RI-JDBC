package uo.ri.cws.application.service.mechanic.crud.commands;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.command.Command;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.cws.application.persistence.util.jdbc.Jdbc;
import uo.ri.util.assertion.BusinessChecks;
import uo.ri.util.exception.BusinessException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteMechanic implements Command<Void> {

    private String idMechanic;
    private MechanicGateway mg = Factories.persistence.forMechanic();

    public DeleteMechanic(String idMechanic){
        ArgumentChecks.isNotBlank(idMechanic, "Invalid id of the mechanic");
        this.idMechanic = idMechanic;
    }

    public Void execute() throws BusinessException {
        // Process
        assertMechanicExists(idMechanic);
        deleteMechanic(idMechanic);
        return null;
    }

    private void deleteMechanic(String id){
        mg.remove(id);
    }

    private void assertMechanicExists(String id) throws BusinessException {
        BusinessChecks.exists(mg.findById(id), "The mechanic does not exist");
    }
}

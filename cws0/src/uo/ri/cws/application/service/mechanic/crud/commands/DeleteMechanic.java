package uo.ri.cws.application.service.mechanic.crud.commands;

import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.jdbc.Jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteMechanic {

    private static final String TMECHANICS_DELETE = "DELETE FROM TMECHANICS "
            + "WHERE ID = ?";

    private String idMechanic;

    public DeleteMechanic(String idMechanic){
        ArgumentChecks.isNotBlank(idMechanic, "Invalid id of the mechanic");
        this.idMechanic = idMechanic;
    }

    public void execute(){
        // Process
        try (Connection c = Jdbc.createThreadConnection();) {
            try (PreparedStatement pst = c
                    .prepareStatement(TMECHANICS_DELETE)) {
                pst.setString(1, idMechanic);
                pst.executeUpdate();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

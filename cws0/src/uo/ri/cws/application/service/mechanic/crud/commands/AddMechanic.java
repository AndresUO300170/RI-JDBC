package uo.ri.cws.application.service.mechanic.crud.commands;

import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.jdbc.Jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.UUID;

public class AddMechanic {

    private static final String TMECHANICS_ADD = "insert into TMechanics"
            + "(id, nif, name, surname, version, "
            + "createdAt, updatedAt, entityState) "
            + "values (?, ?, ?, ?, ?, ?, ?, ?)";

    private MechanicDto dto;

    public AddMechanic(MechanicDto dto){
        ArgumentChecks.isNotNull(dto, "Invalid mechanic dto");
        ArgumentChecks.isNotBlank(dto.nif, "Invalid nid");
        ArgumentChecks.isNotBlank(dto.name, "Invalid name");
        ArgumentChecks.isNotBlank(dto.surname, "Invalid surname");
        this.dto = dto;
        this.dto.nif = dto.nif;
        this.dto.name = dto.name;
        this.dto.surname = dto.surname;
        this.dto.id = UUID.randomUUID().toString();
        this.dto.version = 1;

        this.dto = dto;
    }

    public MechanicDto execute(){
        try (Connection c = Jdbc.createThreadConnection();) {
            try (PreparedStatement pst = c.prepareStatement(TMECHANICS_ADD)) {
                pst.setString(1, dto.id);
                pst.setString(2, dto.nif);
                pst.setString(3, dto.name);
                pst.setString(4, dto.surname);
                pst.setLong(5, dto.version);
                pst.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
                pst.setTimestamp(7, new Timestamp(System.currentTimeMillis()));
                pst.setString(8, "ENABLED");

                pst.executeUpdate();

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return dto;
    }
}

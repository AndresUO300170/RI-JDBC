package uo.ri.cws.application.service.mechanic.crud.commands;

import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessException;
import uo.ri.util.jdbc.Jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class FindMechanicById {

    private static final String TMECHANICS_FINDBYID =
            "select * from TMechanics where id = ?";

    private String id;

    public FindMechanicById(String id){
        ArgumentChecks.isNotBlank(id, "Invalid id");
        this.id = id;
    }

    public Optional<MechanicDto> execute(){
        Optional<MechanicDto> result = Optional.empty();
        try (Connection c = Jdbc.createThreadConnection()) {
            try (PreparedStatement pst = c
                    .prepareStatement(TMECHANICS_FINDBYID)) {
                pst.setString(1, id);
                try (ResultSet rs = pst.executeQuery()) {
                    if (rs.next()) {
                        MechanicDto m = new MechanicDto();

                        m.id = rs.getString(1);
                        m.name = rs.getString(2);
                        m.surname = rs.getString(3);
                        m.nif = rs.getString(4);
                        m.version = rs.getLong(5);

                        result = Optional.of(m);
                    }
                    else
                        throw new BusinessException("Mechanic does not exist");
                }
            }
        } catch (SQLException | BusinessException e) {
            throw new RuntimeException(e);
        }
        return result;
    }
}

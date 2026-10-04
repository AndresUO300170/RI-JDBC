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

public class FindMechanicByNif {

    private static final String TMECHANICS_FINDBYNIF =
            "SELECT ID, NAME, SURNAME, nif, VERSION FROM TMECHANICS "
                    + "WHERE NIF = ?";

    private String nif;

    public FindMechanicByNif(String nif) {
        ArgumentChecks.isNotBlank(nif, "Invalid nif");
        this.nif = nif;
    }

    public Optional<MechanicDto> execute(){
        Optional<MechanicDto> result = Optional.empty();
        try (Connection c = Jdbc.createThreadConnection()) {
            try (PreparedStatement pst = c
                    .prepareStatement(TMECHANICS_FINDBYNIF)) {
                pst.setString(1, nif);
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
